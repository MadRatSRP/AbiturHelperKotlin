package com.madrat.abiturhelper.interfaces.fragments

import android.content.Context
import android.os.Bundle
import com.madrat.abiturhelper.model.*
import com.madrat.abiturhelper.model.faculties.*

interface WorkWithSpecialtiesMVP {
    interface View {
        fun setupMVP()
        fun toActionId(actionId: Int)

        /*fun showFaculties(faculties: ArrayList<Faculty>)
        fun onFacultyClicked(faculty: Faculty, position: Int)
        fun moveToSpecialties(position: Int, titleId: Int)*/
    }
    interface Presenter {
        //Первый этап
        fun generateBachelorsAndSpecialtiesLists(context: Context)
        fun grabSpecialties(context: Context, path: String): ArrayList<Specialty>
        fun grabStudents(context: Context, path: String): ArrayList<Student>
        fun checkTextForBeingEmpty(text: String): Int
        fun divideSpecialtiesByEducationLevel(list: ArrayList<Specialty>): ArrayList<Specialty>?
        fun divideSpecialtiesByFaculty(list: ArrayList<Specialty>)
        fun divideStudentsByAdmissions(list: ArrayList<Student>)
        //Второй этап
        fun generateScoreTypedListsAndCalculateAvailableFacultyPlaces()
        fun returnStudentsSeparatedByScoreType(): ScoreTypes
        fun returnListOfFaculties(): ArrayList<Faculty>
        fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>?): Faculty
        fun withdrawStudentsWithSpecificScore(bachelors: ArrayList<Student>, typeOfScoreId: Int): ArrayList<Student>
        //Третий этап

        fun separateStudentsBySpecialties()
        // УНТИ
        fun checkForUNTI(scoreTypes: ScoreTypes)
        fun separateUNTI(unti: UNTI): ArrayList<ArrayList<Student>>
        // ФЭУ
        fun checkForFEU(scoreTypes: ScoreTypes)
        fun separateFEU(feu: FEU): ArrayList<ArrayList<Student>>
        // ФИТ
        fun checkForFIT(scoreTypes: ScoreTypes)
        fun separateFIT(fit: FIT): ArrayList<ArrayList<Student>>
        // МТФ
        fun checkForMTF(scoreTypes: ScoreTypes)
        fun separateMTF(mtf: MTF): ArrayList<ArrayList<Student>>
        // УНИТ
        fun checkForUNIT(scoreTypes: ScoreTypes)
        fun separateUNIT(unit: UNIT): ArrayList<ArrayList<Student>>
        // ФЭЭ
        fun checkForFEE(scoreTypes: ScoreTypes)
        fun separateFEE(fee: FEE): ArrayList<ArrayList<Student>>
        // Четвёртый этап
        // Нахождение минимального балла для каждой из специальностей

        fun checkSpecialtiesForMinimalScore(context: Context)
        fun checkFacultyForMinimalScore(context: Context, facultyId: Int)
                : ArrayList<Specialty>?
        fun getListOfFacultySpecialtiesByFacultyId(facultyId: Int)
                : ArrayList<Specialty>?

        fun returnFacultyList(): ArrayList<Faculty>?
        fun returnFaculties(): Faculties?
        fun returnFacultyBundle(context: Context, position: Int, titleId: Int): Bundle
        fun returnUNTI(): ArrayList<ArrayList<Student>>?
        fun returnFEU(): ArrayList<ArrayList<Student>>?
        fun returnFIT(): ArrayList<ArrayList<Student>>?
        fun returnMTF(): ArrayList<ArrayList<Student>>?
        fun returnUNIT(): ArrayList<ArrayList<Student>>?
        fun returnFEE(): ArrayList<ArrayList<Student>>?
        fun separateSpecialties(listOfStudents: ArrayList<Student>, arrayOfSpecialties: Array<String>)
                : ArrayList<ArrayList<Student>>

        fun checkForSpecialties(list: ArrayList<Student>, arrayOfSpecialties: Array<String>): ArrayList<Student>
        fun returnListOfStudentsForChosenSpecialty(scoreTypes: ScoreTypes, arrayOfSpecialties: Array<String>): ArrayList<Student>
    }
}
