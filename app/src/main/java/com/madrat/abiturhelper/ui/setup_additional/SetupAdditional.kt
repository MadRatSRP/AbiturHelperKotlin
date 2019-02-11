package com.madrat.abiturhelper.ui.setup_additional

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.ui.base_fragment.BaseFragment
import com.madrat.abiturhelper.ui.base_fragment.BasePresenter
import com.madrat.abiturhelper.ui.result.ResultView
import kotlinx.android.synthetic.main.fragment_setup_additional.*

class SetupAdditional : Fragment(), SetupAdditionalVP.View {
    companion object { val instance = SetupAdditional() }

    private lateinit var setupAdditionalPresenter: SetupAdditionalPresenter
    private lateinit var basePresenter: BasePresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        showResult.setOnClickListener {
            setFieldsValues()

            basePresenter.addFragment(ResultView.instance,
                               R.id.activityFragmentContainer)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.textAdditional)
        return inflater.inflate(R.layout.fragment_setup_additional,
                                container, false)
    }

    override fun setupMVP() {
        setupAdditionalPresenter = SetupAdditionalPresenter(this)
        basePresenter = BasePresenter(this.context!!, BaseFragment())
    }

    override fun setFieldsValues() {
        ResultView.instance.arguments = basePresenter.putAdditionalValues(arguments, soc)
    }
}