package com.madrat.abiturhelper.util

import android.app.Application
import com.madrat.abiturhelper.model.*
import com.madrat.abiturhelper.model.faculties.*

class MyApplication: Application() {
    companion object { val instance = MyApplication() }
    private var scores: Score? = null
    private var additionalScore: Int? = 0
    private var bachelors: ArrayList<Student>? = null
    private var scoreTypes: ScoreTypes? = null
    private var faculties: Faculties? = null

    private var facultyList: ArrayList<Faculty>? = null

    private var currentListOfStudents: ArrayList<Student>? = null

    private var listUNTI: ArrayList<ArrayList<Student>>? = null
    private var listFEU: ArrayList<ArrayList<Student>>? = null
    private var listFIT: ArrayList<ArrayList<Student>>? = null
    private var listMTF: ArrayList<ArrayList<Student>>? = null
    private var listUNIT: ArrayList<ArrayList<Student>>? = null
    private var listFEE: ArrayList<ArrayList<Student>>? = null

    //Score
    fun saveScore(scores: Score) {
        this.scores = scores
        this.scores?.let {
            val maths = it.maths
            val russian = it.russian
            val physics = it.physics
            val computerScience = it.computerScience
            val socialScience = it.socialScience

            showLog("Сохранён Score: математика - $maths, русский язык - $russian,\n физика - $physics," +
                    "информатика - $computerScience, обществознание - $socialScience")
        }
    }
    fun returnScore(): Score? {
        this.scores?.let {
            val maths = it.maths
            val russian = it.russian
            val physics = it.physics
            val computerScience = it.computerScience
            val socialScience = it.socialScience

            showLog("Возвращён Score: математика - $maths, русский язык - $russian,\n физика - $physics," +
                    "информатика - $computerScience, обществознание - $socialScience")
        }
        return scores
    }
    //AdditionalScore
    fun saveAdditionalScore(additionalScore: Int) {
        this.additionalScore = additionalScore
        showLog("Сохранены дополнительные баллы: ${this.additionalScore}")
    }
    fun returnAdditionalScore(): Int? {
        showLog("Возвращёны дополнительные баллы: ${this.additionalScore}")
        return additionalScore
    }
    //Bachelors
    fun saveBachelors(bachelors: ArrayList<Student>?) {
        this.bachelors = bachelors
        showLog("Сохранён bachelors, его размер: ${this.bachelors?.size}")
    }
    fun returnBachelors(): ArrayList<Student>? {
        showLog("Возвращён bachelors, его размер: ${this.bachelors?.size}")
        return bachelors
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
    //Faculties
    fun saveFaculties(faculties: Faculties) {
        this.faculties = faculties
        this.faculties?.let {
            val unti = it.untiList.size
            val feu = it.feuList.size
            val fit = it.fitList.size
            val mtf = it.mtfList.size
            val unit = it.unitList.size
            val fee = it.feeList.size

            showLog("Сохранён Faculties: УНТИ - $unti, ФЭУ - $feu, ФИТ - $fit,\n МТФ - $mtf, " +
                    "УНИТ - $unit, ФЭЭ - $fee")
        }
    }
    fun returnFaculties(): Faculties? {
        this.faculties?.let {
            val unti = it.untiList.size
            val feu = it.feuList.size
            val fit = it.fitList.size
            val mtf = it.mtfList.size
            val unit = it.unitList.size
            val fee = it.feeList.size

            showLog("Возвращён Faculties: УНТИ - $unti, ФЭУ - $feu, ФИТ - $fit,\n МТФ - $mtf, " +
                    "УНИТ - $unit, ФЭЭ - $fee")
        }
        return faculties
    }
    // listUNTI
    fun saveUnti(list: ArrayList<ArrayList<Student>>) {
        this.listUNTI = list
        showLog("Список UNTI сохранён")
    }
    fun returnUNTI(): ArrayList<ArrayList<Student>>? {
        showLog("Возвращён listUNTI, размер: " + listUNTI?.size)
        return listUNTI
    }
    // listFEU
    fun saveFeu(list: ArrayList<ArrayList<Student>>) {
        this.listFEU = list
        showLog("Список FEU сохранён")
    }
    fun returnFEU(): ArrayList<ArrayList<Student>>? {
        showLog("Возвращён listFEU, размер: " + listFEU?.size)
        return listFEU
    }
    // listFIT
    fun saveFIT(list: ArrayList<ArrayList<Student>>) {
        this.listFIT = list
        showLog("Список FIT сохранён")
    }
    fun returnFIT(): ArrayList<ArrayList<Student>>? {
        showLog("Возвращён listFIT, размер: " + listFIT?.size)
        return listFIT
    }
    // listMTF
    fun saveMTF(list: ArrayList<ArrayList<Student>>) {
        this.listMTF = list
        showLog("Список MTF сохранён")
    }
    fun returnMTF(): ArrayList<ArrayList<Student>>? {
        showLog("Возвращён listMTF, размер: " + listMTF?.size)
        return listMTF
    }
    // listUNIT
    fun saveUNIT(list: ArrayList<ArrayList<Student>>) {
        this.listUNIT = list
        showLog("Список UNIT сохранён")
    }
    fun returnUNIT(): ArrayList<ArrayList<Student>>? {
        showLog("Возвращён listUNIT, размер: " + listUNIT?.size)
        return listUNIT
    }
    // listFee
    fun saveFEE(list: ArrayList<ArrayList<Student>>) {
        this.listFEE = list
        showLog("Список ФЭЭ сохранён")
    }
    fun returnFEE(): ArrayList<ArrayList<Student>>? {
        showLog("Возвращён ListFEE, размер: " + listFEE?.size)
        return listFEE
    }

    fun saveFacultyList(facultyList: ArrayList<Faculty>) {
        this.facultyList = facultyList
        showLog("facultyList сохранён")
    }
    fun returnFacultyList(): ArrayList<Faculty>? {
        showLog("Возвращён facultyList, размер: " + facultyList?.size)
        return facultyList
    }

    fun saveCurrentListOfStudents(list: ArrayList<Student>) {
        this.currentListOfStudents = list
        showLog("currentListOfStudents сохранён")
    }
    fun returnCurrentListOfStudents(): ArrayList<Student>? {
        showLog("Возвращён currentListOfStudents, размер: " + currentListOfStudents?.size)
        return currentListOfStudents
    }
}