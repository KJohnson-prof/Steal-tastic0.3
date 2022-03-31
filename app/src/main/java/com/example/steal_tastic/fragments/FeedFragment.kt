package com.example.steal_tastic.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.steal_tastic.Post
import com.example.steal_tastic.PostAdapter
import com.example.steal_tastic.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser


class FeedFragment : Fragment() {

    lateinit var postsRecyclerView : RecyclerView
    lateinit var postsAdapter : PostAdapter
    var allPosts : MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRecyclerView = view.findViewById(R.id.postRecyclerView)
        postsAdapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = postsAdapter

        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()
    }

    protected fun queryPosts(){
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)

        query.include(Post.KEY_USER)

        query.addDescendingOrder("createdAt")

        query.findInBackground(object : FindCallback<Post> { //java.lang.ClassCastException: com.parse.ParseObject cannot be cast to com.example.steal_tastic.Post
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if(e != null){
                    Log.e(TAG, "Error fetching posts")
                }else{
                    if (posts != null) {
                        for(post in posts){ //java.lang.ClassCastException: com.parse.ParseObject cannot be cast to com.example.steal_tastic.Post
                            Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser()?.username)
                        }
                    }
                    if (posts != null) {
                        allPosts.addAll(posts)
                    }
                        postsAdapter.notifyDataSetChanged()

                }
            }
        })
    }
    companion object {
        const val TAG = "FeedFragment"
    }
                }





