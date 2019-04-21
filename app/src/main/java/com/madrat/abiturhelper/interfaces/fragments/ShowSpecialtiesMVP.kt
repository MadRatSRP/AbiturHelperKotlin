package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import com.madrat.abiturhelper.model.Specialty

interface ShowSpecialtiesMVP {
    interface View {
        fun showSpecialties(specialties: List<Specialty>)
        fun toSpecialty(bundle: Bundle)
    }

}
