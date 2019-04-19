package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_faculties.*

class FacultyAdapter(private val clickListener: (Faculty, Int) -> Unit)
    : RecyclerView.Adapter<FacultyAdapter.FacultyHolder>(){
    private var faculties = ArrayList<Faculty>()

    fun updateFacultiesList(new_faculties: ArrayList<Faculty>) {
        faculties.clear()
        faculties.addAll(new_faculties)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyHolder
            = FacultyHolder(parent.inflate(R.layout.list_faculties))

    override fun onBindViewHolder(holder: FacultyHolder, position: Int)
            = holder.bind(position, faculties[position], clickListener)

    override fun getItemCount(): Int
            = faculties.size

    inner class FacultyHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(position: Int, faculty: Faculty, clickListener: (Faculty, Int) -> Unit) {
            containerView.setOnClickListener { clickListener(faculty, position) }

            facultyName.text = faculty.name
            entriesTotalValue.text = faculty.entriesTotalAmount.toString()
            entriesFreeValue.text = faculty.entriesFreeAmount.toString()
        }
    }
}