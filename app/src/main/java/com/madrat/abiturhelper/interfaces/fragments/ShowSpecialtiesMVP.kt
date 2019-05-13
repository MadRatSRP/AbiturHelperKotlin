package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.model.Specialty

interface ShowSpecialtiesMVP {
    interface View {
        fun showSpecialties(specialties: ArrayList<Specialty>)
        fun toSpecialty(bundle: Bundle)
        fun setupMVP()
        fun initializeAdapter(example: (Specialty, Int) -> Unit): SpecialtiesAdapter
    }
    interface Presenter {

    }
}
