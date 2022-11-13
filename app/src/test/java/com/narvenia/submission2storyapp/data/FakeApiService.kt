package com.narvenia.submission2storyapp.data

import com.narvenia.submission2storyapp.data.remote.api.ApiService
import com.narvenia.submission2storyapp.data.remote.response.AllResponse
import com.narvenia.submission2storyapp.utils.DataDummy
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {
    private val dummyLogin = DataDummy.generateDummyLoginResult()
    private val dummyRegister = DataDummy.generateDummyRegister()
    private val dummyListStory = DataDummy.listStoryItem()
    private val dummyAddNewStory = DataDummy.generateDummyAddNewStory()

    override suspend fun login(email: String, password: String): AllResponse {
        return AllResponse(false, "success", dummyLogin, null)
    }

    override suspend fun register(name: String, email: String, password: String): AllResponse {
        return dummyRegister
    }

    override suspend fun getAllStory(token: String, page: Int, size: Int): AllResponse {
        return AllResponse(false, "success", null, dummyListStory)
    }

    override suspend fun addNewStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): AllResponse {
        return dummyAddNewStory
    }

    override suspend fun getMaps(token: String, location: Int): AllResponse {
        return AllResponse(false, "success", null, dummyListStory)
    }
}