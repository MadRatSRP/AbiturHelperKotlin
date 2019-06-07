package com.madrat.abiturhelper.fragments.chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.model.Chance
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_chance_confirm_choice.*
import kotlinx.android.synthetic.main.fragment_chance_confirm_choice.view.*
import kotlinx.android.synthetic.main.fragment_graduation_confirm_choice.*

class ChanceConfirmChoice: Fragment() {
    private var adapter: SpecialtiesAdapter? = null
    private var myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Получаем выбранный пользователем список специальностей для проверки
        // и показываем его
        val chosenSpecialties = myApplication.returnChosenSpecialties()
        chosenSpecialties?.let { showSelectedSpecialties(it) }

        /*confirmChoiceShowGraduationList.setOnClickListener {
            toSpecialties(null, R.id.action_confirm_choice_to_show_current_list)
        }*/

        val listOfChances = chosenSpecialties?.let { calculateChancesData(it) }
        listOfChances?.let { myApplication.saveListOfChances(it) }

        chanceConfirmChoiceShowGraduationList.setOnClickListener {
            toSpecialties(R.id.action_confirmChoice_to_showResults)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.chanceConfirmChoiceTitle)
        val view = inflater.inflate(R.layout.fragment_chance_confirm_choice,
                container, false)

        adapter = SpecialtiesAdapter(null)
        view.chanceConfirmChoiceRecyclerView.adapter = adapter
        view.chanceConfirmChoiceRecyclerView.linearManager()
        return view
    }

    /*override*/ fun showSelectedSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        chanceConfirmChoiceRecyclerView.adapter = adapter
    }

    fun calculateChancesData(chosenSpecialties: ArrayList<Specialty>)
        : ArrayList<Chance>{
        //Объявляем новый список типа Chance
        val listOfChances = ArrayList<Chance>()
        // Получаем ФИО пользователя и его баллы
        val fullName = myApplication.returnFullName()
        val score = myApplication.returnScore()
        //Для каждой специальности из списка chosenSpecialties
        chosenSpecialties.forEach {
            val specialtyName = it.shortName
            val facultyName = it.faculty
            val minimalScore = it.minimalScore
            val profileTerm = it.profileTerm
            val totalOfEntries = it.entriesTotal

            val facultyNumber = returnFacultyNumberByFacultyName(facultyName)
            val studentsOfFaculty = returnStudentsOfFacultyByFacultyName(facultyName)
            val specialtiesOfFaculty = getSpecialtiesListByPosition(facultyNumber)

            val positionOfSpecialty = specialtiesOfFaculty?.indexOf(it)
            showLog("ПОЗИЦИЯ СПЕЦИАЛЬНОСТИ: $positionOfSpecialty")


            val currentStudentsList = positionOfSpecialty?.let{ studentsOfFaculty?.get(positionOfSpecialty) }
            showLog("РАЗМЕР: ${currentStudentsList?.size}")

            val student = score?.let {
                fullName?.let {
                    Student("007", fullName.lastName, fullName.firstName, fullName.patronymic,
                            "####", "net", "Принято", "", "бак",
                            "по конкурсу", " ", "", "",
                            score.russian, score.maths, score.physics, score.computerScience, score.socialScience,
                            score.additionalScore, true, true, 0)
                }
            }
            student?.let {std -> currentStudentsList?.add(std) }
            showLog("РАЗМЕР: ${currentStudentsList?.size}")


            when(profileTerm) {
                "Физика" -> currentStudentsList
                        ?.sortByDescending {st -> st.maths + st.physics + st.additionalScore }
                "Обществознание" -> currentStudentsList
                        ?.sortByDescending {st -> st.maths + st.socialScience + st.additionalScore }
                "Информатика и ИКТ" -> currentStudentsList
                        ?.sortByDescending {st -> st.maths + st.computerScience + st.additionalScore }
            }
            val supposedPosition = currentStudentsList?.indexOf(student)
            showLog("ПОЗИЦИЯ: $supposedPosition")

            currentStudentsList?.remove(student)

            val amountOfStudents = currentStudentsList?.let { currentStudentsList.size }

            /*val chanceOfGraduation = supposedPosition?.let {
                when(amountOfStudents) {
                    0 -> 0
                    in 1..supposedPosition -> 100
                    else -> 0
                }
            }*/

            /*val chanceOfGraduation = when(amountOfStudents) {
                0 -> 0

            }*/

            var chanceOfGraduation: Int? = null
            if (totalOfEntries == 0)
                chanceOfGraduation = 0
            else if (supposedPosition != null) {
                if (supposedPosition <= totalOfEntries - 1)
                    chanceOfGraduation = 100
            }

            val chance = chanceOfGraduation?.let {
                amountOfStudents?.let {
                    supposedPosition?.let {
                        Chance(specialtyName, facultyName, minimalScore,
                                chanceOfGraduation.toDouble(), totalOfEntries,
                                amountOfStudents, supposedPosition)
                    } } }
            chance?.let { it1 -> listOfChances.add(it1) }
        }
        return listOfChances
    }

    fun getSpecialtiesListByPosition(pos: Int?): ArrayList<Specialty>? {
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
    fun returnFacultyNumberByFacultyName(facultyName: String): Int {
        return when(facultyName) {
            // УНТИ
            "Учебно-научный технологический институт"
            -> 0
            // ФЭУ
            "Факультет экономики и управления"
            -> 1
            // ФИТ
            "Факультет информационных технологий"
            -> 2
            // МТФ
            "Механико-технологический факультет"
            -> 3
            // УНИТ
            "Учебно-научный институт транспорта"
            -> 4
            // ФЭЭ
            "Факультет энергетики и электроники"
            -> 5
            else -> 7
        }
    }
    fun returnStudentsOfFacultyByFacultyName(facultyName: String)
            : ArrayList<ArrayList<Student>>? {
        return when (facultyName) {
            // УНТИ
            "Учебно-научный технологический институт"
            -> myApplication.returnUNTI()
            // ФЭУ
            "Факультет экономики и управления"
            -> myApplication.returnFEU()
            // ФИТ
            "Факультет информационных технологий"
            -> myApplication.returnFIT()
            // МТФ
            "Механико-технологический факультет"
            -> myApplication.returnMTF()
            // УНИТ
            "Учебно-научный институт транспорта"
            -> myApplication.returnUNIT()
            // ФЭЭ
            "Факультет энергетики и электроники"
            -> myApplication.returnFEE()
            else -> null
        }
    }

    /*override*/ fun toSpecialties(actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId) }
    }
}