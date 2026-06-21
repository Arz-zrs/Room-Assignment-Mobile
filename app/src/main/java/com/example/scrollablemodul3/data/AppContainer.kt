package com.example.scrollablemodul3.data

import com.example.scrollablemodul3.domain.repository.CategoryRepository
import com.example.scrollablemodul3.domain.repository.GameRepository

interface AppContainer {
    val categoryRepository: CategoryRepository
    val gameRepository: GameRepository
}