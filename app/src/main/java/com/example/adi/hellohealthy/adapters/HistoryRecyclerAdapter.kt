package com.example.adi.hellohealthy.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adi.hellohealthy.database.entity.HistoryEntity
import com.example.adi.hellohealthy.databinding.HistoryItemViewHolderNewBinding

// Адаптер для RecyclerView, отображающий историю выполненных наборов упражнений
class HistoryRecyclerAdapter(
    private val historyList: ArrayList<HistoryEntity>, // Список выполненных наборов упражнений
    private val deleteListener: (date: String) -> Unit, // Слушатель для удаления набора
    private val showExercisesList: (exerciseArrStr: String) -> Unit // Слушатель для отображения списка упражнений
) : RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolderHistory>(){

    // ViewHolder для элементов списка истории
    class ViewHolderHistory(binding: HistoryItemViewHolderNewBinding): RecyclerView.ViewHolder(binding.root) {
        val setName = binding.setName // Текстовое поле для названия набора
        val dateAndTime = binding.setDateAndTime // Текстовое поле для даты и времени выполнения набора
        val deleteBtn = binding.iBDeleteSet // Кнопка для удаления набора
        val marginTv = binding.tVToGiveMargin // Дополнительное текстовое поле для создания отступа
        val llGetList = binding.llGetList // Контейнер для отображения списка упражнений
    }

    // Создание ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistory {
        return ViewHolderHistory(HistoryItemViewHolderNewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // Привязка данных к элементу списка
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderHistory, position: Int) {
        // Проверка, является ли текущий элемент последним в списке, для отображения отступа
        if (position == historyList.size - 1) {
            holder.marginTv.visibility = View.VISIBLE
        } else {
            holder.marginTv.visibility = View.GONE
        }
        val set = historyList[position]
        holder.setName.text = set.name // Установка названия набора
        holder.dateAndTime.text = "on ${set.date.substring(0, 10)} at ${set.date.substring(11, set.date.length)}" // Установка даты и времени выполнения набора
        // Обработчик нажатия на кнопку удаления набора
        holder.deleteBtn.setOnClickListener {
            deleteListener.invoke(set.date)
        }
        // Обработчик нажатия на контейнер для отображения списка упражнений
        holder.llGetList.setOnClickListener {
            showExercisesList.invoke(set.exerciseListString)
        }
    }

    // Возвращает общее количество элементов в списке
    override fun getItemCount(): Int {
        return historyList.size
    }
}
