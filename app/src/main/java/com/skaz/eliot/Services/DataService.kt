package com.skaz.eliot.Services

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.skaz.eliot.Controller.App
import com.skaz.eliot.Utilities.*
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.skaz.eliot.Model.*
import java.lang.reflect.Type

object DataService {

    val dec = DecimalFormat("#.##")

    fun electricIndicationsRequest(request: ElectricIndicationsRequest, onResponse: (ElectricIndicationsResponse?) -> Unit) {
        makeJsonObjectRequest<ElectricIndicationsRequest, ElectricIndicationsResponse>(URL_ELECTRIC_GET_INDICATIONS, request, onResponse);
    }

    fun waterIndicationsRequest(request: WaterIndicationsRequest, onResponse: (WaterIndicationsResponse?) -> Unit) {
        makeJsonObjectRequest<WaterIndicationsRequest, WaterIndicationsResponse>(URL_WATER_GET_INDICATIONS, request, onResponse)
    }

    fun deviceRequest(request: DevicesRequest, onResponse: (List<Device>?) -> Unit) {
        makeJsonArrayRequest(URL_DEVICES, request, object: TypeToken<List<Device>>() {}.type, onResponse)
    }

    private fun <TRequest, TResponse> makeJsonObjectRequest(url: String, request: TRequest, onResponse: (TResponse?) -> Unit) {
        val gson = GsonBuilder().create()
        val requestBody = gson.toJson(request)
        val req = object :
            JsonObjectRequest(Method.POST, url, null, Response.Listener { response ->
                try {
                    val responseType = object : TypeToken<TResponse>() {}.type
                    val response : TResponse = gson.fromJson(response.toString(), responseType)
                    onResponse(response)
                } catch (e: Exception) {
                    onResponse(null)
                }
            }, Response.ErrorListener { error ->
                Log.d("ERROR", "Could not obtain request $requestBody: $error")
                onResponse(null)
            }) {

            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json; version=1")
                headers.put("X-Platform", "Android")
                return headers
            }
        }
        App.prefs.requestQueue.add(req)
    }

    private fun <TRequest, TResponse> makeJsonArrayRequest(url: String, request: TRequest, responseType: Type, onResponse: (List<TResponse>?) -> Unit) {
        val gson = GsonBuilder().create()
        val requestBody = gson.toJson(request)
        val req = object :
            JsonArrayRequest(Method.POST, url, null, Response.Listener { response ->
                try {
                    val response : List<TResponse> = gson.fromJson(response.toString(), responseType)
                    onResponse(response)
                } catch (e: Exception) {
                    Log.d("ERROR", "Could not obtain request $requestBody: exception ${e.message}")
                    onResponse(null)
                }
            }, Response.ErrorListener { error ->
                Log.d("ERROR", "Could not obtain request $requestBody: error $error")
                onResponse(null)
            }) {

            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json; version=1")
                headers.put("X-Platform", "Android")
                return headers
            }
        }
        App.prefs.requestQueue.add(req)
    }
}
