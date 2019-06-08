package com.madrat.abiturhelper.interfaces.fragments.chance

import com.madrat.abiturhelper.model.Chance

interface ChanceShowResultsMVP {
    interface View {

        fun showListOfChances(listOfChances: ArrayList<Chance>)
        fun setupMVP()
    }
    interface Presenter {

    }
}