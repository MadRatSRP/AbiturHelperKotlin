package com.madrat.abiturhelper.data.presenters.fragments.profile

import com.madrat.abiturhelper.data.`object`.FacultiesObject
import com.madrat.abiturhelper.data.interfaces.fragments.profile.GraduationConfirmChoiceMVP
import com.madrat.abiturhelper.data.model.Graduation
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.data.model.Student
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.showLog

class GraduationConfirmChoicePresenter
    : GraduationConfirmChoiceMVP.Presenter {
    val myApplication = MyApplication.instance

    override fun getSpecialtiesListByPosition(pos: Int?): ArrayList<Specialty>? {
        val specialtiesOfFaculties = myApplication.returnSpecialtiesOfFaculties()

        return when (pos) {
            // УНТИ
            FacultiesObject.FACULTY_UNTI ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_UNTI]
            // ФЭУ
            FacultiesObject.FACULTY_FEU ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_FEU]
            // ФИТ
            FacultiesObject.FACULTY_FIT ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_FIT]
            // МТФ
            FacultiesObject.FACULTY_MTF ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_MTF]
            // УНИТ
            FacultiesObject.FACULTY_UNIT ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_UNIT]
            // ФЭЭ
            FacultiesObject.FACULTY_FEE ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_FEE]
            else -> null
        }
    }
    override fun returnFacultyNumberByFacultyName(facultyName: String): Int {
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
    override fun returnStudentsOfFacultyByFacultyName(facultyName: String)
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

    override fun calculateGraduationData(selectedSpecialties: ArrayList<Specialty>)
            : ArrayList<Graduation> {
        // Объявляем новый список типа модели Graduation
        val graduationList = ArrayList<Graduation>()

        // Получаем ФИО пользователя и его баллы
        val fullName = myApplication.returnFullName()
        val score = myApplication.returnScore()

        selectedSpecialties.forEach {
            val specialtyName = it.shortName
            val facultyName = it.faculty
            val profileTerm = it.profileTerm
            val oldMinimalScore = it.minimalScore
            val entriesTotal = it.entriesTotal

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

            //var sortedCurrentStudentList: ArrayList<Student>? = null

            when(profileTerm) {
                "Физика" -> currentStudentsList
                        ?.sortByDescending {st -> st.maths + st.physics + st.additionalScore }
                "Обществознание" -> currentStudentsList
                        ?.sortByDescending {st -> st.maths + st.socialScience + st.additionalScore }
                "Информатика и ИКТ" -> currentStudentsList
                        ?.sortByDescending {st -> st.maths + st.computerScience + st.additionalScore }
            }

            //currentStudentsList?.let { arr -> saveCurrentListOfStudents(arr) }

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
                                entriesTotal,
                                oldMinimalScore,
                                newMinScore,
                                currentStudentsList
                        )
                    }
                }
            }
            graduation?.let { grad -> graduationList.add(grad) }
        }
        return graduationList
    }

    override fun returnSelectedSpecialties()
            = myApplication.returnSelectedSpecialties()

    override fun saveGraduationList(graduationList: ArrayList<Graduation>?) {
        graduationList?.let { myApplication.saveGraduationList(it) }
    }
    /*override fun saveCurrentListOfStudents(list: ArrayList<Student>) {
        myApplication.saveCurrentListOfStudents(list)
    }*/
}