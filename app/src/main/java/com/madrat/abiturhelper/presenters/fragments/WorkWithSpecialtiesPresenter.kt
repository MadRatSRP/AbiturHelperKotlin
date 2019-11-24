package com.madrat.abiturhelper.presenters.fragments

import android.content.Context
import android.os.Bundle
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.`object`.FacultiesObject
import com.madrat.abiturhelper.interfaces.fragments.WorkWithSpecialtiesMVP
import com.madrat.abiturhelper.model.*
import com.madrat.abiturhelper.model.faculties.*
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.filterForSpecialty
import com.madrat.abiturhelper.util.showLog
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class WorkWithSpecialtiesPresenter(private val view: WorkWithSpecialtiesMVP.View)
    : WorkWithSpecialtiesMVP.Presenter{
    private val myApplication = MyApplication.instance

    // Первый этап
    // Считываем из источника(в данный момент это файлы) специальности и поступающих,
    // формируем список поступающих и специальностей,
    // передаем списки в следующую функцию
    override fun generateBachelorsAndSpecialtiesLists(inputStreamToSpecialties: InputStream,
                                                      inputStreamToStudents: InputStream) {
        val specialtiesParser = getInstanceOfCSVParser(inputStreamToSpecialties)
        val listOfSpecialties = grabSpecialties(specialtiesParser)

        val studentsParser = getInstanceOfCSVParser(inputStreamToStudents)
        val listOfStudents = grabStudents(studentsParser)

        getOnlyNeededValues(listOfSpecialties, listOfStudents)
    }
    override fun getInstanceOfCSVParser(inputStream: InputStream): CSVParser {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream, "Windows-1251"))

        return CSVParser(bufferedReader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withDelimiter(';')
                .withIgnoreHeaderCase()
                .withTrim())
    }
    override fun grabSpecialties(csvParser: CSVParser): ArrayList<Specialty> {
        val specialtiesList = ArrayList<Specialty>()
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
    override fun grabStudents(csvParser: CSVParser): ArrayList<Student> {
        val studentsList = ArrayList<Student>()
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

    // Второй этап
    // Оставляем в списке поступающих тех, у кого в графе "приемная комиссия" стоит "бак",
    // а для списка специальностей оставляем те, у которых уровень подготовки специалист
    // или бакалавр, а затем делим по факультету и сохраняем в модели Faculties
    override fun getOnlyNeededValues(listOfSpecialties: ArrayList<Specialty>,
                            listOfStudents: ArrayList<Student>) {
        // Оставляем в списке только специльности с выбранным уровнем подготовки
        // (специалист или бакалавр)
        val filteredListOfSpecialties = filterListOfSpecialtiesByEducationLevel(listOfSpecialties)

        // Оставляем в списке поступающих только тех, у кого
        // в графе "приемная комиссия" стоит "бак"
        val listOfBachelors = filterListOfStudentsByAdmissions(listOfStudents)

        val filteredListOfStudents = removeValuesWithoutScoreFromListOfStudents(listOfBachelors)

        categorizeValues(filteredListOfSpecialties, filteredListOfStudents)
    }
    override fun filterListOfSpecialtiesByEducationLevel(list: ArrayList<Specialty>): ArrayList<Specialty> {
        return list.filter {
            it.educationLevel == "Академический бакалавр" || it.educationLevel == "Специалист"
                    || it.educationLevel == "Прикладной бакалавр"} as ArrayList<Specialty>
    }
    override fun filterListOfStudentsByAdmissions(list: ArrayList<Student>): ArrayList<Student> {
        return list.filter { it.admissions == "бак"} as ArrayList<Student>
    }
    override fun removeValuesWithoutScoreFromListOfStudents(listOfBachelors: ArrayList<Student>): ArrayList<Student> {
        // Из входного списка выбираем только те значения,
        // где недостаточно баллов
        val nullListOfBachelors: ArrayList<Student> = listOfBachelors.filter {
            (it.maths == 0 && it.russian == 0) || it.maths == 0 || it.russian == 0
                    || (it.physics == 0 && it.computerScience == 0 && it.socialScience == 0)} as ArrayList<Student>
        println(nullListOfBachelors.size)

        // Исключаем из списка нулевые значения
        listOfBachelors.removeAll(nullListOfBachelors)

        // Возвращаем список
        return listOfBachelors
    }

    // Третий этап
    override fun categorizeValues(listOfSpecialties: ArrayList<Specialty>,
                         listOfStudents: ArrayList<Student>) {
        // Формируем модель Faculties, содержащую в себе
        // списки специальностей каждого из факультетов
        val faculties = formFacultiesModelFromListOfSpecialties(listOfSpecialties)

        // Разделяем список поступающих по типу баллов и сохраняем
        // в модель ScoreTypes
        val scoreTypes = returnStudentsSeparatedByScoreType(listOfStudents)

        // Сохраняем модель Faculties
        myApplication.saveFaculties(faculties)
        // Сохраняем модель ScoreTypes
        myApplication.saveScoreTypes(scoreTypes)

        generateScoreTypedListsAndCalculateAvailableFacultyPlaces()
    }


    override fun formFacultiesModelFromListOfSpecialties(list: ArrayList<Specialty>): Faculties {
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

        return Faculties(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)
    }
    override fun returnStudentsSeparatedByScoreType(listOfBachelors: ArrayList<Student>): ScoreTypes {
        // Вычисляем количество студентов, у которых достаточно баллов
        val studentsWithEnoughData = listOfBachelors.filter {
            it.maths != 0 && it.russian != 0 && (it.physics != 0 || it.computerScience != 0
                    || it.socialScience != 0) } as ArrayList<Student>
        showLog("Студентов, чьих баллов достаточно: ${studentsWithEnoughData.size}")

        val scoreType =  ScoreTypes(
                withdrawStudentsWithSpecificScore(studentsWithEnoughData, 0),
                withdrawStudentsWithSpecificScore(studentsWithEnoughData, 1),
                withdrawStudentsWithSpecificScore(studentsWithEnoughData, 2),
                withdrawStudentsWithSpecificScore(studentsWithEnoughData, 3)
        )
        showLog("MAMAMA\n${scoreType.physicsStudents.size}\n${scoreType.computerScienceStudents.size}" +
                "\n${scoreType.socialScienceStudents.size}\n${scoreType.partAndAllDataStudents.size}")
        return scoreType
    }

    override fun withdrawStudentsWithSpecificScore(bachelors: ArrayList<Student>, typeOfScoreId: Int): ArrayList<Student> {
        return when(typeOfScoreId) {
            // Физика
            0 -> bachelors.filter { it.physics != 0 && it.computerScience == 0 && it.socialScience == 0 } as ArrayList<Student>
            // Информатика
            1 -> bachelors.filter { it.computerScience != 0 && it.physics == 0 && it.socialScience == 0 } as ArrayList<Student>
            // Обществознание
            2 -> bachelors.filter { it.socialScience != 0 && it.physics == 0 && it.computerScience == 0 } as ArrayList<Student>
            // Баллы по двум/трем предметам
            3 -> bachelors.filter { (it.physics != 0 && it.computerScience != 0 && it.socialScience != 0)
                    || (it.physics != 0 && it.computerScience != 0 && it.socialScience == 0)
                    || (it.physics != 0 && it.computerScience == 0 && it.socialScience != 0)
                    || (it.physics == 0 && it.computerScience != 0 && it.socialScience != 0) } as ArrayList<Student>
            else -> bachelors
        }
    }

    // Третий этап
    override fun generateScoreTypedListsAndCalculateAvailableFacultyPlaces() {
        // Получаем и сохраняем количество общих и свободных мест для каждого из факультетов
        val listOfFaculties = returnListOfFaculties()

        myApplication.saveFacultyList(listOfFaculties)
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
    override fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>?)
            : Faculty {
        val total = list?.sumBy { it.entriesTotal }
        val free = list?.sumBy { it.entriesFree }
        val amountOfSpecialties = list?.size
        return Faculty(name, total, free, amountOfSpecialties)
    }
    // Третий этап
    override fun separateStudentsBySpecialties() {
        val scoreTypes = myApplication.returnScoreTypes()

        scoreTypes?.let {
            checkForUNTI(scoreTypes)
            checkForFEU(scoreTypes)
            checkForFIT(scoreTypes)
            checkForMTF(scoreTypes)
            checkForUNIT(scoreTypes)
            checkForFEE(scoreTypes)
        }
        println("Третий этап завершён")
    }
    // УНТИ
    override fun checkForUNTI(scoreTypes: ScoreTypes) {
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

        // ATP
        val atp = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfATPSpecialties)
        // KTO
        val kto = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfKTOSpecialties)
        // MASH
        val mash = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfMASHSpecialties)
        // MITM
        val mitm = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfMITMSpecialties)
        // MHT
        val mht = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfMHTSpecialties)
        // PTMK
        val ptmk = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfPTMKSpecialties)
        // TMO
        val tmo = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfTMOSpecialties)
        // UTS
        val uts = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfUTSSpecialties)

        // Получаем список списков студентов специальности УНТИ и сохраняем его
        val unti = UNTI(atp, kto, mash, mitm, mht, ptmk, tmo, uts)
        val separatedUNTI = separateUNTI(unti)
        myApplication.saveUnti(separatedUNTI)
    }
    override fun separateUNTI(unti: UNTI)
            : ArrayList<ArrayList<Student>> {
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
    override fun checkForFEU(scoreTypes: ScoreTypes) {
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

        // BI
        val bi = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfBISpecialties)
        // PI
        val pi = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfPISpecialties)
        // SC
        val sc = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfSCSpecialties)
        // TD
        val td = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfTDSpecialties)
        // EB
        val eb = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfEBSpecialties)
        // EK
        val ek = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfEKSpecialties)

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
    override fun checkForFIT(scoreTypes: ScoreTypes) {
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

        // IASB
        val iasb = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfIASBSpecialties)
        // IB
        val ib = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfIBSpecialties)
        // IBAS
        val ibas = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfIBASSpecialties)
        // IVT
        val ivt = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfIVTSpecialties)
        // INN
        val inn = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfINNSpecialties)
        // IST
        val ist = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfISTSpecialties)
        // MOA
        val moa = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfMOASpecialties)
        // PRI
        val pri = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfPRISpecialties)
        // PRO
        val pro = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfPROSpecialties)

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
    override fun checkForMTF(scoreTypes: ScoreTypes) {
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
        // MASH
        val mash = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfMASHSpecialties)
        // SIM
        val sim = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfSIMSpecialties)
        // TB
        val tb = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfTBSpecialties)
        // UK
        val uk = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfUKSpecialties)

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
    override fun checkForUNIT(scoreTypes: ScoreTypes) {
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

        // NTTK
        val nttk = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfNTTKSpecialties)
        // NTTS
        val ntts = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfNTTSSpecialties)
        // PM
        val pm = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfPMSpecialties)
        // PSJD
        val psjd = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfPSJDSpecialties)
        // TTP
        val ttp = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfTTPSpecialties)
        // ETTK
        val ettk = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfETTKSpecialties)

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
    override fun checkForFEE(scoreTypes: ScoreTypes) {
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

        // RAD
        val rad = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfRADSpecialties)
        // TIT
        val tit = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfTITSpecialties)
        // EIN
        val ein = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfEINSpecialties)
        // EIE
        val eie = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfEIESpecialties)
        // EM
        val em = returnListOfStudentsForChosenSpecialty(scoreTypes, arrayOfEMSpecialties)

        val fee = FEE(rad, tit, ein, eie, em)
        val separatedFEE = separateFEE(fee)
        myApplication.saveFEE(separatedFEE)
    }
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
    override fun returnListOfStudentsForChosenSpecialty(scoreTypes: ScoreTypes, arrayOfSpecialties: Array<String>)
            : ArrayList<Student> {
        val arrayListOfStudents = ArrayList<Student>()
        arrayListOfStudents.addAll(checkForSpecialties(scoreTypes.physicsStudents, arrayOfSpecialties))
        arrayListOfStudents.addAll(checkForSpecialties(scoreTypes.computerScienceStudents, arrayOfSpecialties))
        arrayListOfStudents.addAll(checkForSpecialties(scoreTypes.socialScienceStudents, arrayOfSpecialties))
        arrayListOfStudents.addAll(checkForSpecialties(scoreTypes.partAndAllDataStudents, arrayOfSpecialties))
        return arrayListOfStudents
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
        val listUNTI = checkFacultyForMinimalScore(context, FacultiesObject.FACULTY_UNTY)
        val listFEU = checkFacultyForMinimalScore(context, FacultiesObject.FACULTY_FEU)
        val listFIT = checkFacultyForMinimalScore(context, FacultiesObject.FACULTY_FIT)
        val listMTF = checkFacultyForMinimalScore(context, FacultiesObject.FACULTY_MTF)
        val listUNIT = checkFacultyForMinimalScore(context, FacultiesObject.FACULTY_UNIT)
        val listFEE = checkFacultyForMinimalScore(context, FacultiesObject.FACULTY_FEE)

        val faculties = listUNTI?.let { listFEU?.let { it1 ->
            listFIT?.let { it2 -> listMTF?.let { it3 ->
                listUNIT?.let { it4 ->
                    listFEE?.let { it5 -> Faculties(it, it1, it2, it3, it4, it5) } }
            } } } }
        faculties?.let { myApplication.saveFaculties(it) }
    }

    override fun checkFacultyForMinimalScore(context: Context, facultyId: Int)
            : ArrayList<Specialty>? {
        val listOfFacultySpecialties = getListOfFacultySpecialtiesByFacultyId(facultyId)
        val listOfFacultyStudents: ArrayList<ArrayList<Student>>?
                = getListOfFacultyStudentsByFacultyId(facultyId)

        listOfFacultySpecialties?.let {
            for (i in 0 until listOfFacultySpecialties.size) {
                listOfFacultyStudents?.let {
                    listOfFacultySpecialties[i].amountOfStatements = it[i].size

                    when(listOfFacultySpecialties[i].profileTerm) {
                        "Физика" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.physics + r.additionalScore}

                            listOfFacultySpecialties[i].scoreTitle = context.getString(R.string.facultyMathsAndPhysics)

                            minimalScore?.let {  r
                                -> listOfFacultySpecialties[i].minimalScore= r.maths + r.physics + r.additionalScore}
                        }
                        "Обществознание" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.socialScience + r.additionalScore }

                            listOfFacultySpecialties[i].scoreTitle = context.getString(R.string.facultyMathsAndSocialScience)

                            minimalScore?.let {  r
                                -> listOfFacultySpecialties[i].minimalScore= r.maths + r.socialScience + r.additionalScore}
                        }
                        "Информатика и ИКТ" -> {
                            val minimalScore = it[i].minBy { r -> r.maths + r.computerScience + r.additionalScore}

                            listOfFacultySpecialties[i].scoreTitle = context.getString(R.string.facultyMathsAndComputerScience)

                            minimalScore?.let {  r
                                -> listOfFacultySpecialties[i].minimalScore = r.maths + r.computerScience + r.additionalScore}
                        }
                        else -> return null
                    }
                }
            }
        }
        return listOfFacultySpecialties
    }
    override fun getListOfFacultySpecialtiesByFacultyId(facultyId: Int)
            : ArrayList<Specialty>? {
        val faculties = myApplication.returnFaculties()
        return when (facultyId) {
            // УНТИ
            FacultiesObject.FACULTY_UNTY -> faculties?.listUNTI
            // ФЭУ
            FacultiesObject.FACULTY_FEU -> faculties?.listFEU
            // ФИТ
            FacultiesObject.FACULTY_FIT -> faculties?.listFIT
            // МТФ
            FacultiesObject.FACULTY_MTF -> faculties?.listMTF
            // УНИТ
            FacultiesObject.FACULTY_UNIT -> faculties?.listUNIT
            // ФЭЭ
            FacultiesObject.FACULTY_FEE -> faculties?.listFEE
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
    override fun getListOfFacultyStudentsByFacultyId(facultyId: Int)
            : ArrayList<ArrayList<Student>>? {
        return when (facultyId) {
            //УНТИ
            FacultiesObject.FACULTY_UNTY -> myApplication.returnUNTI()
            //ФЭУ
            FacultiesObject.FACULTY_FEU -> myApplication.returnFEU()
            //ФИТ
            FacultiesObject.FACULTY_FIT -> myApplication.returnFIT()
            //МТФ
            FacultiesObject.FACULTY_MTF -> myApplication.returnMTF()
            //УНИТ
            FacultiesObject.FACULTY_UNIT -> myApplication.returnUNIT()
            //ФЭЭ
            FacultiesObject.FACULTY_FEE -> myApplication.returnFEE()
            else -> null
        }
    }
}