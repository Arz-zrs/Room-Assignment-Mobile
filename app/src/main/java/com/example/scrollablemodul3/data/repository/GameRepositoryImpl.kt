package com.example.scrollablemodul3.data.repository

import com.example.scrollablemodul3.data.local.dao.GameDao
import com.example.scrollablemodul3.data.local.entity.toEntity
import com.example.scrollablemodul3.domain.model.Game
import com.example.scrollablemodul3.domain.repository.GameRepository

class GameRepositoryImpl(
    private val gameDao: GameDao
) : GameRepository {

    override suspend fun insert(game: Game): Long {
        return gameDao.insert(game.toEntity())
    }
}
