package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.Specialty

interface ShowResultMVP {
    interface View {
        fun setupMVP()
        fun toSpecialties(bundle: Bundle?, actionId: Int)
        fun updateAmountOfFittingSpecialtiesAndSpecialtiesWithZeroMinimalScore(
                amountOfSpecialtiesWithZeroMinimalScore: Int,
                amountOfFittingSpecialties : Int)
    }

    interface Presenter {
        // Пятый этап
        fun checkForZeroMinimalScore()
        fun checkFacultyForSpecialtiesWithZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>

        // Шестой этап
        fun checkForFittingSpecialties()
        fun returnFittingSpecialties(): ArrayList<ArrayList<Specialty>>
        fun checkFacultyForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>

        // Седьмой шаг
        fun getSpecialtiesListByPosition(pos: Int): ArrayList<Specialty>?


        fun completeAndSaveSummedList()
        fun returnBundleWithListID(listId: Int): Bundle

    }
}
