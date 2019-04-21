package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_specialties.*

class SpecialtiesAdapter(private val clickListener: (Specialty, Int) -> Unit)
    : RecyclerView.Adapter<SpecialtiesAdapter.SpecialtiesHolder>(){
    private var specialties = ArrayList<Specialty>()

    fun updateSpecialtiesList(new_specialties: List<Specialty>) {
        specialties.clear()
        specialties.addAll(new_specialties)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtiesHolder
            = SpecialtiesHolder(parent.inflate(R.layout.list_specialties))

    override fun onBindViewHolder(holder: SpecialtiesHolder, position: Int)
            = holder.bind(position, specialties[position], clickListener)

    override fun getItemCount(): Int
            = specialties.size

    inner class SpecialtiesHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(position: Int, specialty: Specialty, clickListener: (Specialty, Int) -> Unit) {
            containerView.setOnClickListener { clickListener(specialty, position) }

            specialtyName.text = specialty.shortName
            entriesTotalValue.text = specialty.entriesTotal.toString()
            entriesFreeValue.text = specialty.entriesFree.toString()
        }
    }
}