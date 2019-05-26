package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.ShowFittingSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class ShowFittingSpecialtiesPresenter: ShowFittingSpecialtiesMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun returnListOfSpecialtiesWithZeroMinimalScore(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnListOfSpecialtiesWithZeroMinimalScore()
    override fun returnListOfFittingSpecialties(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnListOfFittingSpecialties()
    override fun returnCompleteListOfSpecilaties(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnCompleteListOfSpecilaties()
}