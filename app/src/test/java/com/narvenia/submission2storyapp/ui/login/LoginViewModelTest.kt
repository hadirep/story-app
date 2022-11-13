package com.narvenia.submission2storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.narvenia.submission2storyapp.data.Result
import com.narvenia.submission2storyapp.data.StoryRepository
import com.narvenia.submission2storyapp.data.remote.response.LoginResult
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
class LoginViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLogin = DataDummy.generateDummyLoginResult()
    private val dummyEmail = DataDummy.generateDummyEmail()
    private val dummyPassword = DataDummy.generateDummyPassword()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(storyRepository)
    }

    @Test
    fun `when login Should Not Null and Return Success`() {
        val expectedLogin = MutableLiveData<Result<LoginResult?>>()
        expectedLogin.value = Result.Success(dummyLogin)
        `when`(storyRepository.login(dummyEmail, dummyPassword)).thenReturn(expectedLogin)
        val actualLogin = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).login(dummyEmail, dummyPassword)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Success)
    }

    @Test
    fun `when login Error Should Return Error`() {
        val expectedLogin = MutableLiveData<Result<LoginResult?>>()
        expectedLogin.value = Result.Error("Error")
        `when`(storyRepository.login(dummyEmail, dummyPassword)).thenReturn(expectedLogin)
        val actualLogin = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).login(dummyEmail, dummyPassword)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Error)
    }
}