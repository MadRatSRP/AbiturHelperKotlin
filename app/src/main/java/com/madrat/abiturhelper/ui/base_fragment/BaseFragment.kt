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
}