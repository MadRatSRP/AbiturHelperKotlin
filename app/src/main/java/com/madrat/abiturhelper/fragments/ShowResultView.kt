package com.madrat.abiturhelper.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.presenters.fragments.ShowResultPresenter
import com.madrat.abiturhelper.interfaces.fragments.ShowResultMVP
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_result.*

class ShowResultView : Fragment(), ShowResultMVP.View {
    private var showResultPresenter: ShowResultPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        // Пятый шаг
        showResultPresenter?.checkForZeroMinimalScore()
        // Шестой шаг
        showResultPresenter?.checkForFittingSpecialties()

        // Получаем количество специальностей с неустановленным минимальным баллом
        val sizeOfZeroList = showResultPresenter?.returnAmountOfSpecialtiesWithZeroMinimalScore()
        resultAmountOfZeroMinimalScoreSpecialtiesValue.text = sizeOfZeroList.toString()

        // Получаем количество специальностей с подходящими баллами
        val sizeOfFittingList = showResultPresenter?.returnAmountOfFittingSpecialties()
        resultAmountOfFittingSpecialtiesValue.text = sizeOfFittingList.toString()

        resultShowSpecialtiesWithZeroMinimalScore.setOnClickListener {
            val bundle = returnBundleWithListID(100)
            toSpecialties(bundle, R.id.action_resultView_to_showFittingSpecialties)
        }
        resultShowFittingSpecialties.setOnClickListener {
            val bundle = returnBundleWithListID(200)
            toSpecialties(bundle, R.id.action_resultView_to_showFittingSpecialties)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.textResult)
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun setupMVP() {
        showResultPresenter = ShowResultPresenter()
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
    fun returnBundleWithListID(listId: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("listId", listId)

        return bundle
    }
}
