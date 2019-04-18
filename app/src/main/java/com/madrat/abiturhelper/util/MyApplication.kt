package com.madrat.abiturhelper.util

import android.app.Application
import com.madrat.abiturhelper.model.Faculties
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.ScoreTypes
import com.madrat.abiturhelper.model.Student

class MyApplication: Application() {
    companion object { val instance = MyApplication() }
    private var scores: Score? = null
    private var bachelors: ArrayList<Student>? = null
    private var additionalScore: Int? = 0

    private var faculties: Faculties? = null

    private var scoreTypes: ScoreTypes? = null

    fun saveScore(scores: Score) {
        this.scores = scores
        showLog("Новое значение основных баллов: ${this.scores}")
    }
    fun saveAdditionalScore(additionalScore: Int) {
        this.additionalScore = additionalScore
        showLog("Новое значение дополнительных баллов: ${this.additionalScore}")
    }
    fun saveBachelors(bachelors: ArrayList<Student>?) {
        this.bachelors = bachelors
        showLog("Новое значение bachelors, его size: ${this.bachelors?.size}")
    }
    fun saveFaculties(faculties: Faculties) {
        this.faculties = faculties
        showLog("Новое значение Faculties: ${this.faculties}")
    }

    fun returnScore(): Score? {
        showLog("Получена модель Score: ${this.scores}")
        return scores
    }
    fun returnBachelors(): ArrayList<Student>? {
        showLog("Получен bachelors, его size: ${this.bachelors?.size}")
        return bachelors
    }



    fun returnFaculties(): Faculties? {
        showLog("Получен Faculties: ${this.faculties}")
        return faculties
    }

    //ScoreTypes
    fun saveScoreTypes(scoreTypes: ScoreTypes) {
        this.scoreTypes = scoreTypes
        this.scoreTypes?.let {
            val physics = it.physicsStudents?.size
            val computerScience = it.computerScienceStudents?.size
            val socialScience = it.socialScienceStudents?.size
            val partAndAllData = it.partAndAllDataStudents?.size
            val noOrNotEnoughData = it.noOrNotEnoughDataStudents?.size

            showLog("Сохранён ScoreTypes: физика - $physics, информатика - $computerScience, " +
                    "обществознание - $socialScience,\n баллы по двум или трём специальностям - $partAndAllData, " +
                    "недостаточно данных - $noOrNotEnoughData")
        }
    }
    fun returnScoreTypes(): ScoreTypes? {
        this.scoreTypes?.let {
            val physics = it.physicsStudents?.size
            val computerScience = it.computerScienceStudents?.size
            val socialScience = it.socialScienceStudents?.size
            val partAndAllData = it.partAndAllDataStudents?.size
            val noOrNotEnoughData = it.noOrNotEnoughDataStudents?.size

            showLog("Возвращён ScoreTypes: физика - $physics, информатика - $computerScience, " +
                    "обществознание - $socialScience,\n баллы по двум или трём специальностям - $partAndAllData, " +
                    "недостаточно данных - $noOrNotEnoughData")
        }
        return scoreTypes
    }
}