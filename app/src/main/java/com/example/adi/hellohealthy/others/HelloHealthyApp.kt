package com.example.adi.hellohealthy.others

import android.app.Application
import com.example.adi.hellohealthy.database.database.BackExerciseDataBase
import com.example.adi.hellohealthy.database.database.BicepExerciseDataBase
import com.example.adi.hellohealthy.database.database.ChestExerciseDataBase
import com.example.adi.hellohealthy.database.database.HistoryDataBase
import com.example.adi.hellohealthy.database.database.LegsDataBase
import com.example.adi.hellohealthy.database.database.MiscellaneousExercisesDataBase
import com.example.adi.hellohealthy.database.database.ShoulderDataBase
import com.example.adi.hellohealthy.database.database.TricepAndAbsDataBase

// Класс HelloHealthyApp представляет приложение HelloHealthy.
class HelloHealthyApp: Application() {

    // Ленивая инициализация экземпляров баз данных для доступа к ним из приложения.

    // База данных истории выполненных наборов упражнений.
    val historyDb by lazy {
        HistoryDataBase.getInstance(this)
    }

    // База данных для разнообразных упражнений.
    val miscellaneousExercisesDb by lazy {
        MiscellaneousExercisesDataBase.getInstance(this)
    }

    // База данных упражнений для груди.
    val chestExerciseDb by lazy {
        ChestExerciseDataBase.getInstance(this)
    }

    // База данных упражнений для спины.
    val backExerciseDb by lazy {
        BackExerciseDataBase.getInstance(this)
    }

    // База данных упражнений для бицепса.
    val bicepExerciseDb by lazy {
        BicepExerciseDataBase.getInstance(this)
    }

    // База данных упражнений для трицепса и пресса.
    val tricepAndAbsDb by lazy {
        TricepAndAbsDataBase.getInstance(this)
    }

    // База данных упражнений для плеч.
    val shoulderDb by lazy {
        ShoulderDataBase.getInstance(this)
    }

    // База данных упражнений для ног.
    val legsDb by lazy {
        LegsDataBase.getInstance(this)
    }
}
