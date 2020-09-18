package com.madrat.abiturhelper.data.presenters.fragments.profile

import android.util.SparseBooleanArray
import com.madrat.abiturhelper.data.interfaces.fragments.profile.GraduationSelectSpecialtiesMVP
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class GraduationSelectSpecialtiesPresenter
    : GraduationSelectSpecialtiesMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun saveItemStateArray(itemStateArray: SparseBooleanArray?) {
        itemStateArray?.let { myApplication.saveItemStateArray(it) }
    }
    override fun saveSelectedSpecialties(selectedSpecialties: ArrayList<Specialty>?) {
        selectedSpecialties?.let { myApplication.saveSelectedSpecialties(it) }
    }
    override fun returnItemStateArray()
            = myApplication.returnItemStateArray()
    override fun returnSelectedSpecialties()
            = myApplication.returnSelectedSpecialties()
    override fun returnListOfAllCompleteSpecialties()
            = myApplication.returnListOfAllCompleteSpecialties()
}