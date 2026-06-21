package com.example.scrollablemodul3.data.repository

import com.example.scrollablemodul3.data.local.dao.CategoryDao
import com.example.scrollablemodul3.data.local.entity.toDomain
import com.example.scrollablemodul3.data.local.entity.toEntity
import com.example.scrollablemodul3.domain.model.Category
import com.example.scrollablemodul3.domain.model.Game
import com.example.scrollablemodul3.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getCategoriesWithGames(): Flow<Map<Category, List<Game>>> {
        return categoryDao.getCategoriesWithGames().map { list ->
            list.associate { categoryWithGames ->
                categoryWithGames.category.toDomain() to categoryWithGames.games.map { it.toDomain() }
            }
        }
    }

    override suspend fun getCount(): Int {
        return categoryDao.getCount()
    }

    override suspend fun insert(category: Category): Long {
        return categoryDao.insert(category.toEntity())
    }

    override suspend fun toggleFavorite(categoryId: Int) {
        categoryDao.toggleFavorite(categoryId)
    }

    override suspend fun swapOrders(category1: Category, category2: Category) {
        categoryDao.swapDisplayOrders(
            category1.id, category1.displayOrder,
            category2.id, category2.displayOrder
        )
    }
}
