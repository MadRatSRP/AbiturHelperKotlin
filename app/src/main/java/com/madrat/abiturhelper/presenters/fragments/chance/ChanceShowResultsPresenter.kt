package com.madrat.abiturhelper.presenters.fragments.chance

import com.madrat.abiturhelper.interfaces.fragments.chance.ChanceShowResultsMVP
import com.madrat.abiturhelper.util.MyApplication

class ChanceShowResultsPresenter
    : ChanceShowResultsMVP.Presenter {
    private var myApplication = MyApplication.instance

    fun returnListOfChances()
            = myApplication.returnListOfChances()
}