package com.example.adi.hellohealthy.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.adi.hellohealthy.R

// Активность для обработки пользовательских запросов с помощью GPT модели в области спорта
class GptSportActivity : AppCompatActivity() {

    // ViewModel для взаимодействия с бизнес-логикой активности
    private lateinit var viewModel: GPTActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет активности
        setContentView(R.layout.activity_gpt_sport)
        // Инициализируем ViewModel
        viewModel = ViewModelProvider(this)[GPTActivityViewModel::class.java]
        // Находим прогресс-бар
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)

        // Устанавливаем ActionBar и его параметры
        setSupportActionBar(findViewById(R.id.GptSportActivityToolBar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("GPT-спорт")

        // Наблюдаем за LiveData с ответом от GPT модели
        viewModel.gptResponseLiveData.observe(this) {
            findViewById<TextView>(R.id.answerTextView).text = it
            progressbar.visibility = View.INVISIBLE
        }

        // Находим поле ввода и кнопку
        val gptQuestionText = findViewById<EditText>(R.id.userQuestionFieldEditText)
        val buttonClick: Button = findViewById<View>(R.id.button) as Button

        // Обработчик нажатия на кнопку для отправки запроса к GPT модели
        buttonClick.setOnClickListener {
            // Проверяем доступность Wi-Fi
            if (isWifiEnabled()) {
                val question = gptQuestionText.text.toString().trim()
                if (question.isNotEmpty()) {
                    // Обновляем вопрос в ViewModel и выполняем запрос к GPT модели
                    viewModel.updateGptQueryQuestion(question)
                    viewModel.makeGptCall()
                    progressbar.visibility = View.VISIBLE
                } else {
                    // Если поле ввода пустое, выводим сообщение
                    Toast.makeText(this, "Введите вопрос", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Если Wi-Fi отключен, выводим предупреждение
                Toast.makeText(this, "Пожалуйста, включите Wi-Fi и VPN", Toast.LENGTH_SHORT).show()
            }
        }

        // Очищаем поле ввода после отправки запроса и получения ответа
        viewModel.gptResponseLiveData.observe(this) {
            findViewById<TextView>(R.id.answerTextView).text = it
            progressbar.visibility = View.INVISIBLE
            // Очищаем поле ввода после получения ответа
            gptQuestionText.setText("")
        }

        // Обновляем вопрос в ViewModel при изменении текста в поле ввода
        gptQuestionText.doOnTextChanged { text, _, _, _ ->
            viewModel.updateGptQueryQuestion(text.toString())
        }
    }

    // Проверка доступности Wi-Fi
    private fun isWifiEnabled(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return networkInfo?.isConnected ?: false
    }

    // Обработка нажатия на элемент ActionBar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
