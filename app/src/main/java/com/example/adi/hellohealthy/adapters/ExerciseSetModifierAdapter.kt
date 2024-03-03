package com.example.adi.hellohealthy.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adi.hellohealthy.R
import com.example.adi.hellohealthy.databinding.SerchViewViewHolderBinding
import com.example.adi.hellohealthy.others.Exercise

// Адаптер для модификации набора упражнений
class ExerciseSetModifierAdapter(
    private val allExercisesList: ArrayList<Exercise>, // Список всех упражнений
    private val selectedExerciseList: ArrayList<Exercise>, // Список выбранных упражнений
    private var filterList: ArrayList<Exercise>, // Отфильтрованный список упражнений
    private val addOrRemoveListener: (id: Int, contains: Boolean) -> Unit // Слушатель для добавления или удаления упражнений
):
    RecyclerView.Adapter<ExerciseSetModifierAdapter.ViewHolderExerciseSetModifier>()
{

    // ViewHolder для элементов списка
    class ViewHolderExerciseSetModifier(binding: SerchViewViewHolderBinding): RecyclerView.ViewHolder(binding.root) {
        val exName = binding.exerciseName // Название упражнения
        val addOrRemoveBtn = binding.addOrRemoveIB // Кнопка для добавления или удаления упражнения
    }

    // Установка отфильтрованного списка
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filterList: ArrayList<Exercise>) {
        this.filterList = filterList
        notifyDataSetChanged()
    }

    // Создание ViewHolder
    override fun onCreateViewHolder (
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderExerciseSetModifier {
        return ViewHolderExerciseSetModifier(SerchViewViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // Привязка данных к элементу списка
    override fun onBindViewHolder(holder: ViewHolderExerciseSetModifier, position: Int) {
        val exercise : Exercise = if (filterList.isNotEmpty()) {
            filterList[position]
        } else {
            allExercisesList[position]
        }
        holder.exName.text = exercise.getName() // Установка названия упражнения

        // Проверка, содержится ли упражнение в списке выбранных
        var contains = selectedExerciseList.contains(exercise)

        // Установка иконки кнопки в зависимости от наличия упражнения в списке выбранных
        if (contains) {
            holder.addOrRemoveBtn.setImageResource(R.drawable.ic_remove)
        } else {
            holder.addOrRemoveBtn.setImageResource(R.drawable.ic_add)
        }

        // Обработчик нажатия на кнопку
        holder.addOrRemoveBtn.setOnClickListener {
            addOrRemoveListener.invoke(exercise.getId(), contains) // Вызов слушателя для добавления или удаления упражнений

            // Проверка ограничения по количеству выбранных упражнений (от 5 до 15)
            if ((contains && selectedExerciseList.size > 5) || (!contains && selectedExerciseList.size < 15)) {
                if (contains) {
                    // Если упражнение было выбрано, удаляем его из списка выбранных и меняем иконку на "Добавить"
                    if (filterList.isNotEmpty()) {
                        selectedExerciseList.remove(filterList[position])
                    } else {
                        selectedExerciseList.remove(allExercisesList[position])
                    }
                    holder.addOrRemoveBtn.setImageResource(R.drawable.ic_add)
                } else {
                    // Если упражнение не было выбрано, добавляем его в список выбранных и меняем иконку на "Удалить"
                    if (filterList.isNotEmpty()) {
                        selectedExerciseList.add(filterList[position])
                    } else {
                        selectedExerciseList.add(allExercisesList[position])
                    }
                    holder.addOrRemoveBtn.setImageResource(R.drawable.ic_remove)
                }
                contains = !contains
            }
        }
    }

    // Возвращает общее количество элементов в списке
    override fun getItemCount(): Int {
        return if (filterList.isNotEmpty()) {
            filterList.size
        } else {
            allExercisesList.size
        }
    }
}
