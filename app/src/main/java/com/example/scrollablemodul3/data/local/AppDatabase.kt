package com.example.scrollablemodul3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scrollablemodul3.data.local.dao.CategoryDao
import com.example.scrollablemodul3.data.local.dao.GameDao
import com.example.scrollablemodul3.data.local.entity.CategoryEntity
import com.example.scrollablemodul3.data.local.entity.GameEntity

@Database(entities = [CategoryEntity::class, GameEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "game_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
