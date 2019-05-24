package com.madrat.abiturhelper.interfaces.fragments

import android.content.Context
import android.os.Bundle
import com.madrat.abiturhelper.model.*

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
        fun checkForUnti()
        fun checkForFEU()
        fun checkForFIT()
        // МТФ
        fun checkForMTF()
        fun separateMASH(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateSIM(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateTB(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateUK(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        // УНИТ
        fun checkForUNIT()
        fun separateNTTK(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateNTTS(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separatePM(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separatePSJD(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateTTP(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateETTK(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        // ФЭЭ
        fun checkForFEE()
        fun separateRAD(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateTIT(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateEIN(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateEIE(list: ArrayList<Student>): ArrayList<ArrayList<Student>>
        fun separateEM(list: ArrayList<Student>): ArrayList<ArrayList<Student>>

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

        // Пятый этап
        fun checkForZeroMinimalScore()
        fun checkUNTIForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFEUForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFITForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkMTFForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkUNITForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFEEForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty>

        // Шестой этап
        fun checkForFittingSpecialties()
        fun checkUNTIForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFEUForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFITForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkMTFForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkUNITForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>
        fun checkFEEForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty>

        fun returnFacultyList(): ArrayList<Faculty>?
        fun returnFaculties(): Faculties?
        fun returnFacultyBundle(context: Context, position: Int, titleId: Int): Bundle
        fun returnUNTI(): ArrayList<ArrayList<Student>>?
        fun returnFEU(): ArrayList<ArrayList<Student>>?
        fun returnFIT(): ArrayList<ArrayList<Student>>?
        fun returnMTF(): ArrayList<ArrayList<Student>>?
        fun returnUNIT(): ArrayList<ArrayList<Student>>?
        fun returnFEE(): ArrayList<ArrayList<Student>>?
    }
    interface Repository {

    }
}
