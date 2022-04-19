package com.moises.stocksbaraka.provider

import com.moises.stocksbaraka.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal class TestDispatcherProvider : DispatcherProvider {

    private val testDispatcherProvider = Dispatchers.Unconfined

    override
    val io: CoroutineDispatcher
        get() = testDispatcherProvider

    override
    val main: CoroutineDispatcher
        get() = testDispatcherProvider

}