package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.ListSelectedSpecialtiesBinding
import com.madrat.abiturhelper.model.Graduation
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.inflate

class SelectedSpecialtiesAdapter
    : RecyclerView.Adapter<SelectedSpecialtiesAdapter.SelectedSpecialtiesHolder>(){
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

    inner class SelectedSpecialtiesHolder (private val holderView: View): RecyclerView.ViewHolder(holderView) {
        private val binding = ListSelectedSpecialtiesBinding.bind(holderView)

        fun bind(graduation: Graduation) {
            with(binding) {
                selectedSpecialtyNameValue.text = graduation.specialtyName
                selectedFacultyNameValue.text = graduation.facultyName
                selectedAmountOfStudentsValue.text = graduation.amountOfStudents.toString()
                selectedPositionValue.text = graduation.position.toString()
                selectedEntriesTotalValue.text = graduation.entriesTotal.toString()
                selectedOldMinimalScoreValue.text = graduation.oldMinimalScore.toString()
                selectedNewMinimalScoreValue.text = graduation.newMinimalScore.toString()

                holderView.setOnClickListener {
                    val myApplication = MyApplication.instance
                    myApplication.saveCurrentListOfStudents(graduation.currentStudentsList)

                    Navigation.findNavController(it).navigate(R.id.action_show_current_list_to_showBachelors)
                }
            }
        }
    }
}