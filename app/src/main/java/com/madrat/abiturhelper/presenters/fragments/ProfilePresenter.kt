package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.ProfileMVP
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.returnString

class ProfilePresenter(private var pv: ProfileMVP.View) : ProfileMVP.Presenter {
    var myApplication = MyApplication.instance

    /*override fun setupMaths(): String? {
        return returnString(myApplication.returnMaths())
    }
    override fun setupRussian(): String? {
        return returnString(myApplication.returnRussian())
    }
    override fun setupPhysics(): String? {
        return returnString(myApplication.returnPhysics())
    }
    override fun setupComputerScience(): String? {
        return returnString(myApplication.returnComputerScience())
    }
    override fun setupSocialScience(): String? {
        return returnString(myApplication.returnSocialScience())
    }*/
}