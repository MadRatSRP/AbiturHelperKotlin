package com.madrat.abiturhelper.data.interfaces.fragments

import com.madrat.abiturhelper.data.model.Specialty
import kotlin.collections.ArrayList

interface ShowFittingSpecialtiesMVP {
    interface View {

        fun setupMVP()
        fun showSpecialties(specialties: ArrayList<Specialty>?)
    }
    interface Presenter {

        fun returnListOfSpecialtiesWithZeroMinimalScore(): ArrayList<ArrayList<Specialty>>?
        fun returnListOfFittingSpecialties(): ArrayList<ArrayList<Specialty>>?
        fun returnCompleteListOfSpecilaties(): ArrayList<ArrayList<Specialty>>?
        fun returnListBasedOnListID(listId: Int): ArrayList<ArrayList<Specialty>>?
    }
}