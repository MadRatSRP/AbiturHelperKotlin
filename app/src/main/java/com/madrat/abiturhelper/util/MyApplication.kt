package com.madrat.abiturhelper.util

import android.app.Application

class MyApplication: Application() {

    var maths: Int? = null
    var russian: Int? = null
    var physics: Int? = null
    var computerScience: Int? = null
    var socialScience: Int? = null

    companion object {
        val instance = MyApplication()
    }

    fun saveMaths(maths: Int?) {
        this.maths = maths
        showLog("Новое значение maths: ${this.maths}")
    }
    fun saveRussian(russian: Int?) {
        this.russian = russian
        showLog("Новое значение russian: ${this.russian}")
    }
    fun savePhysics(physics: Int?) {
        this.physics = physics
        showLog("Новое значение physics: ${this.physics}")
    }
    fun saveComputerScience(computerScience: Int?) {
        this.computerScience = computerScience
        showLog("Новое значение computer science: ${this.computerScience}")
    }
    fun saveSocialScience(socialScience: Int?) {
        this.socialScience = socialScience
        showLog("Новое значение social science: ${this.socialScience}")
    }

    fun returnMaths(): Int? {
        return maths
        showLog("Получен maths: ${this.maths}")
    }
    fun returnRussian(): Int? {
        return russian
        showLog("Получен russian: ${this.russian}")
    }
    fun returnPhysics(): Int? {
        return physics
        showLog("Получен physics: ${this.physics}")
    }
    fun returnComputerScience(): Int? {
        return computerScience
        showLog("Получен computerScience: ${this.computerScience}")
    }
    fun returnSocialScience(): Int? {
        return socialScience
        showLog("Получен socialScience: ${this.socialScience}")
    }
}