package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.presenters.fragments.ShowResultPresenter
import com.madrat.abiturhelper.interfaces.fragments.ShowResultMVP
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_result.*

class ShowResultView : Fragment(), ShowResultMVP.View {
    private var showResultPresenter: ShowResultPresenter? = null

    private val myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        setupFields()

        val zeroList = myApplication.returnListOfSpecialtiesWithZeroMinimalScore()
        val sizeOfZeroList = zeroList?.sumBy { it.size }
        //showLog("$sizeOfZeroList")
        resultAmountOfZeroMinimalScoreSpecialtiesValue.text = sizeOfZeroList.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.textResult)
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun setupMVP() {
        //showResultPresenter = ShowResultPresenter(this, arguments!!)
    }

    override fun checkField(linearLayout: LinearLayout, textViewValue: TextView, key: String) {
        /*textViewValue.text = showResultPresenter?.returnString(key, scoreBundle)
        if (textViewValue.text.toString().toInt() == 0) {
            linearLayout.visibility = View.GONE
        }*/
    }

    override fun setupFields() {
        /*val scoreBundle = arguments?.getBundle("scoreBundle")

        mathsValue.text = showResultPresenter?.returnString(scoreBundle,"maths")
        russianValue.text = showResultPresenter?.returnString(scoreBundle,"russian")*/

        /*val additionalBundle = arguments?.getBundle("additionalBundle")
        essayValue.text = showResultPresenter?.returnString(additionalBundle,"soc")*/

        /*checkField(physics, physicsValue, "physics")
        checkField(computerScience, computerScienceValue, "computerScience")
        checkField(socialScience, socialScienceValue, "socialScience")*/

        //resultValue.text = showResultPresenter?.returnSum()
    }
}
