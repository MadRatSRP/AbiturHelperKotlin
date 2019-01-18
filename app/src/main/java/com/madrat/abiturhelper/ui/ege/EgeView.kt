package com.madrat.abiturhelper.ui.ege

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

import com.madrat.abiturhelper.R

import androidx.fragment.app.Fragment

class EgeView : Fragment() {
    private var adapter: ArrayAdapter<CharSequence>? = null
    private lateinit var spinner: Spinner

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ArrayAdapter.createFromResource(context!!,
                R.array.ege_entries, android.R.layout.simple_spinner_item)
        adapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ege, container, false)
        //val new_edit = view.findViewById<EditText>(R.id.new_edit)
        //val new_button = view.findViewById<Button>(R.id.new_button)
        spinner = view.findViewById(R.id.ege_spinner)

        return view
    }
}