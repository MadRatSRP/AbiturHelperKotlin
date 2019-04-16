package com.madrat.abiturhelper.interfaces.fragments

import com.madrat.abiturhelper.model.Specialty

interface ShowSpecialtiesMVP {
    interface View {
        fun showSpecialties(specialties: List<Specialty>)
    }

}
