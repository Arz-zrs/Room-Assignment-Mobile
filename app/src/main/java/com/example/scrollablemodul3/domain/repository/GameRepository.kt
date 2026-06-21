package com.example.scrollablemodul3.domain.repository

import com.example.scrollablemodul3.domain.model.Game

interface GameRepository {
    suspend fun insert(game: Game): Long
}
