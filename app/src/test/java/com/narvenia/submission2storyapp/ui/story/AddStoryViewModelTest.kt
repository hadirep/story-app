package com.narvenia.submission2storyapp.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.narvenia.submission2storyapp.data.StoryRepository
import com.narvenia.submission2storyapp.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import com.narvenia.submission2storyapp.data.Result
import com.narvenia.submission2storyapp.utils.getOrAwaitValue
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addViewModel: AddStoryViewModel
    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyPhoto = DataDummy.generateDummyImages()
    private val dummyDesc = DataDummy.generateDummyDescription()

    @Before
    fun setUp() {
        addViewModel = AddStoryViewModel(storyRepository)
    }

    @Test
    fun `when addNewStory Should Not Null and Return Success`() {
        val expectedAddStory = MutableLiveData<Result<String>>()
        expectedAddStory.value = Result.Success("Upload Success")
        `when`(storyRepository.addNewStory(dummyToken, dummyPhoto, dummyDesc)).thenReturn(expectedAddStory)
        val actualAddStory = addViewModel.addNewStory(dummyToken, dummyPhoto, dummyDesc).getOrAwaitValue()

        Mockito.verify(storyRepository).addNewStory(dummyToken, dummyPhoto, dummyDesc)
        assertNotNull(actualAddStory)
        assertTrue(actualAddStory is Result.Success)
    }

    @Test
    fun `when addNewStory Error and Return Error`() {
        val expectedAddStory = MutableLiveData<Result<String>>()
        expectedAddStory.value = Result.Error("Error")
        `when`(storyRepository.addNewStory(dummyToken, dummyPhoto, dummyDesc)).thenReturn(expectedAddStory)
        val actualAddStory = addViewModel.addNewStory(dummyToken, dummyPhoto, dummyDesc).getOrAwaitValue()

        Mockito.verify(storyRepository).addNewStory(dummyToken, dummyPhoto, dummyDesc)
        assertNotNull(actualAddStory)
        assertTrue(actualAddStory is Result.Error)
    }
}