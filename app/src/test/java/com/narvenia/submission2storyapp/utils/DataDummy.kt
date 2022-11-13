package com.narvenia.submission2storyapp.utils

import com.narvenia.submission2storyapp.data.remote.response.AllResponse
import com.narvenia.submission2storyapp.data.remote.response.ListStoryItem
import com.narvenia.submission2storyapp.data.remote.response.LoginResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    fun generateDummyLoginResult(): LoginResult{
        return LoginResult(
            "950505",
            "hadi",
            "950505",
            true,
        )
    }

    fun generateDummyName(): String{
        return "hadi"
    }

    fun generateDummyEmail(): String{
        return "hadiesarahma@gmail.com"
    }

    fun generateDummyPassword(): String{
        return "hadiganteng"
    }

    fun generateDummyToken(): String{
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUZmT2lHUEVnQzZnUG1oYjAiLCJpYXQiOjE2NjU1NDg5NDh9.q2m1TyFv127KRJujfrSpvQXNy-CQfv48jqzAzifzrR4"
    }

    fun listStoryItem(): List<ListStoryItem>{
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val list = ListStoryItem(
                "$i",
                "name: $i",
                "description: $i",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                -6.178043,
                106.684249
            )
            items.add(list)
        }
        return items
    }

    fun generateDummyStory(): AllResponse{
        return AllResponse(false,"Success", null, listStoryItem())
    }

    fun generateDummyImages(): MultipartBody.Part {
        return MultipartBody.Part.createFormData("twitter", "https://story-api.dicoding.dev/images/stories/photos-1665640858424_MIPUfQux.png")
    }

    fun generateDummyDescription(): RequestBody {
        return "desc".toRequestBody("text/plain".toMediaType())
    }

    fun generateDummyRegister(): AllResponse {
        return AllResponse(false,"success", null, null)
    }

    fun generateDummyAddNewStory(): AllResponse {
        return AllResponse(false, "success", null, null)
    }
}