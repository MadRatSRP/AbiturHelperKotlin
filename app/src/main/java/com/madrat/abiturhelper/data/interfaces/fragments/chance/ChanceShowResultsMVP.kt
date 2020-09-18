package com.madrat.abiturhelper.data.interfaces.fragments.chance

import android.os.Bundle
import com.madrat.abiturhelper.data.model.Chance

interface ChanceShowResultsMVP {
    interface View {

        fun showListOfChances(listOfChances: ArrayList<Chance>)
        fun setupMVP()
        fun toActionId(bundle: Bundle?, actionId: Int)
    }
    interface Presenter {

    }
}