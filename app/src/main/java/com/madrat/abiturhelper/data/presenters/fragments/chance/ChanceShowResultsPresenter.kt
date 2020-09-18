package com.madrat.abiturhelper.data.presenters.fragments.chance

import com.madrat.abiturhelper.data.interfaces.fragments.chance.ChanceShowResultsMVP
import com.madrat.abiturhelper.util.MyApplication

class ChanceShowResultsPresenter
    : ChanceShowResultsMVP.Presenter {
    private var myApplication = MyApplication.instance

    fun returnListOfChances()
            = myApplication.returnListOfChances()
}