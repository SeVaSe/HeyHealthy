package com.example.adi.hellohealthy.activities

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.adi.hellohealthy.database.entity.BackExerciseEntity
import com.example.adi.hellohealthy.database.entity.BicepExerciseEntity
import com.example.adi.hellohealthy.database.entity.ChestExerciseEntity
import com.example.adi.hellohealthy.database.entity.LegsEntity
import com.example.adi.hellohealthy.database.entity.MiscellaneousExercisesEntity
import com.example.adi.hellohealthy.database.entity.ShoulderEntity
import com.example.adi.hellohealthy.database.entity.TricepAndAbsEntity
import com.example.adi.hellohealthy.databinding.ActivityMainBinding
import com.example.adi.hellohealthy.databinding.ProgressDialogBinding
import com.example.adi.hellohealthy.others.HelloHealthyApp
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // Объявляем переменную для привязки к разметке активити
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем привязку к разметке активити
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Проверяем, является ли это первым запуском приложения
        val pref = getSharedPreferences("exercise_addition", MODE_PRIVATE)
        val firstStart = pref.getBoolean("first_start", true)
        if (firstStart) {
            // Если это первый запуск, добавляем упражнения в список для всех семи дней недели
            lifecycleScope.launch {
                addExerciseInAllSevenExerciseList()
            }
        }

        // Регистрируем обработчик нажатия кнопки "Назад"
        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                finish()
            }
        } else {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            })
        }

        // Устанавливаем обработчики нажатия кнопок для перехода на другие активити
        binding?.iBStart?.setOnClickListener {
            intent = Intent(this, DaySelectorActivity::class.java)
            startActivity(intent)
        }

        binding?.iBBmiCalc?.setOnClickListener {
            intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }

        binding?.ibCheckHistory?.setOnClickListener {
            intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding?.iBOtherButton?.setOnClickListener {
            intent = Intent(this, GptSportActivity::class.java)
            startActivity(intent)
        }
    }

    // Функция для добавления упражнений в список для всех семи дней недели
    private suspend fun addExerciseInAllSevenExerciseList() {
        // Создаем диалоговое окно для отображения прогресса
        val dialog = Dialog(this)
        val dialogBinding = ProgressDialogBinding.inflate(layoutInflater)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.show()

        // Добавляем упражнения для каждой группы мышц в соответствующие базы данных
        lifecycleScope.launch {
            val miscellaneousExercisesDao = (application as HelloHealthyApp).miscellaneousExercisesDb.miscellaneousExercisesDao()
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(0))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(1))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(2))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(3))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(4))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(5))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(6))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(7))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(8))}
            lifecycleScope.launch {miscellaneousExercisesDao.insert(MiscellaneousExercisesEntity(9))}
        }
        lifecycleScope.launch {
            val chestExercisesDao = (application as HelloHealthyApp).chestExerciseDb.chestExerciseDao()
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(1))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(3))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(16))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(24))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(25))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(26))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(27))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(28))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(29))}
            lifecycleScope.launch {chestExercisesDao.insert(ChestExerciseEntity(30))}
        }
        lifecycleScope.launch {
            val backExerciseDao = (application as HelloHealthyApp).backExerciseDb.backExerciseDao()
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(31))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(32))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(33))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(34))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(35))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(36))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(37))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(38))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(39))}
            lifecycleScope.launch {backExerciseDao.insert(BackExerciseEntity(40))}
        }
        lifecycleScope.launch {
            val bicepExerciseDao = (application as HelloHealthyApp).bicepExerciseDb.bicepExerciseDao()
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(41))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(42))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(43))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(44))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(45))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(46))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(47))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(48))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(49))}
            lifecycleScope.launch {bicepExerciseDao.insert(BicepExerciseEntity(50))}
        }
        lifecycleScope.launch {
            val tricepAndAbsDao = (application as HelloHealthyApp).tricepAndAbsDb.tricepAndAbsDao()
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(8))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(16))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(51))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(52))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(53))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(54))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(55))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(56))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(57))}
            lifecycleScope.launch {tricepAndAbsDao.insert(TricepAndAbsEntity(58))}
        }
        lifecycleScope.launch {
            val shoulderDao = (application as HelloHealthyApp).shoulderDb.shoulderDao()
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(1))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(48))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(59))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(60))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(61))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(62))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(63))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(64))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(65))}
            lifecycleScope.launch {shoulderDao.insert(ShoulderEntity(66))}
        }
        lifecycleScope.launch {
            val legsDao = (application as HelloHealthyApp).legsDb.legsDao()
            lifecycleScope.launch {legsDao.insert(LegsEntity(2))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(5))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(9))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(17))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(18))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(19))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(20))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(21))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(22))}
            lifecycleScope.launch {legsDao.insert(LegsEntity(23))}
        }

        // Обновляем данные о первом запуске и закрываем диалоговое окно
        val pref = getSharedPreferences("exercise_addition", MODE_PRIVATE)
        val prefEditor = pref.edit()
        prefEditor.putBoolean("first_start", false)
        prefEditor.apply()
        dialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Освобождаем привязку к разметке активити при уничтожении активити
        binding = null
    }
}