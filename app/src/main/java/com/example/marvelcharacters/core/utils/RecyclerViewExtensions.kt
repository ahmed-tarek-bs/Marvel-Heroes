package com.example.marvelcharacters.core.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.utils.constants.Constants

fun RecyclerView.shouldPaginate(isScrolling: Boolean): Boolean {
    val layoutManager = this.layoutManager as LinearLayoutManager
    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
    val visibleItemCount = layoutManager.childCount
    val totalItemCount = layoutManager.itemCount

    val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
    val isNotAtBeginning = firstVisibleItemPosition >= 0
    val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE

    return isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
}