package com.madrat.abiturhelper.util

import android.app.Application
import com.madrat.abiturhelper.model.Faculties
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.ScoreTypes
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.model.faculties.Feu
import com.madrat.abiturhelper.model.faculties.Fit
import com.madrat.abiturhelper.model.faculties.MTF
import com.madrat.abiturhelper.model.faculties.Unti

class MyApplication: Application() {
    companion object { val instance = MyApplication() }
    private var scores: Score? = null
    private var additionalScore: Int? = 0
    private var bachelors: ArrayList<Student>? = null
    private var scoreTypes: ScoreTypes? = null
    private var faculties: Faculties? = null

    private var unti: Unti? = null
    private var feu: Feu? = null
    private var fit: Fit? = null
    private var mtf: MTF? = null

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
    // УНТИ
    fun saveUnti(unti: Unti) {
        this.unti = unti
        showLog("УНТИ сохранён")
    }
    fun returnUnti(): Unti? = unti

    // ФЭУ
    fun saveFeu(feu: Feu) {
        this.feu = feu
        showLog("ФЭУ сохранён")
    }
    fun returnFeu(): Feu? = feu

    // ФИТ
    fun saveFIT(fit: Fit) {
        this.fit = fit
        showLog("ФИТ сохранён")
    }
    fun returnFIT(): Fit? = fit

    // МТФ
    fun saveMTF(mtf: MTF) {
        this.mtf = mtf
        showLog("МТФ сохранён")
    }
    fun returnMTF(): MTF? = mtf
}