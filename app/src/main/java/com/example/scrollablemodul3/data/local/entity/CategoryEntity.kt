package com.example.scrollablemodul3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scrollablemodul3.domain.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nameResId: Int,
    val displayOrder: Int,
    val isFavorite: Boolean
)

fun CategoryEntity.toDomain() = Category(
    id = id,
    nameResId = nameResId,
    displayOrder = displayOrder,
    isFavorite = isFavorite
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    nameResId = nameResId,
    displayOrder = displayOrder,
    isFavorite = isFavorite
)
