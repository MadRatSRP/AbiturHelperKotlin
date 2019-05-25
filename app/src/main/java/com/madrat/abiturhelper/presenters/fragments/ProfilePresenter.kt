package com.madrat.abiturhelper.presenters.fragments

import android.os.Bundle
import com.madrat.abiturhelper.interfaces.fragments.ProfileMVP
import com.madrat.abiturhelper.util.MyApplication

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

    override fun returnBundleWithListID(listId: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("listId", listId)

        return bundle
    }
}