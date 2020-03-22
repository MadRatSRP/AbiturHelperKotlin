package com.madrat.abiturhelper.fragments.select_specialties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.FragmentShowResultBinding
import com.madrat.abiturhelper.interfaces.fragments.ShowResultMVP
import com.madrat.abiturhelper.presenters.fragments.ShowResultPresenter

class ShowResultView : Fragment(), ShowResultMVP.View {
    private var presenter: ShowResultPresenter? = null

    private var mBinding: FragmentShowResultBinding? = null
    private val binding get() = mBinding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        // Пятый шаг
        presenter?.checkForZeroMinimalScore()
        // Шестой шаг
        presenter?.checkForFittingSpecialties()

        // Получаем количество специальностей с неустановленным минимальным баллом
        // Получаем количество специальностей с подходящими баллами
        presenter?.getAmountOfFittingSpecialtiesAndSpecialtiesWithZeroMinimalScore()

        presenter?.completeAndSaveSummedList()

        // Седьмой шаг
        val completeList = presenter?.returnCompleteListOfSpecialties()
        val listOfAllCompleteSpecialties = completeList
                ?.let { presenter?.returnListOfAllCompleteSpecialties(it) }
        listOfAllCompleteSpecialties
                ?.let { presenter?.saveListOfAllCompleteSpecialties(it) }

        binding.specialtiesWithoutScoreShowSpecialties.setOnClickListener {
            val bundle = presenter?.returnBundleWithListID(100)
            toSpecialties(bundle, R.id.action_resultView_to_showFittingSpecialties)
        }
        binding.fittingSpecialtiesShowSpecialties.setOnClickListener {
            val bundle = presenter?.returnBundleWithListID(200)
            toSpecialties(bundle, R.id.action_resultView_to_showFittingSpecialties)
        }
        binding.moveToProfile.setOnClickListener {
            toSpecialties(null, R.id.action_resultView_to_profile)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.resultTitle)
        // Инициализируем mBinding
        mBinding = FragmentShowResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupMVP() {
        presenter = ShowResultPresenter(this)
    }
    override fun updateAmountOfFittingSpecialtiesAndSpecialtiesWithZeroMinimalScore(
            amountOfFittingSpecialties: Int, amountOfSpecialtiesWithZeroMinimalScore: Int) {

        binding.specialtiesWithoutScoreAmountValue.text = amountOfSpecialtiesWithZeroMinimalScore.toString()

        binding.fittingSpecialtiesAmountValue.text = amountOfFittingSpecialties.toString()
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}
