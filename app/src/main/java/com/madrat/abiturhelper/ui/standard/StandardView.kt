package com.madrat.abiturhelper.ui.standard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.netguru.kissme.Kissme
import kotlinx.android.synthetic.main.fragment_standard.*
import com.madrat.abiturhelper.ui.result.ResultView


class StandardView : Fragment(), StandardVP.View {

    private var standardPresenter: StandardPresenter? = null

    public static val login = "jopa"

    private val storage = Kissme(name = login)

    private val math_passing = 27
    private val rus_passing = 36
    private val phys_passing = 36
    private val comp_passing = 40
    private val soc_passing = 42
    private val score_limit = 100

    //private var text = "texty"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setPresenter()

        set_result.setOnClickListener {
            standardPresenter!!.checkMaths(math_passing, score_limit)
            standardPresenter!!.checkRussian(rus_passing, score_limit)
            standardPresenter!!.checkPhysics(phys_passing, score_limit)
            standardPresenter!!.checkComputerScience(comp_passing, score_limit)
            standardPresenter!!.checkSocialScience(soc_passing, score_limit)

            storage.putInt("maths", 10)
            storage.putInt("russian", 20)

            fragmentManager!!.beginTransaction()
                .replace(com.madrat.abiturhelper.R.id.container, ResultView.instance).commit()
        }
        /*fab.setOnClickListener {v ->

            //Resources res = getResources()
            //TypedArray selectedValues = res.obtainTypedArray(R.array.ege_entry_values)
            val ar = v.context.resources.obtainTypedArray(R.array.ege_entry_values)

            var string : String?
            val h = "fqfq"
            val builder = AlertDialog.Builder(v.context)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.alert_dialog, null)

            spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    if (position == 0) {
                        spinner.setSelection(0)
                        string = ar.getString(0)
                        //Toast.makeText(v.context, h, Toast.LENGTH_LONG).show()
                        maths?.hint = h
                    }
                    if (position == 1) {
                        spinner.setSelection(0)
                        string = ar.getString(1)
                        //Toast.makeText(v.context, h, Toast.LENGTH_LONG).show()
                        maths?.hint = h
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

            builder.setView(dialogView)



            //builder.setCancelable(true)
            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                val et = alertDialogView.findViewById(com.madrat.abiturhelper.R.id.EditText1) as EditText
                Toast.makeText(this@Tutoriel18_Android, et.text,
                        Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton(android.R.string.cancel) { _, _ ->
                builder.
            }
            builder.setTitle("kappa")
            builder.setMessage("kappa")
            builder.show()
        }*/
    }

    override fun setPresenter() { standardPresenter = StandardPresenter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_standard, container, false)
    }

    override fun setFragment(fragment: Fragment) {
        fragmentManager!!.beginTransaction()
                .replace(R.id.container, fragment).commit()
    }

    override fun mathsIsValid(math_passing: Int, score_limit: Int){
        /*when {
            maths?.text.isNullOrBlank() ->
                maths?.error = "Баллы за математику не введены"

            maths?.text.toString().toInt() < math_passing ->
                maths?.error = "Балл по математике меньше проходного(%d)".format(math_passing)

            maths?.text.toString().toInt() > score_limit ->
                maths?.error = "Введённый балл больше %d".format(score_limit)
        }*/
    }
    override fun russianIsValid(rus_passing: Int, score_limit: Int){
        /*when {
            russian?.text.isNullOrBlank() ->
                russian?.error = "Баллы за русский язык не введены"

            russian?.text.toString().toInt() < rus_passing ->
                russian?.error = "Балл по русскому языку меньше проходного(%d)".format(rus_passing)

            russian?.text.toString().toInt() > score_limit ->
                russian?.error = "Введённый балл больше %d".format(score_limit)
        }*/
    }
    override fun physicsIsValid(phys_passing: Int, score_limit: Int){
        /*when {
            physics?.text.isNullOrBlank() ->
                physics?.error = "Баллы за физику не введены"

            physics?.text.toString().toInt() < phys_passing ->
                physics?.error = "Балл по физике меньше проходного(%d)".format(phys_passing)

            physics?.text.toString().toInt() > score_limit ->
                physics?.error = "Введённый балл больше %d".format(score_limit)
        }*/
    }
    override fun computerScienceIsValid(comp_passing: Int, score_limit: Int){
        /*when {
            computer_science?.text.isNullOrBlank() ->
                computer_science?.error = "Баллы за информатику не введены"

            computer_science?.text.toString().toInt() < comp_passing ->
                computer_science?.error = "Балл по информатике меньше проходного(%d)".format(comp_passing)

            computer_science?.text.toString().toInt() > score_limit ->
                computer_science?.error = "Введённый балл больше %d".format(score_limit)
        }*/
    }
    override fun socialScienceIsValid(soc_passing: Int, score_limit: Int){
        /*when {
            social_science?.text.isNullOrBlank() ->
                social_science?.error = "Баллы за обществознание не введены"

            social_science?.text.toString().toInt() < soc_passing->
                social_science?.error = "Балл по обществознанию меньше проходного(%d)".format(soc_passing)

            social_science?.text.toString().toInt() > score_limit ->
                social_science?.error = "Введённый балл больше %d".format(score_limit)
        }*/
    }
}
