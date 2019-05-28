package com.madrat.abiturhelper.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.showLog

class CalculateUserPlacesView: Fragment() {
    val myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val selectedSpecialties = myApplication.returnSelectedSpecialties()

        val selectedSpecialty = selectedSpecialties?.let { selectedSpecialties[0] }

        val profileTerm = selectedSpecialty?.profileTerm

        val facultyNumber = when(selectedSpecialty?.faculty) {
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

        val studentsOfFaculty = when (selectedSpecialty?.faculty) {
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

        val positionOfSpecialty = specialtiesOfFaculty?.indexOf(selectedSpecialty)
        showLog("ПОЗИЦИЯ: $positionOfSpecialty")

        val currentStudentsList = positionOfSpecialty?.let{ studentsOfFaculty?.get(positionOfSpecialty) }
        showLog("РАЗМЕР: ${currentStudentsList?.size}")

        val score = myApplication.returnScore()
        val additionalScore = myApplication.returnAdditionalScore()

        val student = score?.let {
            additionalScore?.let { it1 ->
                Student("007", "Алпеев", "Михаил", "Сергеевич",
                        "####", "net", "Принято", "", "бак",
                        "по конкурсу", " ", "", "",
                        score.russian, score.maths, score.physics, score.computerScience, score.socialScience,
                        it1, true, true, 0)
            }
        }
        student?.let { currentStudentsList?.add(it) }
        showLog("РАЗМЕР: ${currentStudentsList?.size}")

        //var sortedCurrentStudentList: ArrayList<Student>? = null

        when(profileTerm) {
            "Физика" -> currentStudentsList?.sortBy { it.maths + it.physics + it.additionalScore }
            "Обществознание" -> currentStudentsList?.sortBy { it.maths + it.socialScience + it.additionalScore }
            "Информатика и ИКТ" -> currentStudentsList?.sortBy { it.maths + it.computerScience + it.additionalScore }
        }

        val newPosition = currentStudentsList?.indexOf(student)
        showLog("ПОЗИЦИЯ: $newPosition")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.calculateUserPlacesTitle)
        val view = inflater.inflate(R.layout.fragment_calculate_user_places,
                container, false)
        return view
    }

    /*override*/ fun getSpecialtiesListByPosition(pos: Int): ArrayList<Specialty>? {
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