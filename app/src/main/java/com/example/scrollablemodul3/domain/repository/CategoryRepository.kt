package com.example.scrollablemodul3.domain.repository

import com.example.scrollablemodul3.domain.model.Category
import com.example.scrollablemodul3.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoriesWithGames(): Flow<Map<Category, List<Game>>>
    suspend fun getCount(): Int
    suspend fun insert(category: Category): Long
    suspend fun toggleFavorite(categoryId: Int)
    suspend fun swapOrders(category1: Category, category2: Category)
}
