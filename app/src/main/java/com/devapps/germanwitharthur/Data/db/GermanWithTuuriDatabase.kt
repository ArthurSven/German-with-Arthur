package com.devapps.germanwitharthur.Data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devapps.germanwitharthur.Data.Dao.RemainderDao
import com.devapps.germanwitharthur.Data.Model.Remainder

@Database(entities = [Remainder::class], version = 1, exportSchema = false)
abstract class GermanWithTuuriDatabase : RoomDatabase() {

    abstract fun remainderDao() : RemainderDao

    companion object {
        @Volatile
        private var INSTANCE: GermanWithTuuriDatabase? = null
        fun getDatabase(context: Context): GermanWithTuuriDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GermanWithTuuriDatabase::class.java,
                    "german_with_tuuri_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


}