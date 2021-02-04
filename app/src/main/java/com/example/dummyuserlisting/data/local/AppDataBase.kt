package com.example.dummyuserlisting.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dummyuserlisting.data.entities.UserProps

@Database(entities = [UserProps::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context):AppDataBase =
            INSTANCE ?: synchronized(this) { INSTANCE ?: createDb(context).also { INSTANCE = it } }


        private fun createDb(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "UserTable"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }

}