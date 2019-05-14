package com.madrat.abiturhelper.presenters.fragments

import android.content.Context
import android.os.Bundle
import com.madrat.abiturhelper.interfaces.fragments.CurrentListMVP
import com.madrat.abiturhelper.util.MyApplication

class CurrentListPresenter: CurrentListMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun returnFacultyList()
            = myApplication.returnFacultyList()
    override fun returnFacultyBundle(context: Context, position: Int, titleId: Int): Bundle {
        val bundle = Bundle()
        val title = context.getString(titleId)

        bundle.putString("title", title)
        bundle.putInt("pos", position)
        return bundle
    }
}