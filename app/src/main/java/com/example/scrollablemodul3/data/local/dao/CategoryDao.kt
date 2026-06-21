package com.example.scrollablemodul3.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.scrollablemodul3.data.local.entity.CategoryEntity
import com.example.scrollablemodul3.data.local.entity.CategoryWithGames
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Transaction
    @Query("SELECT * FROM categories ORDER BY displayOrder ASC")
    fun getCategoriesWithGames(): Flow<List<CategoryWithGames>>

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity): Long

    @Query("UPDATE categories SET isFavorite = NOT isFavorite WHERE id = :categoryId")
    suspend fun toggleFavorite(categoryId: Int)

    @Query("UPDATE categories SET displayOrder = :newOrder WHERE id = :categoryId")
    suspend fun updateDisplayOrder(categoryId: Int, newOrder: Int)

    @Transaction
    suspend fun swapDisplayOrders(id1: Int, order1: Int, id2: Int, order2: Int) {
        updateDisplayOrder(id1, order2)
        updateDisplayOrder(id2, order1)
    }
}
