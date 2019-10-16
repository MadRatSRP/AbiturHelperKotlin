package com.madrat.abiturhelper.fragments.select_specialties

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.WorkWithSpecialtiesMVP
import com.madrat.abiturhelper.presenters.fragments.WorkWithSpecialtiesPresenter
import com.madrat.abiturhelper.util.moveToSelectedFragment
import com.madrat.abiturhelper.util.showLog
import com.madrat.abiturhelper.util.showSnack
import kotlinx.android.synthetic.main.fragment_work_with_specialties.*
import kotlin.system.measureTimeMillis

class WorkWithSpecialtiesView
    : Fragment(), WorkWithSpecialtiesMVP.View{
    private var presenter: WorkWithSpecialtiesPresenter? = null

    init {
        presenter = WorkWithSpecialtiesPresenter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let { formListOfStudentsAndMinimalScores(it) }

        workToCurrentList.setOnClickListener {
            onToCurrentListClicked()
        }
        workToResult.setOnClickListener {
            onToResultClicked()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.workWithSpecialtiesTitle)
        return inflater.inflate(R.layout.fragment_work_with_specialties, container, false)
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
            presenter?.separateStudentsBySpecialties()
            // Четвёртый шаг
            presenter?.checkSpecialtiesForMinimalScore(context)
        }
        showLog("Затраченное время: $time")

        view?.showSnack(R.string.workWithSpecialtiesListsArePrepared)
    }
}