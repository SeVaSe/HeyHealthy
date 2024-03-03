package com.example.adi.hellohealthy.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adi.hellohealthy.databinding.ExerciseNameAndImgViewHolderForEachSetBinding
import com.example.adi.hellohealthy.others.Exercise

// Адаптер для списка упражнений, выполненных в определенном сете
class ExerciseListPerformedAdapter(private val exerciseList: ArrayList<Exercise>): RecyclerView.Adapter<ExerciseListPerformedAdapter.ViewHolderExerciseListPerformed>() {

    // ViewHolder для элементов списка упражнений
    class ViewHolderExerciseListPerformed(binding: ExerciseNameAndImgViewHolderForEachSetBinding): RecyclerView.ViewHolder(binding.root) {
        val exerciseNumber = binding.exerciseNumber // Номер упражнения
        val exerciseName = binding.exerciseName // Название упражнения
    }

    // Создание ViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderExerciseListPerformed {
        return ViewHolderExerciseListPerformed(ExerciseNameAndImgViewHolderForEachSetBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // Привязка данных к элементу списка
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderExerciseListPerformed, position: Int) {
        holder.exerciseNumber.text = (position + 1).toString() // Устанавливаем номер упражнения
        holder.exerciseName.text = exerciseList[position].getName() // Устанавливаем название упражнения
    }

    // Возвращает общее количество элементов в списке
    override fun getItemCount(): Int {
        return exerciseList.size
    }
}
