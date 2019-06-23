package com.madrat.abiturhelper.interfaces.fragments.chance

import android.os.Bundle
import com.madrat.abiturhelper.model.Chance

interface ChanceShowResultsMVP {
    interface View {

        fun showListOfChances(listOfChances: ArrayList<Chance>)
        fun setupMVP()
        fun toActionId(bundle: Bundle?, actionId: Int)
    }
    interface Presenter {

    }
}