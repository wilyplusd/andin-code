package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.lang.RuntimeException

class PostRepositoryImpl : PostRepository {
    override fun getAll(callback: PostRepository.Callback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                callback.onError(RuntimeException("Request failure"))
            }
        })
    }

    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    var errorMessage = response.message()
                    if (errorMessage.isNullOrEmpty()) {
                        errorMessage = "Error saving post";
                    }
                    callback.onError(RuntimeException(errorMessage))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("Body is null"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException("Request failure"))
            }
        })
    }

    override fun removeById(id: Long, callback: PostRepository.Callback<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback.onError(RuntimeException("Request failure"))
            }
        })
    }

    override fun likeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.likeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("Body is null"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException("Request failure"))
            }
        })
    }

}
////package ru.netology.nmedia.repository
////
////import com.google.gson.Gson
////import com.google.gson.reflect.TypeToken
////import okhttp3.*
////import okhttp3.MediaType.Companion.toMediaType
////import okhttp3.RequestBody.Companion.toRequestBody
////import ru.netology.nmedia.dto.Post
////import java.io.IOException
////import java.util.concurrent.TimeUnit
//
////class PostRepositoryImpl : PostRepository {
////    private val client = OkHttpClient.Builder()
////        .connectTimeout(30, TimeUnit.SECONDS)
////        .build()
////    private val gson = Gson()
////    private val postListTypeToken = object : TypeToken<List<Post>>() {}
////    private val postTypeToken = object : TypeToken<Post>() {}
////
////    companion object {
////        private const val BASE_URL = "http://10.0.2.2:9999"
////        private val jsonType = "application/json".toMediaType()
////    }
////
////    override fun getAll(): List<Post> {
////        val request: Request = Request.Builder()
////            .url("${BASE_URL}/api/slow/posts")
////            .build()
////
////        return client.newCall(request)
////            .execute()
////            .let { it.body?.string() ?: throw RuntimeException("body is null") }
////            .let {
////                gson.fromJson(it, postListTypeToken.type)
////            }
////    }
////
////    override fun getAllAsync(callback: PostRepository.GetAllCallback) {
////        val request: Request = Request.Builder()
////            .url("${BASE_URL}/api/slow/posts")
////            .build()
////
////        client.newCall(request)
////            .enqueue(object : Callback {
////                override fun onResponse(call: Call, response: Response) {
////                    val body = response.body?.string() ?: throw RuntimeException("body is null")
////                    try {
////                        callback.onSuccess(gson.fromJson(body, postListTypeToken.type))
////                    } catch (e: Exception) {
////                        callback.onError(e)
////                    }
////                }
////
////                override fun onFailure(call: Call, e: IOException) {
////                    callback.onError(e)
////                }
////            })
////    }
////
////    override fun likeByIdAsync(id: Long, callback: PostRepository.LikeCallback) {
////        val request: Request = Request.Builder()
////            .post("".toRequestBody())
////            .url("${BASE_URL}/api/slow/posts/$id/likes")
////            .build()
////
////        client.newCall(request)
////            .enqueue(object : Callback {
////                override fun onResponse(call: Call, response: Response) {
////                    val body = response.body?.string() ?: throw RuntimeException("body is null")
////                    try {
////                        callback.onSuccess(gson.fromJson(body, postTypeToken.type))
////                    } catch (e: Exception) {
////                        callback.onError(e)
////                    }
////                }
////
////                override fun onFailure(call: Call, e: IOException) {
////                    callback.onError(e)
////                }
////            })
////    }
////
////    override fun save(post: Post) {
////        val request: Request = Request.Builder()
////            .post(gson.toJson(post).toRequestBody(jsonType))
////            .url("${BASE_URL}/api/slow/posts")
////            .build()
////
////        client.newCall(request)
////            .execute()
////            .close()
////    }
////
////    override fun saveAsync(post: Post, callback: PostRepository.SaveCallback) {
////        val request: Request = Request.Builder()
////            .post(gson.toJson(post).toRequestBody(jsonType))
////            .url("${BASE_URL}/api/slow/posts")
////            .build()
////
////        client.newCall(request)
////            .enqueue(object : Callback {
////                override fun onResponse(call: Call, response: Response) {
////                    try {
////                        callback.onSuccess()
////                    } catch (e: Exception) {
////                        callback.onError(e)
////                    }
////                }
////
////                override fun onFailure(call: Call, e: IOException) {
////                    callback.onError(e)
////                }
////            })
////    }
////
////    override fun removeById(id: Long) {
////        val request: Request = Request.Builder()
////            .delete()
////            .url("${BASE_URL}/api/slow/posts/$id")
////            .build()
////
////        client.newCall(request)
////            .execute()
////            .close()
////    }
////
////    override fun removeByIdAsync(id: Long, callback: PostRepository.RemoveCallback) {
////        val request: Request = Request.Builder()
////            .delete()
////            .url("${BASE_URL}/api/slow/posts/$id")
////            .build()
////
////        client.newCall(request)
////            .enqueue(object : Callback {
////                override fun onResponse(call: Call, response: Response) {
////                    try {
////                        callback.onSuccess()
////                    } catch (e: Exception) {
////                        callback.onError(e)
////                    }
////                }
////
////                override fun onFailure(call: Call, e: IOException) {
////                    callback.onError(e)
////                }
////            })
////    }
////}
