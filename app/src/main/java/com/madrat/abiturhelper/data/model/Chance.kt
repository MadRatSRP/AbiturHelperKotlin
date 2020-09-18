package com.madrat.abiturhelper.data.model

data class Chance(
        // Название специальности и факультета
        val specialtyName: String,
        val facultyName: String,
        // Шанс поступления и минимальный балл
        val minimalScore: Int,
        val chance: Double?,
        // Количество мест, поступающих и предполагаемая позиция
        val totalOfEntries: Int,
        val amountOfStudents: Int?,
        val supposedPosition: Int?
)