package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.util.inflate
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_faculties.*

class FacultyAdapter(private val clickListener: (Faculty, Int) -> Unit)
    : RecyclerView.Adapter<FacultyAdapter.FacultyHolder>(){
    private var faculties = ArrayList<Faculty>()

    fun updateFacultiesList(new_faculties: ArrayList<Faculty>) {
        /*val diffCallback = EmployeeDiffCallback(faculties, new_faculties)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        faculties.clear()
        faculties.addAll(new_faculties)
        diffResult.dispatchUpdatesTo(this)*/

        faculties.clear()
        faculties.addAll(new_faculties)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyAdapter.FacultyHolder
            = FacultyHolder(parent.inflate(R.layout.list_faculties))

    override fun onBindViewHolder(holder: FacultyAdapter.FacultyHolder, position: Int)
            = holder.bind(faculties[position], clickListener)

    override fun getItemCount(): Int
            = faculties.size

    inner class FacultyHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(faculty: Faculty, clickListener: (Faculty, Int) -> Unit) {
            containerView.setOnClickListener { clickListener(faculty, position) }

            facultyName.text = faculty.name
            showLog("${faculty.entriesTotalAmount}")
            entriesTotalValue.text = "${faculty.entriesTotalAmount}"
            entriesFreeValue.text = faculty.entriesFreeAmount.toString()
        }
    }
}