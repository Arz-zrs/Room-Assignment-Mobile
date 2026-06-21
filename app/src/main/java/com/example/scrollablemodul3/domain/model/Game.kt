package com.example.scrollablemodul3.domain.model

data class Game(
    val id: Int = 0,
    val titleResId: Int,
    val subtitleResId: Int,
    val descriptionResId: Int,
    val detailResId: Int,
    val imageResId: Int,
    val detailImageResId: Int,
    val steamUrl: String,
    val releaseDate: Long = 0L,
    val categoryId: Int
)
