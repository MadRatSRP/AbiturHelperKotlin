package com.madrat.abiturhelper.data.presenters.fragments

import android.os.Bundle
import com.madrat.abiturhelper.data.`object`.FacultiesObject
import com.madrat.abiturhelper.data.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.data.model.Student
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
        val specialtiesOfFaculties = myApplication.returnSpecialtiesOfFaculties()
        return when (facultyId) {
            // УНТИ
            FacultiesObject.FACULTY_UNTI ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_UNTI]
            // ФЭУ
            FacultiesObject.FACULTY_FEU ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_FEU]
            // ФИТ
            FacultiesObject.FACULTY_FIT ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_FIT]
            // МТФ
            FacultiesObject.FACULTY_MTF ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_MTF]
            // УНИТ
            FacultiesObject.FACULTY_UNIT ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_UNIT]
            // ФЭЭ
            FacultiesObject.FACULTY_FEE ->
                specialtiesOfFaculties[FacultiesObject.FACULTY_FEE]
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
            FacultiesObject.FACULTY_UNTI -> myApplication.returnUNTI()
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