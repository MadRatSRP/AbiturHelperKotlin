package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student

interface PickUpSpecialtiesMVP {
    interface View {
        //Первый этап
        fun generateBachelorsAndSpecialtiesLists()
        fun grabSpecialties(path: String): ArrayList<Specialty>
        fun grabStudents(path: String): ArrayList<Student>
        fun divideSpecialtiesByEducationLevel(list: ArrayList<Specialty>): ArrayList<Specialty>?
        fun divideSpecialtiesByFaculty(list: ArrayList<Specialty>)
        fun divideStudentsByAdmissions(list: ArrayList<Student>)

        //Второй этап
        fun generateScoreTypedListsAndCalculateAvailableFacultyPlaces()
        fun withdrawPhysicsStudents(bachelors: ArrayList<Student>): ArrayList<Student>
        fun withdrawComputerScienceStudents(bachelors: ArrayList<Student>): ArrayList<Student>
        fun withdrawSocialScienceStudents(bachelors: ArrayList<Student>): ArrayList<Student>
        fun withdrawStudentsWithPartAndFullData(bachelors: ArrayList<Student>): ArrayList<Student>
        fun withdrawStudentsWithoutData(bachelors: ArrayList<Student>): ArrayList<Student>
        fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>?)

        //Третий этап
        fun separateStudentsBySpecialties()
        fun checkForUnti()
        fun checkForFEU()
        fun checkForFIT()
        fun checkForMTF()
        fun checkForUNIT()
        fun checkForFEE()

        fun showFaculties(faculties: ArrayList<Faculty>)
        fun toSpecialties(bundle: Bundle)
        fun onFacultyClicked(faculty: Faculty, position: Int)
    }
}
