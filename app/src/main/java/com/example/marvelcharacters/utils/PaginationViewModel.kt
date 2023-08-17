package com.example.marvelcharacters.utils

import androidx.lifecycle.viewModelScope
import com.example.marvelcharacters.core.base.BaseViewModel
import com.example.marvelcharacters.core.base.update
import com.example.marvelcharacters.core.utils.Resource
import com.example.marvelcharacters.core.utils.onError
import com.example.marvelcharacters.core.utils.onResult
import com.example.marvelcharacters.core.utils.onSuccess
import com.example.marvelcharacters.domain.model.PaginatedData
import com.example.marvelcharacters.utils.constants.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class PaginationViewModel<T> : BaseViewModel() {
    protected val _itemList = MutableStateFlow<List<T>?>(null)
    val itemList = _itemList.asStateFlow()

    protected val _isPageLoading = MutableStateFlow(false)
    val isPageLoading = _isPageLoading.asStateFlow()

    var currentOffset: Int = 0
    open val pageSize: Int = Constants.QUERY_PAGE_SIZE
    var totalItemsCount: Int = 0

    protected abstract val getData: suspend () -> Resource<PaginatedData<T>>

    open fun getPage() {
        viewModelScope.launch {
            _isPageLoading.update(true)
            getData()
                .onResult { _isPageLoading.update(false) }
                .onError { emitErrorUIMessage(it) }
                .onSuccess {
                    totalItemsCount = it.totalCount ?: 0
                    setItemList(it.data, currentOffset = it.offSet)
                }
        }
    }

    fun getNextPage() {
        if (_itemList.value.isNullOrEmpty()) {
            getPage()
        } else if (totalItemsCount > 0 && (currentOffset + pageSize) < totalItemsCount) {
            currentOffset += pageSize
            getPage()
        }
    }

    protected fun setItemList(result: List<T>, currentOffset: Int?) {
        val oldData =
            if (currentOffset == 0 || _itemList.value == null) listOf() else _itemList.value

        _itemList.update(oldData?.plus(result))
    }

    protected fun resetPagination() {
        currentOffset = 0
        totalItemsCount = 0
    }

}