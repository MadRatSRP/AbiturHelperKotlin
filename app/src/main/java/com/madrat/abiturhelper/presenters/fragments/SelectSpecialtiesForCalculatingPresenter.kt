package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.SelectSpecialtiesForCalculatingMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class SelectSpecialtiesForCalculatingPresenter
    : SelectSpecialtiesForCalculatingMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun returnCompleteListOfSpecilaties(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnCompleteListOfSpecilaties()
}