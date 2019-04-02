package com.madrat.abiturhelper.util

import android.app.Application

class MyApplication: Application() {
    companion object {
        val instance = MyApplication()
    }
    private var maths: Int? = null
    private var russian: Int? = null
    private var physics: Int? = null
    private var computerScience: Int? = null
    private var socialScience: Int? = null
    private var essay: Int? = null
    private var letter: Int? = null
    private var gto: Int? = null

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
    fun saveEssay(essay: Int?) {
        this.essay = essay
        showLog("Новое значение essay: ${this.essay}")
    }
    fun saveLetter(letter: Int?) {
        this.letter = letter
        showLog("Новое значение letter: ${this.letter}")
    }
    fun saveGTO(gto: Int?) {
        this.gto = gto
        showLog("Новое значение gto: ${this.gto}")
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
    fun returnEssay(): Int? {
        return essay
        showLog("Получен essay: ${this.essay}")
    }
    fun returnLetter(): Int? {
        return letter
        showLog("Получен letter: ${this.letter}")
    }
    fun returnGTO(): Int? {
        return gto
        showLog("Получен gto: ${this.gto}")
    }
}