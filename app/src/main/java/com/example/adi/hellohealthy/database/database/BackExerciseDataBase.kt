package com.example.adi.hellohealthy.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adi.hellohealthy.database.dao.BackExerciseDao
import com.example.adi.hellohealthy.database.entity.BackExerciseEntity

// Абстрактный класс базы данных для упражнений для спины
@Database(entities = [BackExerciseEntity::class], version = 1)
abstract class BackExerciseDataBase : RoomDatabase() {

    // Абстрактный метод для получения объекта доступа к данным (DAO) для упражнений для спины
    abstract fun backExerciseDao(): BackExerciseDao

    // Companion object для создания и получения экземпляра базы данных
    companion object {
        @Volatile
        private var INSTANCE: BackExerciseDataBase? = null
        private const val BACK_EXERCISE_DB_NAME = "back_database"

        // Метод для получения экземпляра базы данных
        fun getInstance(context: Context): BackExerciseDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BackExerciseDataBase::class.java,
                        BACK_EXERCISE_DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


// Этот код относится к созданию и управлению базой данных SQLite с использованием библиотеки Room в приложении Android.
// ПО АНАЛОГИИ ВСЕ ДРУГИЕ ФАЙЛЫ В ЭТОЙ ПАПКЕ