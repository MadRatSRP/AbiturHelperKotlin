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
        fun withdrawPhysicsStudents(bachelors: ArrayList<Student>): ArrayList<Student>
        fun withdrawComputerScienceStudents(bachelors: ArrayList<Student>): ArrayList<Student>
        fun withdrawSocialScienceStudents(bachelors: ArrayList<Student>): ArrayList<Student>
        fun withdrawStudentsWithPartAndFullData(bachelors: ArrayList<Student>): ArrayList<Student>
        fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>?): Faculty

        //Третий этап

        fun separateStudentsBySpecialties()
        // УНТИ
        fun checkForUNTI()
        fun separateUNTI(unti: UNTI): ArrayList<ArrayList<Student>>
        // ФЭУ
        fun checkForFEU()
        fun separateFEU(feu: FEU): ArrayList<ArrayList<Student>>
        // ФИТ
        fun checkForFIT()
        fun separateFIT(fit: FIT): ArrayList<ArrayList<Student>>
        // МТФ
        fun checkForMTF()
        fun separateMTF(mtf: MTF): ArrayList<ArrayList<Student>>
        // УНИТ
        fun checkForUNIT()
        fun separateUNIT(unit: UNIT): ArrayList<ArrayList<Student>>
        // ФЭЭ
        fun checkForFEE()
        fun separateFEE(fee: FEE): ArrayList<ArrayList<Student>>
        // Четвёртый этап
        // Нахождение минимального балла для каждой из специальностей

        fun checkSpecialtiesForMinimalScore(context: Context)
        fun checkUNTIForMinimalScore(context: Context, position: Int): ArrayList<Specialty>?
        fun checkFEUForMinimalScore(context: Context, position: Int): ArrayList<Specialty>?
        fun checkFITForMinimalScore(context: Context, position: Int): ArrayList<Specialty>?
        fun checkMTFForMinimalScore(context: Context, position: Int): ArrayList<Specialty>?
        fun checkUNITForMinimalScore(context: Context, position: Int): ArrayList<Specialty>?
        fun checkFEEForMinimalScore(context: Context, position: Int): ArrayList<Specialty>?
        fun getSpecialtiesListByPosition(pos: Int): ArrayList<Specialty>?

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
    }
}
