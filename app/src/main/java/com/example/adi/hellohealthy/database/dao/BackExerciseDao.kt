package com.example.adi.hellohealthy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.adi.hellohealthy.database.entity.BackExerciseEntity

// Data Access Object (DAO) для работы с упражнениями для спины в базе данных Room
@Dao
interface BackExerciseDao {

    // Получить все упражнения для спины из таблицы "backTable"
    @Query("select * from `backTable`")
    suspend fun fetchAllBackExercises(): List<Int>

    // Вставить новое упражнение для спины в базу данных
    @Insert
    suspend fun insert(backExercisesEntity: BackExerciseEntity)

    // Удалить упражнение для спины из базы данных
    @Delete
    suspend fun delete(backExercisesEntity: BackExerciseEntity)
}


// по аналогии все другие DAO в этой папке !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!