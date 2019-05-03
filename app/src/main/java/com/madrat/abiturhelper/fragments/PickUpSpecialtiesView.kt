package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.FacultiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.PickUpSpecialtiesMVP
import com.madrat.abiturhelper.model.*
import com.madrat.abiturhelper.model.faculties.Unti
import com.madrat.abiturhelper.model.faculties.unti.*
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import com.madrat.abiturhelper.util.stringAndSerializable
import kotlinx.android.synthetic.main.fragment_pick_up_specialties.*
import kotlinx.android.synthetic.main.fragment_pick_up_specialties.view.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class PickUpSpecialtiesView
    : Fragment(), PickUpSpecialtiesMVP.View{
    private var adapter: FacultiesAdapter? = null

    private val myApplication = MyApplication.instance

    private var facultyList = ArrayList<Faculty>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pickUpSpecialtiesRecyclerView.linearManager()

        /*Первый шаг - разбить список специальностей по факультетам,
          выделить из списка студентов тех, кто собирается поступать на бакалавриат*/
        generateBachelorsAndSpecialtiesLists()

        /*Второй шаг - разбить список поступающих по типу баллов
          и высчитать свободные баллы для факультетов*/
        generateScoreTypedListsAndCalculateAvailableFacultyPlaces()

        showFaculties(facultyList)

        /*Третий шаг - */
        separateStudentsBySpecialties()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.pickUpSpecialtiesTitle)
        val view = inflater.inflate(R.layout.fragment_pick_up_specialties, container, false)

        adapter = FacultiesAdapter{ faculty: Faculty, position: Int -> onFacultyClicked(faculty, position)}
        view.pickUpSpecialtiesRecyclerView.adapter = adapter

        return view
    }

    /*Первый этап*/
    override fun generateBachelorsAndSpecialtiesLists() {
        val specialties = grabSpecialties("specialties.csv")
        val students = grabStudents("abiturs.csv")


        val bachelorsAndSpecialists = divideSpecialtiesByEducationLevel(specialties)

        divideSpecialtiesByFaculty(bachelorsAndSpecialists)


        divideStudentsByAdmissions(students)
        showLog("Первый этап завершён")
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
    override fun divideSpecialtiesByEducationLevel(list: ArrayList<Specialty>): ArrayList<Specialty> {
        val bachelorsAndSpecialists = ArrayList<Specialty>()

        for (i in 0 until list.size) {
            when (list[i].educationLevel) {
                "Академический бакалавр" ->
                    bachelorsAndSpecialists.add(list[i])
                "Специалист" ->
                    bachelorsAndSpecialists.add(list[i])
            }
        }
        showLog("Специальностей, ведущих набор на бакалавриат и специалитет: ${bachelorsAndSpecialists.size}")
        return bachelorsAndSpecialists
    }
    override fun divideSpecialtiesByFaculty(list: ArrayList<Specialty>) {
        val untiList = ArrayList<Specialty>()
        val feuList = ArrayList<Specialty>()
        val fitList = ArrayList<Specialty>()
        val mtfList = ArrayList<Specialty>()
        val unitList = ArrayList<Specialty>()
        val feeList = ArrayList<Specialty>()

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
        myApplication.saveFaculties(Faculties(untiList, feuList, fitList, mtfList, unitList, feeList))
    }
    override fun divideStudentsByAdmissions(list: ArrayList<Student>) {
        val bachelors = ArrayList<Student>()

        for (i in 0 until list.size) {
            if (list[i].admissions == "бак")
                bachelors.add(list[i])
        }

        showLog("Бакалавры: ${bachelors.size}")

        myApplication.saveBachelors(bachelors)
    }

    /*Второй этап*/
    override fun generateScoreTypedListsAndCalculateAvailableFacultyPlaces() {
        val bachelors = myApplication.returnBachelors()
        showLog("Бакалавры: " + bachelors?.size.toString())

        val new_bachelors = ArrayList<Student>()
        val bad_bachelors = ArrayList<Student>()

        /*bachelors?.let {
            for (i in 0 until it.size) {
                if (it[i].physics == null && it[i].computerScience == null
                        && it[i].socialScience == null) {
                    bad_bachelors.add(it[i])
                }
            }
            showLog(bad_bachelors.size.toString())
        }*/

        bachelors?.let {
            for (i in 0 until it.size) {
                if ((it[i].maths != null && it[i].maths != 0)
                        && (it[i].russian != null && it[i].russian != 0)) {
                    if (it[i].physics != null || it[i].computerScience != null
                            || it[i].socialScience != null) {
                        new_bachelors.add(it[i])
                    }
                    else bad_bachelors.add(it[i])
                }
            }
            showLog(new_bachelors.size.toString())
        }

        /*for (i in 0 until bad_bachelors.size) {
            showLog(bad_bachelors[i].toString())
        }*/

        //val file = context?.assets?.open("test.csv")
        //val bufferedWriter = BufferedWriter(OutputStreamWriter(file, "Windows-1251"))

        /*bachelors?.let {
            for (i in 0 until it.size) {
                if (((it[i].maths != null && it[i].maths != 0)
                        && (it[i].russian != null && it[i].russian != 0))
                        || ((it[i].maths != null && it[i].maths != 0)
                        || (it[i].russian != null && it[i].russian != 0))) {
                    new_bachelors.add(it[i])
                }
            }
            showLog(new_bachelors.size.toString())
        }*/


        val scoreTypes = ScoreTypes(
            withdrawPhysicsStudents(new_bachelors),
            withdrawComputerScienceStudents(new_bachelors),
            withdrawSocialScienceStudents(new_bachelors),
            withdrawStudentsWithoutData(new_bachelors),
            withdrawStudentsWithPartAndFullData(new_bachelors)
        )

        myApplication.saveScoreTypes(scoreTypes)

        val faculties = myApplication.returnFaculties()

        facultyList.clear()

        calculateAvailableFacultyPlaces("УНТИ", faculties?.untiList)
        calculateAvailableFacultyPlaces("ФЭУ", faculties?.feuList)
        calculateAvailableFacultyPlaces("ФИТ", faculties?.fitList)
        calculateAvailableFacultyPlaces("МТФ", faculties?.mtfList)
        calculateAvailableFacultyPlaces("УНИТ", faculties?.unitList)
        calculateAvailableFacultyPlaces("ФЭЭ", faculties?.feeList)

        println("Второй этап завершён")
    }
    override fun withdrawPhysicsStudents(bachelors: ArrayList<Student>): ArrayList<Student> {
        val physicsStudents = ArrayList<Student>()

        for (i in 0 until bachelors.size) {
            if (bachelors[i].physics != null && bachelors[i].computerScience == null
                            && bachelors[i].socialScience == null) {
                physicsStudents.add(bachelors[i])
            }
        }
        return physicsStudents
    }
    override fun withdrawComputerScienceStudents(bachelors: ArrayList<Student>): ArrayList<Student> {
        val computerScienceStudents = ArrayList<Student>()

        for (i in 0 until bachelors.size) {
            if (bachelors[i].physics == null && bachelors[i].computerScience != null
                            && bachelors[i].socialScience == null) {
                computerScienceStudents.add(bachelors[i])
            }
        }
        return computerScienceStudents
    }
    override fun withdrawSocialScienceStudents(bachelors: ArrayList<Student>): ArrayList<Student> {
        val socialScienceStudents = ArrayList<Student>()

        for (i in 0 until bachelors.size) {
            if (bachelors[i].physics == null && bachelors[i].computerScience == null
                    && bachelors[i].socialScience != null) {
                socialScienceStudents.add(bachelors[i])
            }
        }
        return socialScienceStudents
    }
    override fun withdrawStudentsWithPartAndFullData(bachelors: ArrayList<Student>): ArrayList<Student> {
        val partAndAllDataStudents = ArrayList<Student>()

        for (i in 0 until bachelors.size) {
            if (!(bachelors[i].physics != null && bachelors[i].computerScience == null && bachelors[i].socialScience == null)
                    && !(bachelors[i].physics == null && bachelors[i].computerScience != null
                    && bachelors[i].socialScience == null) && !(bachelors[i].physics == null
                    && bachelors[i].computerScience == null && bachelors[i].socialScience != null)) {
                partAndAllDataStudents.add(bachelors[i])
            }
        }
        return partAndAllDataStudents
    }
    override fun withdrawStudentsWithoutData(bachelors: ArrayList<Student>): ArrayList<Student> {
        val noOrNotEnoughDataStudents = ArrayList<Student>()

        for (i in 0 until bachelors.size) {
            if (!(bachelors[i].maths != null && bachelors[i].russian != null)) {
                noOrNotEnoughDataStudents.add(bachelors[i])
            }
        }
        return noOrNotEnoughDataStudents
    }
    override fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>?) {
        var total = 0
        var free = 0

        list?.let {
            for (i in 0 until it.size) {
                total += it[i].entriesTotal
                free += it[i].entriesFree
            }
        }

        showLog("Для $name - мест всего $total, мест свободно $free")
        facultyList.add(Faculty(name, total, free))
    }

    /*Третий этап*/
    override fun separateStudentsBySpecialties() {
        checkForUnti()
        checkForFEU()
        checkForFIT()
        checkForMTF()
        checkForUNIT()
        println("Третий этап завершён")
    }
    override fun checkForUnti() {
        val scoreTypes = myApplication.returnScoreTypes()
        val atp = ArrayList<Student>()
        val kto = ArrayList<Student>()
        val mash = ArrayList<Student>()
        val mitm = ArrayList<Student>()
        val mht = ArrayList<Student>()
        val ptmk = ArrayList<Student>()
        val tmo = ArrayList<Student>()
        val uts = ArrayList<Student>()

        fun checkForATP(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "АТП_заочн_бюдж" || list[i].specialtyFirst == "АТП_заочн_льгот"
                        || list[i].specialtyFirst == "АТП_заочн_плат" || list[i].specialtyFirst == "АТП_очн_бюдж"
                        || list[i].specialtyFirst == "АТП_очн_льгот" || list[i].specialtyFirst == "АТП_очн_плат"
                        || list[i].specialtyFirst == "АТП_очн_целевое") || (list[i].specialtySecond == "АТП_заочн_бюдж"
                        || list[i].specialtySecond == "АТП_заочн_льгот" || list[i].specialtySecond == "АТП_заочн_плат"
                        || list[i].specialtySecond == "АТП_очн_бюдж" || list[i].specialtySecond == "АТП_очн_льгот"
                        || list[i].specialtySecond == "АТП_очн_плат" || list[i].specialtySecond == "АТП_очн_целевое")
                        || (list[i].specialtyThird == "АТП_заочн_бюдж" || list[i].specialtyThird == "АТП_заочн_льгот"
                        || list[i].specialtyThird == "АТП_заочн_плат" || list[i].specialtyThird == "АТП_очн_бюдж"
                        || list[i].specialtyThird == "АТП_очн_льгот" || list[i].specialtyThird == "АТП_очн_плат"
                        || list[i].specialtyThird == "АТП_очн_целевое")) {
                    atp.add(list[i])
                }
            }
        }
        fun checkForKTO(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "КТО(АТиКМ)_очн_бюдж" || list[i].specialtyFirst == "КТО(АТиКМ)_очн_льгот"
                        || list[i].specialtyFirst == "КТО(АТиКМ)_очн_плат" || list[i].specialtyFirst == "КТО(ТМ)_очн_бюдж"
                        || list[i].specialtyFirst == "КТО(ТМ)_очн_льгот" || list[i].specialtyFirst == "КТО(ТМ)_очн_плат"
                        || list[i].specialtyFirst == "КТО(ТМ)_очн_целевое" || list[i].specialtyFirst == "КТО_веч_бюдж"
                        || list[i].specialtyFirst == "КТО_веч_льгот" || list[i].specialtyFirst == "КТО_веч_плат")
                        || (list[i].specialtySecond == "КТО(АТиКМ)_очн_бюдж" || list[i].specialtySecond == "КТО(АТиКМ)_очн_льгот"
                        || list[i].specialtySecond == "КТО(АТиКМ)_очн_плат" || list[i].specialtySecond == "КТО(ТМ)_очн_бюдж"
                        || list[i].specialtySecond == "КТО(ТМ)_очн_льгот" || list[i].specialtySecond == "КТО(ТМ)_очн_плат"
                        || list[i].specialtySecond == "КТО(ТМ)_очн_целевое" || list[i].specialtySecond == "КТО_веч_бюдж"
                        || list[i].specialtySecond == "КТО_веч_льгот" || list[i].specialtySecond == "КТО_веч_плат")
                        || (list[i].specialtyThird == "КТО(АТиКМ)_очн_бюдж" || list[i].specialtyThird == "КТО(АТиКМ)_очн_льгот"
                        || list[i].specialtyThird == "КТО(АТиКМ)_очн_плат" || list[i].specialtyThird == "КТО(ТМ)_очн_бюдж"
                        || list[i].specialtyThird == "КТО(ТМ)_очн_льгот" || list[i].specialtyThird == "КТО(ТМ)_очн_плат"
                        || list[i].specialtyThird == "КТО(ТМ)_очн_целевое" || list[i].specialtyThird == "КТО_веч_бюдж"
                        || list[i].specialtyThird == "КТО_веч_льгот" || list[i].specialtyThird == "КТО_веч_плат")) {
                    kto.add(list[i])
                }
            }
        }
        fun checkForMASH(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "МАШ(ТМ)_заочн_бюдж" || list[i].specialtyFirst == "МАШ(ТМ)_заочн_льгот"
                        || list[i].specialtyFirst == "МАШ(ТМ)_заочн_плат") || (list[i].specialtySecond == "МАШ(ТМ)_заочн_бюдж"
                        || list[i].specialtySecond == "МАШ(ТМ)_заочн_льгот" || list[i].specialtySecond == "МАШ(ТМ)_заочн_плат")
                        || (list[i].specialtyThird == "МАШ(ТМ)_заочн_бюдж" || list[i].specialtyThird == "МАШ(ТМ)_заочн_льгот"
                        || list[i].specialtyThird == "МАШ(ТМ)_заочн_плат")) {
                    mash.add(list[i])
                }
            }
        }
        fun checkForMiTM(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "МиТМ_очн_бюдж" || list[i].specialtyFirst == "МиТМ_очн_льгот"
                        || list[i].specialtyFirst == "МиТМ_очн_плат") || (list[i].specialtySecond == "МиТМ_очн_бюдж"
                        || list[i].specialtySecond == "МиТМ_очн_льгот" || list[i].specialtySecond == "МиТМ_очн_плат")
                        || (list[i].specialtyThird == "МиТМ_очн_бюдж" || list[i].specialtyThird == "МиТМ_очн_льгот"
                        || list[i].specialtyThird == "МиТМ_очн_плат")) {
                    mitm.add(list[i])
                }
            }
        }
        fun checkForMHT(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "МХТ_очн_бюдж" || list[i].specialtyFirst == "МХТ_очн_льгот"
                        || list[i].specialtyFirst == "МХТ_очн_плат") || (list[i].specialtySecond == "МХТ_очн_бюдж"
                        || list[i].specialtySecond == "МХТ_очн_льгот" || list[i].specialtySecond == "МХТ_очн_плат")
                        || (list[i].specialtyThird == "МХТ_очн_бюдж" || list[i].specialtyThird == "МХТ_очн_льгот"
                        || list[i].specialtyThird == "МХТ_очн_плат")) {
                    mht.add(list[i])
                }
            }
        }
        fun checkForPTMK(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ПТМК_заочн_плат" || list[i].specialtyFirst == "ПТМК_очн_бюдж"
                        || list[i].specialtyFirst == "ПТМК_очн_льгот" || list[i].specialtyFirst == "ПТМК_очн_плат")
                        || (list[i].specialtySecond == "ПТМК_заочн_плат" || list[i].specialtySecond == "ПТМК_очн_бюдж"
                        || list[i].specialtySecond == "ПТМК_очн_льгот" || list[i].specialtySecond == "ПТМК_очн_плат")
                        || (list[i].specialtyThird == "ПТМК_заочн_плат" || list[i].specialtyThird == "ПТМК_очн_бюдж"
                        || list[i].specialtyThird == "ПТМК_очн_льгот" || list[i].specialtyThird == "ПТМК_очн_плат")) {
                    ptmk.add(list[i])
                }
            }
        }
        fun checkFotTMO(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ТМО(ОИиПМ)_заочн_бюдж" || list[i].specialtyFirst == "ТМО(ОИиПМ)_заочн_льгот"
                        || list[i].specialtyFirst == "ТМО(ОИиПМ)_заочн_плат" || list[i].specialtyFirst == "ТМО_очн_бюдж"
                        || list[i].specialtyFirst == "ТМО_очн_льгот" || list[i].specialtyFirst == "ТМО_очн_плат"
                        || list[i].specialtyFirst == "ТМО_очн_целевое") || (list[i].specialtySecond == "ТМО(ОИиПМ)_заочн_бюдж"
                        || list[i].specialtySecond == "ТМО(ОИиПМ)_заочн_льгот" || list[i].specialtySecond == "ТМО(ОИиПМ)_заочн_плат"
                        || list[i].specialtySecond == "ТМО_очн_бюдж" || list[i].specialtySecond == "ТМО_очн_льгот"
                        || list[i].specialtySecond == "ТМО_очн_плат" || list[i].specialtySecond == "ТМО_очн_целевое")
                        || (list[i].specialtyThird == "ТМО(ОИиПМ)_заочн_бюдж" || list[i].specialtyThird == "ТМО(ОИиПМ)_заочн_льгот"
                        || list[i].specialtyThird == "ТМО(ОИиПМ)_заочн_плат" || list[i].specialtyThird == "ТМО_очн_бюдж"
                        || list[i].specialtyThird == "ТМО_очн_льгот" || list[i].specialtyThird == "ТМО_очн_плат"
                        || list[i].specialtyThird == "ТМО_очн_целевое")) {
                    tmo.add(list[i])
                }
            }
        }
        fun checkForUTS(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "УТС_очн_бюдж" || list[i].specialtyFirst == "УТС_очн_льгот"
                        || list[i].specialtyFirst == "УТС_очн_плат" || list[i].specialtyFirst == "УТС_очн_целевое")
                        || (list[i].specialtySecond == "УТС_очн_бюдж" || list[i].specialtySecond == "УТС_очн_льгот"
                        || list[i].specialtySecond == "УТС_очн_плат" || list[i].specialtySecond == "УТС_очн_целевое")
                        || (list[i].specialtyThird == "УТС_очн_бюдж" || list[i].specialtyThird == "УТС_очн_льгот"
                        || list[i].specialtyThird == "УТС_очн_плат" || list[i].specialtyThird == "УТС_очн_целевое")) {
                    uts.add(list[i])
                }
            }
        }

        fun separateATP(list: ArrayList<Student>): ATP {
            val zaochnBudg = ArrayList<Student>()
            val zaochnLgot = ArrayList<Student>()
            val zaochnPlat = ArrayList<Student>()
            val ochnBudg = ArrayList<Student>()
            val ochnLgot = ArrayList<Student>()
            val ochnPlat = ArrayList<Student>()
            val ochnCelevoe = ArrayList<Student>()

            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "АТП_заочн_бюдж" || list[i].specialtySecond == "АТП_заочн_бюдж"
                        || list[i].specialtyThird == "АТП_заочн_бюдж")
                    zaochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "АТП_заочн_льгот" || list[i].specialtySecond == "АТП_заочн_льгот"
                        || list[i].specialtyThird == "АТП_заочн_льгот")
                    zaochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "АТП_заочн_плат" || list[i].specialtySecond == "АТП_заочн_плат"
                        || list[i].specialtyThird == "АТП_заочн_плат")
                    zaochnPlat.add(list[i])
                else if (list[i].specialtyFirst == "АТП_очн_бюдж" || list[i].specialtySecond == "АТП_очн_бюдж"
                        || list[i].specialtyThird == "АТП_очн_бюдж")
                    ochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "АТП_очн_льгот" || list[i].specialtySecond == "АТП_очн_льгот"
                        || list[i].specialtyThird == "АТП_очн_льгот")
                    ochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "АТП_очн_плат" || list[i].specialtySecond == "АТП_очн_плат"
                        || list[i].specialtyThird == "АТП_очн_плат")
                    ochnPlat.add(list[i])
                else if(list[i].specialtyFirst == "АТП_очн_целевое" || list[i].specialtySecond == "АТП_очн_целевое"
                        || list[i].specialtyThird == "АТП_очн_целевое")
                    ochnCelevoe.add(list[i])
            }
            showLog("АТП с размером ${list.size} был разбит на ЗаочноеБюджет - ${zaochnBudg.size}, ЗаочноеЛьгот - ${zaochnLgot.size}, " +
                    "ЗаочноеПлат - ${zaochnPlat.size},\nОчноеБюджет - ${ochnBudg.size}, ОчноеЛьгот -  ${ochnLgot.size}, " +
                    "ОчноеПлат - ${ochnPlat.size}, ОчноеЦелевое -  ${ochnCelevoe.size}")
            return ATP(zaochnBudg, zaochnLgot, zaochnPlat, ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
        }
        fun separateKTO(list: ArrayList<Student>): KTO {
            val atkmOchnBudg = ArrayList<Student>()
            val atkmOchnLgot = ArrayList<Student>()
            val atkmOchnPlat = ArrayList<Student>()
            val tmOchnBudg = ArrayList<Student>()
            val tmOchnLgot = ArrayList<Student>()
            val tmOchnPlat = ArrayList<Student>()
            val tmOchnCelevoe = ArrayList<Student>()
            val vechBudg = ArrayList<Student>()
            val vechLgot = ArrayList<Student>()
            val vechPlat = ArrayList<Student>()

            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "КТО(АТиКМ)_очн_бюдж" || list[i].specialtySecond == "КТО(АТиКМ)_очн_бюдж"
                        || list[i].specialtyThird == "КТО(АТиКМ)_очн_бюдж")
                    atkmOchnBudg.add(list[i])
                else if (list[i].specialtyFirst == "КТО(АТиКМ)_очн_льгот" || list[i].specialtySecond == "КТО(АТиКМ)_очн_льгот"
                        || list[i].specialtyThird == "КТО(АТиКМ)_очн_льгот")
                    atkmOchnLgot.add(list[i])
                else if (list[i].specialtyFirst == "КТО(АТиКМ)_очн_плат" || list[i].specialtySecond == "КТО(АТиКМ)_очн_плат"
                        || list[i].specialtyThird == "КТО(АТиКМ)_очн_плат")
                    atkmOchnPlat.add(list[i])
                else if (list[i].specialtyFirst == "КТО(ТМ)_очн_бюдж" || list[i].specialtySecond == "КТО(ТМ)_очн_бюдж"
                        || list[i].specialtyThird == "КТО(ТМ)_очн_бюдж")
                    tmOchnBudg.add(list[i])
                else if (list[i].specialtyFirst == "КТО(ТМ)_очн_льгот" || list[i].specialtySecond == "КТО(ТМ)_очн_льгот"
                        || list[i].specialtyThird == "КТО(ТМ)_очн_льгот")
                    tmOchnLgot.add(list[i])
                else if (list[i].specialtyFirst == "КТО(ТМ)_очн_плат" || list[i].specialtySecond == "КТО(ТМ)_очн_плат"
                        || list[i].specialtyThird == "КТО(ТМ)_очн_плат")
                    tmOchnPlat.add(list[i])
                else if (list[i].specialtyFirst == "КТО(ТМ)_очн_целевое" || list[i].specialtySecond == "КТО(ТМ)_очн_целевое"
                        || list[i].specialtyThird == "КТО(ТМ)_очн_целевое")
                    tmOchnCelevoe.add(list[i])
                else if (list[i].specialtyFirst == "КТО_веч_бюдж" || list[i].specialtySecond == "КТО_веч_бюдж"
                        || list[i].specialtyThird == "КТО_веч_бюдж")
                    vechBudg.add(list[i])
                else if (list[i].specialtyFirst == "КТО_веч_льгот" || list[i].specialtySecond == "КТО_веч_льгот"
                        || list[i].specialtyThird == "КТО_веч_льгот")
                    vechLgot.add(list[i])
                else if (list[i].specialtyFirst == "КТО_веч_плат" || list[i].specialtySecond == "КТО_веч_плат"
                        || list[i].specialtyThird == "КТО_веч_плат")
                    vechPlat.add(list[i])
            }
            showLog("КТО с размером ${list.size} был разбит на АТиКМ_ОчноеБюджет - ${atkmOchnBudg.size}, " +
                    "АТиКМ_ОчноеЛьготное - ${atkmOchnLgot.size}, АТиКМ_ОчноеПлатное - ${atkmOchnPlat.size}," +
                    "\nТМ_ОчноеБюджет - ${tmOchnBudg.size}, ТМ_ОчноеЛьготное -  ${tmOchnLgot.size}, " +
                    "ТМ_ОчноеПлатное - ${tmOchnPlat.size}, ТМ_ОчноеЦелевое -  ${tmOchnCelevoe.size}, " +
                    "ВечернееБюджет - ${vechBudg.size}, ВечернееЛьготное - ${vechLgot.size}, " +
                    "ВечернееПлатное - ${vechPlat.size}")
            return KTO(atkmOchnBudg, atkmOchnLgot, atkmOchnPlat, tmOchnBudg, tmOchnLgot,
                    tmOchnPlat, tmOchnCelevoe, vechBudg, vechLgot, vechPlat)
        }
        fun separateMASH(list: ArrayList<Student>): MASH {
            val tmZaochnBudg = ArrayList<Student>()
            val tmZaochnLgot = ArrayList<Student>()
            val tmZaochnPlat = ArrayList<Student>()

            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "МАШ(ТМ)_заочн_бюдж" || list[i].specialtySecond == "МАШ(ТМ)_заочн_бюдж"
                        || list[i].specialtyThird == "МАШ(ТМ)_заочн_бюдж")
                    tmZaochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "МАШ(ТМ)_заочн_льгот" || list[i].specialtySecond == "МАШ(ТМ)_заочн_льгот"
                        || list[i].specialtyThird == "МАШ(ТМ)_заочн_льгот")
                    tmZaochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "МАШ(ТМ)_заочн_плат" || list[i].specialtySecond == "МАШ(ТМ)_заочн_плат"
                        || list[i].specialtyThird == "МАШ(ТМ)_заочн_плат")
                    tmZaochnPlat.add(list[i])
            }
            showLog("МАШ с размером ${list.size} был разбит на ТМ_ЗаочноеБюджет - ${tmZaochnBudg.size}, " +
                    "ТМ_ЗаочноеЛьготное - ${tmZaochnLgot.size}, ТМ_ЗаочноеПлатное - ${tmZaochnPlat.size}")
            return MASH(tmZaochnBudg, tmZaochnLgot, tmZaochnPlat)
        }
        fun separateMiTM(list: ArrayList<Student>): MiTM {
            val ochnBudg = ArrayList<Student>()
            val ochnLgot = ArrayList<Student>()
            val ochnPlat = ArrayList<Student>()

            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "МиТМ_очн_бюдж" || list[i].specialtySecond == "МиТМ_очн_бюдж"
                        || list[i].specialtyThird == "МиТМ_очн_бюдж")
                    ochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "МиТМ_очн_льгот" || list[i].specialtySecond == "МиТМ_очн_льгот"
                        || list[i].specialtyThird == "МиТМ_очн_льгот")
                    ochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "МиТМ_очн_плат" || list[i].specialtySecond == "МиТМ_очн_плат"
                        || list[i].specialtyThird == "МиТМ_очн_плат")
                    ochnPlat.add(list[i])
            }
            showLog("МиТМ с размером ${list.size} был разбит на ОчноеБюджет - ${ochnBudg.size}, " +
                    "ОчноеЛьготное - ${ochnLgot.size}, ОчноеПлатное - ${ochnPlat.size}")
            return MiTM(ochnBudg, ochnLgot, ochnPlat)
        }
        fun separateMHT(list: ArrayList<Student>): MHT {
            val ochnBudg = ArrayList<Student>()
            val ochnLgot = ArrayList<Student>()
            val ochnPlat = ArrayList<Student>()

            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "МХТ_очн_бюдж" || list[i].specialtySecond == "МХТ_очн_бюдж"
                        || list[i].specialtyThird == "МХТ_очн_бюдж")
                    ochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "МХТ_очн_льгот" || list[i].specialtySecond == "МХТ_очн_льгот"
                        || list[i].specialtyThird == "МХТ_очн_льгот")
                    ochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "МХТ_очн_плат" || list[i].specialtySecond == "МХТ_очн_плат"
                        || list[i].specialtyThird == "МХТ_очн_плат")
                    ochnPlat.add(list[i])
            }
            showLog("МХТ с размером ${list.size} был разбит на ОчноеБюджет - ${ochnBudg.size}, " +
                    "ОчноеЛьготное - ${ochnLgot.size}, ОчноеПлатное - ${ochnPlat.size}")
            return MHT(ochnBudg, ochnLgot, ochnPlat)
        }
        fun separatePTMK(list: ArrayList<Student>): PTMK {
            val zaochnBudg = ArrayList<Student>()
            val ochnBudg = ArrayList<Student>()
            val ochnLgot = ArrayList<Student>()
            val ochnPlat = ArrayList<Student>()

            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "ПТМК_заочн_плат" || list[i].specialtySecond == "ПТМК_заочн_плат"
                        || list[i].specialtyThird == "ПТМК_заочн_плат")
                    zaochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "ПТМК_очн_бюдж" || list[i].specialtySecond == "ПТМК_очн_бюдж"
                        || list[i].specialtyThird == "ПТМК_очн_бюдж")
                    ochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "ПТМК_очн_льгот" || list[i].specialtySecond == "ПТМК_очн_льгот"
                        || list[i].specialtyThird == "ПТМК_очн_льгот")
                    ochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "ПТМК_очн_плат" || list[i].specialtySecond == "ПТМК_очн_плат"
                        || list[i].specialtyThird == "ПТМК_очн_плат")
                    ochnPlat.add(list[i])
            }
            showLog("ПТМК с размером ${list.size} был разбит на ЗаочноеБюджет - ${zaochnBudg.size}, " +
                    "ОчноеБюджет - ${ochnBudg.size}, ОчноеЛьготное - ${ochnLgot.size}, " +
                    "ОчноеПлатное - ${ochnPlat.size}")
            return PTMK(zaochnBudg, ochnBudg, ochnLgot, ochnPlat)
        }
        fun separateTMO(list: ArrayList<Student>): TMO {
            val oipmZaochnBudg = ArrayList<Student>()
            val oipmZaochnLgot = ArrayList<Student>()
            val oipmZaochnPlat = ArrayList<Student>()
            val ochnBudg = ArrayList<Student>()
            val ochnLgot = ArrayList<Student>()
            val ochnPlat = ArrayList<Student>()
            val ochnCelevoe = ArrayList<Student>()

            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "ТМО(ОИиПМ)_заочн_бюдж" || list[i].specialtySecond == "ТМО(ОИиПМ)_заочн_бюдж"
                        || list[i].specialtyThird == "ТМО(ОИиПМ)_заочн_бюдж")
                    oipmZaochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "ТМО(ОИиПМ)_заочн_льгот" || list[i].specialtySecond == "ТМО(ОИиПМ)_заочн_льгот"
                        || list[i].specialtyThird == "ТМО(ОИиПМ)_заочн_льгот")
                    oipmZaochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "ТМО(ОИиПМ)_заочн_плат" || list[i].specialtySecond == "ТМО(ОИиПМ)_заочн_плат"
                        || list[i].specialtyThird == "ТМО(ОИиПМ)_заочн_плат")
                    oipmZaochnPlat.add(list[i])
                else if (list[i].specialtyFirst == "ТМО_очн_бюдж" || list[i].specialtySecond == "ТМО_очн_бюдж"
                        || list[i].specialtyThird == "ТМО_очн_бюдж")
                    ochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "ТМО_очн_льгот" || list[i].specialtySecond == "ТМО_очн_льгот"
                        || list[i].specialtyThird == "ТМО_очн_льгот")
                    ochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "ТМО_очн_плат" || list[i].specialtySecond == "ТМО_очн_плат"
                        || list[i].specialtyThird == "ТМО_очн_плат")
                    ochnPlat.add(list[i])
                else if (list[i].specialtyFirst == "ТМО_очн_целевое" || list[i].specialtySecond == "ТМО_очн_целевое"
                        || list[i].specialtyThird == "ТМО_очн_целевое")
                    ochnCelevoe.add(list[i])
            }
            showLog("ТМО с размером ${list.size} был разбит на ОИиПМ_ЗаочноеБюджет - ${oipmZaochnBudg.size}, " +
                    "ОИиПМ_ЗаочноеЛьготное - ${oipmZaochnLgot.size}, ОИиПМ_ЗаочноеПлатное - ${oipmZaochnPlat.size}, " +
                    "\nОчноеБюджет - ${ochnBudg.size}, ОчноеЛьготное - ${ochnLgot.size}, ОчноеПлатное - ${ochnPlat.size}, " +
                    "ОчноеЦелевое - ${ochnCelevoe.size}")
            return TMO(oipmZaochnBudg, oipmZaochnLgot, oipmZaochnPlat, ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
        }
        fun separateUTS(list: ArrayList<Student>): UTS {
            val ochnBudg = ArrayList<Student>()
            val ochnLgot = ArrayList<Student>()
            val ochnPlat = ArrayList<Student>()
            val ochnCelevoe = ArrayList<Student>()

            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "УТС_очн_бюдж" || list[i].specialtySecond == "УТС_очн_бюдж"
                        || list[i].specialtyThird == "УТС_очн_бюдж")
                    ochnBudg.add(list[i])
                else if (list[i].specialtyFirst == "УТС_очн_льгот" || list[i].specialtySecond == "УТС_очн_льгот"
                        || list[i].specialtyThird == "УТС_очн_льгот")
                    ochnLgot.add(list[i])
                else if (list[i].specialtyFirst == "УТС_очн_плат" || list[i].specialtySecond == "УТС_очн_плат"
                        || list[i].specialtyThird == "УТС_очн_плат")
                    ochnPlat.add(list[i])
                else if (list[i].specialtyFirst == "УТС_очн_целевое" || list[i].specialtySecond == "УТС_очн_целевое"
                        || list[i].specialtyThird == "УТС_очн_целевое")
                    ochnCelevoe.add(list[i])
            }
            showLog("УТС с размером ${list.size} был разбит на ОчноеБюджет - ${ochnBudg.size}, " +
                    "ОчноеЛьготное - ${ochnLgot.size}, ОчноеПлатное - ${ochnPlat.size}, ОчноеЦелевое - ${ochnCelevoe.size}")
            return UTS(ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
        }

        scoreTypes?.physicsStudents?.let { checkForATP(it) }
        scoreTypes?.computerScienceStudents?.let { checkForATP(it) }
        scoreTypes?.socialScienceStudents?.let { checkForATP(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForATP(it) }

        scoreTypes?.physicsStudents?.let { checkForKTO(it) }
        scoreTypes?.computerScienceStudents?.let { checkForKTO(it) }
        scoreTypes?.socialScienceStudents?.let { checkForKTO(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForKTO(it) }

        scoreTypes?.physicsStudents?.let { checkForMASH(it) }
        scoreTypes?.computerScienceStudents?.let { checkForMASH(it) }
        scoreTypes?.socialScienceStudents?.let { checkForMASH(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForMASH(it) }

        scoreTypes?.physicsStudents?.let { checkForMiTM(it) }
        scoreTypes?.computerScienceStudents?.let { checkForMiTM(it) }
        scoreTypes?.socialScienceStudents?.let { checkForMiTM(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForMiTM(it) }

        scoreTypes?.physicsStudents?.let { checkForMHT(it) }
        scoreTypes?.computerScienceStudents?.let { checkForMHT(it) }
        scoreTypes?.socialScienceStudents?.let { checkForMHT(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForMHT(it) }

        scoreTypes?.physicsStudents?.let { checkForPTMK(it) }
        scoreTypes?.computerScienceStudents?.let { checkForPTMK(it) }
        scoreTypes?.socialScienceStudents?.let { checkForPTMK(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForPTMK(it) }

        scoreTypes?.physicsStudents?.let { checkFotTMO(it) }
        scoreTypes?.computerScienceStudents?.let { checkFotTMO(it) }
        scoreTypes?.socialScienceStudents?.let { checkFotTMO(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkFotTMO(it) }

        scoreTypes?.physicsStudents?.let { checkForUTS(it) }
        scoreTypes?.computerScienceStudents?.let { checkForUTS(it) }
        scoreTypes?.socialScienceStudents?.let { checkForUTS(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForUTS(it) }

        val atpSeparated = separateATP(atp)
        val ktoSeparated = separateKTO(kto)
        val mashSeparated = separateMASH(mash)
        val mitmSeparated = separateMiTM(mitm)
        val mhtSeparated = separateMHT(mht)
        val ptmkSeparated = separatePTMK(ptmk)
        val tmoSeparated = separateTMO(tmo)
        val utsSeparated = separateUTS(uts)

        val unti = Unti(atpSeparated, ktoSeparated, mashSeparated, mitmSeparated, mhtSeparated,
                ptmkSeparated, tmoSeparated, utsSeparated)
        myApplication.saveUnti(unti)
    }
    private fun checkForFEU() {
        val scoreTypes = myApplication.returnScoreTypes()
        val bi = ArrayList<Student>()
        val pi = ArrayList<Student>()
        val sc = ArrayList<Student>()
        val td = ArrayList<Student>()
        val eb = ArrayList<Student>()
        val ek = ArrayList<Student>()
        var check = ArrayList<Student>()

        fun checkForBI(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "БИ_заочн_плат" || list[i].specialtyFirst == "БИ_очн_плат")
                        || (list[i].specialtySecond == "БИ_заочн_плат" || list[i].specialtySecond == "БИ_очн_плат")
                        || (list[i].specialtyThird == "БИ_заочн_плат" || list[i].specialtyThird == "БИ_очн_плат")) {
                    bi.add(list[i])
                }
            }
        }
        fun checkForPI(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ПИ(КИС)_очн_бюдж" || list[i].specialtyFirst == "ПИ(КИС)_очн_льгот"
                        || list[i].specialtyFirst == "ПИ(КИС)_очн_плат" || list[i].specialtyFirst == "ПИ(ЦЭ)_очн_бюдж"
                        || list[i].specialtyFirst == "ПИ(ЦЭ)_очн_льгот" || list[i].specialtyFirst == "ПИ(ЦЭ)_очн_плат")
                        || (list[i].specialtySecond == "ПИ(КИС)_очн_бюдж" || list[i].specialtySecond == "ПИ(КИС)_очн_льгот"
                        || list[i].specialtySecond == "ПИ(КИС)_очн_плат" || list[i].specialtySecond == "ПИ(ЦЭ)_очн_бюдж"
                        || list[i].specialtySecond == "ПИ(ЦЭ)_очн_льгот" || list[i].specialtySecond == "ПИ(ЦЭ)_очн_плат")
                        || (list[i].specialtyThird == "ПИ(КИС)_очн_бюдж" || list[i].specialtyThird == "ПИ(КИС)_очн_льгот"
                        || list[i].specialtyThird == "ПИ(КИС)_очн_плат" || list[i].specialtyThird == "ПИ(ЦЭ)_очн_бюдж"
                        || list[i].specialtyThird == "ПИ(ЦЭ)_очн_льгот" || list[i].specialtyThird == "ПИ(ЦЭ)_очн_плат")) {
                    pi.add(list[i])
                }
            }
        }
        fun checkForSC(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "СЦ_заочн_плат" || list[i].specialtyFirst == "СЦ_очн_плат")
                        || (list[i].specialtySecond == "СЦ_заочн_плат" || list[i].specialtySecond == "СЦ_очн_плат")
                        || (list[i].specialtyThird == "СЦ_заочн_плат" || list[i].specialtyThird == "СЦ_очн_плат")) {
                    sc.add(list[i])
                }
            }
        }
        fun checkForTD(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ТД_заочн_плат" || list[i].specialtyFirst == "ТД_очн_плат")
                        || (list[i].specialtySecond == "ТД_заочн_плат" || list[i].specialtySecond == "ТД_очн_плат")
                        || (list[i].specialtyThird == "ТД_заочн_плат" || list[i].specialtyThird == "ТД_очн_плат")) {
                    td.add(list[i])
                }
            }
        }
        fun checkForEB(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ЭБ_заоч_плат" || list[i].specialtyFirst == "ЭБ_очн_плат")
                        || (list[i].specialtySecond == "ЭБ_заоч_плат" || list[i].specialtySecond == "ЭБ_очн_плат")
                        || (list[i].specialtyThird == "ЭБ_заоч_плат" || list[i].specialtyThird == "ЭБ_очн_плат")) {
                    eb.add(list[i])
                }
            }
        }
        fun checkForEK(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ЭК(БУА)_заоч_плат" || list[i].specialtyFirst == "ЭК(БУА)_очн_плат"
                        || list[i].specialtyFirst == "ЭК(ЛОГ)_очн_плат" || list[i].specialtyFirst == "ЭК(ОЦ)_очн_плат"
                        || list[i].specialtyFirst == "ЭК(Ф)_заоч_плат" || list[i].specialtyFirst == "ЭК(Ф)_очн_плат"
                        || list[i].specialtyFirst == "ЭК(ЭПО)_очн_плат") || (list[i].specialtySecond == "ЭК(БУА)_заоч_плат"
                        || list[i].specialtySecond == "ЭК(БУА)_очн_плат" || list[i].specialtySecond == "ЭК(ЛОГ)_очн_плат"
                        || list[i].specialtySecond == "ЭК(ОЦ)_очн_плат" || list[i].specialtySecond == "ЭК(Ф)_заоч_плат"
                        || list[i].specialtySecond == "ЭК(Ф)_очн_плат" || list[i].specialtySecond == "ЭК(ЭПО)_очн_плат")
                        || (list[i].specialtyThird == "ЭК(БУА)_заоч_плат" || list[i].specialtyThird == "ЭК(БУА)_очн_плат"
                        || list[i].specialtyThird == "ЭК(ЛОГ)_очн_плат" || list[i].specialtyThird == "ЭК(ОЦ)_очн_плат"
                        || list[i].specialtyThird == "ЭК(Ф)_заоч_плат" || list[i].specialtyThird == "ЭК(Ф)_очн_плат"
                        || list[i].specialtyThird == "ЭК(ЭПО)_очн_плат")) {
                    ek.add(list[i])
                }
            }
        }

        /*fun check(list: ArrayList<Student>) {
            val filteredMap = list.filter { it.specialtyFirst == "ПИ(КИС)_очн_бюдж" /*|| it.specialtyFirst == "ПИ(КИС)_очн_льгот"*/ }
            check = filteredMap as ArrayList<Student>
            showLog("CHECK = " + check.size.toString())
        }*/

        scoreTypes?.physicsStudents?.let { checkForBI(it) }
        scoreTypes?.computerScienceStudents?.let { checkForBI(it) }
        scoreTypes?.socialScienceStudents?.let { checkForBI(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForBI(it) }

        scoreTypes?.physicsStudents?.let { checkForPI(it) }
        scoreTypes?.computerScienceStudents?.let { checkForPI(it) }
        scoreTypes?.socialScienceStudents?.let { checkForPI(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForPI(it) }

        scoreTypes?.physicsStudents?.let { checkForSC(it) }
        scoreTypes?.computerScienceStudents?.let { checkForSC(it) }
        scoreTypes?.socialScienceStudents?.let { checkForSC(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForSC(it) }

        scoreTypes?.physicsStudents?.let { checkForTD(it) }
        scoreTypes?.computerScienceStudents?.let { checkForTD(it) }
        scoreTypes?.socialScienceStudents?.let { checkForTD(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForTD(it) }

        scoreTypes?.physicsStudents?.let { checkForEB(it) }
        scoreTypes?.computerScienceStudents?.let { checkForEB(it) }
        scoreTypes?.socialScienceStudents?.let { checkForEB(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForEB(it) }

        scoreTypes?.physicsStudents?.let { checkForEK(it) }
        scoreTypes?.computerScienceStudents?.let { checkForEK(it) }
        scoreTypes?.socialScienceStudents?.let { checkForEK(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForEK(it) }

        /*scoreTypes?.physicsStudents?.let { check(it) }
        scoreTypes?.computerScienceStudents?.let { check(it) }
        scoreTypes?.socialScienceStudents?.let { check(it) }
        scoreTypes?.partAndAllDataStudents?.let { check(it) }*/

        showLog(bi.size.toString())
        showLog(pi.size.toString())
        showLog(sc.size.toString())
        showLog(td.size.toString())
        showLog(eb.size.toString())
        showLog(ek.size.toString())
    }
    private fun checkForFIT() {
        val scoreTypes = myApplication.returnScoreTypes()
        val iasb = ArrayList<Student>()
        val ib = ArrayList<Student>()
        val ibas = ArrayList<Student>()
        val ivt = ArrayList<Student>()
        val inn = ArrayList<Student>()
        val ist = ArrayList<Student>()
        val moa = ArrayList<Student>()
        val pri = ArrayList<Student>()
        val pro = ArrayList<Student>()

        fun checkForIASB(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ИАСБ_очн_бюдж" || list[i].specialtyFirst == "ИАСБ_очн_льгот"
                        || list[i].specialtyFirst == "ИАСБ_очн_плат") || (list[i].specialtySecond == "ИАСБ_очн_бюдж"
                        || list[i].specialtySecond == "ИАСБ_очн_льгот" || list[i].specialtySecond == "ИАСБ_очн_плат")
                        || (list[i].specialtyThird == "ИАСБ_очн_бюдж" || list[i].specialtyThird == "ИАСБ_очн_льгот"
                        || list[i].specialtyThird == "ИАСБ_очн_плат")) {
                    iasb.add(list[i])
                }
            }
        }
        fun checkForIB(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ИБ_веч_платн" || list[i].specialtyFirst == "ИБ_очн_бюдж"
                        || list[i].specialtyFirst == "ИБ_очн_льгот" || list[i].specialtyFirst == "ИБ_очн_плат")
                        || (list[i].specialtySecond == "ИБ_веч_платн" || list[i].specialtySecond == "ИБ_очн_бюдж"
                        || list[i].specialtySecond == "ИБ_очн_льгот" || list[i].specialtySecond == "ИБ_очн_плат")
                        || (list[i].specialtyThird == "ИБ_веч_платн" || list[i].specialtyThird == "ИБ_очн_бюдж"
                        || list[i].specialtyThird == "ИБ_очн_льгот" || list[i].specialtyThird == "ИБ_очн_плат")) {
                    ib.add(list[i])
                }
            }
        }
        fun checkForIBAS(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ИБАС_очн_бюдж" || list[i].specialtyFirst == "ИБАС_очн_льгот"
                        || list[i].specialtyFirst == "ИБАС_очн_плат") || (list[i].specialtySecond == "ИБАС_очн_бюдж"
                        || list[i].specialtySecond == "ИБАС_очн_льгот" || list[i].specialtySecond == "ИБАС_очн_плат")
                        || (list[i].specialtyThird == "ИБАС_очн_бюдж" || list[i].specialtyThird == "ИБАС_очн_льгот"
                        || list[i].specialtyThird == "ИБАС_очн_плат")) {
                    ibas.add(list[i])
                }
            }
        }
        fun checkForIVT(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ИиВТ(ПО)_заочн_плат" || list[i].specialtyFirst == "ИиВТ(ПО)_очн_бюдж"
                        || list[i].specialtyFirst == "ИиВТ(ПО)_очн_льгот" || list[i].specialtyFirst == "ИиВТ(ПО)_очн_плат"
                        || list[i].specialtyFirst == "ИиВТ(ПО)_очн_целевое" || list[i].specialtyFirst == "ИиВТ(САПР)_очн_бюдж"
                        || list[i].specialtyFirst == "ИиВТ(САПР)_очн_льгот" || list[i].specialtyFirst == "ИиВТ(САПР)_очн_плат")
                        || (list[i].specialtySecond == "ИиВТ(ПО)_заочн_плат" || list[i].specialtySecond == "ИиВТ(ПО)_очн_бюдж"
                        || list[i].specialtySecond == "ИиВТ(ПО)_очн_льгот" || list[i].specialtySecond == "ИиВТ(ПО)_очн_плат"
                        || list[i].specialtySecond == "ИиВТ(ПО)_очн_целевое" || list[i].specialtySecond == "ИиВТ(САПР)_очн_бюдж"
                        || list[i].specialtySecond == "ИиВТ(САПР)_очн_льгот" || list[i].specialtySecond == "ИиВТ(САПР)_очн_плат")
                        || (list[i].specialtyThird == "ИиВТ(ПО)_заочн_плат" || list[i].specialtyThird == "ИиВТ(ПО)_очн_бюдж"
                        || list[i].specialtyThird == "ИиВТ(ПО)_очн_льгот" || list[i].specialtyThird == "ИиВТ(ПО)_очн_плат"
                        || list[i].specialtyThird == "ИиВТ(ПО)_очн_целевое" || list[i].specialtyThird == "ИиВТ(САПР)_очн_бюдж"
                        || list[i].specialtyThird == "ИиВТ(САПР)_очн_льгот" || list[i].specialtyThird == "ИиВТ(САПР)_очн_плат")) {
                    ivt.add(list[i])
                }
            }
        }
        fun checkForINN(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ИНН_заочн_плат" || list[i].specialtyFirst == "ИНН_очн_бюдж"
                        || list[i].specialtyFirst == "ИНН_очн_льгот" || list[i].specialtyFirst == "ИНН_очн_плат")
                        || (list[i].specialtySecond == "ИНН_заочн_плат" || list[i].specialtySecond == "ИНН_очн_бюдж"
                        || list[i].specialtySecond == "ИНН_очн_льгот" || list[i].specialtySecond == "ИНН_очн_плат")
                        || (list[i].specialtyThird == "ИНН_заочн_плат" || list[i].specialtyThird == "ИНН_очн_бюдж"
                        || list[i].specialtyThird == "ИНН_очн_льгот" || list[i].specialtyThird == "ИНН_очн_плат")) {
                    inn.add(list[i])
                }
            }
        }
        fun checkForIST(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ИСТ(ИСиТД)_очн_бюдж" || list[i].specialtyFirst == "ИСТ(ИСиТД)_очн_льгот"
                        || list[i].specialtyFirst == "ИСТ(ИСиТД)_очн_плат" || list[i].specialtyFirst == "ИСТ(ИТиПК)_очн_бюдж"
                        || list[i].specialtyFirst == "ИСТ(ИТиПК)_очн_льгот" || list[i].specialtyFirst == "ИСТ(ИТиПК)_очн_плат"
                        || list[i].specialtyFirst == "ИСТ_заочн_плат") || (list[i].specialtySecond == "ИСТ(ИСиТД)_очн_бюдж"
                        || list[i].specialtySecond == "ИСТ(ИСиТД)_очн_льгот" || list[i].specialtySecond == "ИСТ(ИСиТД)_очн_плат"
                        || list[i].specialtySecond == "ИСТ(ИТиПК)_очн_бюдж" || list[i].specialtySecond == "ИСТ(ИТиПК)_очн_льгот"
                        || list[i].specialtySecond == "ИСТ(ИТиПК)_очн_плат" || list[i].specialtySecond == "ИСТ_заочн_плат")
                        || (list[i].specialtyThird == "ИСТ(ИСиТД)_очн_бюдж" || list[i].specialtyThird == "ИСТ(ИСиТД)_очн_льгот"
                        || list[i].specialtyThird == "ИСТ(ИСиТД)_очн_плат" || list[i].specialtyThird == "ИСТ(ИТиПК)_очн_бюдж"
                        || list[i].specialtyThird == "ИСТ(ИТиПК)_очн_льгот" || list[i].specialtyThird == "ИСТ(ИТиПК)_очн_плат"
                        || list[i].specialtyThird == "ИСТ_заочн_плат")) {
                    ist.add(list[i])
                }
            }
        }
        fun checkForMOA(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "МОА_очн_бюдж" || list[i].specialtyFirst == "МОА_очн_льгот"
                        || list[i].specialtyFirst == "МОА_очн_плат" || list[i].specialtyFirst == "МОА_очн_целевое")
                        || (list[i].specialtySecond == "МОА_очн_бюдж" || list[i].specialtySecond == "МОА_очн_льгот"
                        || list[i].specialtySecond == "МОА_очн_плат" || list[i].specialtySecond == "МОА_очн_целевое")
                        || (list[i].specialtyThird == "МОА_очн_бюдж" || list[i].specialtyThird == "МОА_очн_льгот"
                        || list[i].specialtyThird == "МОА_очн_плат" || list[i].specialtyThird == "МОА_очн_целевое")) {
                    moa.add(list[i])
                }
            }
        }
        fun checkForPRI(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ПРИ_очн_бюдж" || list[i].specialtyFirst == "ПРИ_очн_льгот"
                        || list[i].specialtyFirst == "ПРИ_очн_плат" || list[i].specialtyFirst == "ПРИ_очн_целевое")
                        || (list[i].specialtySecond == "ПРИ_очн_бюдж" || list[i].specialtySecond == "ПРИ_очн_льгот"
                        || list[i].specialtySecond == "ПРИ_очн_плат" || list[i].specialtySecond == "ПРИ_очн_целевое")
                        || (list[i].specialtyThird == "ПРИ_очн_бюдж" || list[i].specialtyThird == "ПРИ_очн_льгот"
                        || list[i].specialtyThird == "ПРИ_очн_плат" || list[i].specialtyThird == "ПРИ_очн_целевое")) {
                    pri.add(list[i])
                }
            }
        }
        fun checkForPRO(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ПРО(ГД)_очн_бюдж" || list[i].specialtyFirst == "ПРО(ГД)_очн_льгот"
                        || list[i].specialtyFirst == "ПРО(ГД)_очн_плат" || list[i].specialtyFirst == "ПРО(ИВТ)_заочн_плат"
                        || list[i].specialtyFirst == "ПРО(ЭК)_заочн_плат") || (list[i].specialtySecond == "ПРО(ГД)_очн_бюдж"
                        || list[i].specialtySecond == "ПРО(ГД)_очн_льгот" || list[i].specialtySecond == "ПРО(ГД)_очн_плат"
                        || list[i].specialtySecond == "ПРО(ИВТ)_заочн_плат" || list[i].specialtySecond == "ПРО(ЭК)_заочн_плат")
                        || (list[i].specialtyThird == "ПРО(ГД)_очн_бюдж" || list[i].specialtyThird == "ПРО(ГД)_очн_льгот"
                        || list[i].specialtyThird == "ПРО(ГД)_очн_плат" || list[i].specialtyThird == "ПРО(ИВТ)_заочн_плат"
                        || list[i].specialtyThird == "ПРО(ЭК)_заочн_плат")) {
                    pro.add(list[i])
                }
            }
        }

        scoreTypes?.physicsStudents?.let { checkForIASB(it) }
        scoreTypes?.computerScienceStudents?.let { checkForIASB(it) }
        scoreTypes?.socialScienceStudents?.let { checkForIASB(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForIASB(it) }

        scoreTypes?.physicsStudents?.let { checkForIB(it) }
        scoreTypes?.computerScienceStudents?.let { checkForIB(it) }
        scoreTypes?.socialScienceStudents?.let { checkForIB(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForIB(it) }

        scoreTypes?.physicsStudents?.let { checkForIBAS(it) }
        scoreTypes?.computerScienceStudents?.let { checkForIBAS(it) }
        scoreTypes?.socialScienceStudents?.let { checkForIBAS(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForIBAS(it) }

        scoreTypes?.physicsStudents?.let { checkForIVT(it) }
        scoreTypes?.computerScienceStudents?.let { checkForIVT(it) }
        scoreTypes?.socialScienceStudents?.let { checkForIVT(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForIVT(it) }

        scoreTypes?.physicsStudents?.let { checkForINN(it) }
        scoreTypes?.computerScienceStudents?.let { checkForINN(it) }
        scoreTypes?.socialScienceStudents?.let { checkForINN(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForINN(it) }

        scoreTypes?.physicsStudents?.let { checkForIST(it) }
        scoreTypes?.computerScienceStudents?.let { checkForIST(it) }
        scoreTypes?.socialScienceStudents?.let { checkForIST(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForIST(it) }

        scoreTypes?.physicsStudents?.let { checkForMOA(it) }
        scoreTypes?.computerScienceStudents?.let { checkForMOA(it) }
        scoreTypes?.socialScienceStudents?.let { checkForMOA(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForMOA(it) }

        scoreTypes?.physicsStudents?.let { checkForPRI(it) }
        scoreTypes?.computerScienceStudents?.let { checkForPRI(it) }
        scoreTypes?.socialScienceStudents?.let { checkForPRI(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForPRI(it) }

        scoreTypes?.physicsStudents?.let { checkForPRO(it) }
        scoreTypes?.computerScienceStudents?.let { checkForPRO(it) }
        scoreTypes?.socialScienceStudents?.let { checkForPRO(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForPRO(it) }
    }
    private fun checkForMTF() {
        val scoreTypes = myApplication.returnScoreTypes()
        val mash = ArrayList<Student>()
        val sim = ArrayList<Student>()
        val tb = ArrayList<Student>()
        val uk = ArrayList<Student>()

        fun checkForMASH(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "МАШ(Л)_заочн_бюдж" || list[i].specialtyFirst == "МАШ(Л)_заочн_льгот"
                        || list[i].specialtyFirst == "МАШ(Л)_заочн_плат" || list[i].specialtyFirst == "МАШ(Л)_очн_бюдж"
                        || list[i].specialtyFirst == "МАШ(Л)_очн_льгот" || list[i].specialtyFirst == "МАШ(Л)_очн_плат"
                        || list[i].specialtyFirst == "МАШ(С)_заочн_бюдж" || list[i].specialtyFirst == "МАШ(С)_заочн_льгот"
                        || list[i].specialtyFirst == "МАШ(С)_заочн_плат" || list[i].specialtyFirst == "МАШ(С)_очн_бюдж"
                        || list[i].specialtyFirst == "МАШ(С)_очн_льгот" || list[i].specialtyFirst == "МАШ(С)_очн_плат"
                        || list[i].specialtyFirst == "МАШ(С)_очн_целевое") || (list[i].specialtySecond == "МАШ(Л)_заочн_бюдж"
                        || list[i].specialtySecond == "МАШ(Л)_заочн_льгот" || list[i].specialtySecond == "МАШ(Л)_заочн_плат"
                        || list[i].specialtySecond == "МАШ(Л)_очн_бюдж" || list[i].specialtySecond == "МАШ(Л)_очн_льгот"
                        || list[i].specialtySecond == "МАШ(Л)_очн_плат" || list[i].specialtySecond == "МАШ(С)_заочн_бюдж"
                        || list[i].specialtySecond == "МАШ(С)_заочн_льгот" || list[i].specialtySecond == "МАШ(С)_заочн_плат"
                        || list[i].specialtySecond == "МАШ(С)_очн_бюдж" || list[i].specialtySecond == "МАШ(С)_очн_льгот"
                        || list[i].specialtySecond == "МАШ(С)_очн_плат" || list[i].specialtySecond == "МАШ(С)_очн_целевое")
                        || (list[i].specialtyThird == "МАШ(Л)_заочн_бюдж" || list[i].specialtyThird == "МАШ(Л)_заочн_льгот"
                        || list[i].specialtyThird == "МАШ(Л)_заочн_плат" || list[i].specialtyThird == "МАШ(Л)_очн_бюдж"
                        || list[i].specialtyThird == "МАШ(Л)_очн_льгот" || list[i].specialtyThird == "МАШ(Л)_очн_плат"
                        || list[i].specialtyThird == "МАШ(С)_заочн_бюдж" || list[i].specialtyThird == "МАШ(С)_заочн_льгот"
                        || list[i].specialtyThird == "МАШ(С)_заочн_плат" || list[i].specialtyThird == "МАШ(С)_очн_бюдж"
                        || list[i].specialtyThird == "МАШ(С)_очн_льгот" || list[i].specialtyThird == "МАШ(С)_очн_плат"
                        || list[i].specialtyThird == "МАШ(С)_очн_целевое")) {
                    mash.add(list[i])
                }
            }
        }
        fun checkForSIM(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "СиМ_заочн_плат" || list[i].specialtyFirst == "СиМ_очн_бюдж"
                        || list[i].specialtyFirst == "СиМ_очн_льгот" || list[i].specialtyFirst == "СиМ_очн_плат")
                        || (list[i].specialtySecond == "СиМ_заочн_плат" || list[i].specialtySecond == "СиМ_очн_бюдж"
                        || list[i].specialtySecond == "СиМ_очн_льгот" || list[i].specialtySecond == "СиМ_очн_плат")
                        || (list[i].specialtyThird == "СиМ_заочн_плат" || list[i].specialtyThird == "СиМ_очн_бюдж"
                        || list[i].specialtyThird == "СиМ_очн_льгот" || list[i].specialtyThird == "СиМ_очн_плат")) {
                    sim.add(list[i])
                }
            }
        }
        fun checkForTB(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ТБ(БТПиП)_заочн_плат" || list[i].specialtyFirst == "ТБ(БТПиП)_очн_бюдж"
                        || list[i].specialtyFirst == "ТБ(БТПиП)_очн_льгот" || list[i].specialtyFirst == "ТБ(БТПиП)_очн_плат")
                        || (list[i].specialtySecond == "ТБ(БТПиП)_заочн_плат" || list[i].specialtySecond == "ТБ(БТПиП)_очн_бюдж"
                        || list[i].specialtySecond == "ТБ(БТПиП)_очн_льгот" || list[i].specialtySecond == "ТБ(БТПиП)_очн_плат")
                        || (list[i].specialtyThird == "ТБ(БТПиП)_заочн_плат" || list[i].specialtyThird == "ТБ(БТПиП)_очн_бюдж"
                        || list[i].specialtyThird == "ТБ(БТПиП)_очн_льгот" || list[i].specialtyThird == "ТБ(БТПиП)_очн_плат")) {
                    tb.add(list[i])
                }
            }
        }
        fun checkForUK(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == "УК_заочн_плат" || list[i].specialtySecond == "УК_заочн_плат"
                        || list[i].specialtyThird == "УК_заочн_плат") {
                    uk.add(list[i])
                }
            }
        }

        scoreTypes?.physicsStudents?.let { checkForMASH(it) }
        scoreTypes?.computerScienceStudents?.let { checkForMASH(it) }
        scoreTypes?.socialScienceStudents?.let { checkForMASH(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForMASH(it) }

        scoreTypes?.physicsStudents?.let { checkForSIM(it) }
        scoreTypes?.computerScienceStudents?.let { checkForSIM(it) }
        scoreTypes?.socialScienceStudents?.let { checkForSIM(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForSIM(it) }

        scoreTypes?.physicsStudents?.let { checkForTB(it) }
        scoreTypes?.computerScienceStudents?.let { checkForTB(it) }
        scoreTypes?.socialScienceStudents?.let { checkForTB(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForTB(it) }

        scoreTypes?.physicsStudents?.let { checkForUK(it) }
        scoreTypes?.computerScienceStudents?.let { checkForUK(it) }
        scoreTypes?.socialScienceStudents?.let { checkForUK(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForUK(it) }
    }
    private fun checkForUNIT() {
        val scoreTypes = myApplication.returnScoreTypes()
        val nttk = ArrayList<Student>()
        val ntts = ArrayList<Student>()
        val pm = ArrayList<Student>()
        val psjd = ArrayList<Student>()
        val ttp = ArrayList<Student>()
        val ettk = ArrayList<Student>()

        fun checkForNTTK(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "НТТК_заочн_бюдж" || list[i].specialtyFirst == "НТТК_заочн_льгот"
                        || list[i].specialtyFirst == "НТТК_заочн_плат") || (list[i].specialtySecond == "НТТК_заочн_бюдж"
                        || list[i].specialtySecond == "НТТК_заочн_льгот" || list[i].specialtySecond == "НТТК_заочн_плат")
                        || (list[i].specialtyThird == "НТТК_заочн_бюдж" || list[i].specialtyThird == "НТТК_заочн_льгот"
                        || list[i].specialtyThird == "НТТК_заочн_плат")) {
                    nttk.add(list[i])
                }
            }
        }
        fun checkForNTTS(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "НТТС_очн_бюдж" || list[i].specialtyFirst == "НТТС_очн_льгот"
                        || list[i].specialtyFirst == "НТТС_очн_плат") || (list[i].specialtySecond == "НТТС_очн_бюдж"
                        || list[i].specialtySecond == "НТТС_очн_льгот" || list[i].specialtySecond == "НТТС_очн_плат")
                        || (list[i].specialtyThird == "НТТС_очн_бюдж" || list[i].specialtyThird == "НТТС_очн_льгот"
                        || list[i].specialtyThird == "НТТС_очн_плат")) {
                    ntts.add(list[i])
                }
            }
        }
        fun checkForPM(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ПМ(БМ)_очн_бюдж" || list[i].specialtyFirst == "ПМ(БМ)_очн_льгот"
                        || list[i].specialtyFirst == "ПМ(БМ)_очн_плат" || list[i].specialtyFirst == "ПМ(ДПМ)_очн_бюдж"
                        || list[i].specialtyFirst == "ПМ(ДПМ)_очн_льгот" || list[i].specialtyFirst == "ПМ(ДПМ)_очн_плат")
                        || (list[i].specialtySecond == "ПМ(БМ)_очн_бюдж" || list[i].specialtySecond == "ПМ(БМ)_очн_льгот"
                        || list[i].specialtySecond == "ПМ(БМ)_очн_плат" || list[i].specialtySecond == "ПМ(ДПМ)_очн_бюдж"
                        || list[i].specialtySecond == "ПМ(ДПМ)_очн_льгот" || list[i].specialtySecond == "ПМ(ДПМ)_очн_плат")
                        || (list[i].specialtyThird == "ПМ(БМ)_очн_бюдж" || list[i].specialtyThird == "ПМ(БМ)_очн_льгот"
                        || list[i].specialtyThird == "ПМ(БМ)_очн_плат" || list[i].specialtyThird == "ПМ(ДПМ)_очн_бюдж"
                        || list[i].specialtyThird == "ПМ(ДПМ)_очн_льгот" || list[i].specialtyThird == "ПМ(ДПМ)_очн_плат")) {
                    pm.add(list[i])
                }
            }
        }
        fun checkForPSJD(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ПСЖД(В)_очн_плат" || list[i].specialtyFirst == "ПСЖД(Л)_очн_плат"
                        || list[i].specialtyFirst == "ПСЖД_заочн_плат") || (list[i].specialtySecond == "ПСЖД(В)_очн_плат"
                        || list[i].specialtySecond == "ПСЖД(Л)_очн_плат" || list[i].specialtySecond == "ПСЖД_заочн_плат")
                        || (list[i].specialtyThird == "ПСЖД(В)_очн_плат" || list[i].specialtyThird == "ПСЖД(Л)_очн_плат"
                        || list[i].specialtyThird == "ПСЖД_заочн_плат")) {
                    psjd.add(list[i])
                }
            }
        }
        fun checkForTTP(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ТТП_заочн_бюдж" || list[i].specialtyFirst == "ТТП_заочн_льгот"
                        || list[i].specialtyFirst == "ТТП_заочн_плат" || list[i].specialtyFirst == "ТТП_очн_бюдж"
                        || list[i].specialtyFirst == "ТТП_очн_льгот" || list[i].specialtyFirst == "ТТП_очн_плат")
                        || (list[i].specialtySecond == "ТТП_заочн_бюдж" || list[i].specialtySecond == "ТТП_заочн_льгот"
                        || list[i].specialtySecond == "ТТП_заочн_плат" || list[i].specialtySecond == "ТТП_очн_бюдж"
                        || list[i].specialtySecond == "ТТП_очн_льгот" || list[i].specialtySecond == "ТТП_очн_плат")
                        || (list[i].specialtyThird == "ТТП_заочн_бюдж" || list[i].specialtyThird == "ТТП_заочн_льгот"
                        || list[i].specialtyThird == "ТТП_заочн_плат" || list[i].specialtyThird == "ТТП_очн_бюдж"
                        || list[i].specialtyThird == "ТТП_очн_льгот" || list[i].specialtyThird == "ТТП_очн_плат")) {
                    ttp.add(list[i])
                }
            }
        }
        fun checkForETTK(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ЭТТК(АиАХ)_очн_бюдж" || list[i].specialtyFirst == "ЭТТК(АиАХ)_очн_льгот"
                        || list[i].specialtyFirst == "ЭТТК(АиАХ)_очн_плат" || list[i].specialtyFirst == "ЭТТК(АиАХ)_очн_целевое"
                        || list[i].specialtyFirst == "ЭТТК(ПСЖД)_очн_бюдж" || list[i].specialtyFirst == "ЭТТК(ПСЖД)_очн_льгот"
                        || list[i].specialtyFirst == "ЭТТК(ПСЖД)_очн_плат") || (list[i].specialtySecond == "ЭТТК(АиАХ)_очн_бюдж"
                        || list[i].specialtySecond == "ЭТТК(АиАХ)_очн_льгот" || list[i].specialtySecond == "ЭТТК(АиАХ)_очн_плат"
                        || list[i].specialtySecond == "ЭТТК(АиАХ)_очн_целевое" || list[i].specialtySecond == "ЭТТК(ПСЖД)_очн_бюдж"
                        || list[i].specialtySecond == "ЭТТК(ПСЖД)_очн_льгот" || list[i].specialtySecond == "ЭТТК(ПСЖД)_очн_плат")
                        || (list[i].specialtyThird == "ЭТТК(АиАХ)_очн_бюдж" || list[i].specialtyThird == "ЭТТК(АиАХ)_очн_льгот"
                        || list[i].specialtyThird == "ЭТТК(АиАХ)_очн_плат" || list[i].specialtyThird == "ЭТТК(АиАХ)_очн_целевое"
                        || list[i].specialtyThird == "ЭТТК(ПСЖД)_очн_бюдж" || list[i].specialtyThird == "ЭТТК(ПСЖД)_очн_льгот"
                        || list[i].specialtyThird == "ЭТТК(ПСЖД)_очн_плат")) {
                    ettk.add(list[i])
                }
            }
        }

        scoreTypes?.physicsStudents?.let { checkForNTTK(it) }
        scoreTypes?.computerScienceStudents?.let { checkForNTTK(it) }
        scoreTypes?.socialScienceStudents?.let { checkForNTTK(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForNTTK(it) }

        scoreTypes?.physicsStudents?.let { checkForNTTS(it) }
        scoreTypes?.computerScienceStudents?.let { checkForNTTS(it) }
        scoreTypes?.socialScienceStudents?.let { checkForNTTS(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForNTTS(it) }

        scoreTypes?.physicsStudents?.let { checkForPM(it) }
        scoreTypes?.computerScienceStudents?.let { checkForPM(it) }
        scoreTypes?.socialScienceStudents?.let { checkForPM(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForPM(it) }

        scoreTypes?.physicsStudents?.let { checkForPSJD(it) }
        scoreTypes?.computerScienceStudents?.let { checkForPSJD(it) }
        scoreTypes?.socialScienceStudents?.let { checkForPSJD(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForPSJD(it) }

        scoreTypes?.physicsStudents?.let { checkForTTP(it) }
        scoreTypes?.computerScienceStudents?.let { checkForTTP(it) }
        scoreTypes?.socialScienceStudents?.let { checkForTTP(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForTTP(it) }

        scoreTypes?.physicsStudents?.let { checkForETTK(it) }
        scoreTypes?.computerScienceStudents?.let { checkForETTK(it) }
        scoreTypes?.socialScienceStudents?.let { checkForETTK(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForETTK(it) }
    }


    override fun showFaculties(faculties: ArrayList<Faculty>) {
        pickUpSpecialtiesRecyclerView.post {
            adapter?.updateFacultiesList(faculties)
            pickUpSpecialtiesRecyclerView.adapter = adapter
        }

        /*activity?.runOnUiThread {
            adapter?.updateFacultiesList(faculties)
            pickUpSpecialtiesRecyclerView.adapter = adapter
        }*/
    }
    override fun onFacultyClicked(faculty: Faculty, position: Int) {
        showLog("Выбран: ${faculty.name}")
        val bundle = Bundle()
        val faculties = myApplication.returnFaculties()

        fun moveToSpecialties(position: Int, title: String, list: ArrayList<Specialty>) {
            bundle.stringAndSerializable(title, list)
            bundle.putInt("pos", position)
            toSpecialties(bundle)
        }
        faculties?.let {
            when (position) {
                //УНТИ
                0 -> moveToSpecialties(position,"УНТИ", it.untiList)
                //ФЭУ
                1 -> moveToSpecialties(position,"ФЭУ", it.feuList)
                //ФИТ
                2 -> moveToSpecialties(position,"ФИТ", it.fitList)
                //МТФ
                3 -> moveToSpecialties(position,"МТФ", it.mtfList)
                //УНИТ
                4 -> moveToSpecialties(position,"УНИТ", it.unitList)
                //ФЭЭ
                5 -> moveToSpecialties(position,"ФЭЭ", it.feeList)
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