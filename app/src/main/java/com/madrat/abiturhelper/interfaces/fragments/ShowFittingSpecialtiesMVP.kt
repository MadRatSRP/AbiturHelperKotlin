package com.madrat.abiturhelper.interfaces.fragments

import com.madrat.abiturhelper.model.Specialty
import java.util.ArrayList

interface ShowFittingSpecialtiesMVP {
    interface View {

        fun setupMVP()
        fun showSpecialties(specialties: ArrayList<Specialty>?)
    }
    interface Presenter {

    }
}