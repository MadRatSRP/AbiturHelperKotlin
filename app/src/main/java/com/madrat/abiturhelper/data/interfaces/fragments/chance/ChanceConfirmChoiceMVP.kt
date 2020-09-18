package com.madrat.abiturhelper.data.interfaces.fragments.chance

import com.madrat.abiturhelper.data.model.Chance
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.data.model.Student

interface ChanceConfirmChoiceMVP {
    interface View {
        fun setupMVP()
        fun showSelectedSpecialties(specialties: ArrayList<Specialty>)
        fun toSpecialties(actionId: Int)
    }
    interface Presenter {
        fun calculateChancesData(chosenSpecialties: ArrayList<Specialty>)
                : ArrayList<Chance>

        fun saveListOfChances(listOfChances: ArrayList<Chance>)
        fun returnListOfSpecialtiesByPosition(pos: Int?)
                : ArrayList<Specialty>?
        fun returnFacultyPositionNumberByFacultyName(facultyName: String)
                : Int
        fun returnListOfStudentsInFacultyByFacultyName(facultyName: String)
                : ArrayList<ArrayList<Student>>?
        fun returnChosenSpecialties()
                : ArrayList<Specialty>?
    }
}