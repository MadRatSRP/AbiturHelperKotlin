package com.madrat.abiturhelper.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.ListFacultiesBinding
import com.madrat.abiturhelper.data.model.Faculty
import com.madrat.abiturhelper.util.inflate

class FacultiesAdapter(private val clickListener: (Faculty, Int) -> Unit)
    : RecyclerView.Adapter<FacultiesAdapter.FacultiesHolder>(){
    private var faculties = ArrayList<Faculty>()

    fun updateFacultiesList(new_faculties: ArrayList<Faculty>) {
        faculties.clear()
        faculties.addAll(new_faculties)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultiesHolder
            = FacultiesHolder(parent.inflate(R.layout.list_faculties))

    override fun onBindViewHolder(holder: FacultiesHolder, position: Int)
            = holder.bind(position, faculties[position], clickListener)

    override fun getItemCount(): Int
            = faculties.size

    inner class FacultiesHolder (private val holderView: View): RecyclerView.ViewHolder(holderView) {
        private val binding = ListFacultiesBinding.bind(holderView)

        fun bind(position: Int, faculty: Faculty, clickListener: (Faculty, Int) -> Unit) {
            with(binding) {
                holderView.setOnClickListener {
                    clickListener(faculty, position)
                }

                facultyName.text = faculty.name
                specialtyEntriesTotalValue.text = faculty.entriesTotalAmount.toString()
                specialtyEntriesFreeValue.text = faculty.entriesFreeAmount.toString()
                facultyAmountOfSpecialtiesValue.text = faculty.amountOfSpecialties.toString()
            }
        }
    }
}