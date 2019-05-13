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
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import com.madrat.abiturhelper.util.stringAndSerializable
import kotlinx.android.synthetic.main.fragment_specialties.*
import kotlinx.android.synthetic.main.fragment_specialties.view.*

class ShowSpecialtiesView
    : Fragment(), ShowSpecialtiesMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private val myApplication = MyApplication.instance

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
                    adapter = initializeAdapter(this::onUNTISpecialtyClicked)

                    val listUNTI = myApplication.returnUnti()
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
                    adapter = initializeAdapter(this::onFEUSpecialtyClicked)

                    val listFEU = myApplication.returnFeu()
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
                    adapter = initializeAdapter(this::onFITSpecialtyClicked)

                    val listFIT = myApplication.returnFIT()
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
                    adapter = initializeAdapter(this::onMTFSpecialtyClicked)

                    val listMTF = myApplication.returnMTF()
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
                    adapter = initializeAdapter(this::onUNITSpecialtyClicked)

                    val listUNIT = myApplication.returnUNIT()
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
                    adapter = initializeAdapter(this::onFEESpecialtyClicked)

                    val listFEE = myApplication.returnFEE()
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
    override fun initializeAdapter(example: (Specialty, Int) -> Unit): SpecialtiesAdapter {
        return SpecialtiesAdapter{specialty: Specialty, position: Int -> example(specialty, position)}
    }

    override fun showSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        specialtiesRecyclerView?.adapter = adapter
    }

    private fun onUNTISpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val listUNTI = myApplication.returnUnti()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }
        listUNTI?.let {
            moveToSpecialty(it[position])
        }
    }
    private fun onFEUSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val listFEU = myApplication.returnFeu()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }
        listFEU?.let {
            moveToSpecialty(it[position])
        }
    }
    private fun onFITSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val listFIT = myApplication.returnFIT()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }
        listFIT?.let {
            moveToSpecialty(it[position])
        }
    }
    private fun onMTFSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val listMTF = myApplication.returnMTF()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }
        listMTF?.let {
            moveToSpecialty(it[position])
        }
    }
    private fun onUNITSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val listUNIT = myApplication.returnUNIT()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }
        listUNIT?.let {
            moveToSpecialty(it[position])
        }
    }
    private fun onFEESpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        //val fee = myApplication.returnFEE()
        val listFEE = myApplication.returnFEE()
        showLog("Размер listFEE" + listFEE?.size)

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }
        listFEE?.let {
            moveToSpecialty(it[position])
        }
    }

    override fun toSpecialty(bundle: Bundle) {
        view?.let {
            Navigation.findNavController(it)
                    .navigate(R.id.action_showSpecialtiesView_to_showBachelors, bundle)
        }
    }
}