package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.PickUpSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.showLog
import com.opencsv.CSVReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader


class PickUpSpecialtiesView
    : Fragment(), PickUpSpecialtiesMVP.View{

    var list = ArrayList<Specialty>()
    var untiList = ArrayList<Specialty>()
    var feuList = ArrayList<Specialty>()
    var fitList = ArrayList<Specialty>()
    var mtfList = ArrayList<Specialty>()
    var unitList = ArrayList<Specialty>()
    var feeList = ArrayList<Specialty>()
    var oadList = ArrayList<Specialty>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        grabSpecialties()
        divideList()
    }

    override fun divideList() {
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
        showLog(untiList.size.toString())
        showLog(feuList.size.toString())
        showLog(fitList.size.toString())
        showLog(mtfList.size.toString())
        showLog(unitList.size.toString())
        showLog(feeList.size.toString())
        showLog(oadList.size.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.pickUpSpecialtiesTitle)
        var view =  inflater.inflate(R.layout.fragment_pick_up_specialties, container, false)
        return view
    }

    override fun grabSpecialties() {

        val file = context?.assets?.open("specialties.csv")
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

            list.add(Specialty(shortName, fullName, specialty, profileTerm, educationForm,
                    educationLevel, graduationReason, receptionFeatures, faculty,
                    entriesAmount.toInt(), enrolledAmount.toInt()))
        }
        showLog(list[0].toString())
        showLog(list.size.toString())


        /*showLog("Record No - " + csvRecord.recordNumber
            + "\n---------------"
            + "\nShortName: $shortName"
            + "\nFullName: $fullName"
            + "\nSpecialty: $specialty"
            + "\nProfileTerm: $profileTerm"
            + "\nEducationForm: $educationForm"
            + "\nEducationLevel: $educationLevel"
            + "\nGraduationReason: $graduationReason"
            + "\nReceptionFeatures: $receptionFeatures"
            + "\nFaculty: $faculty"
            + "\nEntriesAmount: $entriesAmount"
            + "\nEnrolledAmount: $enrolledAmount"
            + "\n---------------\n\n")
        }*/



        /*val file = File("" + File.separator + "win")

val bufferedReader = file.bufferedReader()*/

        /*try {
            println("--- Read line by line ---")

            val file = context?.assets?.open("specialties.csv")
            val isr = InputStreamReader(file)

            fileReader = BufferedReader(isr)


            csvReader = CSVReader(fileReader)

            var record: Array<String>?
            csvReader?.readNext() // skip Header

            record = csvReader?.readNext()
            while (record != null) {
                println(record[0] + " | " + record[1] + " | " + record[2] + " | " + record[3])
                record = csvReader?.readNext()
            }

            csvReader?.close()
        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
                fileReader?.close()
                csvReader?.close()
            } catch (e: IOException) {
                println("Closing fileReader/csvParser Error!")
                e.printStackTrace()
            }
        }*/







        //val myFile = File("specialties.csv")



        /*val file = File("" + File.separator + "win")

        val bufferedReader = file.bufferedReader()*/

        //val pathToFile = Paths.get(filename)
        //showLog(pathToFile.toAbsolutePath().toString())

        //val reader = Files.newBufferedReader(pathToFile)

        //var bufReader = BufferedReader(FileReader(filename))



        //val csvFilePath = "C:\\AndroidLabs\\CurrentProjects\\AbiturHelperKotlin\\app\\specialties.csv"

        //val reader = Files.newBufferedReader(Paths.get("specialties.csv"))

        //showLog(Paths.get(csvFilePath).toAbsolutePath())



        //val myFile = File("C:\\AndroidLabs\\CurrentProjects\\AbiturHelperKotlin\\app" + "\\specialties.csv")

        //val file = File("C://AndroidLabs//CurrentProjects//AbiturHelperKotlin//app"+File.separator+"/win.text/")

        //val bufferedReader = file.bufferedReader()

        //val reader = Files.newBufferedReader(Paths.get(csvFilePath))

        /*val csvParser = CSVParser(bufferedReader, CSVFormat.EXCEL
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim())*/

        //var file = File("C:/Anime/example.text")
        /*showLog(file.parent)
        showLog(file.absolutePath)
        showLog(file.canonicalPath)
        showLog(file.path)*/
        //val bufferedReader = file.bufferedReader()


        //val bufferedReader = file.bufferedReader()

        /*for (csvRecord in csvParser) {
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

            showLog("Record No - " + csvRecord.recordNumber
                + "\n---------------"
                + "\nShortName: $shortName"
                + "\nFullName: $fullName"
                + "\nSpecialty: $specialty"
                + "\nProfileTerm: $profileTerm"
                + "\nEducationForm: $educationForm"
                + "\nEducationLevel: $educationLevel"
                + "\nGraduationReason: $graduationReason"
                + "\nReceptionFeatures: $receptionFeatures"
                + "\nFaculty: $faculty"
                + "\nEntriesAmount: $entriesAmount"
                + "\nEnrolledAmount: $enrolledAmount"
                + "\n---------------\n\n")
        }*/


    }
}