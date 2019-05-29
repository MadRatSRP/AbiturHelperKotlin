package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Graduation
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.inflate
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_selected_specialties.*

class SelectedSpecialtiesAdapter
    : RecyclerView.Adapter<SelectedSpecialtiesAdapter.SelectedSpecialtiesHolder>(){
    val myApplication = MyApplication.instance

    private var graduationList = ArrayList<Graduation>()

    fun updateGraduationList(new_graduationList: ArrayList<Graduation>) {
        graduationList.clear()
        graduationList.addAll(new_graduationList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedSpecialtiesHolder
            = SelectedSpecialtiesHolder(parent.inflate(R.layout.list_selected_specialties))

    override fun onBindViewHolder(holder: SelectedSpecialtiesHolder, position: Int)
            = holder.bind(graduationList[position])

    override fun getItemCount(): Int
            = graduationList.size

    inner class SelectedSpecialtiesHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(graduation: Graduation) {

            selectedSpecialtyNameValue.text = graduation.specialtyName
            selectedAmountOfStudentsValue.text = graduation.amountOfStudents.toString()
            selectedPositionValue.text = graduation.position.toString()
            selectedOldMinimalScoreValue.text = graduation.oldMinimalScore.toString()
            selectedNewMinimalScoreValue.text = graduation.newMinimalScore.toString()
        }
    }
}