package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.ShowFittingSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class ShowFittingSpecialtiesPresenter: ShowFittingSpecialtiesMVP.Presenter {
    private val myApplication = MyApplication.instance

    fun returnListOfSpecialtiesWithZeroMinimalScore(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnListOfSpecialtiesWithZeroMinimalScore()
    fun returnListOfFittingSpecialties(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnListOfFittingSpecialties()
}