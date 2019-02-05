package com.madrat.abiturhelper.ui.base_fragment

import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class BaseFragment : Fragment(), BaseVP.View {

    override fun setFragment(fragment: Fragment, fragmentManager: FragmentManager, id: Int) {
        fragmentManager.beginTransaction()
                       .replace(id, fragment)
                       .commit()
    }

    override fun unsetFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
                       .remove(fragment)
                       .commit()
    }

    override fun setEgeBundle(maths: EditText,
                        russian: EditText,
                        physics: EditText?,
                        computerScience: EditText?,
                        socialScience: EditText?) : Bundle {

        val bundle = Bundle()
        bundle.putString("maths", maths.text.toString())
        bundle.putString("russian", russian.text.toString())
        bundle.putString("physics", physics?.text.toString())
        bundle.putString("computerScience", computerScience?.text.toString())
        bundle.putString("socialScience", socialScience?.text.toString())
        return bundle
    }
}