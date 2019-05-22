package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.ShowResultMVP
import com.madrat.abiturhelper.util.MyApplication

class ShowResultPresenter : ShowResultMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun returnAmountOfSpecialtiesWithZeroMinimalScore(): Int? {
        val zeroList = myApplication.returnListOfSpecialtiesWithZeroMinimalScore()
        return zeroList?.sumBy { it.size }
    }
}
