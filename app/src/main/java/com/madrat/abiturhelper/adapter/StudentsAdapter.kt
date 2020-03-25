package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.ListBachelorsBinding
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.inflate

class StudentsAdapter
    : RecyclerView.Adapter<StudentsAdapter.BachelorsHolder>(){
    private var bachelors = ArrayList<Student>()

    fun updateBachelorsList(new_bachelors: ArrayList<Student>) {
        bachelors.clear()
        bachelors.addAll(new_bachelors)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BachelorsHolder
            = BachelorsHolder(parent.inflate(R.layout.list_bachelors))

    override fun onBindViewHolder(holder: BachelorsHolder, position: Int)
            = holder.bind(bachelors[position])

    override fun getItemCount(): Int
            = bachelors.size

    inner class BachelorsHolder (private val holderView: View)
        : RecyclerView.ViewHolder(holderView) {
        private val binding = ListBachelorsBinding.bind(holderView)

        fun bind(student: Student) {
            with(binding) {
                bachelorFullName.text = holderView.context.getString(
                        R.string.bachelorFullName, student.lastName,
                        student.firstName, student.patronymic)

                bachelorMathsValue.text = student.maths.toString()
                bachelorRussianValue.text = student.russian.toString()
                bachelorPhysicsValue.text = student.physics.toString()
                bachelorComputerScienceValue.text = student.computerScience.toString()
                bachelorSocialScienceValue.text = student.socialScience.toString()
                bachelorAdditionalScoreValue.text = student.additionalScore.toString()
            }
        }
    }
}