package com.madrat.abiturhelper.interfaces.fragments

interface AdditionalScoreMVP  {
    interface View {
        fun setupMVP()
        //fun setFieldsValues()
    }

    interface Presenter {
        fun saveAdditionalScore(additionalScore: Int)
    }
}