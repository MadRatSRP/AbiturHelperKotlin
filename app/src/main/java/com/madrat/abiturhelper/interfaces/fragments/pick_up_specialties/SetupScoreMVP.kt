package com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties

import android.content.Context

interface SetupScoreMVP {
    interface View {
        fun moveToWorkWithSpecialtiesView()
        fun checkScoreForBeingEmpty(score: Int?): Int
        fun showErrorMessageByScoreId(scoreId: Int): Boolean
        fun scoreError(scoreId: Int, score: Int?, passingScore: Int): Boolean
    }

    interface Presenter {
        fun saveFullNameAndScore(lastName: String, firstName: String, patronymic: String, maths: Int,
                                 russian: Int, physics: Int?, computerScience: Int?, socialScience: Int?,
                                 additionalScore: Int)
        fun checkScoreForBeingEmpty(score: Int?): Int
    }
}
