package com.madrat.abiturhelper.presenters.fragments

import android.util.SparseBooleanArray
import com.madrat.abiturhelper.interfaces.fragments.SelectSpecialtiesForGraduationMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class SelectSpecialtiesForGraduationPresenter
    : SelectSpecialtiesForGraduationMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun saveItemStateArray(itemStateArray: SparseBooleanArray?) {
        itemStateArray?.let { myApplication.saveItemStateArray(it) }
    }

    override fun saveSelectedSpecialties(selectedSpecialties: ArrayList<Specialty>?) {
        selectedSpecialties?.let { myApplication.saveSelectedSpecialties(it) }
    }

    override fun returnCompleteListOfSpecilaties(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnCompleteListOfSpecilaties()
    override fun returnItemStateArray()
            = myApplication.returnItemStateArray()
}