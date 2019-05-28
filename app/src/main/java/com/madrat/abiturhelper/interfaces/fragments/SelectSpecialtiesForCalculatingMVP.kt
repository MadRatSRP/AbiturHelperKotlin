package com.madrat.abiturhelper.interfaces.fragments

import com.madrat.abiturhelper.model.Specialty

interface SelectSpecialtiesForCalculatingMVP {
    interface View {
        fun setupMVP()
        fun showSpecialties(specialties: ArrayList<Specialty>?)
    }
    interface Presenter {
        fun returnCompleteListOfSpecilaties(): ArrayList<ArrayList<Specialty>>?
    }
}