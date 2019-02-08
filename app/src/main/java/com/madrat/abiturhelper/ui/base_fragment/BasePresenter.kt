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

    override fun returnBundle(maths: EditText,
                              russian: EditText,
                              physics: EditText?,
                              computerScience: EditText?,
                              socialScience: EditText?): Bundle {

        val bundle = Bundle()
        bundle.putInt("maths", maths.text.toString().toInt())
        bundle.putInt("russian", russian.text.toString().toInt())
        physics?.text.toString().toIntOrNull()?.let { bundle.putInt("physics", it) }
        computerScience?.text.toString().toIntOrNull()?.let { bundle.putInt("computerScience", it) }
        socialScience?.text.toString().toIntOrNull()?.let { bundle.putInt("socialScience", it) }
        return bundle
    }

    override fun removeFragment(fragment: Fragment) {
        bvi.unsetFragment(fragment, fragmentManager)
    }
}