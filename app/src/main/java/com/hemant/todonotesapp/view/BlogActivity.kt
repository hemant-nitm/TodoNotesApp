package com.hemant.todonotesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.bumptech.glide.Priority
import com.hemant.todonotesapp.R
import com.hemant.todonotesapp.model.JsonResponse
import com.androidnetworking.common.Priority.HIGH
import com.hemant.todonotesapp.adapter.BlogsAdapter

class BlogActivity : AppCompatActivity() {
    lateinit var recyclerViewBlogs:RecyclerView
    val TAG="BlogActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }
    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(HIGH)
                .build()
                .getAsObject(JsonResponse::class.java, object : ParsedRequestListener<JsonResponse> {
                    override fun onResponse(response: JsonResponse?) {
                        setUpBlogRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, anError?.localizedMessage)
                    }

                })
    }

    private fun setUpBlogRecyclerView(response: JsonResponse?) {
        val blogAdapter = BlogsAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this@BlogActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        val dividerItemDecoration = DividerItemDecoration(recyclerViewBlogs.context,linearLayoutManager.orientation)
        recyclerViewBlogs.addItemDecoration(dividerItemDecoration)
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }

    private fun bindViews() {
        recyclerViewBlogs=findViewById(R.id.recyclerViewBlogs)
    }
}