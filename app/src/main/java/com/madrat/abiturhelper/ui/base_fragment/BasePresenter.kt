package com.madrat.abiturhelper.ui.base_fragment

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class BasePresenter(context: Context,
                    private var bvi: BaseFragment) : BaseVP.Presenter {

    private var fragmentManager = (context as AppCompatActivity)
                                                  .supportFragmentManager!!

    override fun addFragment(fragment: Fragment, id: Int) {
        bvi.setFragment(fragment, fragmentManager, id)
    }

    override fun addEgeBundle(maths: EditText,
                              russian: EditText,
                              physics: EditText?,
                              computerScience: EditText?,
                              socialScience: EditText?): Bundle {

        return bvi.setEgeBundle(maths,
                         russian,
                         physics,
                         computerScience,
                         socialScience)
    }

    override fun removeFragment(fragment: Fragment) {
        bvi.unsetFragment(fragment, fragmentManager)
    }
}