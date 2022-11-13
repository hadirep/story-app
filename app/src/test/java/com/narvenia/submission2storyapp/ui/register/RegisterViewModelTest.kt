package com.narvenia.submission2storyapp.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.narvenia.submission2storyapp.data.Result
import com.narvenia.submission2storyapp.data.StoryRepository
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
class RegisterViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyName = DataDummy.generateDummyName()
    private val dummyEmail = DataDummy.generateDummyEmail()
    private val dummyPassword = DataDummy.generateDummyPassword()

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(storyRepository)
    }

    @Test
    fun `when register Should Not Null and Return Success`() {
        val expectedRegister = MutableLiveData<Result<String>>()
        expectedRegister.value = Result.Success("User Created")
        `when`(storyRepository.register(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedRegister)
        val actualRegister = registerViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).register(dummyName, dummyEmail, dummyPassword)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is Result.Success)
    }

    @Test
    fun `when register Error Should Return Error`() {
        val expectedRegister = MutableLiveData<Result<String>>()
        expectedRegister.value = Result.Error("Error")
        `when`(storyRepository.register(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedRegister)
        val actualRegister = registerViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).register(dummyName, dummyEmail, dummyPassword)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is Result.Error)
    }
}