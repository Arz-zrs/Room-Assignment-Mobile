package com.example.scrollablemodul3.domain.model

data class Category(
    val id: Int = 0,
    val nameResId: Int,
    val displayOrder: Int = 0,
    val isFavorite: Boolean = false
)
