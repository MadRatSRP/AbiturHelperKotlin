package com.madrat.abiturhelper.interfaces.fragments.profile

import android.os.Bundle
import com.madrat.abiturhelper.model.Graduation

interface GraduationShowCurrentListMVP {
    interface View {

        fun showGraduation(graduationList: ArrayList<Graduation>)
        fun setupMVP()
        fun toSpecialties(bundle: Bundle?, actionId: Int)
    }
    interface Presenter {

        fun returnGraduationList(): ArrayList<Graduation>?
    }
}