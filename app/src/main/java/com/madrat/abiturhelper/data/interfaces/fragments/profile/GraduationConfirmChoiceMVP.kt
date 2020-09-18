package com.madrat.abiturhelper.data.interfaces.fragments.profile

import android.os.Bundle
import com.madrat.abiturhelper.data.model.Graduation
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.data.model.Student

interface GraduationConfirmChoiceMVP {
    interface View {

        fun setupMVP()
        fun showSelectedSpecialties(specialties: ArrayList<Specialty>)
        fun toSpecialties(bundle: Bundle?, actionId: Int)
    }
    interface Presenter {

        fun returnSelectedSpecialties(): ArrayList<Specialty>?
        fun getSpecialtiesListByPosition(pos: Int?): ArrayList<Specialty>?
        fun returnFacultyNumberByFacultyName(facultyName: String): Int
        fun returnStudentsOfFacultyByFacultyName(facultyName: String): ArrayList<ArrayList<Student>>?
        fun calculateGraduationData(selectedSpecialties: ArrayList<Specialty>): ArrayList<Graduation>
        fun saveGraduationList(graduationList: ArrayList<Graduation>?)
        //fun saveCurrentListOfStudents(list: ArrayList<Student>)
    }
}