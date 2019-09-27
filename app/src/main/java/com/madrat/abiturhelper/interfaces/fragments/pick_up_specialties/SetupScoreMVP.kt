package com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties

import android.content.Context

interface SetupScoreMVP {
    interface View {
        fun setupMVP()
        fun checkForFIO(context: Context): Boolean
        fun moveToWorkWithSpecialtiesView()
    }

    interface Presenter {
        fun saveFullNameAndScore(lastName: String, firstName: String, patronymic: String, maths: Int,
                                 russian: Int, physics: Int?, computerScience: Int?, socialScience: Int?,
                                 additionalScore: String)
        fun checkScoreForBeingEmpty(score: Int?): Int
    }
}
