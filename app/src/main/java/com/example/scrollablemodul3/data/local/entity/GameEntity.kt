package com.example.scrollablemodul3.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.scrollablemodul3.domain.model.Game

@Entity(
    tableName = "games",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titleResId: Int,
    val subtitleResId: Int,
    val descriptionResId: Int,
    val detailResId: Int,
    val imageResId: Int,
    val detailImageResId: Int,
    val steamUrl: String,
    val rating: Float,
    val releaseDate: Long,
    val categoryId: Int
)

fun GameEntity.toDomain() = Game(
    id = id,
    titleResId = titleResId,
    subtitleResId = subtitleResId,
    descriptionResId = descriptionResId,
    detailResId = detailResId,
    imageResId = imageResId,
    detailImageResId = detailImageResId,
    steamUrl = steamUrl,
    rating = rating,
    releaseDate = releaseDate,
    categoryId = categoryId
)

fun Game.toEntity() = GameEntity(
    id = id,
    titleResId = titleResId,
    subtitleResId = subtitleResId,
    descriptionResId = descriptionResId,
    detailResId = detailResId,
    imageResId = imageResId,
    detailImageResId = detailImageResId,
    steamUrl = steamUrl,
    rating = rating,
    releaseDate = releaseDate,
    categoryId = categoryId
)
