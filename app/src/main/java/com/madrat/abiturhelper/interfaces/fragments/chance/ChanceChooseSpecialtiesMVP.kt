package com.madrat.abiturhelper.interfaces.fragments.chance

import android.util.SparseBooleanArray
import com.madrat.abiturhelper.model.Specialty

interface ChanceChooseSpecialtiesMVP {
    interface View {
        fun setupMVP()
        fun showSpecialties(specialties: ArrayList<Specialty>?)
        fun toSpecialties(actionId: Int)
    }
    interface Presenter {
        fun saveChosenSpecialties(listOfChosenSpecialties: ArrayList<Specialty>)
        fun saveChanceItemStateArray(chanceItemStateArray: SparseBooleanArray)

        fun returnListOfAllCompleteSpecialties(): ArrayList<Specialty>?
        fun returnChanceItemStateArray(): SparseBooleanArray?
        fun returnChosenSpecialties(): ArrayList<Specialty>?
    }
}