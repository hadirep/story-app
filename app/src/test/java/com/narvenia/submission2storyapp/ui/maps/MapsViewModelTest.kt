package com.narvenia.submission2storyapp.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.narvenia.submission2storyapp.data.Result
import com.narvenia.submission2storyapp.data.StoryRepository
import com.narvenia.submission2storyapp.data.remote.response.AllResponse
import com.narvenia.submission2storyapp.utils.DataDummy
import com.narvenia.submission2storyapp.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyStory = DataDummy.generateDummyStory()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when Maps Should Not Null and Return Success`() {
        val expectedMaps = MutableLiveData<Result<AllResponse>>()
        expectedMaps.value = Result.Success(dummyStory)
        `when`(storyRepository.getMaps(dummyToken)).thenReturn(expectedMaps)
        val actualMaps = mapsViewModel.getMaps(dummyToken).getOrAwaitValue()

        Mockito.verify(storyRepository).getMaps(dummyToken)
        assertNotNull(actualMaps)
        assertTrue(actualMaps is Result.Success)
        assertEquals(dummyStory, (actualMaps as Result.Success).data)
    }

    @Test
    fun `when Maps Error and Return Error`() {
        val expectedMaps = MutableLiveData<Result<AllResponse>>()
        expectedMaps.value = Result.Error("Error")
        `when`(storyRepository.getMaps(dummyToken)).thenReturn(expectedMaps)
        val actualResponse = mapsViewModel.getMaps(dummyToken).getOrAwaitValue()

        Mockito.verify(storyRepository).getMaps(dummyToken)
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Result.Error)
    }
}