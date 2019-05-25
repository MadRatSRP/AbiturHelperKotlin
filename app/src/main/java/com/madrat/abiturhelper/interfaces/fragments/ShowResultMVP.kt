package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.Specialty

interface ShowResultMVP {
    interface View {
        fun setupMVP()
    }

    interface Presenter {
        // Пятый этап
        fun checkForZeroMinimalScore()
        fun checkUNTIForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFEUForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFITForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkMTFForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkUNITForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFEEForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>

        // Шестой этап
        fun checkForFittingSpecialties()
        fun checkUNTIForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFEUForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFITForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkMTFForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkUNITForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFEEForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>

        fun getSpecialtiesListByPosition(pos: Int): ArrayList<Specialty>?
        fun returnAmountOfSpecialtiesWithZeroMinimalScore(): Int?
        fun returnAmountOfFittingSpecialties(): Int?
    }
}
