package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.ShowStudentsMVP
import com.madrat.abiturhelper.util.MyApplication

class ShowStudentsPresenter: ShowStudentsMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun returnCurrentListOfStudents()
            = myApplication.returnCurrentListOfStudents()
}