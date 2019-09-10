package com.madrat.abiturhelper.presenters.fragments

import android.os.Bundle
import com.madrat.abiturhelper.`object`.FacultiesObject
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication

class ShowSpecialtiesPresenter(private val view: ShowSpecialtiesMVP.View)
    : ShowSpecialtiesMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun initializeViewComponentsAndFillItWithData(facultyId: Int) {
        val listOfFacultySpecialties = getListOfFacultySpecialtiesByFacultyId(facultyId)

        view.initializeAdapterAndSetLayoutManager(facultyId)
        listOfFacultySpecialties?.let { view.showSpecialties(it) }
    }

    override fun getListOfFacultySpecialtiesByFacultyId(facultyId: Int)
            : ArrayList<Specialty>? {
        val faculties = myApplication.returnFaculties()
        return when (facultyId) {
            // УНТИ
            FacultiesObject.FACULTY_UNTY -> faculties?.listUNTI
            // ФЭУ
            FacultiesObject.FACULTY_FEU -> faculties?.listFEU
            // ФИТ
            FacultiesObject.FACULTY_FIT -> faculties?.listFIT
            // МТФ
            FacultiesObject.FACULTY_MTF -> faculties?.listMTF
            // УНИТ
            FacultiesObject.FACULTY_UNIT -> faculties?.listUNIT
            // ФЭЭ
            FacultiesObject.FACULTY_FEE -> faculties?.listFEE
            else -> null
        }
    }

    override fun saveCurrentListOfStudents(list: ArrayList<Student>) {
        myApplication.saveCurrentListOfStudents(list)
    }
    override fun returnSpecialtyBundle(list: ArrayList<Student>, specialty: Specialty): Bundle {
        val bundle = Bundle()

        saveCurrentListOfStudents(list)
        bundle.putString("title", specialty.shortName)

        return bundle
    }
    override fun returnUNTI(): ArrayList<ArrayList<Student>>?
            = myApplication.returnUNTI()
    override fun returnFEU(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFEU()
    override fun returnFIT(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFIT()
    override fun returnMTF(): ArrayList<ArrayList<Student>>?
            = myApplication.returnMTF()
    override fun returnUNIT(): ArrayList<ArrayList<Student>>?
            = myApplication.returnUNIT()
    override fun returnFEE(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFEE()

    override fun getListOfFacultyStudentsByFacultyId(facultyId: Int)
            : ArrayList<ArrayList<Student>>? {
        return when (facultyId) {
            //УНТИ
            FacultiesObject.FACULTY_UNTY -> myApplication.returnUNTI()
            //ФЭУ
            FacultiesObject.FACULTY_FEU -> myApplication.returnFEU()
            //ФИТ
            FacultiesObject.FACULTY_FIT -> myApplication.returnFIT()
            //МТФ
            FacultiesObject.FACULTY_MTF -> myApplication.returnMTF()
            //УНИТ
            FacultiesObject.FACULTY_UNIT -> myApplication.returnUNIT()
            //ФЭЭ
            FacultiesObject.FACULTY_FEE -> myApplication.returnFEE()
            else -> null
        }
    }
}