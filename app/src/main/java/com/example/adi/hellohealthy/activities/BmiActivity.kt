package com.example.adi.hellohealthy.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.adi.hellohealthy.R
import com.example.adi.hellohealthy.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class BmiActivity : AppCompatActivity() {

    // Объявляем переменную для привязки к разметке активити
    private var binding: ActivityBmiBinding? = null

    // Аннотация для игнорирования предупреждения об использовании методов,
    // помеченных как "deprecated"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        // Инициализируем привязку к разметке активити
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

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

        // Устанавливаем видимость раздела с результатами расчета в начальное состояние
        binding?.lLYourBmiAndDescription?.visibility = View.GONE

        // Устанавливаем панель инструментов и кнопку "Назад" на панели инструментов
        setSupportActionBar(binding?.bmiCalcActivityToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.bmiCalcActivityToolBar?.setNavigationOnClickListener {
            finish()
        }

        // Устанавливаем слушатель изменения выбора единиц измерения
        binding?.radioGrpUnit?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbMetricUnits) {
                // Если выбраны метрические единицы, скрываем поля для футов и дюймов,
                // очищаем их и меняем подсказки для полей ввода
                binding?.lLYourBmiAndDescription?.visibility = View.GONE
                binding?.eTInches?.text?.clear()
                binding?.eTWeight?.text?.clear()
                binding?.eTHeightOrFeet?.text?.clear()
                binding?.tILInches?.visibility = View.GONE
                binding?.tIWeight?.setHint(R.string.weight_in_kg)
                binding?.tILHeight0rFeet?.setHint(R.string.height_in_cm)
            } else {
                // Если выбраны английские единицы, показываем поля для футов и дюймов
                // и меняем подсказки для полей ввода
                binding?.lLYourBmiAndDescription?.visibility = View.GONE
                binding?.tILInches?.visibility = View.VISIBLE
                binding?.eTInches?.text?.clear()
                binding?.eTWeight?.text?.clear()
                binding?.eTHeightOrFeet?.text?.clear()
                binding?.tIWeight?.setHint(R.string.weight_in_pounds)
                binding?.tILHeight0rFeet?.setHint(R.string.feet)
            }
        }

        // Устанавливаем обработчик нажатия кнопки "Рассчитать"
        binding?.btnCalcBmi?.setOnClickListener {
            // Проверяем, введены ли допустимые значения
            if (validateMetricUnit()) {
                // Если введены допустимые значения, расчитываем ИМТ
                binding?.lLYourBmiAndDescription?.visibility = View.VISIBLE
                val heightInMeter: Float
                val feet: Float
                val inch: Float
                val weightInKg: Float
                val weightInPounds: Float
                if (binding?.rbMetricUnits?.isChecked!!) {
                    // Если выбраны метрические единицы, преобразуем значения
                    heightInMeter = binding?.eTHeightOrFeet?.text.toString().toFloat() / 100
                    weightInKg = binding?.eTWeight?.text.toString().toFloat()
                } else {
                    // Если выбраны английские единицы, преобразуем значения
                    feet = binding?.eTHeightOrFeet?.text.toString().toFloat()
                    inch = if (binding?.eTInches?.text.isNullOrEmpty()) {
                        0f
                    } else {
                        binding?.eTInches?.text.toString().toFloat()
                    }
                    weightInPounds = binding?.eTWeight?.text.toString().toFloat()
                    weightInKg = 0.45359236f * weightInPounds
                    heightInMeter = ((feet * 12f) + inch) * 0.0254f
                }
                // Рассчитываем ИМТ и выводим результаты на экран
                val bmi: Float = weightInKg / heightInMeter.pow(2)

                binding?.bmiValueTV?.text = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
                binding?.bmiTypeTV?.text = when (bmi) {
                    in 0f..15f -> "Очень сильный дефицит веса"
                    in 15f..16f -> "Сильно недостаточный вес"
                    in 16f..18.5f -> "Недовес"
                    in 18.5f..25f -> "Нормальный"
                    in 25f..30f -> "Избыточный вес"
                    in 30f..35f -> "Ожирение 1 класса (умеренное ожирение)"
                    in 35f..40f -> "Ожирение 2 класса (тяжелое ожирение)"
                    else -> "Ожирение 3 класса (очень тяжелое ожирение)"
                }
                binding?.bmiDescriptionTV?.text = when (bmi) {
                    in 0f..18.5f -> "Упс! Вам действительно нужно лучше заботиться о себе! Ешьте больше!"
                    in 18.5f..25f -> "Поздравляем! Вы в хорошей форме!"
                    in 25f..35f -> "Упс! Вам действительно нужно лучше заботиться о себе! Начните тренироваться!"
                    else -> "МОЙ БОГ! Вы находитесь в очень опасном состоянии! Действуйте сейчас!"
                }
            } else {
                // Если введены недопустимые значения, очищаем поля ввода и выводим сообщение
                binding?.lLYourBmiAndDescription?.visibility = View.GONE
                binding?.eTHeightOrFeet?.text?.clear()
                binding?.eTWeight?.text?.clear()
                binding?.eTInches?.text?.clear()
                Toast.makeText(this, "Пожалуйста, введите допустимые значения", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Функция для проверки введенных данных
    private fun validateMetricUnit(): Boolean {
        var valid = true

        // Проверяем, заполнены ли все поля и не равны ли нулю
        if (binding?.eTWeight?.text.isNullOrEmpty() || binding?.eTHeightOrFeet?.text.isNullOrEmpty() || binding?.eTWeight?.text.toString().toFloat() == 0f || binding?.eTHeightOrFeet?.text.toString().toFloat() == 0f) {
            valid = false
        }

        return valid
    }

    override fun onDestroy() {
        super.onDestroy()
        // Освобождаем привязку к разметке активити при уничтожении активити
        binding = null
    }
}
