package com.madrat.abiturhelper.interfaces.fragments

import android.content.Context
import android.os.Bundle
import com.madrat.abiturhelper.model.*
import com.madrat.abiturhelper.model.faculties.*
import kotlinx.coroutines.Job
import org.apache.commons.csv.CSVParser
import java.io.InputStream

interface WorkWithSpecialtiesMVP {
    interface View {

        /*fun showFaculties(faculties: ArrayList<Faculty>)
        fun onFacultyClicked(faculty: Faculty, position: Int)
        fun moveToSpecialties(position: Int, titleId: Int)*/
        fun onToCurrentListClicked()

        fun onToResultClicked()
    }
    interface Presenter {
        //Первый этап
        fun generateBachelorsAndSpecialtiesLists(inputStreamToSpecialties: InputStream,
                                                 inputStreamToStudents: InputStream)
        fun grabSpecialties(csvParser: CSVParser): ArrayList<Specialty>
        fun grabStudents(csvParser: CSVParser): ArrayList<Student>
        fun checkTextForBeingEmpty(text: String): Int
        fun filterListOfSpecialtiesByEducationLevel(list: ArrayList<Specialty>): ArrayList<Specialty>?
        fun formFacultiesModelFromListOfSpecialties(list: ArrayList<Specialty>): Faculties
        fun filterListOfStudentsByAdmissions(list: ArrayList<Student>): ArrayList<Student>
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
        fun separateSpecialties(listOfStudents: ArrayList<Student>, arrayOfSpecialties: Array<String>)
                : ArrayList<ArrayList<Student>>

        fun checkForSpecialties(list: ArrayList<Student>, arrayOfSpecialties: Array<String>): ArrayList<Student>
        fun returnListOfStudentsForChosenSpecialty(scoreTypes: ScoreTypes, arrayOfSpecialties: Array<String>): ArrayList<Student>
        fun getListOfFacultyStudentsByFacultyId(facultyId: Int): ArrayList<ArrayList<Student>>?
        fun getInstanceOfCSVParser(inputStream: InputStream): CSVParser
        fun getOnlyNeededValues(listOfSpecialties: ArrayList<Specialty>, listOfStudents: ArrayList<Student>)
    }
}
