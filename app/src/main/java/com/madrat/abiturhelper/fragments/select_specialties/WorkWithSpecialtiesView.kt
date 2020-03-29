package com.madrat.abiturhelper.fragments.select_specialties

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.FragmentWorkWithSpecialtiesBinding
import com.madrat.abiturhelper.interfaces.fragments.WorkWithSpecialtiesMVP
import com.madrat.abiturhelper.presenters.fragments.WorkWithSpecialtiesPresenter
import com.madrat.abiturhelper.util.moveToSelectedFragment
import com.madrat.abiturhelper.util.showLog
import com.madrat.abiturhelper.util.showSnack
import kotlin.system.measureTimeMillis

class WorkWithSpecialtiesView
    : Fragment(), WorkWithSpecialtiesMVP.View{
    private var presenter: WorkWithSpecialtiesPresenter? = null

    private var mBinding: FragmentWorkWithSpecialtiesBinding? = null
    private val binding get() = mBinding!!

    init {
        presenter = WorkWithSpecialtiesPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.workWithSpecialtiesTitle)

        // Инициализируем mBinding
        mBinding = FragmentWorkWithSpecialtiesBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let { formListOfStudentsAndMinimalScores(it) }

        binding.workToCurrentList.setOnClickListener {
            onToCurrentListClicked()
        }
        binding.workToResult.setOnClickListener {
            onToResultClicked()
        }
    }
    override fun onDestroyView() {
        presenter = null
        super.onDestroyView()
    }

    override fun onToCurrentListClicked() {
        moveToSelectedFragment(R.id.action_workWithSpecialtiesView_to_currentList)
    }
    override fun onToResultClicked() {
        moveToSelectedFragment(R.id.action_workWithSpecialtiesView_to_resultView)
    }
    fun formListOfStudentsAndMinimalScores(context: Context) {
        val time = measureTimeMillis {

            context.assets?.open("specialties.csv")?.let {specialties ->
                context.assets?.open("abiturs.csv")?.let {abiturs->
                    presenter?.generateBachelorsAndSpecialtiesLists(
                            specialties, abiturs)
                }
            }
            // Первый шаг - разбить список специальностей по факультетам,
            // выделить из списка студентов тех, кто собирается поступать на бакалавриат

            // Второй шаг - разбить список поступающих по типу баллов
            // и высчитать свободные баллы для факультетов
            //presenter?.generateScoreTypedListsAndCalculateAvailableFacultyPlaces()



            // Третий шаг
            //presenter?.separateStudentsBySpecialties()
            // Четвёртый шаг
            //presenter?.checkSpecialtiesForMinimalScore(context)
        }
        showLog("Затраченное время: $time")

        view?.showSnack(R.string.workWithSpecialtiesListsArePrepared)
    }
}