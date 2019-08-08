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

class WorkWithSpecialtiesPresenter(private var pv: WorkWithSpecialtiesMVP.View)
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

        val arrayOfATPSpecialties = arrayOf(
                "АТП_заочн_бюдж", "АТП_заочн_льгот",
                "АТП_заочн_плат", "АТП_очн_бюдж",
                "АТП_очн_льгот", "АТП_очн_плат",
                "АТП_очн_целевое"
        )
        val arrayOfKTOSpecialties = arrayOf(
                "КТО(АТиКМ)_очн_бюдж", "КТО(АТиКМ)_очн_льгот",
                "КТО(АТиКМ)_очн_плат", "КТО(ТМ)_очн_бюдж",
                "КТО(ТМ)_очн_льгот", "КТО(ТМ)_очн_плат",
                "КТО(ТМ)_очн_целевое", "КТО_веч_бюдж",
                "КТО_веч_льгот", "КТО_веч_плат"
        )
        val arrayOfMASHSpecialties = arrayOf(
                "МАШ(ТМ)_заочн_бюдж", "МАШ(ТМ)_заочн_льгот",
                "МАШ(ТМ)_заочн_плат"
        )
        val arrayOfMITMSpecialties = arrayOf(
                "МиТМ_очн_бюдж", "МиТМ_очн_льгот",
                "МиТМ_очн_плат"
        )
        val arrayOfMHTSpecialties = arrayOf(
                "МХТ_очн_бюдж", "МХТ_очн_льгот",
                "МХТ_очн_плат"
        )
        val arrayOfPTMKSpecialties = arrayOf(
                "ПТМК_заочн_плат", "ПТМК_очн_бюдж",
                "ПТМК_очн_льгот", "ПТМК_очн_плат"
        )
        val arrayOfTMOSpecialties = arrayOf(
                "ТМО(ОИиПМ)_заочн_бюдж", "ТМО(ОИиПМ)_заочн_льгот",
                "ТМО(ОИиПМ)_заочн_плат", "ТМО_очн_бюдж",
                "ТМО_очн_льгот", "ТМО_очн_плат",
                "ТМО_очн_целевое"
        )
        val arrayOfUTSSpecialties = arrayOf(
                "УТС_очн_бюдж", "УТС_очн_льгот",
                "УТС_очн_плат", "УТС_очн_целевое"
        )

        val separatedATP = separateSpecialties(unti.atp, arrayOfATPSpecialties)
        val separatedKTO = separateSpecialties(unti.kto, arrayOfKTOSpecialties)
        val separatedMASH = separateSpecialties(unti.mash, arrayOfMASHSpecialties)
        val separatedMiTM = separateSpecialties(unti.mitm, arrayOfMITMSpecialties)
        val separatedMHT = separateSpecialties(unti.mht, arrayOfMHTSpecialties)
        val separatedPTMK = separateSpecialties(unti.ptmk, arrayOfPTMKSpecialties)
        val separatedTMO = separateSpecialties(unti.tmo, arrayOfTMOSpecialties)
        val separatedUTS = separateSpecialties(unti.uts, arrayOfUTSSpecialties)

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

        val arrayOfBISpecialties = arrayOf(
                "БИ_заочн_плат", "БИ_очн_плат"
        )
        val arrayOfPISpecialties = arrayOf(
                "ПИ(КИС)_очн_бюдж", "ПИ(КИС)_очн_льгот",
                "ПИ(КИС)_очн_плат", "ПИ(ЦЭ)_очн_бюдж",
                "ПИ(ЦЭ)_очн_льгот", "ПИ(ЦЭ)_очн_плат"
        )
        val arrayOfSCSpecialties = arrayOf(
                "СЦ_заочн_плат", "СЦ_очн_плат"
        )
        val arrayOfTDSpecialties = arrayOf(
                "ТД_заочн_плат", "ТД_очн_плат"
        )
        val arrayOfEBSpecialties = arrayOf(
                "ЭБ_заоч_плат", "ЭБ_очн_плат"
        )
        val arrayOfEKSpecialties = arrayOf(
                "ЭК(БУА)_заоч_плат", "ЭК(БУА)_очн_плат",
                "ЭК(ЛОГ)_очн_плат", "ЭК(ОЦ)_очн_плат",
                "ЭК(Ф)_заоч_плат", "ЭК(Ф)_очн_плат",
                "ЭК(ЭПО)_очн_плат"
        )

        val separatedBI = separateSpecialties(feu.bi, arrayOfBISpecialties)
        val separatedPI = separateSpecialties(feu.pi, arrayOfPISpecialties)
        val separatedSC = separateSpecialties(feu.sc, arrayOfSCSpecialties)
        val separatedTD = separateSpecialties(feu.td, arrayOfTDSpecialties)
        val separatedEB = separateSpecialties(feu.eb, arrayOfEBSpecialties)
        val separatedEK = separateSpecialties(feu.ek, arrayOfEKSpecialties)

        listFEU.addAll(separatedBI)
        listFEU.addAll(separatedPI)
        listFEU.addAll(separatedSC)
        listFEU.addAll(separatedTD)
        listFEU.addAll(separatedEB)
        listFEU.addAll(separatedEK)

        return listFEU
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

        val arrayOfIASBSpecialties = arrayOf(
                "ИАСБ_очн_бюдж", "ИАСБ_очн_льгот", "ИАСБ_очн_плат"
        )
        val arrayOfIBSpecialties = arrayOf(
                "ИБ_веч_платн", "ИБ_очн_бюдж",
                "ИБ_очн_льгот", "ИБ_очн_плат"
        )
        val arrayOfIBASSpecialties = arrayOf(
                "ИБАС_очн_бюдж", "ИБАС_очн_льгот", "ИБАС_очн_плат"
        )
        val arrayOfIVTSpecialties = arrayOf(
                "ИиВТ(ПО)_заочн_плат", "ИиВТ(ПО)_очн_бюдж",
                "ИиВТ(ПО)_очн_льгот", "ИиВТ(ПО)_очн_плат",
                "ИиВТ(ПО)_очн_целевое", "ИиВТ(САПР)_очн_бюдж",
                "ИиВТ(САПР)_очн_льгот", "ИиВТ(САПР)_очн_плат"
        )
        val arrayOfINNSpecialties = arrayOf(
                "ИНН_заочн_плат", "ИНН_очн_бюдж",
                "ИНН_очн_льгот", "ИНН_очн_плат"
        )
        val arrayOfISTSpecialties = arrayOf(
                "ИСТ(ИСиТД)_очн_бюдж", "ИСТ(ИСиТД)_очн_льгот",
                "ИСТ(ИСиТД)_очн_плат", "ИСТ(ИТиПК)_очн_бюдж",
                "ИСТ(ИТиПК)_очн_льгот", "ИСТ(ИТиПК)_очн_плат",
                "ИСТ_заочн_плат"
        )
        val arrayOfMOASpecialties = arrayOf(
                "МОА_очн_бюдж", "МОА_очн_льгот",
                "МОА_очн_плат", "МОА_очн_целевое"
        )
        val arrayOfPRISpecialties = arrayOf(
                "ПРИ_очн_бюдж", "ПРИ_очн_льгот",
                "ПРИ_очн_плат", "ПРИ_очн_целевое"
        )
        val arrayOfPROSpecialties = arrayOf(
                "ПРО(ГД)_очн_бюдж", "ПРО(ГД)_очн_льгот",
                "ПРО(ГД)_очн_плат", "ПРО(ИВТ)_заочн_плат",
                "ПРО(ЭК)_заочн_плат"
        )

        val separatedIASB = separateSpecialties(fit.iasb, arrayOfIASBSpecialties)
        val separatedIB = separateSpecialties(fit.ib, arrayOfIBSpecialties)
        val separatedIBAS = separateSpecialties(fit.ibas, arrayOfIBASSpecialties)
        val separatedIVT = separateSpecialties(fit.ivt, arrayOfIVTSpecialties)
        val separatedINN = separateSpecialties(fit.inn, arrayOfINNSpecialties)
        val separatedIST = separateSpecialties(fit.ist, arrayOfISTSpecialties)
        val separatedMOA = separateSpecialties(fit.moa, arrayOfMOASpecialties)
        val separatedPRI = separateSpecialties(fit.pri, arrayOfPRISpecialties)
        val separatedPRO = separateSpecialties(fit.pro, arrayOfPROSpecialties)

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

        val arrayOfMASHSpecialties = arrayOf(
                "МАШ(Л)_заочн_бюдж", "МАШ(Л)_заочн_льгот", "МАШ(Л)_заочн_плат",
                "МАШ(Л)_очн_бюдж", "МАШ(Л)_очн_льгот", "МАШ(Л)_очн_плат",
                "МАШ(С)_заочн_бюдж", "МАШ(С)_заочн_льгот", "МАШ(С)_заочн_плат",
                "МАШ(С)_очн_бюдж", "МАШ(С)_очн_льгот", "МАШ(С)_очн_плат", "МАШ(С)_очн_целевое"
        )
        val arrayOfSIMSpecialties = arrayOf(
                "СиМ_заочн_плат", "СиМ_очн_бюдж",
                "СиМ_очн_льгот", "СиМ_очн_плат"
        )
        val arrayOfTBSpecialties = arrayOf(
                "ТБ(БТПиП)_заочн_плат", "ТБ(БТПиП)_очн_бюдж",
                "ТБ(БТПиП)_очн_льгот", "ТБ(БТПиП)_очн_плат"
        )
        val arrayOfUKSpecialties = arrayOf(
                "УК_заочн_плат"
        )

        val separatedMASH = separateSpecialties(mtf.mash, arrayOfMASHSpecialties)
        val separatedSIM = separateSpecialties(mtf.sim, arrayOfSIMSpecialties)
        val separatedTB = separateSpecialties(mtf.tb, arrayOfTBSpecialties)
        val separatedUK = separateSpecialties(mtf.uk, arrayOfUKSpecialties)

        listMTF.addAll(separatedMASH)
        listMTF.addAll(separatedSIM)
        listMTF.addAll(separatedTB)
        listMTF.addAll(separatedUK)

        return listMTF
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

        val arrayOfNTTKSpecialties = arrayOf(
                "НТТК_заочн_бюдж", "НТТК_заочн_льгот", "НТТК_заочн_плат"
        )
        val arrayOfNTTSSpecialties = arrayOf(
                "НТТС_очн_бюдж", "НТТС_очн_льгот", "НТТС_очн_плат"
        )
        val arrayOfPMSpecialties = arrayOf(
                "ПМ(БМ)_очн_бюдж", "ПМ(БМ)_очн_льгот", "ПМ(БМ)_очн_плат",
                "ПМ(ДПМ)_очн_бюдж", "ПМ(ДПМ)_очн_льгот", "ПМ(ДПМ)_очн_плат"
        )
        val arrayOfPSJDSpecialties = arrayOf(
                "ПСЖД(В)_очн_плат", "ПСЖД(Л)_очн_плат", "ПСЖД_заочн_плат"
        )
        val arrayOfTTPSpecialties = arrayOf(
                "ТТП_заочн_бюдж", "ТТП_заочн_льгот", "ТТП_заочн_плат",
                "ТТП_очн_бюдж", "ТТП_очн_льгот", "ТТП_очн_плат"
        )
        val arrayOfETTKSpecialties = arrayOf(
                "ЭТТК(АиАХ)_очн_бюдж", "ЭТТК(АиАХ)_очн_льгот", "ЭТТК(АиАХ)_очн_плат",
                "ЭТТК(АиАХ)_очн_целевое", "ЭТТК(ПСЖД)_очн_бюдж", "ЭТТК(ПСЖД)_очн_льгот",
                "ЭТТК(ПСЖД)_очн_плат"
        )

        val separatedNTTK = separateSpecialties(unit.nttk, arrayOfNTTKSpecialties)
        val separatedNTTS = separateSpecialties(unit.ntts, arrayOfNTTSSpecialties)
        val separatedPM = separateSpecialties(unit.pm, arrayOfPMSpecialties)
        val separatedPSJD = separateSpecialties(unit.psjd, arrayOfPSJDSpecialties)
        val separatedTTP = separateSpecialties(unit.ttp, arrayOfTTPSpecialties)
        val separatedETTK = separateSpecialties(unit.ettk, arrayOfETTKSpecialties)

        listUNIT.addAll(separatedNTTK)
        listUNIT.addAll(separatedNTTS)
        listUNIT.addAll(separatedPM)
        listUNIT.addAll(separatedPSJD)
        listUNIT.addAll(separatedTTP)
        listUNIT.addAll(separatedETTK)
        return listUNIT
    }
    // ФЭЭ
    override fun checkForFEE() {
        val scoreTypes = myApplication.returnScoreTypes()
        val rad = ArrayList<Student>()
        val tit = ArrayList<Student>()
        val ein = ArrayList<Student>()
        val eie = ArrayList<Student>()
        val em = ArrayList<Student>()

        val arrayOfRADSpecialties = arrayOf(
                "РАД_очн_бюдж", "РАД_очн_льгот", "РАД_очн_плат", "РАД_очн_целевое")
        val arrayOfTITSpecialties = arrayOf(
                "ТиТ(ИСК)_заочн_плат", "ТиТ_очн_бюдж", "ТиТ_очн_льгот", "ТиТ_очн_плат")
        val arrayOfEINSpecialties = arrayOf(
                "ЭиН(МТЭ)_очн_бюдж", "ЭиН(МТЭ)_очн_льгот", "ЭиН(МТЭ)_очн_плат",
                "ЭиН(ПЭ)_очн_бюдж", "ЭиН(ПЭ)_очн_льгот", "ЭиН(ПЭ)_очн_плат")
        val arrayOfEIESpecialties = arrayOf(
                "ЭиЭ_заочн_плат", "ЭиЭ_очн_бюдж", "ЭиЭ_очн_льгот",
                "ЭиЭ_очн_плат", "ЭиЭ_очн_целевое")
        val arrayOfEMSpecialties = arrayOf(
                "ЭМ(ДВС)_заочн_бюдж", "ЭМ(ДВС)_заочн_льгот", "ЭМ(ДВС)_заочн_плат",
                "ЭМ(ДВС)_очн_бюдж", "ЭМ(ДВС)_очн_льгот", "ЭМ(ДВС)_очн_плат",
                "ЭМ(Т)_очн_бюдж", "ЭМ(Т)_очн_льгот", "ЭМ(Т)_очн_плат",
                "ЭМ(Т)_очн_целевое", "ЭМ(ЭМКС)_заочн_плат")

        scoreTypes?.let {
            // RAD
            rad.addAll(checkForSpecialties(scoreTypes.physicsStudents, arrayOfRADSpecialties))
            rad.addAll(checkForSpecialties(scoreTypes.computerScienceStudents, arrayOfRADSpecialties))
            rad.addAll(checkForSpecialties(scoreTypes.socialScienceStudents, arrayOfRADSpecialties))
            rad.addAll(checkForSpecialties(scoreTypes.partAndAllDataStudents, arrayOfRADSpecialties))
            // TIT
            tit.addAll(checkForSpecialties(scoreTypes.physicsStudents, arrayOfTITSpecialties))
            tit.addAll(checkForSpecialties(scoreTypes.computerScienceStudents, arrayOfTITSpecialties))
            tit.addAll(checkForSpecialties(scoreTypes.socialScienceStudents, arrayOfTITSpecialties))
            tit.addAll(checkForSpecialties(scoreTypes.partAndAllDataStudents, arrayOfTITSpecialties))
            // EIN
            ein.addAll(checkForSpecialties(scoreTypes.physicsStudents, arrayOfEINSpecialties))
            ein.addAll(checkForSpecialties(scoreTypes.computerScienceStudents, arrayOfEINSpecialties))
            ein.addAll(checkForSpecialties(scoreTypes.socialScienceStudents, arrayOfEINSpecialties))
            ein.addAll(checkForSpecialties(scoreTypes.partAndAllDataStudents, arrayOfEINSpecialties))
            // EIE
            eie.addAll(checkForSpecialties(scoreTypes.physicsStudents, arrayOfEIESpecialties))
            eie.addAll(checkForSpecialties(scoreTypes.computerScienceStudents, arrayOfEIESpecialties))
            eie.addAll(checkForSpecialties(scoreTypes.socialScienceStudents, arrayOfEIESpecialties))
            eie.addAll(checkForSpecialties(scoreTypes.partAndAllDataStudents, arrayOfEIESpecialties))
            // EM
            em.addAll(checkForSpecialties(scoreTypes.physicsStudents, arrayOfEMSpecialties))
            em.addAll(checkForSpecialties(scoreTypes.computerScienceStudents, arrayOfEMSpecialties))
            em.addAll(checkForSpecialties(scoreTypes.socialScienceStudents, arrayOfEMSpecialties))
            em.addAll(checkForSpecialties(scoreTypes.partAndAllDataStudents, arrayOfEMSpecialties))
        }
        val fee = FEE(rad, tit, ein, eie, em)
        val separatedFEE = separateFEE(fee)
        myApplication.saveFEE(separatedFEE)
    }
    /*fun checkForRAD(list: ArrayList<Student>): ArrayList<Student> {
        val rad = ArrayList<Student>()
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
        return rad
    }
    fun checkForTIT(list: ArrayList<Student>): ArrayList<Student> {
        val tit = ArrayList<Student>()
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
        return tit
    }
    fun checkForEIN(list: ArrayList<Student>): ArrayList<Student> {
        val ein = ArrayList<Student>()
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
        return ein
    }
    fun checkForEIE(list: ArrayList<Student>): ArrayList<Student> {
        val eie = ArrayList<Student>()
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
        return eie
    }
    fun checkForEM(list: ArrayList<Student>): ArrayList<Student> {
        val em = ArrayList<Student>()
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
        return em
    }*/
    override fun separateFEE(fee: FEE): ArrayList<ArrayList<Student>> {
        val listFEE = ArrayList<ArrayList<Student>>()

        val arrayOfRADSpecialties = arrayOf(
                "РАД_очн_бюдж", "РАД_очн_льгот", "РАД_очн_плат", "РАД_очн_целевое")
        val arrayOfTITSpecialties = arrayOf(
                "ТиТ(ИСК)_заочн_плат", "ТиТ_очн_бюдж", "ТиТ_очн_льгот", "ТиТ_очн_плат")
        val arrayOfEINSpecialties = arrayOf(
                "ЭиН(МТЭ)_очн_бюдж", "ЭиН(МТЭ)_очн_льгот", "ЭиН(МТЭ)_очн_плат",
                "ЭиН(ПЭ)_очн_бюдж", "ЭиН(ПЭ)_очн_льгот", "ЭиН(ПЭ)_очн_плат")
        val arrayOfEIESpecialties = arrayOf(
                "ЭиЭ_заочн_плат", "ЭиЭ_очн_бюдж", "ЭиЭ_очн_льгот",
                "ЭиЭ_очн_плат", "ЭиЭ_очн_целевое")
        val arrayOfEMSpecialties = arrayOf(
                "ЭМ(ДВС)_заочн_бюдж", "ЭМ(ДВС)_заочн_льгот", "ЭМ(ДВС)_заочн_плат",
                "ЭМ(ДВС)_очн_бюдж", "ЭМ(ДВС)_очн_льгот", "ЭМ(ДВС)_очн_плат",
                "ЭМ(Т)_очн_бюдж", "ЭМ(Т)_очн_льгот", "ЭМ(Т)_очн_плат",
                "ЭМ(Т)_очн_целевое", "ЭМ(ЭМКС)_заочн_плат")

        val separatedRAD = separateSpecialties(fee.rad, arrayOfRADSpecialties)
        val separatedTIT = separateSpecialties(fee.tit, arrayOfTITSpecialties)
        val separatedEIN = separateSpecialties(fee.ein, arrayOfEINSpecialties)
        val separatedEIE = separateSpecialties(fee.eie, arrayOfEIESpecialties)
        val separatedEM = separateSpecialties(fee.em, arrayOfEMSpecialties)

        listFEE.addAll(separatedRAD)
        listFEE.addAll(separatedTIT)
        listFEE.addAll(separatedEIN)
        listFEE.addAll(separatedEIE)
        listFEE.addAll(separatedEM)
        return listFEE
    }
    override fun checkForSpecialties(list: ArrayList<Student>, arrayOfSpecialties: Array<String>)
            : ArrayList<Student> {
        val arrayListOfStudents = ArrayList<Student>()
        arrayOfSpecialties.forEach {specialty->
            for (i in 0 until list.size) {
                if (list[i].specialtyFirst == specialty || list[i].specialtySecond == specialty
                        || list[i].specialtyThird == specialty)
                    arrayListOfStudents.add(list[i])
            }
        }
        return arrayListOfStudents
    }
    override fun separateSpecialties(listOfStudents: ArrayList<Student>, arrayOfSpecialties: Array<String>)
            : ArrayList<ArrayList<Student>> {
        val arrayListOfListsWithStudents = ArrayList<ArrayList<Student>>()
        arrayOfSpecialties.forEach {
            val sortedList = listOfStudents.filterForSpecialty(it)
            arrayListOfListsWithStudents.add(sortedList)
        }
        return arrayListOfListsWithStudents
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