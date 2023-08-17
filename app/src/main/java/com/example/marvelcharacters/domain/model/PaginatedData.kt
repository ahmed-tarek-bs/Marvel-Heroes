package com.example.marvelcharacters.domain.model


data class PaginatedData<T>(
    val data: List<T>,
    val offSet: Int?,
    val pageSize: Int?,
    val totalCount: Int?
)