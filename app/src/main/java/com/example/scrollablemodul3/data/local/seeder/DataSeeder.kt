package com.example.scrollablemodul3.data.local.seeder

import com.example.scrollablemodul3.R
import com.example.scrollablemodul3.domain.model.Category
import com.example.scrollablemodul3.domain.model.Game
import com.example.scrollablemodul3.domain.repository.CategoryRepository
import com.example.scrollablemodul3.domain.repository.GameRepository

object DataSeeder {
    suspend fun seed(categoryRepository: CategoryRepository, gameRepository: GameRepository) {
        if (categoryRepository.getCount() > 0) return

        val actionId = categoryRepository.insert(
            Category(
                nameResId = R.string.category_action,
                displayOrder = 1,
                isFavorite = true
            )
        ).toInt()
        val rpgId = categoryRepository.insert(
            Category(
                nameResId = R.string.category_rpg,
                displayOrder = 2,
                isFavorite = false
            )
        ).toInt()
        val adventureId = categoryRepository.insert(
            Category(
                nameResId = R.string.category_adventure,
                displayOrder = 3,
                isFavorite = true
            )
        ).toInt()

        gameRepository.insert(
            Game(
                titleResId = R.string.item1,
                subtitleResId = R.string.item1_sub,
                descriptionResId = R.string.item1_desc,
                detailResId = R.string.item1_detail,
                imageResId = R.drawable.crosscode,
                detailImageResId = R.drawable.crosscode_detail,
                steamUrl = "https://store.steampowered.com/app/368340/CrossCode/",
                categoryId = rpgId
            )
        )
        gameRepository.insert(
            Game(
                titleResId = R.string.item2,
                subtitleResId = R.string.item2_sub,
                descriptionResId = R.string.item2_desc,
                detailResId = R.string.item2_detail,
                imageResId = R.drawable.hades2,
                detailImageResId = R.drawable.hades2_detail,
                steamUrl = "https://store.steampowered.com/app/1145350/Hades_II/",
                categoryId = actionId
            )
        )
        gameRepository.insert(
            Game(
                titleResId = R.string.item3,
                subtitleResId = R.string.item3_sub,
                descriptionResId = R.string.item3_desc,
                detailResId = R.string.item3_detail,
                imageResId = R.drawable.nms,
                detailImageResId = R.drawable.nms_detail,
                steamUrl = "https://store.steampowered.com/app/275850/No_Mans_Sky/",
                categoryId = adventureId
            )
        )
        gameRepository.insert(
            Game(
                titleResId = R.string.item4,
                subtitleResId = R.string.item4_sub,
                descriptionResId = R.string.item4_desc,
                detailResId = R.string.item4_detail,
                imageResId = R.drawable.coe33,
                detailImageResId = R.drawable.coe33_detail,
                steamUrl = "https://store.steampowered.com/app/1903340/Clair_Obscur_Expedition_33/",
                categoryId = rpgId
            )
        )
        gameRepository.insert(
            Game(
                titleResId = R.string.item5,
                subtitleResId = R.string.item5_sub,
                descriptionResId = R.string.item5_desc,
                detailResId = R.string.item5_detail,
                imageResId = R.drawable.sekiro,
                detailImageResId = R.drawable.sekiro_detail,
                steamUrl = "https://store.steampowered.com/app/814380/Sekiro_Shadows_Die_Twice__GOTY_Edition/",
                categoryId = actionId
            )
        )
    }
}