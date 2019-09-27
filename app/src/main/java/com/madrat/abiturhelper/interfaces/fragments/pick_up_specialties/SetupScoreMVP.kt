package com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties

import android.content.Context

interface SetupScoreMVP {
    interface View {
        fun setupMVP()
        fun moveToWorkWithSpecialties(view: android.view.View)
        fun checkForFIO(context: Context): Boolean
    }

    interface Presenter {
        fun saveFullName(lastName: String, firstName: String, patronymic: String)

        fun savePointsAsAScoreModel(maths: Int, russian: Int, physics: Int?, computerScience: Int?,
                                    socialScience: Int?, additionalScore: String)

        fun checkScoreForBeingEmpty(score: Int?): Int
    }
}
