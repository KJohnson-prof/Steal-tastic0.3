package com.example.steal_tastic.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.steal_tastic.Post
import com.example.steal_tastic.PostAdapter
import com.example.steal_tastic.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class SearchFragment : Fragment() {

    lateinit var adapter: PostAdapter
    lateinit var  SearchRecyclerView: RecyclerView
    var allPosts: MutableList<Post> = mutableListOf()
    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSearch).setOnClickListener {
            val searchFor = view.findViewById<EditText>(R.id.tvSearch).text.toString()
            queryPosts(searchFor)
        }

        SearchRecyclerView = view.findViewById(R.id.SearchRecyclerView)

        //swipeContainer = view.findViewById(R.id.swipeContainer)

//        swipeContainer.setOnRefreshListener {
//            Log.i(TAG, "Refreshing timeline")
//            queryPosts()
//        }
//
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//            android.R.color.holo_green_light,
//            android.R.color.holo_orange_light,
//            android.R.color.holo_red_light)

        //Steps to populate RecyclerView
        //1. Create layout for each row in list (item_post.xml)
        //2. Create data source for each row(this is the post class)
        //3. Create adapter that will bridge data and row layout (PostAdapter)
        //4. Set adapter on RecyclerView
        adapter = PostAdapter(requireContext(), allPosts as ArrayList<Post>)
        SearchRecyclerView.adapter = adapter
        //5. Set layout manager on RecyclerView

        SearchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        //queryPosts()

    }

    open fun queryPosts(string: String){

        //specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // find all Post objects
        query.include(Post.KEY_USER)
        // Return the posts in descending order: ie newer posts will appear first
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if(e != null) {
                    Log.e(TAG, "Error fetching posts")
                } else {
                    adapter.clear()
                    if(posts != null) {
                        for (post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() )
                        }

                        for(item in posts){
                            //if(){ //if post has a tag of search value the add to list.
                                //Search the post's tag array for this -Kristle
                                allPosts.add(item)
                            //}

                        }


                        //allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                        // Now we call setRefreshing(false) to signal refresh has finished
                        swipeContainer.setRefreshing(false)
                    }
                }
            }

        })
    }
    companion object {
        const val TAG = "SearchFragment"
    }

}