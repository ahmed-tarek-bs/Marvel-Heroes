package com.example.marvelcharacters.core.base

import androidx.lifecycle.ViewModel
import com.example.marvelcharacters.core.utils.AppError
import com.example.marvelcharacters.core.utils.UIMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel() {

    private val loadingStateFlow = MutableSharedFlow<Boolean>(1)
    val loadingState = loadingStateFlow.asSharedFlow()

    protected val uiMessageFlow = MutableSharedFlow<UIMessage?>()
    val uiMessageState = uiMessageFlow.asSharedFlow()


    open suspend fun emitErrorUIMessage(appError: AppError) {
        val uiMessage = UIMessage.fromAppError(appError)
        uiMessage?.let { uiMessageFlow.emit(it) }
    }

    protected suspend fun showLoading() {
        loadingStateFlow.emit(true)
    }

    protected suspend fun hideLoading() {
        delay(400) //To avoid flashing
        loadingStateFlow.emit(false)
    }
}

fun <T> MutableStateFlow<T>.update(newValue: T) {
    this.value = newValue
}
