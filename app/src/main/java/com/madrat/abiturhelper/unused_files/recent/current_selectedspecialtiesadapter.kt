package com.madrat.abiturhelper.unused_files.recent

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.inflate
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_selected_specialties.*

class SelectedSpecialtiesAdapter
    : RecyclerView.Adapter<SelectedSpecialtiesAdapter.SelectedSpecialtiesHolder>(){
    val myApplication = MyApplication.instance

    private var specialties = ArrayList<Specialty>()

    fun updateSpecialtiesList(new_specialties: ArrayList<Specialty>) {
        specialties.clear()
        specialties.addAll(new_specialties)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedSpecialtiesHolder
            = SelectedSpecialtiesHolder(parent.inflate(R.layout.list_selected_specialties))

    override fun onBindViewHolder(holder: SelectedSpecialtiesHolder, position: Int)
            = holder.bind(specialties[position])

    override fun getItemCount(): Int
            = specialties.size

    inner class SelectedSpecialtiesHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(specialty: Specialty) {
            val profileTerm = specialty.profileTerm
            val oldMinimalScore = specialty.minimalScore

            val facultyNumber = when(specialty.faculty) {
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

            val studentsOfFaculty = when (specialty.faculty) {
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

            val positionOfSpecialty = specialtiesOfFaculty?.indexOf(specialty)
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

            val newMinimalScore = when(specialty.profileTerm) {
                "Физика" -> {
                    val studentWithMinimalScore = currentStudentsList?.minBy {
                        it -> it.maths + it.physics + it.additionalScore
                    }
                    studentWithMinimalScore?.let {
                        r -> r.maths + r.physics + r.additionalScore
                    }
                }
                "Обществознание" -> {
                    val studentWithMinimalScore = currentStudentsList?.minBy {
                        it -> it.maths + it.socialScience + it.additionalScore
                    }
                    studentWithMinimalScore?.let {
                        r -> r.maths + r.socialScience + r.additionalScore
                    }
                }
                "Информатика и ИКТ" -> {
                    val studentWithMinimalScore = currentStudentsList?.minBy {
                        it -> it.maths + it.computerScience + it.additionalScore
                    }
                    studentWithMinimalScore?.let {
                        r -> r.maths + r.computerScience + r.additionalScore
                    }
                }
                else -> 0
            }

            selectedSpecialtyNameValue.text = specialty.shortName
            selectedAmountOfStudentsValue.text = currentStudentsList?.size.toString()
            selectedPositionValue.text = newPosition.toString()
            selectedOldMinimalScoreValue.text = oldMinimalScore.toString()
            selectedNewMinimalScoreValue.text = newMinimalScore.toString()
        }
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