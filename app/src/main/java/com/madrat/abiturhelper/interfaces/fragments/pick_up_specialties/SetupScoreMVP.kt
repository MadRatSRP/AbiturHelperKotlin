package com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties

interface SetupScoreMVP {
    interface View {
        fun setupMVP()
    }

    interface Presenter {
        fun saveFullName(lastName: String, firstName: String, patronymic: String)
        fun saveScore(maths: String, russian: String, physics: String, computerScience: String,
                      socialScience: String, additionalScore: String)
        fun checkTextForBeingEmpty(text: String): Int

        fun returnIntFromString(text: String): Int
    }
}
