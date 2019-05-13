package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_bachelors.*

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

    inner class BachelorsHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(student: Student) {
            bachelorFirstName.text = student.firstName
            bachelorLastName.text = student.lastName
            bachelorMathsValue.text = student.maths.toString()
            bachelorRussianValue.text = student.russian.toString()
            bachelorPhysicsValue.text = student.physics.toString()
            bachelorComputerScienceValue.text = student.computerScience.toString()
            bachelorSocialScienceValue.text = student.socialScience.toString()
        }
    }
}