package com.madrat.abiturhelper.interfaces.fragments.profile

import android.util.SparseBooleanArray
import com.madrat.abiturhelper.model.Specialty

interface GraduationSelectSpecialtiesMVP {
    interface View {
        fun setupMVP()
        fun showSpecialties(specialties: ArrayList<Specialty>?)
    }
    interface Presenter {
        fun returnCompleteListOfSpecilaties(): ArrayList<ArrayList<Specialty>>?
        fun saveItemStateArray(itemStateArray: SparseBooleanArray?)
        fun returnItemStateArray(): SparseBooleanArray?
        fun saveSelectedSpecialties(selectedSpecialties: ArrayList<Specialty>?)
        fun returnSelectedSpecialties(): ArrayList<Specialty>?
    }
}