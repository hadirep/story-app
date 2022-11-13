package com.narvenia.submission2storyapp.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.narvenia.submission2storyapp.data.local.database.StoryDatabase
import com.narvenia.submission2storyapp.data.remote.api.ApiService
import com.narvenia.submission2storyapp.data.remote.response.AllResponse
import com.narvenia.submission2storyapp.data.remote.response.ListStoryItem
import com.narvenia.submission2storyapp.data.remote.response.LoginResult
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository (
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase,
    ) {

    fun login(email: String, password: String): LiveData<Result<LoginResult?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response.loginResult))
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
            Log.d(TAG, "onFailure: ${e.message.toString()}")
        }
    }

    fun register(name: String, email: String, password: String): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response.message))
        } catch (e: Exception){
            Log.d(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addNewStory(token: String, image: MultipartBody.Part, description: RequestBody): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addNewStory(token, image, description)
            emit(Result.Success(response.message))
        } catch (e: Exception){
            Log.d(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllStory(token: String) : LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getMaps(token: String): LiveData<Result<AllResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getMaps(token, 1)
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.d(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(
            apiService: ApiService,
            storyDatabase: StoryDatabase
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, storyDatabase)
            }.also { instance = it }
    }
}