package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student

interface ShowSpecialtiesMVP {
    interface View {
        fun setupMVP()
        fun showSpecialties(specialties: ArrayList<Specialty>)
        fun toStudents(bundle: Bundle)
        fun moveToStudents(list: ArrayList<Student>, specialty: Specialty)

        fun onUNTISpecialtyClicked(specialty: Specialty, position: Int)
        fun onFEUSpecialtyClicked(specialty: Specialty, position: Int)
        fun onFITSpecialtyClicked(specialty: Specialty, position: Int)
        fun onMTFSpecialtyClicked(specialty: Specialty, position: Int)
        fun onUNITSpecialtyClicked(specialty: Specialty, position: Int)
        fun onFEESpecialtyClicked(specialty: Specialty, position: Int)
    }
    interface Presenter {
        fun initializeAdapter(example: (Specialty, Int) -> Unit): SpecialtiesAdapter
        fun getSpecialtiesListByPosition(pos: Int): ArrayList<Specialty>?

        fun saveCurrentListOfStudents(list: ArrayList<Student>)
        fun returnSpecialtyBundle(list: ArrayList<Student>, specialty: Specialty): Bundle
        fun returnUNTI(): ArrayList<ArrayList<Student>>?
        fun returnFEU(): ArrayList<ArrayList<Student>>?
        fun returnFIT(): ArrayList<ArrayList<Student>>?
        fun returnMTF(): ArrayList<ArrayList<Student>>?
        fun returnUNIT(): ArrayList<ArrayList<Student>>?
        fun returnFEE(): ArrayList<ArrayList<Student>>?
    }
}
