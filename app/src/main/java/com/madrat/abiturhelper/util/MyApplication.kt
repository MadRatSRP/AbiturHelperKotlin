package com.madrat.abiturhelper.util

import android.app.Application
import android.util.SparseBooleanArray
import com.madrat.abiturhelper.model.*

class MyApplication: Application() {
    companion object { val instance = MyApplication() }
    // Score
    private var fullName: FullName? = null

    private var scores: Score? = null

    private var scoreTypes: ScoreTypes? = null
    // Список списков специальностей для каждого из факультетов
    lateinit var specialtiesOfFaculties: ArrayList<ArrayList<Specialty>>

    private var facultyList: ArrayList<Faculty>? = null

    private var currentListOfStudents: ArrayList<Student>? = null

    private var listOfSpecialtiesWithZeroMinimalScore: ArrayList<ArrayList<Specialty>>? = null
    private var listOfFittingSpecialties: ArrayList<ArrayList<Specialty>>? = null
    private var completeListOfSpecialties: ArrayList<ArrayList<Specialty>>? = null
    private var listOfAllCompleteSpecialties: ArrayList<Specialty>? = null

    private var listUNTI: ArrayList<ArrayList<Student>>? = null
    private var listFEU: ArrayList<ArrayList<Student>>? = null
    private var listFIT: ArrayList<ArrayList<Student>>? = null
    private var listMTF: ArrayList<ArrayList<Student>>? = null
    private var listUNIT: ArrayList<ArrayList<Student>>? = null
    private var listFEE: ArrayList<ArrayList<Student>>? = null


    // Позиции специальностей в списке для поступления
    private var itemStateArray: SparseBooleanArray? = null
    private var selectedSpecialties: ArrayList<Specialty>? = null

    private var graduationList: ArrayList<Graduation>? = null

    // Оценить шанс поступления
    private var chosenSpecialties: ArrayList<Specialty>? = null
    private var chanceItemStateArray: SparseBooleanArray? = null

    // Список с шансами
    private var listOfChances: ArrayList<Chance>? = null

    //Score
    fun saveFullName(fullName: FullName) {
        this.fullName = fullName
    }
    fun returnFullName() = fullName

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
    //ScoreTypes
    fun saveScoreTypes(scoreTypes: ScoreTypes) {
        this.scoreTypes = scoreTypes
        this.scoreTypes?.let {
            val physics = it.physicsStudents.size
            val computerScience = it.computerScienceStudents.size
            val socialScience = it.socialScienceStudents.size
            val partAndAllData = it.partAndAllDataStudents.size

            showLog("Сохранён ScoreTypes: физика - $physics, информатика - $computerScience, " +
                    "обществознание - $socialScience,\n баллы по двум или трём специальностям - $partAndAllData")
        }
    }
    fun returnScoreTypes(): ScoreTypes? {
        this.scoreTypes?.let {
            val physics = it.physicsStudents?.size
            val computerScience = it.computerScienceStudents?.size
            val socialScience = it.socialScienceStudents?.size
            val partAndAllData = it.partAndAllDataStudents?.size

            showLog("Возвращён ScoreTypes: физика - $physics, информатика - $computerScience, " +
                    "обществознание - $socialScience,\n баллы по двум или трём специальностям - $partAndAllData")
        }
        return scoreTypes
    }

    // Specialties of Faculties
    fun saveSpecialtiesOfFaculties(specialtiesOfFaculties: ArrayList<ArrayList<Specialty>>) {
        this.specialtiesOfFaculties = specialtiesOfFaculties
        /*this.faculties?.let {
            val unti = it.listUNTI.size
            val feu = it.listFEU.size
            val fit = it.listFIT.size
            val mtf = it.listMTF.size
            val unit = it.listUNIT.size
            val fee = it.listFEE.size

            showLog("Сохранён Faculties: УНТИ - $unti, ФЭУ - $feu, ФИТ - $fit,\n МТФ - $mtf, " +
                    "УНИТ - $unit, ФЭЭ - $fee")
        }*/
    }
    fun returnSpecialtiesOfFaculties(): ArrayList<ArrayList<Specialty>> {
        /*this.faculties?.let {
            val unti = it.listUNTI.size
            val feu = it.listFEU.size
            val fit = it.listFIT.size
            val mtf = it.listMTF.size
            val unit = it.listUNIT.size
            val fee = it.listFEE.size

            showLog("Возвращён Faculties: УНТИ - $unti, ФЭУ - $feu, ФИТ - $fit,\n МТФ - $mtf, " +
                    "УНИТ - $unit, ФЭЭ - $fee")
        }*/
        return specialtiesOfFaculties
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

    fun saveListOfSpecialtiesWithZeroMinimalScore(list: ArrayList<ArrayList<Specialty>>) {
        this.listOfSpecialtiesWithZeroMinimalScore = list
        showLog("listOfSpecialtiesWithZeroMinimalScore сохранён")
    }
    fun returnListOfSpecialtiesWithZeroMinimalScore(): ArrayList<ArrayList<Specialty>>? {
        /*showLog("Возвращён listOfSpecialtiesWithZeroMinimalScore, " +
                "размер: " + listOfSpecialtiesWithZeroMinimalScore?.size)*/
        return listOfSpecialtiesWithZeroMinimalScore
    }

    fun saveListOfFittingSpecialties(list: ArrayList<ArrayList<Specialty>>) {
        this.listOfFittingSpecialties = list
        showLog("listOfFittingSpecialties сохранён")
    }
    fun returnListOfFittingSpecialties(): ArrayList<ArrayList<Specialty>>? {
        //showLog("Возвращён listOfFittingSpecialties, размер: " + listOfFittingSpecialties?.size)
        return listOfFittingSpecialties
    }

    fun saveCompleteListOfSpecialties(list: ArrayList<ArrayList<Specialty>>) {
        this.completeListOfSpecialties = list
        showLog("completeListOfSpecialties сохранён")
    }
    fun returnCompleteListOfSpecilaties(): ArrayList<ArrayList<Specialty>>? {
        return completeListOfSpecialties
    }


    fun saveListOfAllCompleteSpecialties(list: ArrayList<Specialty>) {
        this.listOfAllCompleteSpecialties = list
    }
    fun returnListOfAllCompleteSpecialties(): ArrayList<Specialty>?
            = listOfAllCompleteSpecialties

    fun saveItemStateArray(list: SparseBooleanArray) {
        this.itemStateArray = list
    }
    fun returnItemStateArray(): SparseBooleanArray?
            = itemStateArray

    fun saveSelectedSpecialties(selectedSpecialties: ArrayList<Specialty>) {
        this.selectedSpecialties = selectedSpecialties
    }
    fun returnSelectedSpecialties() = selectedSpecialties

    fun saveGraduationList(list: ArrayList<Graduation>) {
        this.graduationList = list
    }
    fun returnGraduationList() = graduationList

    // Оценить шанс поступления
    fun saveChosenSpecialties(chosenSpecialties: ArrayList<Specialty>) {
        this.chosenSpecialties = chosenSpecialties
    }
    fun saveChanceItemStateArray(chanceItemStateArray: SparseBooleanArray) {
        this.chanceItemStateArray = chanceItemStateArray
    }
    fun returnChosenSpecialties() = chosenSpecialties
    fun returnChanceItemStateArray() = chanceItemStateArray

    fun saveListOfChances(listOfChances: ArrayList<Chance>) {
        this.listOfChances = listOfChances
    }
    fun returnListOfChances() = listOfChances
}