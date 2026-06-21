package com.example.scrollablemodul3.ui

import com.example.scrollablemodul3.domain.model.Category
import com.example.scrollablemodul3.domain.model.Game

data class ScrollableUiState(
    val categoriesWithGames: Map<Category, List<Game>> = emptyMap(),
    val currentItemIndex: Int = 0,
    val selectedLocale: String = "en",
    val sortMode: SortMode = SortMode.ORDER
)
