package com.madrat.abiturhelper.data.interfaces.fragments.pick_up_specialties


interface SetupScoreMVP {
    interface View {
        fun moveToWorkWithSpecialtiesView()
        fun showErrorMessageByMessageId(messageId: Int)
        fun onShowSpecialtiesScreenClicked()
    }

    interface Presenter {
        fun saveFullNameAndScore(lastName: String, firstName: String, patronymic: String, maths: Int,
                                 russian: Int, physics: Int?, computerScience: Int?, socialScience: Int?,
                                 additionalScore: Int)
        fun checkScoreForBeingEmpty(score: Int?): Int
        fun checkIsFullNameValid(lastName: String, firstName: String, patronymic: String): Boolean
        fun checkForNullability(maths: Int?, russian: Int?, physics: Int?, computerScience: Int?, socialScience: Int?): Boolean
        fun checkIsFullNameAndScoreValid(lastName: String, firstName: String, patronymic: String, maths: Int?, russian: Int?, physics: Int?, computerScience: Int?, socialScience: Int?, additionalScore: Int)
        fun scoreError(scoreId: Int, score: Int?, passingScore: Int): Boolean
        fun checkIsScoreValid(maths: Int?, russian: Int?, physics: Int?, computerScience: Int?, socialScience: Int?): Boolean
        fun checkMathsAndRussianForPassing(maths: Int?, russian: Int?): Boolean
        fun checkTypedScoreForPassing(physics: Int?, computerScience: Int?, socialScience: Int?): Boolean
    }
}
