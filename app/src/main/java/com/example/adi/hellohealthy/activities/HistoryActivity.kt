package com.example.adi.hellohealthy.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adi.hellohealthy.*
import com.example.adi.hellohealthy.adapters.ExerciseListPerformedAdapter
import com.example.adi.hellohealthy.adapters.HistoryRecyclerAdapter
import com.example.adi.hellohealthy.database.dao.HistoryDao
import com.example.adi.hellohealthy.database.entity.HistoryEntity
import com.example.adi.hellohealthy.databinding.ActivityHistoryBinding
import com.example.adi.hellohealthy.databinding.ExerciseListForEachSetDialogBinding
import com.example.adi.hellohealthy.databinding.HistoryDeleteDiialogBinding
import com.example.adi.hellohealthy.others.Constants
import com.example.adi.hellohealthy.others.HelloHealthyApp
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    // View binding для этой активности
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет активности и инициализируем view binding
        setContentView(R.layout.activity_history)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Устанавливаем ActionBar и его параметры
        setSupportActionBar(binding?.historyActivityToolBar)
        supportActionBar?.setTitle(R.string.history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Получаем доступ к базе данных и настраиваем обработчик нажатия на кнопку назад
        val historyDao = (application as HelloHealthyApp).historyDb.historyDao()
        setupBackButton()

        // Наблюдаем за данными из базы данных и обновляем список прошедших тренировок
        lifecycleScope.launch {
            historyDao.fetchAllSetsInPast().collect {
                val list = ArrayList(it)
                setUpListOfSetsInRecyclerView(list, historyDao)
            }
        }
    }

    // Настройка обработчика нажатия на кнопку назад
    private fun setupBackButton() {
        // Используем новый API для API >= 33 и резервный обработчик для старых версий
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

        // Настройка нажатия на кнопку назад в ActionBar
        binding?.historyActivityToolBar?.setNavigationOnClickListener {
            finish()
        }
    }

    // Настройка RecyclerView для отображения списка тренировок
    private fun setUpListOfSetsInRecyclerView(historyList: ArrayList<HistoryEntity>, historyDao: HistoryDao) {
        if (historyList.isNotEmpty()) {
            val historyAdapter = HistoryRecyclerAdapter(historyList, { deleteDate ->
                deleteSetPerformed(historyDao, deleteDate)
            }, { exerciseArrStr ->
                showExerciseListDialog(exerciseArrStr)
            })
            val lLM = LinearLayoutManager(this)
            lLM.reverseLayout = true
            lLM.scrollToPosition(historyList.size - 1)
            binding?.historyRecyclerView?.layoutManager = lLM
            binding?.historyRecyclerView?.adapter = historyAdapter
            binding?.historyRecyclerView?.visibility = View.VISIBLE
            binding?.tv?.visibility = View.GONE
        } else {
            binding?.historyRecyclerView?.visibility = View.GONE
            binding?.tv?.visibility = View.VISIBLE
        }
    }

    // Показывает диалог с подробным списком упражнений для каждого выполненного набора
    @SuppressLint("NotifyDataSetChanged")
    private fun showExerciseListDialog(exerciseArrStr: String) {
        val exerciseList = Constants.getExerciseListForDisplayingOnHistoryDialog(exerciseArrStr)
        val exerciseListAdapter = ExerciseListPerformedAdapter(exerciseList)
        val dialog = Dialog(this)
        val dialogBinding = ExerciseListForEachSetDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.exerciseListRV.layoutManager = LinearLayoutManager(this)
        dialogBinding.exerciseListRV.adapter = exerciseListAdapter
        exerciseListAdapter.notifyDataSetChanged()
        dialog.show()
    }

    // Удаляет выполненный набор тренировки по указанной дате
    private fun deleteSetPerformed(historyDao: HistoryDao, date: String) {
        val dialog = Dialog(this)
        val dialogBinding = HistoryDeleteDiialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.yesDeleteBtn.setOnClickListener {
            lifecycleScope.launch {
                historyDao.delete(HistoryEntity(date))
            }
            dialog.dismiss()
        }
        dialogBinding.noDeleteBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    // Освобождаем ресурсы при уничтожении активности
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
