package com.madrat.abiturhelper.util

import android.app.Application
import com.madrat.abiturhelper.model.Student

class MyApplication: Application() {
    companion object { val instance = MyApplication() }
    private var maths: Int? = null
    private var russian: Int? = null
    private var physics: Int? = null
    private var computerScience: Int? = null
    private var socialScience: Int? = null
    private var essay: Int? = null
    private var letter: Int? = null
    private var gto: Int? = null

    private var bachelors: ArrayList<Student>? = null

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
    fun saveBachelors(bachelors: ArrayList<Student>?) {
        this.bachelors = bachelors
        showLog("Новое значение bachelors, его size: ${this.bachelors?.size}")
    }

    fun returnMaths(): Int? {
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
    }
    fun returnEssay(): Int? {
        showLog("Получен essay: ${this.essay}")
        return essay
    }
    fun returnLetter(): Int? {
        showLog("Получен letter: ${this.letter}")
        return letter
    }
    fun returnGTO(): Int? {
        showLog("Получен gto: ${this.gto}")
        return gto
    }
    fun returnBachelors(): ArrayList<Student>? {
        showLog("Получен bachelors, его size: ${this.bachelors?.size}")
        return bachelors
    }
}