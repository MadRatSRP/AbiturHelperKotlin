package com.madrat.abiturhelper.presenters.fragments.profile

import com.madrat.abiturhelper.interfaces.fragments.profile.GraduationShowCurrentListMVP
import com.madrat.abiturhelper.util.MyApplication

class GraduationShowCurrentListPresenter: GraduationShowCurrentListMVP.Presenter {
    val myApplication = MyApplication.instance

    override fun returnGraduationList()
            = myApplication.returnGraduationList()
}