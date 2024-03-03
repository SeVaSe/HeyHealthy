package com.example.adi.hellohealthy.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Этот класс представляет сущность (Entity) для хранения упражнений для спины в базе данных с использованием Room.
@Entity(tableName = "backTable")
data class BackExerciseEntity(
    @PrimaryKey
    val id: Int // Поле, которое будет использоваться в качестве первичного ключа для идентификации упражнений.
)


// Все другие файлы в этой папки по аналогии !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!