package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class ShowSpecialtiesPresenter: ShowSpecialtiesMVP.Presenter {
    private val myApplication = MyApplication.instance

    fun getSpecialtiesListByPosition(pos: Int): ArrayList<Specialty>? {
        val faculties = myApplication.returnFaculties()
        var list: ArrayList<Specialty>? = null

        faculties?.let {
            when (pos) {
                //УНТИ
                0 -> list = it.untiList
                //ФЭУ
                1 -> list = it.feuList
                //ФИТ
                2 -> list = it.fitList
                //МТФ
                3 -> list = it.mtfList
                //УНИТ
                4 -> list = it.unitList
                //ФЭЭ
                5 -> list = it.feeList
                else -> list = null
            }
        }
        return list
    }
}