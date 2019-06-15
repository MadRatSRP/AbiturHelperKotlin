package com.madrat.abiturhelper.presenters.fragments

import android.content.Context
import android.os.Bundle
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.WorkWithSpecialtiesMVP
import com.madrat.abiturhelper.model.*
import com.madrat.abiturhelper.model.faculties.*
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.filterForSpecialty
import com.madrat.abiturhelper.util.showLog
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.system.measureTimeMillis

class WorkWithSpecialtiesPresenter(private var pv: WorkWithSpecialtiesMVP.View,
                                   private var pr: WorkWithSpecialtiesMVP.Repository)
    : WorkWithSpecialtiesMVP.Presenter{
    private val myApplication = MyApplication.instance

    /*Первый этап*/
    override fun generateBachelorsAndSpecialtiesLists(context: Context) {
        showLog("Начат первый этап")
        val time = measureTimeMillis {
            val specialties = grabSpecialties(context, "specialties.csv")
            val bachelorsAndSpecialists = divideSpecialtiesByEducationLevel(specialties)
            divideSpecialtiesByFaculty(bachelorsAndSpecialists)

            val students = grabStudents(context, "abiturs.csv")
            divideStudentsByAdmissions(students)
        }
        showLog("Первый этап завершён за $time ms")
    }
    override fun grabSpecialties(context: Context, path: String): ArrayList<Specialty> {
        val specialtiesList = ArrayList<Specialty>()
        val file = context.assets?.open(path)
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
    override fun grabStudents(context: Context, path: String): ArrayList<Student> {
        val studentsList = ArrayList<Student>()
        val file = context.assets?.open(path)
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
                    checkTextForBeingEmpty(russian), checkTextForBeingEmpty(maths), checkTextForBeingEmpty(physics),
                    checkTextForBeingEmpty(computerScience), checkTextForBeingEmpty(socialScience),
                    checkTextForBeingEmpty(additionalScore), isCertificateAvailable.toBoolean(),
                    isChargeAvailable.toBoolean(), priority.toIntOrNull()))
        }
        showLog("Подавших документы: ${studentsList.size}")
        return studentsList
    }
    override fun checkTextForBeingEmpty(text: String): Int {
        return if (text.isEmpty()) {
            0
        } else text.toInt()
    }
    override fun divideSpecialtiesByEducationLevel(list: ArrayList<Specialty>): ArrayList<Specialty> {
        val bachelorsAndSpecialists = list.filter {
            it.educationLevel == "Академический бакалавр" || it.educationLevel == "Специалист" }
                as ArrayList<Specialty>
        showLog("Специальностей, ведущих набор на бакалавриат и специалитет: ${bachelorsAndSpecialists.size}")
        return bachelorsAndSpecialists
    }
    override fun divideSpecialtiesByFaculty(list: ArrayList<Specialty>) {
        // УНТИ
        val listUNTI = list.filter { it.faculty == "Учебно-научный технологический институт" }
                as ArrayList<Specialty>
        // ФЭУ
        val listFEU = list.filter { it.faculty == "Факультет экономики и управления" }
                as ArrayList<Specialty>
        // ФИТ
        val listFIT = list.filter { it.faculty == "Факультет информационных технологий" }
                as ArrayList<Specialty>
        // МТФ
        val listMTF = list.filter { it.faculty == "Механико-технологический факультет" }
                as ArrayList<Specialty>
        // УНИТ
        val listUNIT = list.filter { it.faculty == "Учебно-научный институт транспорта" }
                as ArrayList<Specialty>
        // ФЭЭ
        val listFEE = list.filter { it.faculty == "Факультет энергетики и электроники" }
                as ArrayList<Specialty>

        myApplication.saveFaculties(Faculties(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE))
    }
    override fun divideStudentsByAdmissions(list: ArrayList<Student>) {
        val bachelors = list.filter { it.admissions == "бак"} as ArrayList<Student>
        myApplication.saveBachelors(bachelors)
    }

    // Второй этап

    override fun generateScoreTypedListsAndCalculateAvailableFacultyPlaces() {
        showLog("Начат второй этап")
        val time = measureTimeMillis {
            // Получить и сохранить списки студентов, разделённые по баллам
            val scoreTypes = returnStudentsSeparatedByScoreType()
            myApplication.saveScoreTypes(scoreTypes)

            // Получаем и сохраняем количество общих и свободных мест для каждого из факультетов
            val listOfFaculties = returnListOfFaculties()
            myApplication.saveFacultyList(listOfFaculties)
        }
        showLog("Второй этап завершён за $time ms")
    }
    override fun returnStudentsSeparatedByScoreType(): ScoreTypes {
        val bachelors = myApplication.returnBachelors()

        // Вычисляем количество студентов, у которых достаточно баллов
        val studentsWithEnoughData = bachelors?.filter {
            it.maths != 0 && it.russian != 0 && (it.physics != 0 || it.computerScience != 0
                    || it.socialScience != 0) } as ArrayList<Student>
        showLog("Студентов, чьих баллов достаточно: ${studentsWithEnoughData.size}")

        // Вычисляем количество студентов, у которых недостаточно баллов по предметам
        val studentsWithoutEnoughData = bachelors.filter {
            (it.maths == 0 && it.russian == 0) || it.maths == 0 || it.russian == 0
                    || (it.physics == 0 && it.computerScience == 0 && it.socialScience == 0)} as ArrayList<Student>

        return ScoreTypes(
                withdrawPhysicsStudents(studentsWithEnoughData),
                withdrawComputerScienceStudents(studentsWithEnoughData),
                withdrawSocialScienceStudents(studentsWithEnoughData),
                studentsWithoutEnoughData,
                withdrawStudentsWithPartAndFullData(studentsWithEnoughData)
        )
    }
    override fun returnListOfFaculties(): ArrayList<Faculty> {
        val facultyList = ArrayList<Faculty>()
        val faculties = myApplication.returnFaculties()
        //facultyList.clear()

        val calculatedPlacesUNTI = calculateAvailableFacultyPlaces("УНТИ", faculties?.listUNTI)
        val calculatedPlacesFEU = calculateAvailableFacultyPlaces("ФЭУ", faculties?.listFEU)
        val calculatedPlacesFIT = calculateAvailableFacultyPlaces("ФИТ", faculties?.listFIT)
        val calculatedPlacesMTF = calculateAvailableFacultyPlaces("МТФ", faculties?.listMTF)
        val calculatedPlacesUNIT = calculateAvailableFacultyPlaces("УНИТ", faculties?.listUNIT)
        val calculatedPlacesFEE = calculateAvailableFacultyPlaces("ФЭЭ", faculties?.listFEE)

        val collection = arrayListOf(calculatedPlacesUNTI, calculatedPlacesFEU, calculatedPlacesFIT,
                calculatedPlacesMTF, calculatedPlacesUNIT, calculatedPlacesFEE)
        facultyList.addAll(collection)
        return facultyList
    }
    // Физика
    override fun withdrawPhysicsStudents(bachelors: ArrayList<Student>)
            = bachelors.filter { it.physics != 0 && it.computerScience == 0 && it.socialScience == 0 } as ArrayList<Student>
    // Информатика
    override fun withdrawComputerScienceStudents(bachelors: ArrayList<Student>)
            = bachelors.filter { it.physics == 0 && it.computerScience != 0 && it.socialScience == 0 } as ArrayList<Student>
    // Обществознание
    override fun withdrawSocialScienceStudents(bachelors: ArrayList<Student>)
            = bachelors.filter { it.physics == 0 && it.computerScience == 0 && it.socialScience != 0 } as ArrayList<Student>
    // Баллы по двум/трем предметам
    override fun withdrawStudentsWithPartAndFullData(bachelors: ArrayList<Student>)
            = bachelors.filter { (it.physics != 0 && it.computerScience != 0 && it.socialScience != 0)
            || (it.physics != 0 && it.computerScience != 0 && it.socialScience == 0)
            || (it.physics != 0 && it.computerScience == 0 && it.socialScience != 0)
            || (it.physics == 0 && it.computerScience != 0 && it.socialScience != 0) } as ArrayList<Student>
    override fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>?)
            : Faculty {
        val total = list?.sumBy { it.entriesTotal }
        val free = list?.sumBy { it.entriesFree }
        val amountOfSpecialties = list?.size
        return Faculty(name, total, free, amountOfSpecialties)
    }

    // Третий этап

    override fun separateStudentsBySpecialties() {
        checkForUNTI()
        checkForFEU()
        checkForFIT()
        checkForMTF()
        checkForUNIT()
        checkForFEE()
        println("Третий этап завершён")
    }
    // УНТИ
    override fun checkForUNTI() {
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

        // Получаем список списков студентов специальности УНТИ и сохраняем его
        val unti = UNTI(atp, kto, mash, mitm, mht, ptmk, tmo, uts)
        val separatedUNTI = separateUNTI(unti)
        myApplication.saveUnti(separatedUNTI)
    }
    override fun separateUNTI(unti: UNTI): ArrayList<ArrayList<Student>> {
        val listUNTI = ArrayList<ArrayList<Student>>()

        val separatedATP = separateForATP(unti.atp)
        val separatedKTO = separateForKTO(unti.kto)
        val separatedMASH = separateForMASH(unti.mash)
        val separatedMiTM = separateForMiTM(unti.mitm)
        val separatedMHT = separateForMHT(unti.mht)
        val separatedPTMK = separateForPTMK(unti.ptmk)
        val separatedTMO = separateForTMO(unti.tmo)
        val separatedUTS = separateForUTS(unti.uts)

        listUNTI.addAll(separatedATP)
        listUNTI.addAll(separatedKTO)
        listUNTI.addAll(separatedMASH)
        listUNTI.addAll(separatedMiTM)
        listUNTI.addAll(separatedMHT)
        listUNTI.addAll(separatedPTMK)
        listUNTI.addAll(separatedTMO)
        listUNTI.addAll(separatedUTS)

        return listUNTI
    }
    override fun separateForATP(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnBudg = list.filterForSpecialty("АТП_заочн_бюдж")
        val zaochnLgot = list.filterForSpecialty("АТП_заочн_льгот")
        val zaochnPlat = list.filterForSpecialty("АТП_заочн_плат")
        val ochnBudg = list.filterForSpecialty("АТП_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("АТП_очн_льгот")
        val ochnPlat = list.filterForSpecialty("АТП_очн_плат")
        val ochnCelevoe = list.filterForSpecialty("АТП_очн_целевое")

        return arrayListOf(zaochnBudg, zaochnLgot, zaochnPlat, ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
    }
    override fun separateForKTO(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val atkmOchnBudg = list.filterForSpecialty("КТО(АТиКМ)_очн_бюдж")
        val atkmOchnLgot = list.filterForSpecialty("КТО(АТиКМ)_очн_льгот")
        val atkmOchnPlat = list.filterForSpecialty("КТО(АТиКМ)_очн_плат")
        val tmOchnBudg = list.filterForSpecialty("КТО(ТМ)_очн_бюдж")
        val tmOchnLgot = list.filterForSpecialty("КТО(ТМ)_очн_льгот")
        val tmOchnPlat = list.filterForSpecialty("КТО(ТМ)_очн_плат")
        val tmOchnCelevoe = list.filterForSpecialty("КТО(ТМ)_очн_целевое")
        val vechBudg = list.filterForSpecialty("КТО_веч_бюдж")
        val vechLgot = list.filterForSpecialty("КТО_веч_льгот")
        val vechPlat = list.filterForSpecialty("КТО_веч_плат")

        return arrayListOf(atkmOchnBudg, atkmOchnLgot, atkmOchnPlat, tmOchnBudg, tmOchnLgot,
                tmOchnPlat, tmOchnCelevoe, vechBudg, vechLgot, vechPlat)
    }
    override fun separateForMASH(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val tmZaochnBudg = list.filterForSpecialty("МАШ(ТМ)_заочн_бюдж")
        val tmZaochnLgot = list.filterForSpecialty("МАШ(ТМ)_заочн_льгот")
        val tmZaochnPlat = list.filterForSpecialty("МАШ(ТМ)_заочн_плат")

        return arrayListOf(tmZaochnBudg, tmZaochnLgot, tmZaochnPlat)
    }
    override fun separateForMiTM(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("МиТМ_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("МиТМ_очн_льгот")
        val ochnPlat = list.filterForSpecialty("МиТМ_очн_плат")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateForMHT(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("МХТ_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("МХТ_очн_льгот")
        val ochnPlat = list.filterForSpecialty("МХТ_очн_плат")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateForPTMK(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnBudg = list.filterForSpecialty("ПТМК_заочн_плат")
        val ochnBudg = list.filterForSpecialty("ПТМК_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ПТМК_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ПТМК_очн_плат")

        return arrayListOf(zaochnBudg, ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateForTMO(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val oipmZaochnBudg = list.filterForSpecialty("ТМО(ОИиПМ)_заочн_бюдж")
        val oipmZaochnLgot = list.filterForSpecialty("ТМО(ОИиПМ)_заочн_льгот")
        val oipmZaochnPlat = list.filterForSpecialty("ТМО(ОИиПМ)_заочн_плат")
        val ochnBudg = list.filterForSpecialty("ТМО_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ТМО_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ТМО_очн_плат")
        val ochnCelevoe = list.filterForSpecialty("ТМО_очн_целевое")

        return arrayListOf(oipmZaochnBudg, oipmZaochnLgot, oipmZaochnPlat, ochnBudg,
                ochnLgot, ochnPlat, ochnCelevoe)
    }
    override fun separateForUTS(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("УТС_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("УТС_очн_льгот")
        val ochnPlat = list.filterForSpecialty("УТС_очн_плат")
        val ochnCelevoe = list.filterForSpecialty("УТС_очн_целевое")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
    }
    // ФЭУ
    override fun checkForFEU() {
        val scoreTypes = myApplication.returnScoreTypes()
        val bi = ArrayList<Student>()
        val pi = ArrayList<Student>()
        val sc = ArrayList<Student>()
        val td = ArrayList<Student>()
        val eb = ArrayList<Student>()
        val ek = ArrayList<Student>()

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

        val feu = FEU(bi, pi, sc, td, eb, ek)
        val separatedFEU = separateFEU(feu)
        myApplication.saveFeu(separatedFEU)
    }
    override fun separateFEU(feu: FEU): ArrayList<ArrayList<Student>> {
        val listFEU = ArrayList<ArrayList<Student>>()

        val separatedBI = separateBI(feu.bi)
        val separatedPI = separatePI(feu.pi)
        val separatedSC = separateSC(feu.sc)
        val separatedTD = separateTD(feu.td)
        val separatedEB = separateEB(feu.eb)
        val separatedEK = separateEK(feu.ek)

        listFEU.addAll(separatedBI)
        listFEU.addAll(separatedPI)
        listFEU.addAll(separatedSC)
        listFEU.addAll(separatedTD)
        listFEU.addAll(separatedEB)
        listFEU.addAll(separatedEK)

        return listFEU
    }
    override fun separateBI(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnPlat = list.filterForSpecialty("БИ_заочн_плат")
        val ochnPlat = list.filterForSpecialty("БИ_очн_плат")

        return arrayListOf(zaochnPlat, ochnPlat)
    }
    override fun separatePI(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val kisOchnBudg = list.filterForSpecialty("ПИ(КИС)_очн_бюдж")
        val kisOchnLgot = list.filterForSpecialty("ПИ(КИС)_очн_льгот")
        val kisOchnPlat = list.filterForSpecialty("ПИ(КИС)_очн_плат")
        val ceOchnBudg = list.filterForSpecialty("ПИ(ЦЭ)_очн_бюдж")
        val ceOchnLgot = list.filterForSpecialty("ПИ(ЦЭ)_очн_льгот")
        val ceOchnPlat = list.filterForSpecialty("ПИ(ЦЭ)_очн_плат")

        return arrayListOf(kisOchnBudg, kisOchnLgot, kisOchnPlat, ceOchnBudg, ceOchnLgot, ceOchnPlat)
    }
    override fun separateSC(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnPlat = list.filterForSpecialty("СЦ_заочн_плат")
        val ochnPlat = list.filterForSpecialty("СЦ_очн_плат")

        return arrayListOf(zaochnPlat, ochnPlat)
    }
    override fun separateTD(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnPlat = list.filterForSpecialty("ТД_заочн_плат")
        val ochnPlat = list.filterForSpecialty("ТД_очн_плат")

        return arrayListOf(zaochnPlat, ochnPlat)
    }
    override fun separateEB(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnPlat = list.filterForSpecialty("ЭБ_заоч_плат")
        val ochnPlat = list.filterForSpecialty("ЭБ_очн_плат")

        return arrayListOf(zaochnPlat, ochnPlat)
    }
    override fun separateEK(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val buaZaochnPlat = list.filterForSpecialty("ЭК(БУА)_заоч_плат")
        val buaOchnPlat = list.filterForSpecialty("ЭК(БУА)_очн_плат")
        val logOchnPlat = list.filterForSpecialty("ЭК(ЛОГ)_очн_плат")
        val ocOchnPlat = list.filterForSpecialty("ЭК(ОЦ)_очн_плат")
        val fZaochnPlat = list.filterForSpecialty("ЭК(Ф)_заоч_плат")
        val fOchnPlat = list.filterForSpecialty("ЭК(Ф)_очн_плат")
        val epoOchnPlat = list.filterForSpecialty("ЭК(ЭПО)_очн_плат")

        return arrayListOf(buaZaochnPlat, buaOchnPlat, logOchnPlat, ocOchnPlat,
                fZaochnPlat, fOchnPlat, epoOchnPlat)
    }
    // ФИТ
    override fun checkForFIT() {
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

        val fit = FIT(iasb, ib, ibas, ivt, inn, ist, moa, pri, pro)
        val separatedFIT = separateFIT(fit)
        myApplication.saveFIT(separatedFIT)
    }
    override fun separateFIT(fit: FIT): ArrayList<ArrayList<Student>> {
        val listFIT = ArrayList<ArrayList<Student>>()

        val separatedIASB = separateIASB(fit.iasb)
        val separatedIB = separateIB(fit.ib)
        val separatedIBAS = separateIBAS(fit.ibas)
        val separatedIVT = separateIVT(fit.ivt)
        val separatedINN = separateINN(fit.inn)
        val separatedIST = separateIST(fit.ist)
        val separatedMOA = separateMOA(fit.moa)
        val separatedPRI = separatePRI(fit.pri)
        val separatedPRO = separatePRO(fit.pro)

        listFIT.addAll(separatedIASB)
        listFIT.addAll(separatedIB)
        listFIT.addAll(separatedIBAS)
        listFIT.addAll(separatedIVT)
        listFIT.addAll(separatedINN)
        listFIT.addAll(separatedIST)
        listFIT.addAll(separatedMOA)
        listFIT.addAll(separatedPRI)
        listFIT.addAll(separatedPRO)

        return listFIT
    }
    override fun separateIASB(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("ИАСБ_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ИАСБ_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ИАСБ_очн_плат")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateIB(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val vechPlat = list.filterForSpecialty("ИБ_веч_платн")
        val ochnBudg = list.filterForSpecialty("ИБ_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ИБ_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ИБ_очн_плат")

        return arrayListOf(vechPlat, ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateIBAS(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("ИБАС_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ИБАС_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ИБАС_очн_плат")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateIVT(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val poZaochnPlat = list.filterForSpecialty("ИиВТ(ПО)_заочн_плат")
        val poOchnBudg = list.filterForSpecialty("ИиВТ(ПО)_очн_бюдж")
        val poOchnLgot = list.filterForSpecialty("ИиВТ(ПО)_очн_льгот")
        val poOchnPlat = list.filterForSpecialty("ИиВТ(ПО)_очн_плат")
        val poOchnCelevoe = list.filterForSpecialty("ИиВТ(ПО)_очн_целевое")
        val saprOchnBudg = list.filterForSpecialty("ИиВТ(САПР)_очн_бюдж")
        val saprOchnLgot = list.filterForSpecialty("ИиВТ(САПР)_очн_льгот")
        val saprOchnPlat = list.filterForSpecialty("ИиВТ(САПР)_очн_плат")

        return arrayListOf(poZaochnPlat, poOchnBudg, poOchnLgot, poOchnPlat, poOchnCelevoe,
                saprOchnBudg, saprOchnLgot, saprOchnPlat)
    }
    override fun separateINN(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnPlat = list.filterForSpecialty("ИНН_заочн_плат")
        val ochnBudg = list.filterForSpecialty("ИНН_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ИНН_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ИНН_очн_плат")

        return arrayListOf(zaochnPlat, ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateIST(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val isitdOchnBudg = list.filterForSpecialty("ИСТ(ИСиТД)_очн_бюдж")
        val isitdOchnLgot = list.filterForSpecialty("ИСТ(ИСиТД)_очн_льгот")
        val isitdOchnPlat = list.filterForSpecialty("ИСТ(ИСиТД)_очн_плат")
        val itipkOchnBudg = list.filterForSpecialty("ИСТ(ИТиПК)_очн_бюдж")
        val itipkOchnLgot = list.filterForSpecialty("ИСТ(ИТиПК)_очн_льгот")
        val itipkOchnPlat = list.filterForSpecialty("ИСТ(ИТиПК)_очн_плат")
        val zaochnPlat = list.filterForSpecialty("ИСТ_заочн_плат")

        return arrayListOf(isitdOchnBudg, isitdOchnLgot, isitdOchnPlat, itipkOchnBudg,
                itipkOchnLgot, itipkOchnPlat, zaochnPlat)
    }
    override fun separateMOA(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("МОА_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("МОА_очн_льгот")
        val ochnPlat = list.filterForSpecialty("МОА_очн_плат")
        val ochnCelevoe = list.filterForSpecialty("МОА_очн_целевое")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
    }
    override fun separatePRI(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("ПРИ_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ПРИ_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ПРИ_очн_плат")
        val ochnCelevoe = list.filterForSpecialty("ПРИ_очн_целевое")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
    }
    override fun separatePRO(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val gdOchnBudg = list.filterForSpecialty("ПРО(ГД)_очн_бюдж")
        val gdOchnLgot = list.filterForSpecialty("ПРО(ГД)_очн_льгот")
        val gdOchnPlat = list.filterForSpecialty("ПРО(ГД)_очн_плат")
        val ivtZaochnPlat = list.filterForSpecialty("ПРО(ИВТ)_заочн_плат")
        val ekZaochnPlat = list.filterForSpecialty("ПРО(ЭК)_заочн_плат")

        return arrayListOf(gdOchnBudg, gdOchnLgot, gdOchnPlat, ivtZaochnPlat, ekZaochnPlat)
    }
    // МТФ
    override fun checkForMTF() {
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

        val mtf = MTF(mash, sim, tb, uk)
        val separatedMTF = separateMTF(mtf)
        myApplication.saveMTF(separatedMTF)
    }
    override fun separateMTF(mtf: MTF): ArrayList<ArrayList<Student>> {
        val listMTF = ArrayList<ArrayList<Student>>()

        val separatedMASH = separateMASH(mtf.mash)
        val separatedSIM = separateSIM(mtf.sim)
        val separatedTB = separateTB(mtf.tb)
        val separatedUK = separateUK(mtf.uk)

        listMTF.addAll(separatedMASH)
        listMTF.addAll(separatedSIM)
        listMTF.addAll(separatedTB)
        listMTF.addAll(separatedUK)

        return listMTF
    }
    override fun separateMASH(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val lZaochnBudg = list.filterForSpecialty("МАШ(Л)_заочн_бюдж")
        val lZaochnLgot = list.filterForSpecialty("МАШ(Л)_заочн_льгот")
        val lZaochnPlat = list.filterForSpecialty("МАШ(Л)_заочн_плат")
        val lOchnBudg = list.filterForSpecialty("МАШ(Л)_очн_бюдж")
        val lOchnLgot = list.filterForSpecialty("МАШ(Л)_очн_льгот")
        val lOchnPlat = list.filterForSpecialty("МАШ(Л)_очн_плат")
        val sZaochnBudg = list.filterForSpecialty("МАШ(С)_заочн_бюдж")
        val sZaochnLgot = list.filterForSpecialty("МАШ(С)_заочн_льгот")
        val sZaochnPlat = list.filterForSpecialty("МАШ(С)_заочн_плат")
        val sOchnBudg = list.filterForSpecialty("МАШ(С)_очн_бюдж")
        val sOchnLgot = list.filterForSpecialty("МАШ(С)_очн_льгот")
        val sOchnPlat = list.filterForSpecialty("МАШ(С)_очн_плат")
        val sOchnCelevoe = list.filterForSpecialty("МАШ(С)_очн_целевое")

        return arrayListOf(lZaochnBudg, lZaochnLgot, lZaochnPlat,lOchnBudg, lOchnLgot, lOchnPlat,
                sZaochnBudg, sZaochnLgot, sZaochnPlat, sOchnBudg, sOchnLgot, sOchnPlat, sOchnCelevoe)
    }
    override fun separateSIM(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnPlat = list.filterForSpecialty("СиМ_заочн_плат")
        val ochnBudg = list.filterForSpecialty("СиМ_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("СиМ_очн_льгот")
        val ochnPlat = list.filterForSpecialty("СиМ_очн_плат")

        return arrayListOf(zaochnPlat, ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateTB(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val btpipZaochnPlat = list.filterForSpecialty("ТБ(БТПиП)_заочн_плат")
        val btpipOchnBudg = list.filterForSpecialty("ТБ(БТПиП)_очн_бюдж")
        val btpipOchnLgot = list.filterForSpecialty("ТБ(БТПиП)_очн_льгот")
        val btpipOchnPlat = list.filterForSpecialty("ТБ(БТПиП)_очн_плат")

        return arrayListOf(btpipZaochnPlat, btpipOchnBudg, btpipOchnLgot, btpipOchnPlat)
    }
    override fun separateUK(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnPlat = list.filterForSpecialty("УК_заочн_плат")

        return arrayListOf(zaochnPlat)
    }
    // УНИТ
    override fun checkForUNIT() {
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

        val unit = UNIT(nttk, ntts, pm, psjd, ttp, ettk)
        val separatedUNIT = separateUNIT(unit)
        myApplication.saveUNIT(separatedUNIT)
    }
    override fun separateUNIT(unit: UNIT): ArrayList<ArrayList<Student>> {
        val listUNIT = ArrayList<ArrayList<Student>>()

        val separatedNTTK = separateNTTK(unit.nttk)
        val separatedNTTS = separateNTTS(unit.ntts)
        val separatedPM = separatePM(unit.pm)
        val separatedPSJD = separatePSJD(unit.psjd)
        val separatedTTP = separateTTP(unit.ttp)
        val separatedETTK = separateETTK(unit.ettk)

        listUNIT.addAll(separatedNTTK)
        listUNIT.addAll(separatedNTTS)
        listUNIT.addAll(separatedPM)
        listUNIT.addAll(separatedPSJD)
        listUNIT.addAll(separatedTTP)
        listUNIT.addAll(separatedETTK)

        return listUNIT
    }
    override fun separateNTTK(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnBudg = list.filterForSpecialty("НТТК_заочн_бюдж")
        val zaochnLgot = list.filterForSpecialty("НТТК_заочн_льгот")
        val zaochnPlat = list.filterForSpecialty("НТТК_заочн_плат")

        return arrayListOf(zaochnBudg, zaochnLgot, zaochnPlat)
    }
    override fun separateNTTS(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("НТТС_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("НТТС_очн_льгот")
        val ochnPlat = list.filterForSpecialty("НТТС_очн_плат")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separatePM(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val bmOchnBudg = list.filterForSpecialty("ПМ(БМ)_очн_бюдж")
        val bmOchnLgot = list.filterForSpecialty("ПМ(БМ)_очн_льгот")
        val bmOchnPlat = list.filterForSpecialty("ПМ(БМ)_очн_плат")
        val dpmOchnBudg = list.filterForSpecialty("ПМ(ДПМ)_очн_бюдж")
        val dpmOchnLgot = list.filterForSpecialty("ПМ(ДПМ)_очн_льгот")
        val dpmOchnPlat = list.filterForSpecialty("ПМ(ДПМ)_очн_плат")

        return arrayListOf(bmOchnBudg, bmOchnLgot, bmOchnPlat,
                dpmOchnBudg, dpmOchnLgot, dpmOchnPlat)
    }
    override fun separatePSJD(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val vOchnPlat = list.filterForSpecialty("ПСЖД(В)_очн_плат")
        val lOchnPlat = list.filterForSpecialty("ПСЖД(Л)_очн_плат")
        val zaochnPlat = list.filterForSpecialty("ПСЖД_заочн_плат")

        return arrayListOf(vOchnPlat, lOchnPlat, zaochnPlat)
    }
    override fun separateTTP(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnBudg = list.filterForSpecialty("ТТП_заочн_бюдж")
        val zaochnLgot = list.filterForSpecialty("ТТП_заочн_льгот")
        val zaochnPlat = list.filterForSpecialty("ТТП_заочн_плат")
        val ochnBudg = list.filterForSpecialty("ТТП_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ТТП_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ТТП_очн_плат")

        return arrayListOf(zaochnBudg, zaochnLgot, zaochnPlat, ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateETTK(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val aiahOchnBudg = list.filterForSpecialty("ЭТТК(АиАХ)_очн_бюдж")
        val aiahOchnLgot = list.filterForSpecialty("ЭТТК(АиАХ)_очн_льгот")
        val aiahOchnPlat = list.filterForSpecialty("ЭТТК(АиАХ)_очн_плат")
        val aiahOchnCelevoe = list.filterForSpecialty("ЭТТК(АиАХ)_очн_целевое")
        val psjdOchnBudg = list.filterForSpecialty("ЭТТК(ПСЖД)_очн_бюдж")
        val psjdOchnLgot = list.filterForSpecialty("ЭТТК(ПСЖД)_очн_льгот")
        val psjdOchnPlat = list.filterForSpecialty("ЭТТК(ПСЖД)_очн_плат")

        return arrayListOf(aiahOchnBudg, aiahOchnLgot, aiahOchnPlat, aiahOchnCelevoe,
                psjdOchnBudg, psjdOchnLgot, psjdOchnPlat)
    }
    // ФЭЭ
    override fun checkForFEE() {
        val scoreTypes = myApplication.returnScoreTypes()
        val rad = ArrayList<Student>()
        val tit = ArrayList<Student>()
        val ein = ArrayList<Student>()
        val eie = ArrayList<Student>()
        val em = ArrayList<Student>()

        fun checkForRAD(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "РАД_очн_бюдж" || list[i].specialtyFirst == "РАД_очн_льгот"
                                || list[i].specialtyFirst == "РАД_очн_плат" || list[i].specialtyFirst == "РАД_очн_целевое")
                        || (list[i].specialtySecond == "РАД_очн_бюдж" || list[i].specialtySecond == "РАД_очн_льгот"
                                || list[i].specialtySecond == "РАД_очн_плат" || list[i].specialtySecond == "РАД_очн_целевое")
                        || (list[i].specialtyThird == "РАД_очн_бюдж" || list[i].specialtyThird == "РАД_очн_льгот"
                                || list[i].specialtyThird == "РАД_очн_плат" || list[i].specialtyThird == "РАД_очн_целевое")) {
                    rad.add(list[i])
                }
            }
        }
        fun checkForTIT(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ТиТ(ИСК)_заочн_плат" || list[i].specialtyFirst == "ТиТ_очн_бюдж"
                                || list[i].specialtyFirst == "ТиТ_очн_льгот" || list[i].specialtyFirst == "ТиТ_очн_плат")
                        || (list[i].specialtySecond == "ТиТ(ИСК)_заочн_плат" || list[i].specialtySecond == "ТиТ_очн_бюдж"
                                || list[i].specialtySecond == "ТиТ_очн_льгот" || list[i].specialtySecond == "ТиТ_очн_плат")
                        || (list[i].specialtyThird == "ТиТ(ИСК)_заочн_плат" || list[i].specialtyThird == "ТиТ_очн_бюдж"
                                || list[i].specialtyThird == "ТиТ_очн_льгот" || list[i].specialtyThird == "ТиТ_очн_плат")) {
                    tit.add(list[i])
                }
            }
        }
        fun checkForEIN(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ЭиН(МТЭ)_очн_бюдж" || list[i].specialtyFirst == "ЭиН(МТЭ)_очн_льгот"
                                || list[i].specialtyFirst == "ЭиН(МТЭ)_очн_плат" || list[i].specialtyFirst == "ЭиН(ПЭ)_очн_бюдж"
                                || list[i].specialtyFirst == "ЭиН(ПЭ)_очн_льгот" || list[i].specialtyFirst == "ЭиН(ПЭ)_очн_плат")
                        || (list[i].specialtySecond == "ЭиН(МТЭ)_очн_бюдж" || list[i].specialtySecond == "ЭиН(МТЭ)_очн_льгот"
                                || list[i].specialtySecond == "ЭиН(МТЭ)_очн_плат" || list[i].specialtySecond == "ЭиН(ПЭ)_очн_бюдж"
                                || list[i].specialtySecond == "ЭиН(ПЭ)_очн_льгот" || list[i].specialtySecond == "ЭиН(ПЭ)_очн_плат")
                        || (list[i].specialtyThird == "ЭиН(МТЭ)_очн_бюдж" || list[i].specialtyThird == "ЭиН(МТЭ)_очн_льгот"
                                || list[i].specialtyThird == "ЭиН(МТЭ)_очн_плат" || list[i].specialtyThird == "ЭиН(ПЭ)_очн_бюдж"
                                || list[i].specialtyThird == "ЭиН(ПЭ)_очн_льгот" || list[i].specialtyThird == "ЭиН(ПЭ)_очн_плат")) {
                    ein.add(list[i])
                }
            }
        }
        fun checkForEIE(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ЭиЭ_заочн_плат" || list[i].specialtyFirst == "ЭиЭ_очн_бюдж"
                                || list[i].specialtyFirst == "ЭиЭ_очн_льгот" || list[i].specialtyFirst == "ЭиЭ_очн_плат"
                                || list[i].specialtyFirst == "ЭиЭ_очн_целевое") || (list[i].specialtySecond == "ЭиЭ_заочн_плат"
                                || list[i].specialtySecond == "ЭиЭ_очн_бюдж" || list[i].specialtySecond == "ЭиЭ_очн_льгот"
                                || list[i].specialtySecond == "ЭиЭ_очн_плат" || list[i].specialtySecond == "ЭиЭ_очн_целевое")
                        || (list[i].specialtyThird == "ЭиЭ_заочн_плат" || list[i].specialtyThird == "ЭиЭ_очн_бюдж"
                                || list[i].specialtyThird == "ЭиЭ_очн_льгот" || list[i].specialtyThird == "ЭиЭ_очн_плат"
                                || list[i].specialtyThird == "ЭиЭ_очн_целевое")) {
                    eie.add(list[i])
                }
            }
        }
        fun checkForEM(list: ArrayList<Student>) {
            for (i in 0 until list.size) {
                if ((list[i].specialtyFirst == "ЭМ(ДВС)_заочн_бюдж" || list[i].specialtyFirst == "ЭМ(ДВС)_заочн_льгот"
                                || list[i].specialtyFirst == "ЭМ(ДВС)_заочн_плат" || list[i].specialtyFirst == "ЭМ(ДВС)_очн_бюдж"
                                || list[i].specialtyFirst == "ЭМ(ДВС)_очн_льгот" || list[i].specialtyFirst == "ЭМ(ДВС)_очн_плат"
                                || list[i].specialtyFirst == "ЭМ(Т)_очн_бюдж" || list[i].specialtyFirst == "ЭМ(Т)_очн_льгот"
                                || list[i].specialtyFirst == "ЭМ(Т)_очн_плат" || list[i].specialtyFirst == "ЭМ(Т)_очн_целевое"
                                || list[i].specialtyFirst == "ЭМ(ЭМКС)_заочн_плат") || (list[i].specialtySecond == "ЭМ(ДВС)_заочн_бюдж"
                                || list[i].specialtySecond == "ЭМ(ДВС)_заочн_льгот" || list[i].specialtySecond == "ЭМ(ДВС)_заочн_плат"
                                || list[i].specialtySecond == "ЭМ(ДВС)_очн_бюдж" || list[i].specialtySecond == "ЭМ(ДВС)_очн_льгот"
                                || list[i].specialtySecond == "ЭМ(ДВС)_очн_плат" || list[i].specialtySecond == "ЭМ(Т)_очн_бюдж"
                                || list[i].specialtySecond == "ЭМ(Т)_очн_льгот" || list[i].specialtySecond == "ЭМ(Т)_очн_плат"
                                || list[i].specialtySecond == "ЭМ(Т)_очн_целевое" || list[i].specialtySecond == "ЭМ(ЭМКС)_заочн_плат")
                        || (list[i].specialtyThird == "ЭМ(ДВС)_заочн_бюдж" || list[i].specialtyThird == "ЭМ(ДВС)_заочн_льгот"
                                || list[i].specialtyThird == "ЭМ(ДВС)_заочн_плат" || list[i].specialtyThird == "ЭМ(ДВС)_очн_бюдж"
                                || list[i].specialtyThird == "ЭМ(ДВС)_очн_льгот" || list[i].specialtyThird == "ЭМ(ДВС)_очн_плат"
                                || list[i].specialtyThird == "ЭМ(Т)_очн_бюдж" || list[i].specialtyThird == "ЭМ(Т)_очн_льгот"
                                || list[i].specialtyThird == "ЭМ(Т)_очн_плат" || list[i].specialtyThird == "ЭМ(Т)_очн_целевое"
                                || list[i].specialtyThird == "ЭМ(ЭМКС)_заочн_плат")) {
                    em.add(list[i])
                }
            }
        }

        scoreTypes?.physicsStudents?.let { checkForRAD(it) }
        scoreTypes?.computerScienceStudents?.let { checkForRAD(it) }
        scoreTypes?.socialScienceStudents?.let { checkForRAD(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForRAD(it) }

        scoreTypes?.physicsStudents?.let { checkForTIT(it) }
        scoreTypes?.computerScienceStudents?.let { checkForTIT(it) }
        scoreTypes?.socialScienceStudents?.let { checkForTIT(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForTIT(it) }

        scoreTypes?.physicsStudents?.let { checkForEIN(it) }
        scoreTypes?.computerScienceStudents?.let { checkForEIN(it) }
        scoreTypes?.socialScienceStudents?.let { checkForEIN(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForEIN(it) }

        scoreTypes?.physicsStudents?.let { checkForEIE(it) }
        scoreTypes?.computerScienceStudents?.let { checkForEIE(it) }
        scoreTypes?.socialScienceStudents?.let { checkForEIE(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForEIE(it) }

        scoreTypes?.physicsStudents?.let { checkForEM(it) }
        scoreTypes?.computerScienceStudents?.let { checkForEM(it) }
        scoreTypes?.socialScienceStudents?.let { checkForEM(it) }
        scoreTypes?.partAndAllDataStudents?.let { checkForEM(it) }

        val fee = FEE(rad, tit, ein, eie, em)
        val separatedFEE = separateFEE(fee)
        myApplication.saveFEE(separatedFEE)
    }
    override fun separateFEE(fee: FEE): ArrayList<ArrayList<Student>> {
        val listFEE = ArrayList<ArrayList<Student>>()

        val separatedRAD = separateRAD(fee.rad)
        val separatedTIT = separateTIT(fee.tit)
        val separatedEIN = separateEIN(fee.ein)
        val separatedEIE = separateEIE(fee.eie)
        val separatedEM = separateEM(fee.em)

        listFEE.addAll(separatedRAD)
        listFEE.addAll(separatedTIT)
        listFEE.addAll(separatedEIN)
        listFEE.addAll(separatedEIE)
        listFEE.addAll(separatedEM)

        return listFEE
    }
    override fun separateRAD(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val ochnBudg = list.filterForSpecialty("РАД_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("РАД_очн_льгот")
        val ochnPlat = list.filterForSpecialty("РАД_очн_плат")
        val ochnCelevoe = list.filterForSpecialty("РАД_очн_целевое")

        return arrayListOf(ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
    }
    override fun separateTIT(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val iskZaochnPlat = list.filterForSpecialty("ТиТ(ИСК)_заочн_плат")
        val ochnBudg = list.filterForSpecialty("ТиТ_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ТиТ_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ТиТ_очн_плат")

        return arrayListOf(iskZaochnPlat, ochnBudg, ochnLgot, ochnPlat)
    }
    override fun separateEIN(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val mteOchnBudg = list.filterForSpecialty("ЭиН(МТЭ)_очн_бюдж")
        val mteOchnLgot = list.filterForSpecialty("ЭиН(МТЭ)_очн_льгот")
        val mteOchnPlat = list.filterForSpecialty("ЭиН(МТЭ)_очн_плат")
        val peOchnBudg = list.filterForSpecialty("ЭиН(ПЭ)_очн_бюдж")
        val peOchnLgot = list.filterForSpecialty("ЭиН(ПЭ)_очн_льгот")
        val peOchnPlat = list.filterForSpecialty("ЭиН(ПЭ)_очн_плат")

        return arrayListOf(mteOchnBudg, mteOchnLgot, mteOchnPlat, peOchnBudg, peOchnLgot, peOchnPlat)
    }
    override fun separateEIE(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val zaochnPlat = list.filterForSpecialty("ЭиЭ_заочн_плат")
        val ochnBudg = list.filterForSpecialty("ЭиЭ_очн_бюдж")
        val ochnLgot = list.filterForSpecialty("ЭиЭ_очн_льгот")
        val ochnPlat = list.filterForSpecialty("ЭиЭ_очн_плат")
        val ochnCelevoe = list.filterForSpecialty("ЭиЭ_очн_целевое")

        return arrayListOf(zaochnPlat, ochnBudg, ochnLgot, ochnPlat, ochnCelevoe)
    }
    override fun separateEM(list: ArrayList<Student>): ArrayList<ArrayList<Student>> {
        val dvsZaochnBudg = list.filterForSpecialty("ЭМ(ДВС)_заочн_бюдж")
        val dvsZaochnLgot = list.filterForSpecialty("ЭМ(ДВС)_заочн_льгот")
        val dvsZaochnPlat = list.filterForSpecialty("ЭМ(ДВС)_заочн_плат")
        val dvsOchnBudg = list.filterForSpecialty("ЭМ(ДВС)_очн_бюдж")
        val dvsOchnLgot = list.filterForSpecialty("ЭМ(ДВС)_очн_льгот")
        val dvsOchnPlat = list.filterForSpecialty("ЭМ(ДВС)_очн_плат")
        val tOchnBudg = list.filterForSpecialty("ЭМ(Т)_очн_бюдж")
        val tOchnLgot = list.filterForSpecialty("ЭМ(Т)_очн_льгот")
        val tOchnPlat = list.filterForSpecialty("ЭМ(Т)_очн_плат")
        val tOchnCelevoe = list.filterForSpecialty("ЭМ(Т)_очн_целевое")
        val emksZaochnPlat = list.filterForSpecialty("ЭМ(ЭМКС)_заочн_плат")

        return arrayListOf(dvsZaochnBudg, dvsZaochnLgot, dvsZaochnPlat, dvsOchnBudg, dvsOchnLgot,
                dvsOchnPlat, tOchnBudg, tOchnLgot, tOchnPlat, tOchnCelevoe, emksZaochnPlat)
    }

    // Четвертый этап

    override fun checkSpecialtiesForMinimalScore(context: Context) {
        val listUNTI = checkUNTIForMinimalScore(context, 0)
        val listFEU = checkFEUForMinimalScore(context, 1)
        val listFIT = checkFITForMinimalScore(context, 2)
        val listMTF = checkMTFForMinimalScore(context, 3)
        val listUNIT = checkUNITForMinimalScore(context, 4)
        val listFEE = checkFEEForMinimalScore(context, 5)

        val faculties = listUNTI?.let { listFEU?.let { it1 ->
            listFIT?.let { it2 -> listMTF?.let { it3 ->
                listUNIT?.let { it4 ->
                    listFEE?.let { it5 -> Faculties(it, it1, it2, it3, it4, it5) } }
            } } } }
        faculties?.let { myApplication.saveFaculties(it) }
    }
    override fun checkUNTIForMinimalScore(context: Context, position: Int): ArrayList<Specialty>? {
        val list = getSpecialtiesListByPosition(position)
        val listUNTI = returnUNTI()

        list?.let {
            for (i in 0 until list.size) {
                listUNTI?.let {
                    list[i].amountOfStatements = it[i].size

                    when(list[i].profileTerm) {
                        "Физика" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.physics + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndPhysics)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.physics + r.additionalScore}
                        }
                        "Обществознание" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.socialScience + r.additionalScore }

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndSocialScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.socialScience + r.additionalScore}
                        }
                        "Информатика и ИКТ" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.computerScience + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndComputerScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore = r.maths + r.computerScience + r.additionalScore}
                        }
                        else -> return null
                    }
                }
            }
        }

        return list
    }
    override fun checkFEUForMinimalScore(context: Context, position: Int): ArrayList<Specialty>? {
        val list = getSpecialtiesListByPosition(position)
        val listFEU = returnFEU()

        list?.let {
            for (i in 0 until list.size) {
                listFEU?.let {
                    list[i].amountOfStatements = it[i].size

                    when(list[i].profileTerm) {
                        "Физика" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.physics + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndPhysics)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.physics + r.additionalScore}
                        }
                        "Обществознание" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.socialScience + r.additionalScore }

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndSocialScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.socialScience + r.additionalScore}
                        }
                        "Информатика и ИКТ" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.computerScience + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndComputerScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore = r.maths + r.computerScience + r.additionalScore}
                        }
                        else -> return null
                    }
                }
            }
        }
        return list
    }
    override fun checkFITForMinimalScore(context: Context, position: Int): ArrayList<Specialty>? {
        val list = getSpecialtiesListByPosition(position)
        val listFIT = returnFIT()

        list?.let {
            for (i in 0 until list.size) {
                listFIT?.let {
                    list[i].amountOfStatements = it[i].size

                    when(list[i].profileTerm) {
                        "Физика" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.physics + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndPhysics)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.physics + r.additionalScore}
                        }
                        "Обществознание" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.socialScience + r.additionalScore }

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndSocialScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.socialScience + r.additionalScore}
                        }
                        "Информатика и ИКТ" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.computerScience + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndComputerScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore = r.maths + r.computerScience + r.additionalScore}
                        }
                        else -> return null
                    }
                }
            }
        }
        return list
    }
    override fun checkMTFForMinimalScore(context: Context, position: Int): ArrayList<Specialty>? {
        val list = getSpecialtiesListByPosition(position)
        val listMTF = returnMTF()

        list?.let {
            for (i in 0 until list.size) {
                listMTF?.let {
                    list[i].amountOfStatements = it[i].size

                    when(list[i].profileTerm) {
                        "Физика" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.physics + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndPhysics)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.physics + r.additionalScore}
                        }
                        "Обществознание" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.socialScience + r.additionalScore }

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndSocialScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.socialScience + r.additionalScore}
                        }
                        "Информатика и ИКТ" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.computerScience + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndComputerScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore = r.maths + r.computerScience + r.additionalScore}
                        }
                        else -> return null
                    }
                }
            }
        }
        return list
    }
    override fun checkUNITForMinimalScore(context: Context, position: Int): ArrayList<Specialty>? {
        val list = getSpecialtiesListByPosition(position)
        val listUNIT = returnUNIT()

        list?.let {
            for (i in 0 until list.size) {
                listUNIT?.let {
                    list[i].amountOfStatements = it[i].size

                    when(list[i].profileTerm) {
                        "Физика" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.physics + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndPhysics)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.physics + r.additionalScore}
                        }
                        "Обществознание" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.socialScience + r.additionalScore }

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndSocialScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.socialScience + r.additionalScore}
                        }
                        "Информатика и ИКТ" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.computerScience + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndComputerScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore = r.maths + r.computerScience + r.additionalScore}
                        }
                        else -> return null
                    }
                }
            }
        }
        return list
    }
    override fun checkFEEForMinimalScore(context: Context, position: Int): ArrayList<Specialty>? {
        val list = getSpecialtiesListByPosition(position)
        val listFEE = returnFEE()

        list?.let {
            for (i in 0 until list.size) {
                listFEE?.let {
                    list[i].amountOfStatements = it[i].size

                    when(list[i].profileTerm) {
                        "Физика" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.physics + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndPhysics)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.physics + r.additionalScore}
                        }
                        "Обществознание" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.socialScience + r.additionalScore }

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndSocialScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore= r.maths + r.socialScience + r.additionalScore}
                        }
                        "Информатика и ИКТ" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.computerScience + r.additionalScore}

                            list[i].scoreTitle = context.getString(R.string.facultyMathsAndComputerScience)

                            minimalScore?.let {  r
                                -> list[i].minimalScore = r.maths + r.computerScience + r.additionalScore}
                        }
                        else -> return null
                    }
                }
            }
        }
        return list
    }
    override fun getSpecialtiesListByPosition(pos: Int)
            : ArrayList<Specialty>? {
        val faculties = myApplication.returnFaculties()
        return when (pos) {
            //УНТИ
            0 -> faculties?.listUNTI
            //ФЭУ
            1 -> faculties?.listFEU
            //ФИТ
            2 -> faculties?.listFIT
            //МТФ
            3 -> faculties?.listMTF
            //УНИТ
            4 -> faculties?.listUNIT
            //ФЭЭ
            5 -> faculties?.listFEE
            else -> null
        }
    }


    override fun returnFacultyList(): ArrayList<Faculty>?
            = myApplication.returnFacultyList()
    override fun returnFaculties(): Faculties?
            = myApplication.returnFaculties()
    override fun returnFacultyBundle(context: Context, position: Int, titleId: Int): Bundle {
        val bundle = Bundle()
        val title = context.getString(titleId)

        bundle.putString("title", title)
        bundle.putInt("pos", position)
        return bundle
    }
    override fun returnUNTI(): ArrayList<ArrayList<Student>>?
            = myApplication.returnUNTI()
    override fun returnFEU(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFEU()
    override fun returnFIT(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFIT()
    override fun returnMTF(): ArrayList<ArrayList<Student>>?
            = myApplication.returnMTF()
    override fun returnUNIT(): ArrayList<ArrayList<Student>>?
            = myApplication.returnUNIT()
    override fun returnFEE(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFEE()
}