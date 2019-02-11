package com.madrat.abiturhelper.ui.base_fragment

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class BasePresenter(context: Context,
                    private var bvi: BaseFragment) : BaseVP.Presenter {

    private var fragmentManager = (context as AppCompatActivity).supportFragmentManager

    override fun addFragment(fragment: Fragment, id: Int) {
        bvi.setFragment(fragment, fragmentManager, id)
    }

    override fun removeFragment(fragment: Fragment) {
        bvi.unsetFragment(fragment, fragmentManager)
    }

    override fun putScoreValues(maths: EditText,
                                russian: EditText,
                                physics: EditText?,
                                computerScience: EditText?,
                                socialScience: EditText?): Bundle {

        val scoreBundle = Bundle()

        scoreBundle.putInt("maths", maths.text.toString().toInt())
        scoreBundle.putInt("russian", russian.text.toString().toInt())
        physics?.text.toString().toIntOrNull()?.let { scoreBundle.putInt("physics", it) }
        computerScience?.text.toString().toIntOrNull()?.let { scoreBundle.putInt("computerScience", it) }
        socialScience?.text.toString().toIntOrNull()?.let { scoreBundle.putInt("socialScience", it) }

        scoreBundle.putBundle("scoreBundle", scoreBundle)
        return scoreBundle
    }

    override fun putAdditionalValues(arguments: Bundle?, soc: EditText):Bundle {
        val additionalBundle = Bundle()
        val scoreBundle = arguments?.getBundle("scoreBundle")

        additionalBundle.putBundle("scoreBundle", scoreBundle)
        additionalBundle.putInt("soc", soc.text.toString().toInt())
        additionalBundle.putBundle("additionalBundle", additionalBundle)
        return additionalBundle
    }
}