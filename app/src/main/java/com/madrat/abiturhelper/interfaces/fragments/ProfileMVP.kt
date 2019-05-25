package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle

interface ProfileMVP {
    interface View {
        fun setupMVP()
        fun setupFields()
        fun toSpecialties(bundle: Bundle?, actionId: Int)
    }

    interface Presenter {
        /*fun setupMaths(): String?
        fun setupRussian(): String?
        fun setupPhysics(): String?
        fun setupComputerScience(): String?
        fun setupSocialScience(): String?*/
        fun returnBundleWithListID(listId: Int): Bundle
    }
}