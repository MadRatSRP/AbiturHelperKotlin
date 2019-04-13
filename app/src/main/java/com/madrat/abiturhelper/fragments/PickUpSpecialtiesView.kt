package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader


class PickUpSpecialtiesView
    : Fragment(), PickUpSpecialtiesMVP.View{
    private var adapter: FacultyAdapter? = null

    private var bachelours = ArrayList<Student>()

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

    private var facultyList = ArrayList<Faculty>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*Первый шаг - разбить список специальностей по факультетам,
          выделить из списка студентов тех, кто собирается поступать на бакалавриат*/
        generateBacheloursAndSpecialtiesLists()

        for (i in 0 until facultyList.size) { showLog(facultyList[i].toString()) }

        pickUpSpecialtiesRecyclerView.linearManager()

        /*Второй шаг - разбить список поступающих по типу баллов
          и высчитать свободные баллы для факультетов*/
        generateScoreTypedListsAndCalculateAvailableFacultyPlaces()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.pickUpSpecialtiesTitle)
        val view = inflater.inflate(R.layout.fragment_pick_up_specialties, container, false)

        adapter = FacultyAdapter{faculty: Faculty, position: Int -> onItemClicked(faculty, position)}
        view.pickUpSpecialtiesRecyclerView.adapter = adapter

        return view
    }
    /*Первый этап*/
    override fun generateBacheloursAndSpecialtiesLists() {
        val divideSpecialties = Thread {
            val specialties = grabSpecialties("specialties.csv")
            divideSpecialtiesByFaculty(specialties)
        }
        val divideStudents = Thread {
            val students = grabStudents("abiturs.csv")
            divideStudentsByAdmissions(students)
        }

        divideSpecialties.start()
        divideStudents.start()

        divideSpecialties.join()
        divideStudents.join()

        println("Первый этап завершён")
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
            }
        }
        showLog("УНТИ: ${untiList.size}")
        showLog("ФЭУ: ${feuList.size}")
        showLog("ФИТ: ${fitList.size}")
        showLog("МТФ: ${mtfList.size}")
        showLog("УНИТ: ${unitList.size}")
        showLog("ФЭЭ: ${feeList.size}")
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
    override fun divideStudentsByAdmissions(list: ArrayList<Student>) {
        for (i in 0 until list.size) {
            when (list[i].admissions) {
                "бак" ->
                    bachelours.add(list[i])
            }
        }
        showLog("Бакалавры: ${bachelours.size}")
    }

    /*Второй этап*/
    override fun generateScoreTypedListsAndCalculateAvailableFacultyPlaces() {
        val generateStudentsWithPhysicsList = GlobalScope.async(start = CoroutineStart.LAZY) {
            withdrawPhysicsStudents()
        }
        val generateStudentsWithComputerScienceList = GlobalScope.async(start = CoroutineStart.LAZY) {
            withdrawComputerScienceStudents()
        }
        val generateStudentsWithSocialScienceList = GlobalScope.async(start = CoroutineStart.LAZY) {
            withdrawSocialScienceStudents()
        }
        val generateStudentsWithPartAndFullDataList = GlobalScope.async(start = CoroutineStart.LAZY) {
            withdrawStudentsWithPartAndFullData()
        }
        val generateStudentsWithoutDataList = GlobalScope.async(start = CoroutineStart.LAZY) {
            withdrawStudentsWithoutData()
        }
        val calculateUntiPlaces = GlobalScope.async(start = CoroutineStart.LAZY) {
            calculateAvailableFacultyPlaces("УНТИ", untiList)
        }
        val calculateFeuPlaces = GlobalScope.async(start = CoroutineStart.LAZY) {
            calculateAvailableFacultyPlaces("ФЭУ", feuList)
        }
        val calculateFitPlaces = GlobalScope.async(start = CoroutineStart.LAZY) {
            calculateAvailableFacultyPlaces("ФИТ", fitList)
        }
        val calculateMtfPlaces = GlobalScope.async(start = CoroutineStart.LAZY) {
            calculateAvailableFacultyPlaces("МТФ", mtfList)
        }
        val calculateUnitPlaces = GlobalScope.async(start = CoroutineStart.LAZY) {
            calculateAvailableFacultyPlaces("УНИТ", unitList)
        }
        val calculateFeePlaces = GlobalScope.async(start = CoroutineStart.LAZY) {
            calculateAvailableFacultyPlaces("ФЭЭ", feeList)
        }

        GlobalScope.launch {
            generateStudentsWithPhysicsList.await()
            generateStudentsWithComputerScienceList.await()
            generateStudentsWithSocialScienceList.await()
            generateStudentsWithPartAndFullDataList.await()
            generateStudentsWithoutDataList.await()

            calculateUntiPlaces.await()
            calculateFeuPlaces.await()
            calculateFitPlaces.await()
            calculateMtfPlaces.await()
            calculateUnitPlaces.await()
            calculateFeePlaces.await()

            activity?.runOnUiThread {
                showFaculties(facultyList)
            }
        }

        println("Второй этап завершён")
    }
    override fun withdrawPhysicsStudents() {
        for (i in 0 until bachelours.size) {
            if (bachelours[i].maths != null && bachelours[i].russian != null) {
                if (bachelours[i].physics != null && bachelours[i].computerScience == null
                        && bachelours[i].socialScience == null) {
                    physicsStudents.add(bachelours[i])
                }
            }
        }
        showLog("Студентов с физикой: ${physicsStudents.size}")
    }
    override fun withdrawComputerScienceStudents() {
        for (i in 0 until bachelours.size) {
            if (bachelours[i].maths != null && bachelours[i].russian != null) {
                if (bachelours[i].physics == null && bachelours[i].computerScience != null
                        && bachelours[i].socialScience == null) {
                    computerScienceStudents.add(bachelours[i])
                }
            }
        }
        showLog("Студентов с информатикой: ${computerScienceStudents.size}")
    }
    override fun withdrawSocialScienceStudents() {
        for (i in 0 until bachelours.size) {
            if (bachelours[i].maths != null && bachelours[i].russian != null) {
                if (bachelours[i].physics == null && bachelours[i].computerScience == null
                        && bachelours[i].socialScience != null) {
                    socialScienceStudents.add(bachelours[i])
                }
            }
        }
        showLog("Студентов с обществознанием: ${socialScienceStudents.size}")
    }
    override fun withdrawStudentsWithPartAndFullData() {
        for (i in 0 until bachelours.size) {
            if (bachelours[i].maths != null && bachelours[i].russian != null) {
                if (!(bachelours[i].physics != null && bachelours[i].computerScience == null
                    && bachelours[i].socialScience == null) && !(bachelours[i].physics == null &&
                    bachelours[i].computerScience != null && bachelours[i].socialScience == null) &&
                    !(bachelours[i].physics == null && bachelours[i].computerScience == null
                    && bachelours[i].socialScience != null)) {
                    partAndAllDataStudents.add(bachelours[i])
                }
            }
        }
        showLog("Студентов, указавших баллы по всем или двум специальностям: ${partAndAllDataStudents.size}")
    }
    override fun withdrawStudentsWithoutData() {
        for (i in 0 until bachelours.size) {
            if (!(bachelours[i].maths != null && bachelours[i].russian != null)) {
                noOrNotEnoughDataStudents.add(bachelours[i])
            }
        }
        showLog("Студентов, которые не указали данные или данных недостаточно: ${noOrNotEnoughDataStudents.size}")
    }
    override fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>) {
        var total = 0
        var free = 0
        for (i in 0 until list.size) {
            total += list[i].entriesTotal
            free += list[i].entriesFree
        }
        facultyList.add(Faculty(name, total, free))
    }
    
    override fun showFaculties(faculties: List<Faculty>) {
        adapter?.updateFacultiesList(faculties)
        pickUpSpecialtiesRecyclerView.adapter = adapter
    }
    override fun onItemClicked(faculty: Faculty, position: Int) {
        showLog("Выбран: ${faculty.name}")
        val bundle = Bundle()
        when (position) {
            0 -> {
                bundle.putString("title", "УНТИ")
                bundle.putSerializable("array", untiList)
                toSpecialties(bundle)
            }
            1 -> {
                bundle.putString("title", "ФЭУ")
                bundle.putSerializable("array", feuList)
                toSpecialties(bundle)
            }
            2 -> {
                bundle.putString("title", "ФИТ")
                bundle.putSerializable("array", fitList)
                toSpecialties(bundle)
            }
            3 -> {
                bundle.putString("title", "МТФ")
                bundle.putSerializable("array", mtfList)
                toSpecialties(bundle)
            }
            4 -> {
                bundle.putString("title", "УНИТ")
                bundle.putSerializable("array", unitList)
                toSpecialties(bundle)
            }
            5 -> {
                bundle.putString("title", "ФЭЭ")
                bundle.putSerializable("array", feeList)
                toSpecialties(bundle)
            }
        }
    }
    override fun toSpecialties(bundle: Bundle) {
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_pickUpSpecialtiesView_to_showSpecialtiesView, bundle)
        }
    }
}