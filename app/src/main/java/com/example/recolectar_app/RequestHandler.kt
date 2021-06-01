package com.example.recolectar_app

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class RequestHandler private constructor(context: Context) {
    private var mRequestQueue: RequestQueue?=null
    private var context: Context?= context

    private val requestQueue:RequestQueue
        get(){
            if(mRequestQueue == null)
                mRequestQueue = Volley.newRequestQueue(context!!.applicationContext)
            return mRequestQueue!!
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: RequestHandler? = null

        @Synchronized
        fun getInstance(context: Context): RequestHandler {
            if (mInstance == null) {
                mInstance = RequestHandler(context)
            }
            return mInstance!!
        }
    }



    private fun <T> addToRequestQueue(request : Request<T>){
        requestQueue.add(request)
    }

    //GET METHOD
    fun getRequest(url:String, response : Response.Listener<String>, error: Response.ErrorListener){
        val getRequest = StringRequest(Request.Method.GET,url,response,error)
        addToRequestQueue(getRequest)
    }

    //GET ARRAY METHOD
    fun getArrayRequest(url:String, response: Response.Listener<JSONArray>, error: Response.ErrorListener, obj: JSONArray?){
        val getArrayRequest = JsonArrayRequest(Request.Method.GET,url,obj,response,error)
        addToRequestQueue(getArrayRequest)
    }

    //POST Method with params
    fun postRequest(url:String, response : Response.Listener<JSONObject>, error: Response.ErrorListener, obj: JSONObject)
    {
        val postRequest = JsonObjectRequest(Request.Method.POST, url,obj,response,error)
        addToRequestQueue(postRequest)
    }



    //PUT Method with params
    fun putRequest(url:String, response : Response.Listener<JSONObject>, error: Response.ErrorListener, obj : JSONObject)
    {
        val putRequest = JsonObjectRequest(Request.Method.PUT, url,obj,response,error)
        addToRequestQueue(putRequest)
    }

    //PATCH Method
    fun patchRequest(url:String,obj : JSONObject, response : Response.Listener<JSONObject>, error: Response.ErrorListener, )
    {
        val patchRequest = JsonObjectRequest(Request.Method.POST, url,obj,response,error)
        addToRequestQueue(patchRequest)
    }

    //DELETE Method
    fun deleteRequest(url:String, response: Response.Listener<String>, error: Response.ErrorListener)
    {
        val deleteRequest = StringRequest(Request.Method.DELETE,url,response,error)
        addToRequestQueue(deleteRequest)
    }

//    fun makeHashMap(keys: Array<String>, values: Array<String>) : HashMap<String,String>{
//        val hash = HashMap<String,String>()
//        for (i in keys.indices) run {
//            hash.put(keys[i] as String, values[i] as String)
//        }
//        return hash
//    }

}
