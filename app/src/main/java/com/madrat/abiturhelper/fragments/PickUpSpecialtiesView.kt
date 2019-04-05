package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.PickUpSpecialtiesMVP
import com.madrat.abiturhelper.util.showLog
import com.opencsv.CSVReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader


class PickUpSpecialtiesView
    : Fragment(), PickUpSpecialtiesMVP.View{
    var fileReader: BufferedReader? = null
    var csvReader: CSVReader? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        grabSpecialties()
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
        }



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