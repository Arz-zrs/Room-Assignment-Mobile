package com.example.scrollablemodul3.data

import android.content.Context
import com.example.scrollablemodul3.data.local.AppDatabase
import com.example.scrollablemodul3.data.repository.CategoryRepositoryImpl
import com.example.scrollablemodul3.data.repository.GameRepositoryImpl
import com.example.scrollablemodul3.domain.repository.CategoryRepository
import com.example.scrollablemodul3.domain.repository.GameRepository

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepositoryImpl(AppDatabase.getDatabase(context).categoryDao())
    }

    override val gameRepository: GameRepository by lazy {
        GameRepositoryImpl(AppDatabase.getDatabase(context).gameDao())
    }
}