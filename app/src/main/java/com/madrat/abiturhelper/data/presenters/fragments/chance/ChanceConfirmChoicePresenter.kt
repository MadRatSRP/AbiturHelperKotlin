package com.madrat.abiturhelper.data.presenters.fragments.chance

import com.madrat.abiturhelper.data.`object`.FacultiesObject
import com.madrat.abiturhelper.data.interfaces.fragments.chance.ChanceConfirmChoiceMVP
import com.madrat.abiturhelper.data.model.Chance
import com.madrat.abiturhelper.data.model.FullName
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.data.model.Student
import com.madrat.abiturhelper.util.Constants
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.showLog
import io.paperdb.Book
import io.paperdb.Paper

class ChanceConfirmChoicePresenter
    : ChanceConfirmChoiceMVP.Presenter {
    private var myApplication = MyApplication.instance

    override fun calculateChancesData(chosenSpecialties: ArrayList<Specialty>)
            : ArrayList<Chance>{

        //Объявляем новый список типа Chance
        val listOfChances = ArrayList<Chance>()

        // Получаем ФИО пользователя и его баллы
        val fullName: FullName = Paper.book().read(Constants.FULL_NAME)
        
        val score = myApplication.returnScore()

        val student = score?.let {
            fullName?.let {
                Student("007", fullName.lastName, fullName.firstName, fullName.patronymic,
                        "####", "net", "Принято", "", "бак",
                        "по конкурсу", " ", "", "",
                        score.russian, score.maths, score.physics, score.computerScience, score.socialScience,
                        score.additionalScore, true, true, 0)
            }
        }

        fun prepareChancesData(specialty: Specialty): Chance {
            showLog("Размер списка Chance${listOfChances.size}")

            // Название специальности и факультета, минимальный балл,
            // профильный предмет и количество мест
            val specialtyName = specialty.shortName
            val facultyName = specialty.faculty
            val minimalScore = specialty.minimalScore
            val profileTerm = specialty.profileTerm
            val totalOfEntries = specialty.entriesTotal

            val facultyNumber = returnFacultyPositionNumberByFacultyName(facultyName)

            val studentsOfFaculty = returnListOfStudentsInFacultyByFacultyName(facultyName)

            val specialtiesOfFaculty = returnListOfSpecialtiesByPosition(facultyNumber)

            val positionOfSpecialty = specialtiesOfFaculty?.indexOf(specialty)
            showLog("ПОЗИЦИЯ СПЕЦИАЛЬНОСТИ: $positionOfSpecialty")


            val currentStudentsList = positionOfSpecialty?.let{ studentsOfFaculty?.get(positionOfSpecialty) }
            showLog("РАЗМЕР: ${currentStudentsList?.size}")

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

            /*var chanceOfGraduation: Int? = null
            if (totalOfEntries == 0)
                chanceOfGraduation = 0
            else if (supposedPosition != null) {
                if (supposedPosition <= totalOfEntries - 1)
                    chanceOfGraduation = 100
            }*/

            /*val chanceOfGraduation = supposedPosition?.let {
                when (totalOfEntries) {
                    0 -> 0

                    /*if (supposedPosition <= totalOfEntries - 1)
                        -> 100*/

                    //in 0..supposedPosition ->
                    in 0..supposedPosition -> 100



                    else -> 0
                }
            }*/
            /*var chance:Int = if (totalOfEntries == 0)
                0 else {

            }*/

            val chanceOfGraduation: Int = if(totalOfEntries == 0) {
                0
            } else {
                when (supposedPosition) {
                    in 0 until totalOfEntries -> 100
                    else -> 0
                }
            }

            /*val chanceOfGraduation: Int = when(supposedPosition) {
                in 0 until totalOfEntries -> 100

            }*/

            val chance: Chance = Chance(specialtyName, facultyName, minimalScore,
                    chanceOfGraduation?.toDouble(), totalOfEntries, amountOfStudents, supposedPosition)

            showLog("CHANSIK$chance")
            return chance
        }

        //Для каждой специальности из списка chosenSpecialties
        /*chosenSpecialties.forEach {
            prepareChancesData(it)?.let { it1 -> listOfChances.add(it1) }
        }*/

        /*for (i in 0 until chosenSpecialties.size) {
            listOfChances.add(prepareChancesData(chosenSpecialties[i]))
        }*/

        for (i in 0 until chosenSpecialties.size) {
            listOfChances.add(prepareChancesData(chosenSpecialties[i]))
        }

        showLog("CHOCHOHO${listOfChances.size}")
        return listOfChances
    }

    override fun saveListOfChances(listOfChances: ArrayList<Chance>)
            = myApplication.saveListOfChances(listOfChances)
    override fun returnListOfSpecialtiesByPosition(pos: Int?)
            : ArrayList<Specialty>? {
        val specialtiesOfFaculty = myApplication.returnSpecialtiesOfFaculties()

        return when (pos) {
            // УНТИ
            FacultiesObject.FACULTY_UNTI ->
                specialtiesOfFaculty[FacultiesObject.FACULTY_UNTI]
            // ФЭУ
            FacultiesObject.FACULTY_FEU ->
                specialtiesOfFaculty[FacultiesObject.FACULTY_FEU]
            // ФИТ
            FacultiesObject.FACULTY_FIT ->
                specialtiesOfFaculty[FacultiesObject.FACULTY_FIT]
            // МТФ
            FacultiesObject.FACULTY_MTF ->
                specialtiesOfFaculty[FacultiesObject.FACULTY_MTF]
            // УНИТ
            FacultiesObject.FACULTY_UNIT ->
                specialtiesOfFaculty[FacultiesObject.FACULTY_UNIT]
            // ФЭЭ
            FacultiesObject.FACULTY_FEE ->
                specialtiesOfFaculty[FacultiesObject.FACULTY_FEE]
            else -> null
        }
    }
    override fun returnFacultyPositionNumberByFacultyName(facultyName: String)
            : Int {
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
    override fun returnListOfStudentsInFacultyByFacultyName(facultyName: String)
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
    override fun returnChosenSpecialties()
            = myApplication.returnChosenSpecialties()
}