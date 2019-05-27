package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import com.madrat.abiturhelper.model.Score

interface ProfileMVP {
    interface View {
        fun setupMVP()
        fun setupScoreFields()
        fun toSpecialties(bundle: Bundle?, actionId: Int)
    }

    interface Presenter {
        fun updateScores(maths: Int, russian: Int, physics: Int, computerScience: Int,
                         socialScience: Int, additionalScore: Int)

        fun returnScore(): Score?
        fun returnAdditionalScore(): Int?
        fun returnBundleWithListID(listId: Int): Bundle
    }
}