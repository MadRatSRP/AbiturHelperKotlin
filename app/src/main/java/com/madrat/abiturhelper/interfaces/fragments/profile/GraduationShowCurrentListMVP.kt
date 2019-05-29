package com.madrat.abiturhelper.interfaces.fragments.profile

import com.madrat.abiturhelper.model.Graduation

interface GraduationShowCurrentListMVP {
    interface View {

        fun showGraduation(graduationList: ArrayList<Graduation>)
        fun setupMVP()
    }
    interface Presenter {

        fun returnGraduationList(): ArrayList<Graduation>?
    }
}