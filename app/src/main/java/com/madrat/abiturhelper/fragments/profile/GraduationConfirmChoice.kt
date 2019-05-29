package com.madrat.abiturhelper.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.GraduationConfirmChoiceMVP
import com.madrat.abiturhelper.model.Graduation
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_graduation_confirm_choice.*
import kotlinx.android.synthetic.main.fragment_graduation_confirm_choice.view.*

class GraduationConfirmChoice : Fragment(), GraduationConfirmChoiceMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    val myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val selectedSpecialties = myApplication.returnSelectedSpecialties()

        selectedSpecialties?.let { showSelectedSpecialties(it) }

        val graduationList = selectedSpecialties?.let { calculateGraduationData(it) }
        graduationList?.let { myApplication.saveGraduationList(it) }

        confirmChoiceShowGraduationList.setOnClickListener {
            toSpecialties(null, R.id.action_confirm_choice_to_show_current_list)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity)
                .supportActionBar?.setTitle(R.string.profileGraduationConfirmChoiceTitle)
        val view = inflater.inflate(R.layout.fragment_graduation_confirm_choice,
                container, false)

        adapter = SpecialtiesAdapter(null)
        view.confirmChoiceRecyclerView.adapter = adapter
        view.confirmChoiceRecyclerView.linearManager()

        return view
    }

    /*override*/fun showSelectedSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        confirmChoiceRecyclerView.adapter = adapter
    }
    /*override*/ fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }

    fun calculateGraduationData(selectedSpecialties: ArrayList<Specialty>): ArrayList<Graduation> {
        val graduationList = ArrayList<Graduation>()

        selectedSpecialties.forEach {
            val specialtyName = it.shortName
            val facultyName = it.faculty
            val profileTerm = it.profileTerm
            val oldMinimalScore = it.minimalScore

            val facultyNumber = when(facultyName) {
                // УНТИ
                "Учебно-научный технологический институт" -> 0
                // ФЭУ
                "Факультет экономики и управления" -> 1
                // ФИТ
                "Факультет информационных технологий" -> 2
                // МТФ
                "Механико-технологический факультет" -> 3
                // УНИТ
                "Учебно-научный институт транспорта" -> 4
                // ФЭЭ
                "Факультет энергетики и электроники" -> 5
                else -> 7
            }

            val studentsOfFaculty = when (facultyName) {
                // УНТИ
                "Учебно-научный технологический институт" -> myApplication.returnUNTI()
                // ФЭУ
                "Факультет экономики и управления" -> myApplication.returnFEU()
                // ФИТ
                "Факультет информационных технологий" -> myApplication.returnFIT()
                // МТФ
                "Механико-технологический факультет" -> myApplication.returnMTF()
                // УНИТ
                "Учебно-научный институт транспорта" -> myApplication.returnUNIT()
                // ФЭЭ
                "Факультет энергетики и электроники" -> myApplication.returnFEE()
                else -> null
            }

            val specialtiesOfFaculty = getSpecialtiesListByPosition(facultyNumber)

            val positionOfSpecialty = specialtiesOfFaculty?.indexOf(it)
            showLog("ПОЗИЦИЯ СПЕЦИАЛЬНОСТИ: $positionOfSpecialty")

            val currentStudentsList = positionOfSpecialty?.let{ studentsOfFaculty?.get(positionOfSpecialty) }
            showLog("РАЗМЕР: ${currentStudentsList?.size}")

            val score = myApplication.returnScore()
            val fullName = myApplication.returnFullName()

            val student = score?.let {
                fullName?.let {
                    Student("007", fullName.lastName, fullName.firstName, fullName.patronymic,
                            "####", "net", "Принято", "", "бак",
                            "по конкурсу", " ", "", "",
                            score.russian, score.maths, score.physics, score.computerScience, score.socialScience,
                            score.additionalScore, true, true, 0)
                }
            }
            student?.let { currentStudentsList?.add(it) }
            showLog("РАЗМЕР: ${currentStudentsList?.size}")

            //var sortedCurrentStudentList: ArrayList<Student>? = null

            when(profileTerm) {
                "Физика" -> currentStudentsList?.sortByDescending { it.maths + it.physics + it.additionalScore }
                "Обществознание" -> currentStudentsList?.sortByDescending { it.maths + it.socialScience + it.additionalScore }
                "Информатика и ИКТ" -> currentStudentsList?.sortByDescending { it.maths + it.computerScience + it.additionalScore }
            }

            val newPosition = currentStudentsList?.indexOf(student)
            showLog("ПОЗИЦИЯ: $newPosition")

            val newMinimalScore = when(profileTerm) {
                "Физика" -> {
                    val studentWithMinimalScore = currentStudentsList?.minBy {
                        r -> r.maths + r.physics + r.additionalScore
                    }
                    studentWithMinimalScore?.let {
                        r -> r.maths + r.physics + r.additionalScore
                    }
                }
                "Обществознание" -> {
                    val studentWithMinimalScore = currentStudentsList?.minBy {
                        r -> r.maths + r.socialScience + r.additionalScore
                    }
                    studentWithMinimalScore?.let {
                        r -> r.maths + r.socialScience + r.additionalScore
                    }
                }
                "Информатика и ИКТ" -> {
                    val studentWithMinimalScore = currentStudentsList?.minBy {
                        r -> r.maths + r.computerScience + r.additionalScore
                    }
                    studentWithMinimalScore?.let {
                        r -> r.maths + r.computerScience + r.additionalScore
                    }
                }
                else -> 0
            }

            val amountOfStudents = currentStudentsList?.let { currentStudentsList.size }

            val graduation = amountOfStudents?.let { amount ->
                newPosition?.let { newPos ->
                    newMinimalScore?.let { newMinScore ->
                        Graduation(
                                specialtyName,
                                facultyName,
                                amount,
                                newPos,
                                oldMinimalScore,
                                newMinScore
                        )
                    }
                }
            }

            graduation?.let { grad -> graduationList.add(grad) }
        }

        return graduationList
    }


    fun getSpecialtiesListByPosition(pos: Int): ArrayList<Specialty>? {
        val faculties = myApplication.returnFaculties()

        return when (pos) {
            //УНТИ
            0 -> faculties?.listUNTI
            //ФЭУ
            1 -> faculties?.listFEU
            //ФИТ
            2 -> faculties?.listFIT
            //МТФ
            3 -> faculties?.listMTF
            //УНИТ
            4 -> faculties?.listUNIT
            //ФЭЭ
            5 -> faculties?.listFEE
            else -> null
        }
    }
}