package com.submission.picodiploma.aplikasigithubusers_navigationapi.data.remote

import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.DetailUserResponse
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.GithubResponse
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.ItemsItem
import retrofit2.http.*


interface GithubService {

//@JvmSuppressWildcards
    @GET("users")
    suspend fun getUser(  @Header("Authorization") token: String = "token ghp_OH8yCzL....(token api github)"
    ): MutableList<ItemsItem>

    @GET("users/{username}")
    suspend fun getDetail(
        @Path("username") username: String,
        @Header("Authorization") token: String = "token ghp_OH8yCzL....(token api github)"
    ): DetailUserResponse

    @GET("/users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String = "token ghp_OH8yCzL....(token api github)"
        ): MutableList<ItemsItem>

    @GET("/users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String,
                             @Header("Authorization") token: String = "token ghp_OH8yCzL....(token api github)"
        ): MutableList<ItemsItem>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUser(@QueryMap param: Map<String, Any>,
        @Header("Authorization") token: String = "token ghp_OH8yCzL....(token api github)"
        ): GithubResponse
}