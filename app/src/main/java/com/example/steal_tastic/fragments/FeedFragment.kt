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
import com.parse.ParseQuery
import com.parse.ParseUser


class FeedFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView
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
    }

    open fun queryPosts(isCurrentUserOnly : Boolean) {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)

        query.include(Post.KEY_USER)

        if(isCurrentUserOnly) {
            query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        }

        query.addDescendingOrder("createdAt")

        query.limit = 20

        query.findInBackground { posts, e ->
            if (e != null) {
                e.printStackTrace()
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(TAG, "Post: " + post.getDescription() + "; username: " + post.getUser()?.username)
                    }
                    allPosts.addAll(posts)
                    postsAdapter.notifyDataSetChanged()

                }
            }
        }
    }

    companion object{
        const val TAG = "FeedFragment"
    }
}

