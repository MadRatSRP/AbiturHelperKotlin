package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.ListSpecialtiesBinding
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.inflate

class SpecialtiesAdapter(private val facultyId: Int?,
                         private val onSpecialtyClicked: ((Int?, Specialty, Int) -> Unit)?)
    : RecyclerView.Adapter<SpecialtiesAdapter.SpecialtiesHolder>(){
    private var specialties = ArrayList<Specialty>()

    fun updateSpecialtiesList(new_specialties: ArrayList<Specialty>) {
        specialties.clear()
        specialties.addAll(new_specialties)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtiesHolder
            = SpecialtiesHolder(parent.inflate(R.layout.list_specialties))

    override fun onBindViewHolder(holder: SpecialtiesHolder, position: Int)
            = holder.bind(position, specialties[position], onSpecialtyClicked)

    override fun getItemCount(): Int
            = specialties.size

    inner class SpecialtiesHolder (private val holderView: View)
        : RecyclerView.ViewHolder(holderView) {
        private val binding = ListSpecialtiesBinding.bind(holderView)

        fun bind(position: Int, specialty: Specialty,
                 onSpecialtyClicked: ((Int?, Specialty, Int) -> Unit)?) {
            with(binding) {
                holderView.setOnClickListener {
                    onSpecialtyClicked?.invoke(facultyId, specialty, position)
                }

                specialtyName.text = specialty.shortName
                specialtyEntriesTotalValue.text = specialty.entriesTotal.toString()
                specialtyEntriesFreeValue.text = specialty.entriesFree.toString()
                specialtyAmountOfStatementsValue.text = specialty.amountOfStatements.toString()
                /*specialtyMinimalMathsValue.text = specialty.minimalMaths.toString()
                specialtyMinimalRussianValue.text = specialty.minimalRussian.toString()*/
                specialtyMinimalScoreText.text = specialty.scoreTitle
                specialtyMinimalScoreValue.text = specialty.minimalScore.toString()
            }
        }
    }
}