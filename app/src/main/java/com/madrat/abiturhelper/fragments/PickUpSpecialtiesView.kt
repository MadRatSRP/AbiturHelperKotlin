package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.FacultyAdapter
import com.madrat.abiturhelper.interfaces.PickUpSpecialtiesMVP
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_pick_up_specialties.*
import kotlinx.android.synthetic.main.fragment_pick_up_specialties.view.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader

class PickUpSpecialtiesView
    : Fragment(), PickUpSpecialtiesMVP.View{
    private var adapter: FacultyAdapter? = null

    private var bachelourList = ArrayList<Student>()
    private var masterList = ArrayList<Student>()
    private var postGraduateList = ArrayList<Student>()

    private var physicsStudents = ArrayList<Student>()
    private var computerScienceStudents = ArrayList<Student>()
    private var socialScienceStudents = ArrayList<Student>()
    private var noOrNotEnoughDataStudents = ArrayList<Student>()
    private var partAndAllDataStudents = ArrayList<Student>()

    private var untiList = ArrayList<Specialty>()
    private var feuList = ArrayList<Specialty>()
    private var fitList = ArrayList<Specialty>()
    private var mtfList = ArrayList<Specialty>()
    private var unitList = ArrayList<Specialty>()
    private var feeList = ArrayList<Specialty>()
    private var oadList = ArrayList<Specialty>()

    private var facultyList = ArrayList<Faculty>()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        divideSpecialtiesByFaculty(grabSpecialties("specialties.csv"))

        calculateAvailableFacultyPlaces("УНТИ", untiList)
        calculateAvailableFacultyPlaces("ФЭУ", feuList)
        calculateAvailableFacultyPlaces("ФИТ", fitList)
        calculateAvailableFacultyPlaces("МТФ", mtfList)
        calculateAvailableFacultyPlaces("УНИТ", unitList)
        calculateAvailableFacultyPlaces("ФЭЭ", feeList)
        calculateAvailableFacultyPlaces("ОАД", oadList)

        for (i in 0 until facultyList.size) {
            showLog(facultyList[i].toString())
        }

        divideStudentsListByAdmissions(grabStudents("abiturs.csv"))
        divideStudentsByScoreType()

        pickUpSpecialtiesRecyclerView.linearManager()
        showFaculties(facultyList)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.pickUpSpecialtiesTitle)
        var view = inflater.inflate(R.layout.fragment_pick_up_specialties, container, false)

        adapter = FacultyAdapter()
        view.pickUpSpecialtiesRecyclerView.adapter = adapter

        return view
    }

    override fun grabSpecialties(path: String): ArrayList<Specialty> {
        val specialtiesList = ArrayList<Specialty>()
        val file = context?.assets?.open(path)
        val bufferedReader = BufferedReader(InputStreamReader(file, "Windows-1251"))

        val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT
               .withFirstRecordAsHeader()
               .withDelimiter(';')
               .withIgnoreHeaderCase()
               .withTrim())

        for (csvRecord in csvParser) {
            val shortName = csvRecord.get("СокращенноеНаименование")
            val fullName = csvRecord.get("ПолноеНаименование")
            val specialty = csvRecord.get("Специализация")
            val profileTerm = csvRecord.get("ПрофильныйПредмет")
            val educationForm = csvRecord.get("ФормаОбучения")
            val educationLevel = csvRecord.get("УровеньПодготовки")
            val graduationReason = csvRecord.get("ОснованиеПоступления")
            val receptionFeatures = csvRecord.get("ОсобенностиПриема")
            val faculty = csvRecord.get("Факультет")
            val entriesAmount = csvRecord.get("КоличествоМест")
            val enrolledAmount = csvRecord.get("Зачислено")

            specialtiesList.add(Specialty(shortName, fullName, specialty, profileTerm, educationForm,
                    educationLevel, graduationReason, receptionFeatures, faculty,
                    entriesAmount.toInt(), enrolledAmount.toInt()))
        }
        showLog("Всего специальностей: ${specialtiesList.size}")
        return specialtiesList
    }
    override fun divideSpecialtiesByFaculty(list: ArrayList<Specialty>) {
        for (i in 0 until list.size) {
            when(list[i].faculty) {
                "Учебно-научный технологический институт" ->
                    untiList.add(list[i])
                "Факультет экономики и управления" ->
                    feuList.add(list[i])
                "Факультет информационных технологий" ->
                    fitList.add(list[i])
                "Механико-технологический факультет" ->
                    mtfList.add(list[i])
                "Учебно-научный институт транспорта" ->
                    unitList.add(list[i])
                "Факультет энергетики и электроники" ->
                    feeList.add(list[i])
                "Отдел аспирантуры и докторантуры" ->
                    oadList.add(list[i])
            }
        }
        showLog("УНТИ: ${untiList.size}")
        showLog("ФЭУ: ${feuList.size}")
        showLog("ФИТ: ${fitList.size}")
        showLog("МТФ: ${mtfList.size}")
        showLog("УНИТ: ${unitList.size}")
        showLog("ФЭЭ: ${feeList.size}")
        showLog("ОАД: ${oadList.size}")
    }
    override fun grabStudents(path: String): ArrayList<Student> {
        val studentsList = ArrayList<Student>()
        val file = context?.assets?.open(path)
        val bufferedReader = BufferedReader(InputStreamReader(file, "Windows-1251"))

        val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withDelimiter(';')
                .withIgnoreHeaderCase()
                .withTrim())

        for (csvRecord in csvParser) {
            val studentId: String = csvRecord.get("id")
            val lastName: String = csvRecord.get("Фамилия")
            val firstName: String = csvRecord.get("Имя")
            val patronymic: String = csvRecord.get("Отчество") //?
            val documentsDate: String = csvRecord.get("Дата получения документов")
            val getWay: String = csvRecord.get("Способ получения")
            val status: String = csvRecord.get("Статус")
            val cancelReason: String = csvRecord.get("Причина отклонения")  //?
            val admissions: String = csvRecord.get("Приемная кампания")
            val category: String = csvRecord.get("Категория")
            val specialtyFirst: String = csvRecord.get("Направление1")
            val specialtySecond: String = csvRecord.get("Направление2")
            val specialtyThird: String = csvRecord.get("Направление3")
            val russian: String = csvRecord.get("Русский язык")
            val maths: String = csvRecord.get("Математика")
            val physics: String = csvRecord.get("Физика")
            val computerScience: String = csvRecord.get("Информатика")
            val socialScience: String = csvRecord.get("Обществознание")
            val additionalScore: String = csvRecord.get("Индивидуальные достижения")
            val isCertificateAvailable: String = csvRecord.get("Наличие подлинника аттестата")
            val isChargeAvailable: String = csvRecord.get("Наличие заявления о приеме на Направление1")
            val priority: String = csvRecord.get("Приоритет")

            studentsList.add(Student(studentId, lastName, firstName, patronymic, documentsDate, getWay,
                    status, cancelReason, admissions, category, specialtyFirst, specialtySecond, specialtyThird,
                    russian.toIntOrNull(), maths.toIntOrNull(), physics.toIntOrNull(), computerScience.toIntOrNull(),
                    socialScience.toIntOrNull(), additionalScore.toIntOrNull(), isCertificateAvailable.toBoolean(),
                    isChargeAvailable.toBoolean(), priority.toIntOrNull()))
        }
        showLog("Подавших документы: ${studentsList.size}")
        return studentsList
    }
    override fun divideStudentsListByAdmissions(list: ArrayList<Student>) {
        for (i in 0 until list.size) {
            when (list[i].admissions) {
                "бак" ->
                    bachelourList.add(list[i])
                "маг" ->
                    masterList.add(list[i])
                "асп" ->
                    postGraduateList.add(list[i])
            }
        }
        showLog("Бакалавры: ${bachelourList.size}")
        showLog("Магистры: ${masterList.size}")
        showLog("Аспиранты: ${postGraduateList.size}")
    }
    override fun divideStudentsByScoreType() {
        for (i in 0 until bachelourList.size) {
            if (bachelourList[i].maths != null && bachelourList[i].russian != null) {

                //физика
                if (bachelourList[i].physics != null && bachelourList[i].computerScience == null
                        && bachelourList[i].socialScience == null) {
                    physicsStudents.add(bachelourList[i])
                }
                //информатика
                else if (bachelourList[i].physics == null && bachelourList[i].computerScience != null
                        && bachelourList[i].socialScience == null) {
                    computerScienceStudents.add(bachelourList[i])
                }
                //обществознание
                else if (bachelourList[i].physics == null && bachelourList[i].computerScience == null
                        && bachelourList[i].socialScience != null) {
                    socialScienceStudents.add(bachelourList[i])
                }
                //по двум или трём предметам
                else partAndAllDataStudents.add(bachelourList[i])
            }

            else noOrNotEnoughDataStudents.add(bachelourList[i])

        }

        showLog("Студентов с физикой: ${physicsStudents.size}")
        showLog("Студентов с информатикой: ${computerScienceStudents.size}")
        showLog("Студентов с обществознанием: ${socialScienceStudents.size}")
        showLog("Студентов, которые не указали данные или данных недостаточно: ${noOrNotEnoughDataStudents.size}")
        showLog("Студентов, указавших баллы по всем или двум специальностям: ${partAndAllDataStudents.size}")
    }
    override fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>) {
        var total: Int = 0
        var free: Int = 0
        for (i in 0 until list.size) {
            total += list[i].entriesAmount
            free += list[i].availableEntries
        }
        facultyList.add(Faculty(name, total, free))
    }
    override fun showFaculties(faculties: List<Faculty>) {
        adapter?.updateFacultiesList(faculties)
        pickUpSpecialtiesRecyclerView.adapter = adapter
    }
}