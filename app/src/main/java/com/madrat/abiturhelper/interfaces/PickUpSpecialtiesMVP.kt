package com.madrat.abiturhelper.interfaces

import android.os.Bundle
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student

interface PickUpSpecialtiesMVP {
    interface View {
        fun grabSpecialties(path: String): ArrayList<Specialty>
        fun divideSpecialtiesByFaculty(list: ArrayList<Specialty>)
        fun grabStudents(path: String): ArrayList<Student>
        fun divideStudentsByAdmissions(list: ArrayList<Student>)
        fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>)
        fun showFaculties(faculties: List<Faculty>)
        fun toSpecialties(bundle: Bundle)
        fun onItemClicked(faculty: Faculty, position: Int)
        fun generateBacheloursAndSpecialtiesLists()
        fun generateScoreTypedListsAndCalculateAvailableFacultyPlaces()
        fun withdrawPhysicsStudents()
        fun withdrawComputerScienceStudents()
        fun withdrawSocialScienceStudents()
        fun withdrawStudentsWithoutData()
        fun withdrawStudentsWithPartAndFullData()
    }

}
