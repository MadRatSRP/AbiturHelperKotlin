package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_faculties.*

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

    inner class FacultiesHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(position: Int, faculty: Faculty, clickListener: (Faculty, Int) -> Unit) {
            containerView.setOnClickListener { clickListener(faculty, position) }

            facultyName.text = faculty.name
            specialtyEntriesTotalValue.text = faculty.entriesTotalAmount.toString()
            specialtyEntriesFreeValue.text = faculty.entriesFreeAmount.toString()
        }
    }
}