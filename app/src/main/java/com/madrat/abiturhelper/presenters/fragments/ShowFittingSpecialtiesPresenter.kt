package com.madrat.abiturhelper.presenters.fragments

import android.content.Context
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.ShowFittingSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class ShowFittingSpecialtiesPresenter: ShowFittingSpecialtiesMVP.Presenter {
    private val myApplication = MyApplication.instance

    fun returnTitleBasedOnListID(context: Context, listId: Int): String? {
        return when(listId) {
            100 -> context.getString(R.string.resultSpecialtiesWithoutScoreTitle)
            200 -> context.getString(R.string.resultFittingSpecialtiesTitle)
            300 -> context.getString(R.string.resultShowFittingSpecialties)
            else -> null
        }
    }

    override fun returnListBasedOnListID(listId: Int): ArrayList<ArrayList<Specialty>>? {
        return when(listId) {
            100 -> returnListOfSpecialtiesWithZeroMinimalScore()
            200 -> returnListOfFittingSpecialties()
            300 -> returnCompleteListOfSpecilaties()
            else -> null
        }
    }

    override fun returnListOfSpecialtiesWithZeroMinimalScore(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnListOfSpecialtiesWithZeroMinimalScore()
    override fun returnListOfFittingSpecialties(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnListOfFittingSpecialties()
    override fun returnCompleteListOfSpecilaties(): ArrayList<ArrayList<Specialty>>?
            = myApplication.returnCompleteListOfSpecilaties()
}