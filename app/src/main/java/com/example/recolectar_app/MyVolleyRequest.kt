package com.example.recolectar_app

import android.content.Context
import android.graphics.Bitmap
import androidx.collection.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MyVolleyRequest {
    private var mRequestQueue:RequestQueue?=null
    private var context:Context?=null
    private var iVolley:iVolley?=null
    var imageLoader:ImageLoader?=null
        private set

    val requestQueue:RequestQueue
        get(){
        if(mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(context!!.applicationContext)
        return mRequestQueue!!
    }

    private constructor(context: Context,iVolley: iVolley)
    {
        this.context = context
        this.iVolley = iVolley
        mRequestQueue = requestQueue
        this.imageLoader = ImageLoader(mRequestQueue,object : ImageLoader.ImageCache{
            private val mCache = LruCache<String, Bitmap >(10)
            override fun getBitmap(url: String?): Bitmap? {
                return mCache.get(url!!)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                mCache.put(url!!,bitmap!!)
            }
        }
        )
    }

    private constructor(context: Context)
    {
        this.context = context
        mRequestQueue = requestQueue
        this.imageLoader = ImageLoader(mRequestQueue,object : ImageLoader.ImageCache{
            private val mCache = LruCache<String, Bitmap >(10)
            override fun getBitmap(url: String?): Bitmap {
                return mCache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                mCache.put(url,bitmap)
            }
        }
        )
    }

    fun <T> addToRequestQueue(req:Request<T>){
        requestQueue.add(req)
    }

    //GET METHOD
    fun getRequest(url:String){
        val getRequest = JsonObjectRequest(Request.Method.GET,url,null,
            { response ->
             iVolley!!.onResponse(response.toString())
         },
            { error ->
             iVolley!!.onResponse(error.message!!)
         })
        addToRequestQueue(getRequest)
    }

    //POST Method with params
    fun postRequest(url:String)
    {
        val postRequest = object:StringRequest(Request.Method.POST, url,
            { response ->
                iVolley!!.onResponse(response.toString())
            },
            { error ->
                iVolley!!.onResponse(error.message!!)
            })
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["name"] = ""
                params["value"] = ""
                return params
            }
        }

        addToRequestQueue(postRequest)
    }

    //PUT Method with params
    fun putRequest(url:String)
    {
        val postRequest = object:StringRequest(Request.Method.PUT, url,
            { response ->
                iVolley!!.onResponse(response.toString())
            },
            { error ->
                iVolley!!.onResponse(error.message!!)
            })
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["name"] = ""
                params["value"] = ""
                return params
            }
        }

        addToRequestQueue(postRequest)
    }

    //PATCH Method with params
    fun patchRequest(url:String)
    {
        val postRequest = object:StringRequest(Request.Method.PATCH, url,
            { response ->
                iVolley!!.onResponse(response.toString())
            },
            { error ->
                iVolley!!.onResponse(error.message!!)
            })
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["name"] = ""
                params["value"] = ""
                return params
            }
        }

        addToRequestQueue(postRequest)
    }

    //DELETE Method
    fun deleteRequest(url:String)
    {
        val deleteRequest = StringRequest(Request.Method.DELETE, url,
            { response ->
                iVolley!!.onResponse(response)
            },
            { error ->
                iVolley!!.onResponse(error.message!!)
            })
        addToRequestQueue(deleteRequest)
    }


    companion object{
        private var mInstance : MyVolleyRequest? = null
        @Synchronized
        fun getInstance(context: Context) : MyVolleyRequest{
            if(mInstance == null)
            {
                mInstance = MyVolleyRequest(context)
            }
            return mInstance!!
        }

        @Synchronized
        fun getInstance(context: Context, iVolley: iVolley) : MyVolleyRequest{
            if(mInstance == null)
            {
                mInstance = MyVolleyRequest(context,iVolley)
            }
            return mInstance!!
        }

    }
}