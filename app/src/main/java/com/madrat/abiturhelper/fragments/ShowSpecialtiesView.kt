package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.presenters.fragments.ShowSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_specialties.*
import kotlinx.android.synthetic.main.fragment_specialties.view.*

class ShowSpecialtiesView
    : Fragment(), ShowSpecialtiesMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var showSpecialtiesPresenter: ShowSpecialtiesPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val pos = arguments?.getInt("pos")
        val list = pos?.let { showSpecialtiesPresenter?.getSpecialtiesListByPosition(it) }

        list?.let {
            when (pos) {
                //УНТИ
                0 -> {
                    adapter = showSpecialtiesPresenter?.initializeAdapter(this::onUNTISpecialtyClicked)

                    val listUNTI = showSpecialtiesPresenter?.returnUNTI()
                    for (i in 0 until list.size) {
                        listUNTI?.let {
                            list[i].amountOfStatements = it[i].size

                            when(list[i].profileTerm) {
                                "Физика" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.physics }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndPhysics)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.physics }
                                }
                                "Обществознание" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.socialScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndSocialScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.socialScience }
                                }
                                "Информатика и ИКТ" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.computerScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndComputerScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.computerScience }
                                }
                                else -> return
                            }
                        }
                    }
                }
                //ФЭУ
                1 -> {
                    adapter = showSpecialtiesPresenter?.initializeAdapter(this::onFEUSpecialtyClicked)

                    val listFEU = showSpecialtiesPresenter?.returnFEU()
                    for (i in 0 until list.size) {
                        listFEU?.let {
                            list[i].amountOfStatements = it[i].size

                            when(list[i].profileTerm) {
                                "Физика" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.physics }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndPhysics)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.physics }
                                }
                                "Обществознание" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.socialScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndSocialScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.socialScience }
                                }
                                "Информатика и ИКТ" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.computerScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndComputerScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.computerScience }
                                }
                                else -> return
                            }
                        }
                    }
                }
                //ФИТ
                2 -> {
                    adapter = showSpecialtiesPresenter?.initializeAdapter(this::onFITSpecialtyClicked)

                    val listFIT = showSpecialtiesPresenter?.returnFIT()
                    for (i in 0 until list.size) {
                        listFIT?.let {
                            list[i].amountOfStatements = it[i].size

                            when(list[i].profileTerm) {
                                "Физика" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.physics }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndPhysics)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.physics }
                                }
                                "Обществознание" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.socialScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndSocialScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.socialScience }
                                }
                                "Информатика и ИКТ" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.computerScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndComputerScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.computerScience }
                                }
                                else -> return
                            }
                        }
                    }
                }
                //МТФ
                3 -> {
                    adapter = showSpecialtiesPresenter?.initializeAdapter(this::onMTFSpecialtyClicked)

                    val listMTF = showSpecialtiesPresenter?.returnMTF()
                    for (i in 0 until list.size) {
                        listMTF?.let {
                            list[i].amountOfStatements = it[i].size

                            when(list[i].profileTerm) {
                                "Физика" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.physics }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndPhysics)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.physics }
                                }
                                "Обществознание" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.socialScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndSocialScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.socialScience }
                                }
                                "Информатика и ИКТ" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.computerScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndComputerScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.computerScience }
                                }
                                else -> return
                            }
                        }
                    }
                }
                //УНИТ
                4 -> {
                    adapter = showSpecialtiesPresenter?.initializeAdapter(this::onUNITSpecialtyClicked)

                    val listUNIT = showSpecialtiesPresenter?.returnUNIT()
                    for (i in 0 until list.size) {
                        listUNIT?.let {
                            list[i].amountOfStatements = it[i].size

                            when(list[i].profileTerm) {
                                "Физика" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.physics }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndPhysics)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.physics }
                                }
                                "Обществознание" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.socialScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndSocialScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.socialScience }
                                }
                                "Информатика и ИКТ" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.computerScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndComputerScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.computerScience }
                                }
                                else -> return
                            }
                        }
                    }
                }
                //ФЭЭ
                5 -> {
                    adapter = showSpecialtiesPresenter?.initializeAdapter(this::onFEESpecialtyClicked)

                    val listFEE = showSpecialtiesPresenter?.returnFEE()
                    for (i in 0 until list.size) {
                        listFEE?.let {
                            list[i].amountOfStatements = it[i].size

                            when(list[i].profileTerm) {
                                "Физика" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.physics }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndPhysics)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.physics }
                                }
                                "Обществознание" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.socialScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndSocialScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.socialScience }
                                }
                                "Информатика и ИКТ" -> {
                                    val minimalScore = it[i].minBy { r -> r.maths + r.computerScience }

                                    list[i].scoreTitle = context?.getString(R.string.facultyMathsAndComputerScience)

                                    minimalScore?.let {  r -> list[i].minimalScore = r.maths + r.computerScience }
                                }
                                else -> return
                            }
                        }
                    }
                }
            }
        }

        list?.let { showSpecialties(it) }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val title = arguments?.getString("title")
        (activity as AppCompatActivity).supportActionBar?.title = title
        val view = inflater.inflate(R.layout.fragment_specialties, container, false)

        view.specialtiesRecyclerView.adapter = adapter
        view.specialtiesRecyclerView.linearManager()
        return view
    }

    override fun setupMVP() {
        showSpecialtiesPresenter = ShowSpecialtiesPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        specialtiesRecyclerView?.adapter = adapter
    }
    override fun toStudents(bundle: Bundle) {
        view?.let {
            Navigation.findNavController(it)
                    .navigate(R.id.action_showSpecialtiesView_to_showBachelors, bundle)
        }
    }
    override fun moveToStudents(list: ArrayList<Student>, specialty: Specialty) {
        val bundle = showSpecialtiesPresenter?.returnSpecialtyBundle(list, specialty)
        bundle?.let { toStudents(it) }
    }
    override fun onUNTISpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val listUNTI = showSpecialtiesPresenter?.returnUNTI()
        listUNTI?.let {
            moveToStudents(it[position], specialty)
        }
    }
    override fun onFEUSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val listFEU = showSpecialtiesPresenter?.returnFEU()
        listFEU?.let {
            moveToStudents(it[position], specialty)
        }
    }
    override fun onFITSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val listFIT = showSpecialtiesPresenter?.returnFIT()
        listFIT?.let {
            moveToStudents(it[position], specialty)
        }
    }
    override fun onMTFSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val listMTF = showSpecialtiesPresenter?.returnMTF()
        listMTF?.let {
            moveToStudents(it[position], specialty)
        }
    }
    override fun onUNITSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val listUNIT = showSpecialtiesPresenter?.returnUNIT()
        listUNIT?.let {
            moveToStudents(it[position], specialty)
        }
    }
    override fun onFEESpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val listFEE = showSpecialtiesPresenter?.returnFEE()
        listFEE?.let {
            moveToStudents(it[position], specialty)
        }
    }
}