package com.madrat.abiturhelper.data.presenters.fragments.profile

import com.madrat.abiturhelper.data.interfaces.fragments.profile.GraduationShowCurrentListMVP
import com.madrat.abiturhelper.util.MyApplication

class GraduationShowCurrentListPresenter: GraduationShowCurrentListMVP.Presenter {
    val myApplication = MyApplication.instance

    override fun returnGraduationList()
            = myApplication.returnGraduationList()
}