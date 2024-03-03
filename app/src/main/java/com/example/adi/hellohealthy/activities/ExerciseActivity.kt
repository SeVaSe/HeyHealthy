package com.example.adi.hellohealthy.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adi.hellohealthy.*
import com.example.adi.hellohealthy.adapters.ExerciseStatusAdapter
import com.example.adi.hellohealthy.database.entity.HistoryEntity
import com.example.adi.hellohealthy.databinding.ActivityExerciseBinding
import com.example.adi.hellohealthy.databinding.IcCustomDialogBackConfirmationBinding
import com.example.adi.hellohealthy.others.Constants
import com.example.adi.hellohealthy.others.Exercise
import com.example.adi.hellohealthy.others.HelloHealthyApp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // Привязка к разметке через ViewBinding
    private var binding: ActivityExerciseBinding? = null

    // Переменные для управления таймером
    private var countDownTimer: CountDownTimer? = null
    private var millisRemainingForRest: Long = 10000
    private var restTimerRunning: Boolean = false
    private var millisRemainingForExercise: Long = 30000
    private var exerciseTimerRunning: Boolean = false
    private var isRestingTime: Boolean = false

    // Переменные для управления упражнениями
    private var exListName: String? = null
    private var exerciseList = ArrayList<Exercise>()
    private var currentExercisePosition = 0
    private var exercisesArrStr = ""

    // Переменная для TextToSpeech
    private var tts: TextToSpeech? = null

    // Адаптер для RecyclerView с информацией о выполнении упражнений
    private var exerciseRVAdapter: ExerciseStatusAdapter? = null

    // Создание активити
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация ViewBinding
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Инициализация TextToSpeech
        tts = TextToSpeech(this, this)

        // Установка панели инструментов и кнопки "Назад" на панели инструментов
        setSupportActionBar(binding?.exerciseActivityToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.exerciseActivityToolBar?.setNavigationOnClickListener {
            customDialogForBackBtn()
        }

        // Обработка нажатия кнопки "Назад" для Android 12 и выше
        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                customDialogForBackBtn()
            }
        } else {
            // Обработка нажатия кнопки "Назад" для версий Android ниже 12
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    customDialogForBackBtn()
                }
            })
        }

        // Получение имени списка упражнений из предыдущей активити
        exListName = intent.getStringExtra(Constants.EXERCISE_LIST_NAME)
        supportActionBar?.title = exListName

        // Установка текста таймера в зависимости от текущего состояния (отдых или упражнение)
        if (isRestingTime) {
            binding?.tVProgressRest?.text = "10"
        } else {
            binding?.tVProgressRest?.text = "30"
        }

        // Обработчик нажатия кнопки "Пауза/Воспроизведение"
        binding?.pauseOrPlayBtn?.setOnClickListener {
            if (isRestingTime && !restTimerRunning) {
                // Если текущий период отдыха и таймер отдыха не запущен, запустить его
                binding?.pauseOrPlayBtn?.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                binding?.tVPauseOrPlay?.setText(R.string.pause)
                binding?.tVNote?.visibility = View.INVISIBLE
                restTimerRunning = true
                startCountDown(millisRemainingForRest)
            } else if (!isRestingTime && !exerciseTimerRunning) {
                // Если текущее упражнение и таймер упражнения не запущен, запустить его
                binding?.pauseOrPlayBtn?.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                binding?.tVPauseOrPlay?.setText(R.string.pause)
                binding?.tVNote?.visibility = View.INVISIBLE
                exerciseTimerRunning = true
                startCountDown(millisRemainingForExercise)
            } else {
                // Если таймер запущен, поставить на паузу
                pauseCountDown()
            }
        }

        // Обработчик нажатия кнопки "Перезапустить упражнение"
        binding?.restartExerciseBtn?.setOnClickListener {
            restartExercise()
        }

        // Обработчик нажатия кнопки "Перезапустить набор"
        binding?.restartSetBtn?.setOnClickListener {
            restartSet()
        }

        // Обработчик нажатия кнопки "Домой"
        binding?.homeBtn?.setOnClickListener {
            val intent = Intent(this@ExerciseActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Загрузка списка упражнений и настройка UI
        lifecycleScope.launch {
            exerciseList = getExerciseListBySetName(exListName!!)
            setUpUiAndExerciseStatusRV()
        }
    }

    // Настройка пользовательского интерфейса и списка упражнений в RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpUiAndExerciseStatusRV() {
        var i = 0
        // Составление строки с идентификаторами упражнений для записи в базу данных
        while (i < exerciseList.size) {
            exercisesArrStr = if (i == exerciseList.size - 1) {
                "$exercisesArrStr${exerciseList[i].getId()}"
            } else {
                "$exercisesArrStr${exerciseList[i].getId()},"
            }
            i++
        }
        // Инициализация адаптера для RecyclerView
        exerciseRVAdapter = ExerciseStatusAdapter(exerciseList)
        // Установка менеджера компоновки и адаптера для RecyclerView
        binding?.rVExerciseStatus?.layoutManager =
            LinearLayoutManager(this@ExerciseActivity, LinearLayoutManager.HORIZONTAL, false)
        binding?.rVExerciseStatus?.adapter = exerciseRVAdapter
        // Обновление данных в RecyclerView
        exerciseRVAdapter?.notifyDataSetChanged()
        // Установка изображения и названия текущего упражнения
        binding?.exerciseImgView?.setImageResource(
            exerciseList[currentExercisePosition].getImage()
        )
        binding?.tVTitle?.text = exerciseList[currentExercisePosition].getName()
        // Скрытие текста о предстоящем упражнении
        binding?.upcomingExerciseTextView?.visibility = View.GONE
    }

    // Пользовательский диалог для кнопки "Назад"
    private fun customDialogForBackBtn() {
        pauseCountDown()
        // Создание диалогового окна
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogBinding = IcCustomDialogBackConfirmationBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        // Обработчик нажатия кнопки "Да"
        dialogBinding.yesBtnBackConfirmationDialog.setOnClickListener {
            this@ExerciseActivity.finish()
            dialog.dismiss()
        }
        // Обработчик нажатия кнопки "Нет"
        dialogBinding.noBtnBackConformationDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    // Метод перезапуска набора упражнений
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun restartSet() {
        pauseCountDown()
        // Скрытие кнопки "Домой"
        binding?.llBtnHome?.visibility = View.GONE
        // Загрузка списка упражнений и обновление адаптера
        lifecycleScope.launch {
            exerciseList = getExerciseListBySetName(exListName!!)
            exerciseRVAdapter?.notifyDataSetChanged()
        }
        // Сброс таймеров и установка начальных значений
        millisRemainingForRest = 10000
        millisRemainingForExercise = 30000
        isRestingTime = false
        exerciseTimerRunning = false
        restTimerRunning = false
        binding?.pauseOrPlayBtn?.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
        binding?.tVPauseOrPlay?.setText(R.string.play)
        binding?.tVNote?.visibility = View.VISIBLE
        binding?.lLBtn?.visibility = View.VISIBLE
        binding?.lLRestartExerciseBtn?.visibility = View.VISIBLE
        binding?.upcomingExerciseTextView?.visibility = View.GONE
        currentExercisePosition = 0
        // Установка изображения и названия первого упражнения
        binding?.exerciseImgView?.setImageResource(exerciseList[currentExercisePosition].getImage())
        binding?.tVTitle?.text = exerciseList[currentExercisePosition].getName()
        binding?.tVProgressRest?.text = "30"
        binding?.progressBarRest?.max = 30
        binding?.progressBarRest?.progress = 30
    }

    // Метод перезапуска текущего упражнения
    private fun restartExercise() {
        pauseCountDown()
        isRestingTime = false
        exerciseTimerRunning = true
        millisRemainingForExercise = 30000
        binding?.pauseOrPlayBtn?.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
        binding?.tVPauseOrPlay?.setText(R.string.pause)
        binding?.tVNote?.visibility = View.INVISIBLE
        startCountDown(millisRemainingForExercise)
    }

    // Метод постановки таймера на паузу
    private fun pauseCountDown() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        // Изменение внешнего вида кнопки "Пауза/Воспроизведение"
        binding?.pauseOrPlayBtn?.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
        binding?.tVPauseOrPlay?.setText(R.string.play)
        binding?.tVNote?.visibility = View.VISIBLE
        restTimerRunning = false
        exerciseTimerRunning = false
        // Остановка проигрывания речи
        if (tts != null) {
            tts?.stop()
        }
    }


    // Метод для запуска обратного отсчета времени
    @SuppressLint("NotifyDataSetChanged")
    private fun startCountDown(timeRemaining: Long) {
        // Установка максимального значения прогресса ProgressBar в зависимости от текущего состояния
        if (isRestingTime) {
            binding?.progressBarRest?.max = 10
        } else {
            binding?.progressBarRest?.max = 30
            // Установка текущего упражнения в выбранное состояние и обновление адаптера RecyclerView
            exerciseList[currentExercisePosition].setIsSelected(true)
            exerciseRVAdapter?.notifyDataSetChanged()
        }
        // Установка текущего прогресса и времени в TextView
        binding?.progressBarRest?.progress = (timeRemaining / 1000).toInt()
        binding?.tVProgressRest?.text = (timeRemaining / 1000).toInt().toString()
        // Произношение текста с помощью TextToSpeech
        if (isRestingTime) {
            speakOut("${binding?.tVTitle?.text} ${binding?.upcomingExerciseTextView?.text}")
        } else {
            speakOut("${binding?.tVTitle?.text}")
        }

        // Создание и запуск CountDownTimer
        countDownTimer = object : CountDownTimer(timeRemaining + 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Обновление прогресса и времени на каждом тике таймера
                if (isRestingTime) {
                    millisRemainingForRest = millisUntilFinished
                } else {
                    millisRemainingForExercise = millisUntilFinished
                }
                binding?.progressBarRest?.progress = (millisUntilFinished / 1000).toInt()
                binding?.tVProgressRest?.text = (millisUntilFinished / 1000).toInt().toString()
            }

            override fun onFinish() {
                // Обработка завершения отсчета времени
                millisRemainingForExercise = 30000
                millisRemainingForRest = 10000
                if (isRestingTime) {
                    // Обработка завершения отдыха и переход к следующему упражнению
                    binding?.upcomingExerciseTextView?.visibility = View.GONE
                    binding?.lLRestartExerciseBtn?.visibility = View.VISIBLE
                    isRestingTime = false
                    exerciseTimerRunning = true
                    binding?.exerciseImgView?.setImageResource(exerciseList[currentExercisePosition].getImage())
                    binding?.tVTitle?.text = exerciseList[currentExercisePosition].getName()
                    startCountDown(millisRemainingForExercise)
                } else {
                    // Обработка завершения упражнения и переход к следующему
                    exerciseList[currentExercisePosition].setIsCompleted(true)
                    exerciseRVAdapter?.notifyDataSetChanged()
                    currentExercisePosition++
                    if (currentExercisePosition >= exerciseList.size) {
                        // Если все упражнения завершены, вывод сообщения и добавление в базу данных
                        binding?.lLBtn?.visibility = View.GONE
                        binding?.llBtnHome?.visibility = View.VISIBLE
                        binding?.tVNote?.visibility = View.GONE
                        binding?.exerciseImgView?.setImageResource(R.drawable.ic_well_done)
                        binding?.upcomingExerciseTextView?.visibility = View.GONE
                        binding?.tVTitle?.text = "Набор завершен!!"
                        speakOut("Набор поздравлений завершен")
                        addToDataBase()
                    } else {
                        // Если есть еще упражнения, переход к отдыху перед следующим упражнением
                        binding?.lLRestartExerciseBtn?.visibility = View.GONE
                        isRestingTime = true
                        restTimerRunning = true
                        binding?.exerciseImgView?.setImageResource(R.drawable.ic_rest)
                        binding?.tVTitle?.setText(R.string.get_ready_for)
                        binding?.upcomingExerciseTextView?.visibility = View.VISIBLE
                        exerciseList[currentExercisePosition].setIsSelected(true)
                        exerciseRVAdapter?.notifyDataSetChanged()
                        binding?.upcomingExerciseTextView?.text = "Следующее упражнение ${exerciseList[currentExercisePosition].getName()}"
                        startCountDown(millisRemainingForRest)
                    }
                }
            }
        }.start()
    }

    // Метод для добавления информации о выполненном наборе в базу данных
    private fun addToDataBase() {
        val historyDao = (application as HelloHealthyApp).historyDb.historyDao()
        val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss aaa", Locale.getDefault())
        val date = sdf.format(dateTime)
        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date, exListName!!, exercisesArrStr))
        }
    }

    // Метод, вызываемый при инициализации TextToSpeech
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Установка языка для TextToSpeech
            val result = tts?.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Указанный язык не поддерживается")
            }
        } else {
            Log.e("TTS", "Ошибка инициализации")
        }
    }

    // Метод для произнесения текста с помощью TextToSpeech
    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    // Функция для получения списка упражнений по имени набора
    private suspend fun getExerciseListBySetName(exListName: String): ArrayList<Exercise> {
        // Получение полного списка упражнений из Constants
        val allExerciseList = Constants.getAllExerciseList()
        // Создание списка для хранения результатов
        var ans = ArrayList<Exercise>()
        // В зависимости от имени набора выбирается соответствующая база данных и заполняется список ans
        when (exListName) {
            Constants.MISCELLANEOUS_LIST -> {
                val miscellaneousExercisesDao = (application as HelloHealthyApp).miscellaneousExercisesDb.miscellaneousExercisesDao()
                val idList = ArrayList(miscellaneousExercisesDao.fetchAllMiscellaneousExercises())
                for (id in idList) {
                    ans.add(allExerciseList[id])
                }
            }
            // Остальные случаи аналогичны
            Constants.CHEST_LIST -> {
                val chestExerciseDao = (application as HelloHealthyApp).chestExerciseDb.chestExerciseDao()
                val idList = ArrayList(chestExerciseDao.fetchAllChestExercises())
                for (id in idList) {
                    ans.add(allExerciseList[id])
                }
            }
            Constants.BICEP_LIST -> {
                val bicepExerciseDao = (application as HelloHealthyApp).bicepExerciseDb.bicepExerciseDao()
                val idList = ArrayList(bicepExerciseDao.fetchAllBicepExercises())
                for (id in idList) {
                    ans.add(allExerciseList[id])
                }
            }
            Constants.TRICEP_AND_ABS_LIST -> {
                val tricepAndAbsDao = (application as HelloHealthyApp).tricepAndAbsDb.tricepAndAbsDao()
                val idList = ArrayList(tricepAndAbsDao.fetchAllTricepAndAbsExercises())
                for (id in idList) {
                    ans.add(allExerciseList[id])
                }
            }
            Constants.SHOULDER_LIST -> {
                val shoulderDao = (application as HelloHealthyApp).shoulderDb.shoulderDao()
                val idList = ArrayList(shoulderDao.fetchAllShoulderExercises())
                for (id in idList) {
                    ans.add(allExerciseList[id])
                }
            }
            Constants.LEGS_LIST -> {
                val legsDao = (application as HelloHealthyApp).legsDb.legsDao()
                val idList = ArrayList(legsDao.fetchAllLegsExercises())
                for (id in idList) {
                    ans.add(allExerciseList[id])
                }
            }
            else -> {
                ans = allExerciseList
            }
        }
        // Установка начальных значений флагов выбранности и завершенности упражнений
        for(exercise in ans) {
            exercise.setIsSelected(false)
            exercise.setIsCompleted(false)
        }
        return ans
    }

    // Метод onDestroy() для очистки ресурсов и остановки таймеров
    override fun onDestroy() {
        // Остановка таймера, если он запущен, и сброс состояний таймеров
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            exerciseTimerRunning = false
            restTimerRunning = false
            millisRemainingForExercise = 30000
            millisRemainingForRest = 10000
            isRestingTime = false
        }
        // Вызов метода onDestroy суперкласса
        super.onDestroy()
        // Остановка TextToSpeech
        if (tts != null) {
            tts?.stop()
        }
        // Очистка ссылки на binding
        binding = null
    }
}