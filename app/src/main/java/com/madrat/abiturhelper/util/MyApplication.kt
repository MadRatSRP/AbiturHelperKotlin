package com.madrat.abiturhelper.util

import android.app.Application
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.Student

class MyApplication: Application() {
    companion object { val instance = MyApplication() }
    private var scores: Score? = null
    private var bachelors: ArrayList<Student>? = null
    /*private var maths: Int? = null
    private var russian: Int? = null
    private var physics: Int? = null
    private var computerScience: Int? = null
    private var socialScience: Int? = null*/

    fun saveMaths(maths: Int) {
        this.scores?.maths = maths
        showLog("Новое значение maths: ${this.scores?.maths}")
    }
    fun saveRussian(russian: Int) {
        this.scores?.russian = russian
        showLog("Новое значение russian: ${this.scores?.russian}")
    }
    fun savePhysics(physics: Int) {
        this.scores?.physics = physics
        showLog("Новое значение physics: ${this.scores?.physics}")
    }
    fun saveComputerScience(computerScience: Int) {
        this.scores?.computerScience = computerScience
        showLog("Новое значение computer science: ${this.scores?.computerScience}")
    }
    fun saveSocialScience(socialScience: Int) {
        this.scores?.socialScience = socialScience
        showLog("Новое значение social science: ${this.scores?.socialScience}")
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