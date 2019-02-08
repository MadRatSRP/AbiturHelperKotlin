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
        bundle.putInt("maths", maths.text.toString().toInt())
        bundle.putInt("russian", russian.text.toString().toInt())
        physics?.text.toString().toIntOrNull()?.let { bundle.putInt("physics", it) }
        computerScience?.text.toString().toIntOrNull()?.let { bundle.putInt("computerScience", it) }
        socialScience?.text.toString().toIntOrNull()?.let { bundle.putInt("socialScience", it) }
        return bundle
    }
}