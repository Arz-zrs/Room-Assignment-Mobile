package com.example.scrollablemodul3.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.scrollablemodul3.data.local.seeder.DataSeeder
import com.example.scrollablemodul3.domain.model.Category
import com.example.scrollablemodul3.domain.repository.CategoryRepository
import com.example.scrollablemodul3.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScrollableViewModel(
    private val categoryRepository: CategoryRepository,
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScrollableUiState())
    val uiState: StateFlow<ScrollableUiState> = _uiState.asStateFlow()

    init {
        val currentLocale = AppCompatDelegate.getApplicationLocales().get(0)?.language ?: "en"
        _uiState.update { it.copy(selectedLocale = currentLocale) }

        viewModelScope.launch {
            DataSeeder.seed(categoryRepository, gameRepository)
        }

        viewModelScope.launch {
            categoryRepository.getCategoriesWithGames().collect { data ->
                _uiState.update { it.copy(categoriesWithGames = data) }
            }
        }
    }

    fun updateCurrentItem(index: Int) {
        _uiState.update { it.copy(currentItemIndex = index) }
    }

    fun updateLocale(locale: String) {
        _uiState.update { it.copy(selectedLocale = locale) }
        val appLocale = LocaleListCompat.forLanguageTags(locale)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    fun toggleCategoryFavorite(categoryId: Int) {
        viewModelScope.launch {
            categoryRepository.toggleFavorite(categoryId)
        }
    }

    fun setSortMode(sortMode: SortMode) {
        _uiState.update { it.copy(sortMode = sortMode) }
    }

    fun moveCategoryUp(category: Category) {
        val currentList = uiState.value.categoriesWithGames.keys.sortedBy { it.displayOrder }
        val index = currentList.indexOf(category)
        if (index > 0) {
            val other = currentList[index - 1]
            viewModelScope.launch {
                categoryRepository.swapOrders(category, other)
            }
        }
    }

    fun moveCategoryDown(category: Category) {
        val currentList = uiState.value.categoriesWithGames.keys.sortedBy { it.displayOrder }
        val index = currentList.indexOf(category)
        if (index >= 0 && index < currentList.size - 1) {
            val other = currentList[index + 1]
            viewModelScope.launch {
                categoryRepository.swapOrders(category, other)
            }
        }
    }

    companion object {
        fun provideFactory(
            categoryRepository: CategoryRepository,
            gameRepository: GameRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return ScrollableViewModel(categoryRepository, gameRepository) as T
            }
        }
    }
}
