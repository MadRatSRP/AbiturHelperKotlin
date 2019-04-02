package com.madrat.abiturhelper.interfaces.fragments

interface SetupScoreMVP {
    interface View {
        fun setupMVP()
        fun setFieldsListeners()

        fun mathsIsValid(math_passing: Int, score_limit: Int)
        fun russianIsValid(rus_passing: Int, score_limit: Int)
        fun physicsIsValid(phys_passing: Int, score_limit: Int)
        fun computerScienceIsValid(comp_passing: Int, score_limit: Int)
        fun socialScienceIsValid(soc_passing: Int, score_limit: Int)
    }

    interface Presenter {
        fun addFieldsListeners()

        fun checkMaths(math_passing: Int, score_limit: Int)
        fun checkRussian(rus_passing: Int, score_limit: Int)
        fun checkPhysics(phys_passing: Int, score_limit: Int)
        fun checkComputerScience(comp_passing: Int, score_limit: Int)
        fun checkSocialScience(soc_passing: Int, score_limit: Int)

        fun saveUserData(maths: String, russian: String, physics: String,
                         computerScience: String, socialScience: String)
    }
}
