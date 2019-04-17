package com.madrat.abiturhelper.util

import android.app.Application
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.Student

class MyApplication: Application() {
    companion object { val instance = MyApplication() }
    private var scores: Score? = null
    private var bachelors: ArrayList<Student>? = null
    private var additionalScore: Int? = 0

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

    fun returnScore(): Score? {
        showLog("Получена модель Score: ${this.scores}")
        return scores
    }
    /*fun returnMaths(): Int? {
        showLog("Получен maths: ${this.maths}")
        return maths
    }
    fun returnRussian(): Int? {
        showLog("Получен russian: ${this.russian}")
        return russian
    }
    fun returnPhysics(): Int? {
        showLog("Получен physics: ${this.physics}")
        return physics
    }
    fun returnComputerScience(): Int? {
        showLog("Получен computerScience: ${this.computerScience}")
        return computerScience
    }
    fun returnSocialScience(): Int? {
        showLog("Получен socialScience: ${this.socialScience}")
        return socialScience
    }*/
    fun returnBachelors(): ArrayList<Student>? {
        showLog("Получен bachelors, его size: ${this.bachelors?.size}")
        return bachelors
    }
}