package com.example.scrollablemodul3.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithGames(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val games: List<GameEntity>
)
