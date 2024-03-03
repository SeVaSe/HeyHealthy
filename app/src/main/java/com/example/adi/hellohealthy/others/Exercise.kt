package com.example.adi.hellohealthy.others

// Класс Exercise представляет упражнение в приложении.
class Exercise(
    private var id: Int, // Уникальный идентификатор упражнения.
    private var name: String, // Название упражнения.
    private var image: Int, // Ресурс изображения упражнения.
    private var isCompleted: Boolean, // Флаг, указывающий, завершено ли упражнение.
    private var isSelected: Boolean // Флаг, указывающий, выбрано ли упражнение.
){
    // Методы доступа к приватным полям класса.

    fun getId(): Int {
        return this.id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return this.name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImage(): Int {
        return this.image
    }

    fun setImage(image: Int) {
        this.image = image
    }

    fun getIsCompleted(): Boolean {
        return this.isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return this.isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

    // Метод, заглушка для получения данных об упражнении по его идентификатору.
    // В текущей реализации ничего не делает.
    fun getExercise(id: Int) {
        return
    }
}
