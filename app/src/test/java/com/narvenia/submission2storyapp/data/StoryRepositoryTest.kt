package com.narvenia.submission2storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.narvenia.submission2storyapp.adapter.StoryAdapter
import com.narvenia.submission2storyapp.data.local.database.StoryDatabase
import com.narvenia.submission2storyapp.data.remote.api.ApiService
import com.narvenia.submission2storyapp.data.remote.response.ListStoryItem
import com.narvenia.submission2storyapp.utils.DataDummy
import com.narvenia.submission2storyapp.utils.MainDispatcherRule
import com.narvenia.submission2storyapp.utils.getOrAwaitValue
import com.narvenia.submission2storyapp.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var storyDatabase: StoryDatabase
    @Mock
    private lateinit var storyRepositoryMock: StoryRepository
    private lateinit var storyRepository: StoryRepository

    private val dummyToken = "token"
    private val dummyName = DataDummy.generateDummyName()
    private val dummyEmail = DataDummy.generateDummyEmail()
    private val dummyPassword = DataDummy.generateDummyPassword()
    private val dummyDesc = DataDummy.generateDummyDescription()
    private val dummyImages = DataDummy.generateDummyImages()
    private val dummyPagingListStory = DataDummy.listStoryItem()
    private val dummyStoryResponse = DataDummy.generateDummyStory()

    @Before
    fun setUp() {
        storyRepository = StoryRepository(apiService, storyDatabase)
    }

    @Test
    fun `when login Should Not Null and Return Success`() = runTest {
        val actualLogin = storyRepository.login("hadiesarahma@gmail.com","vaganza1")
        actualLogin.observeForTesting {
            assertNotNull(actualLogin)
        }
    }

    @Test
    fun `when register Should Not Null and Return Success`() = runTest {
        val actualRegister = storyRepository.register(dummyName, dummyEmail, dummyPassword)
        actualRegister.observeForTesting {
            assertNotNull(actualRegister)
        }
    }

    @Test
    fun `when getAllStory Should Not Null and Return Success`() = runTest {
        val data = StoryPagingTest.snapshot(dummyPagingListStory)
        val expectedResult = MutableLiveData<PagingData<ListStoryItem>>()
        expectedResult.value = data

        `when`(storyRepositoryMock.getAllStory(dummyToken)).thenReturn(expectedResult)

        val actualStory = storyRepositoryMock.getAllStory(dummyToken).getOrAwaitValue()
        val asyncPagingDataDiffer = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainDispatcherRule.testDispatcher,
            workerDispatcher = mainDispatcherRule.testDispatcher
        )
        asyncPagingDataDiffer.submitData(actualStory)

        assertNotNull(asyncPagingDataDiffer.snapshot())
        assertEquals(dummyStoryResponse.listStory!!.size, asyncPagingDataDiffer.snapshot().size)
    }

    @Test
    fun `when addNewStory Should Not Null and Return Success`() = runTest {
        val actualAddNewStory = storyRepository.addNewStory(dummyToken, dummyImages, dummyDesc)
        actualAddNewStory.observeForTesting {
            assertNotNull(actualAddNewStory)
        }
    }

    @Test
    fun `when getMaps Should Not Null and Return ListStoryItem`() = runTest {
        val actualMaps = storyRepository.getMaps(dummyToken)
        actualMaps.observeForTesting {
            assertNotNull(actualMaps)
            assertTrue(actualMaps.value is Result.Success)
        }
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}