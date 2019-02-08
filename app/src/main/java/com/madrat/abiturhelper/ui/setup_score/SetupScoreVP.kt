package com.madrat.abiturhelper.ui.setup_score

interface SetupScoreVP {
    interface View {
        fun setMVP()
        fun setFieldsListeners()
        fun setFieldsValues()

        fun mathsIsValid(math_passing: Int, score_limit: Int)
        fun russianIsValid(rus_passing: Int, score_limit: Int)
        fun physicsIsValid(phys_passing: Int, score_limit: Int)
        fun computerScienceIsValid(comp_passing: Int, score_limit: Int)
        fun socialScienceIsValid(soc_passing: Int, score_limit: Int)
    }

    interface Presenter {
        fun addFieldsListeners()
        fun addFieldsValues()

        fun checkMaths(math_passing: Int, score_limit: Int)
        fun checkRussian(rus_passing: Int, score_limit: Int)
        fun checkPhysics(phys_passing: Int, score_limit: Int)
        fun checkComputerScience(comp_passing: Int, score_limit: Int)
        fun checkSocialScience(soc_passing: Int, score_limit: Int)
    }
}