package com.madrat.abiturhelper.interfaces.fragments

import android.content.Context
import android.os.Bundle
import com.madrat.abiturhelper.model.Faculty

interface CurrentListMVP {
    interface View {

        fun setupMVP()
        fun showFaculties(faculties: ArrayList<Faculty>)
        fun onFacultyClicked(faculty: Faculty, position: Int)
        fun toSpecialties(bundle: Bundle)
        fun moveToSpecialties(position: Int, titleId: Int)
    }
    interface Presenter {

        fun returnFacultyList(): ArrayList<Faculty>?
        fun returnFacultyBundle(context: Context, position: Int, titleId: Int): Bundle
    }
}