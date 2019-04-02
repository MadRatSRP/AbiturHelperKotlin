package com.madrat.abiturhelper.interfaces.fragments

interface ProfileMVP {
    interface View {
        fun setupMVP()
        fun setupFields()
    }

    interface Presenter {
        fun setupMaths(): String?
        fun setupRussian(): String?
        fun setupPhysics(): String?
        fun setupComputerScience(): String?
        fun setupSocialScience(): String?
    }
}