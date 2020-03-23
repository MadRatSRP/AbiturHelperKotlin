package com.madrat.abiturhelper.presenters.fragments

import android.os.Bundle
import com.madrat.abiturhelper.interfaces.fragments.ShowResultMVP
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.showLog

class ShowResultPresenter(val view: ShowResultMVP.View)
    : ShowResultMVP.Presenter {
    private val myApplication = MyApplication.instance

    // Пятый этап
    override fun checkForZeroMinimalScore() {
        val scores = myApplication.returnScore()
        //val zeroList = ArrayList<ArrayList<Specialty>>()

        val listUNTI = checkFacultyForSpecialtiesWithZeroMinimalScore(0, scores)
        val listFEU = checkFacultyForSpecialtiesWithZeroMinimalScore(1, scores)
        val listFIT = checkFacultyForSpecialtiesWithZeroMinimalScore(2, scores)
        val listMTF = checkFacultyForSpecialtiesWithZeroMinimalScore(3, scores)
        val listUNIT = checkFacultyForSpecialtiesWithZeroMinimalScore(4, scores)
        val listFEE = checkFacultyForSpecialtiesWithZeroMinimalScore(5, scores)

        showLog("ZERO MINIMAL SCORE: ${listUNTI.size}, ${listFEU.size}, ${listFIT.size}, " +
                "${listMTF.size}, ${listUNIT.size}, ${listFEE.size}")

        val collection = arrayListOf(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)

        //val faculties = Faculties(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)
        myApplication.saveListOfSpecialtiesWithZeroMinimalScore(collection)
    }
    override fun checkFacultyForSpecialtiesWithZeroMinimalScore(position: Int, scores: Score?)
            : ArrayList<Specialty> {
        val list = getSpecialtiesListByPosition(position)
        var listWithZeroMinimalScore = ArrayList<Specialty>()

        list?.let {
            scores?.let {
                // Физика
                if (scores.physics != 0 && scores.computerScience == 0 && scores.socialScience == 0 )
                    listWithZeroMinimalScore = list.filter {it.scoreTitle == "Математика + Физика" &&
                            it.minimalScore == 0} as ArrayList<Specialty>
                // Информатика
                else if (scores.physics == 0 && scores.computerScience != 0 && scores.socialScience == 0 )
                    listWithZeroMinimalScore = list.filter {it.scoreTitle == "Математика + Информатика" &&
                            it.minimalScore == 0} as ArrayList<Specialty>
                // Обществознание
                else if (scores.physics == 0 && scores.computerScience == 0 && scores.socialScience != 0 )
                    listWithZeroMinimalScore = list.filter {it.scoreTitle == "Математика + Обществознание" &&
                            it.minimalScore == 0} as ArrayList<Specialty>
                // Все три
                else if (scores.physics != 0 && scores.computerScience != 0 && scores.socialScience != 0 )
                    listWithZeroMinimalScore = list.filter {it.minimalScore == 0} as ArrayList<Specialty>
                // Физика + Информатика
                else if (scores.physics != 0 && scores.computerScience != 0 && scores.socialScience == 0 )
                    listWithZeroMinimalScore = list.filter {(it.scoreTitle == "Математика + Информатика"
                            || it.scoreTitle == "Математика + Физика") && it.minimalScore == 0} as ArrayList<Specialty>
                // Обществознание + Информатика
                else if (scores.physics == 0 && scores.computerScience != 0 && scores.socialScience != 0 )
                    listWithZeroMinimalScore = list.filter {(it.scoreTitle == "Математика + Информатика"
                            || it.scoreTitle == "Математика + Обществознание") && it.minimalScore == 0} as ArrayList<Specialty>
                // Физика + Обществознание
                else if (scores.physics != 0 && scores.computerScience == 0 && scores.socialScience != 0 )
                    listWithZeroMinimalScore = list.filter {(it.scoreTitle == "Математика + Физика"
                            || it.scoreTitle == "Математика + Обществознание") && it.minimalScore == 0} as ArrayList<Specialty>
            }
        }
        return listWithZeroMinimalScore
    }

    // Шестой этап
    override fun checkForFittingSpecialties() {
        val listOfFittingSpecialties = returnFittingSpecialties()
        myApplication.saveListOfFittingSpecialties(listOfFittingSpecialties)
    }
    override fun returnFittingSpecialties()
            : ArrayList<ArrayList<Specialty>> {
        val scores = myApplication.returnScore()

        val listUNTI = checkFacultyForFittingSpecialties(0, scores)
        val listFEU = checkFacultyForFittingSpecialties(1, scores)
        val listFIT = checkFacultyForFittingSpecialties(2, scores)
        val listMTF = checkFacultyForFittingSpecialties(3, scores)
        val listUNIT = checkFacultyForFittingSpecialties(4, scores)
        val listFEE = checkFacultyForFittingSpecialties(5, scores)

        showLog("AMOUNT OF FITTING SPECIALTIES : ${listUNTI.size}, ${listFEU.size}, " +
                "${listFIT.size}, ${listMTF.size}, ${listUNIT.size}, ${listFEE.size}")

        val fittingList = ArrayList<ArrayList<Specialty>>()
        val collection = arrayListOf(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)
        fittingList.addAll(collection)

        return fittingList
        /*val list = ArrayList<ArrayList<Specialty>>()
        list.add(listUNTI)
        list.add(listFEU)
        list.add(listFIT)
        showLog("LIST ${list.size}")

        list.clear()
        list.addAll(arrayListOf(listUNTI, listFEU, listFIT))
        showLog("LIST ${list.size}")*/

        //val faculties = Faculties(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)
    }
    override fun checkFacultyForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty> {
        val list = getSpecialtiesListByPosition(position)
        var listWithFittingSpecialties = ArrayList<Specialty>()

        scores?.let {
            val mathsAndPhysics: Int = scores.maths + scores.physics + scores.additionalScore
            val mathsAndComputerScience = scores.maths + scores.computerScience + scores.additionalScore
            val mathsAndSocialScience = scores.maths + scores.socialScience + scores.additionalScore

            list?.let {
                // Физика
                if (scores.physics != 0 && scores.computerScience == 0 && scores.socialScience == 0 )
                    listWithFittingSpecialties = list.filter {it.minimalScore != 0 &&
                            it.scoreTitle == "Математика + Физика" && (it.minimalScore == mathsAndPhysics
                            || it.minimalScore < mathsAndPhysics)} as ArrayList<Specialty>
                // Информатика
                else if (scores.physics == 0 && scores.computerScience != 0 && scores.socialScience == 0 )
                    listWithFittingSpecialties = list.filter {it.minimalScore != 0 &&
                            it.scoreTitle == "Математика + Информатика" && (it.minimalScore == mathsAndComputerScience
                            || it.minimalScore < mathsAndComputerScience)} as ArrayList<Specialty>
                // Обществознание
                else if (scores.physics == 0 && scores.computerScience == 0 && scores.socialScience != 0 )
                    listWithFittingSpecialties = list.filter {it.minimalScore != 0 &&
                            it.scoreTitle == "Математика + Обществознание" && (it.minimalScore == mathsAndSocialScience
                            || it.minimalScore < mathsAndSocialScience)} as ArrayList<Specialty>
                // Все три
                else if (scores.physics != 0 && scores.computerScience != 0 && scores.socialScience != 0 )
                    listWithFittingSpecialties = list.filter {it.minimalScore != 0 && ((it.scoreTitle == "Математика + Физика"
                            && (it.minimalScore == mathsAndPhysics || it.minimalScore < mathsAndPhysics))
                            || (it.scoreTitle == "Математика + Информатика" && (it.minimalScore == mathsAndComputerScience ||
                            it.minimalScore < mathsAndComputerScience)) || (it.scoreTitle == "Математика + Обществознание"
                            && (it.minimalScore == mathsAndSocialScience || it.minimalScore < mathsAndSocialScience)))}
                            as ArrayList<Specialty>
                // Информатика + Физика
                else if (scores.physics != 0 && scores.computerScience != 0 && scores.socialScience == 0 )
                    listWithFittingSpecialties = list.filter {it.minimalScore != 0 &&
                            ((it.scoreTitle == "Математика + Информатика" && (it.minimalScore == mathsAndComputerScience
                                    || it.minimalScore < mathsAndComputerScience)) || (it.scoreTitle == "Математика + Физика"
                                    && (it.minimalScore == mathsAndPhysics || it.minimalScore < mathsAndPhysics)))}
                            as ArrayList<Specialty>
                // Информатика + Обществознание
                else if (scores.physics == 0 && scores.computerScience != 0 && scores.socialScience != 0 )
                    listWithFittingSpecialties = list.filter {it.minimalScore != 0 &&
                            ((it.scoreTitle == "Математика + Информатика" && (it.minimalScore == mathsAndComputerScience
                                    || it.minimalScore < mathsAndComputerScience)) || (it.scoreTitle == "Математика + Обществознание"
                                    && (it.minimalScore == mathsAndSocialScience || it.minimalScore < mathsAndSocialScience)))}
                            as ArrayList<Specialty>

                // Физика + Обществознание
                else if (scores.physics != 0 && scores.computerScience == 0 && scores.socialScience != 0 )
                    listWithFittingSpecialties = list.filter {it.minimalScore != 0 &&
                            ((it.scoreTitle == "Математика + Физика" && (it.minimalScore == mathsAndPhysics
                                    || it.minimalScore < mathsAndPhysics)) || (it.scoreTitle == "Математика + Обществознание"
                                    && (it.minimalScore == mathsAndSocialScience || it.minimalScore < mathsAndSocialScience)))}
                            as ArrayList<Specialty>
            }
        }
        return listWithFittingSpecialties
    }

    // Седьмой шаг
    fun returnListOfAllCompleteSpecialties(list: ArrayList<ArrayList<Specialty>>)
                : ArrayList<Specialty> {
        val sumOfFaculties = ArrayList<Specialty>()
        list.let {
            for (i in 0 until list.size) {
                sumOfFaculties += list[i]
            }
        }
        return sumOfFaculties
    }
    fun returnCompleteListOfSpecialties()
            = myApplication.returnCompleteListOfSpecilaties()
    fun saveListOfAllCompleteSpecialties(list: ArrayList<Specialty>)
            = myApplication.saveListOfAllCompleteSpecialties(list)

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

    fun getAmountOfFittingSpecialtiesAndSpecialtiesWithZeroMinimalScore() {
        val listOfSpecialtiesWithZeroMinimalScore = myApplication.returnListOfSpecialtiesWithZeroMinimalScore()
        val listOfFittingSpecialties = myApplication.returnListOfFittingSpecialties()

        listOfSpecialtiesWithZeroMinimalScore?.sumBy { it.size }?.let {
            listOfFittingSpecialties?.sumBy { it.size }?.let { it1 ->
                view.updateAmountOfFittingSpecialtiesAndSpecialtiesWithZeroMinimalScore(it, it1)
            }
        }
    }
    override fun completeAndSaveSummedList() {
        val zeroList = myApplication.returnListOfSpecialtiesWithZeroMinimalScore()
        val fittingList = myApplication.returnListOfFittingSpecialties()
        val completeList = ArrayList<ArrayList<Specialty>>()

        zeroList?.let {
            fittingList?.let {
                for (i in 0 until zeroList.size) {
                    val array = (zeroList[i] + fittingList[i]) as ArrayList<Specialty>
                    completeList.add(array)
                    completeList[i].sortBy { it.minimalScore }
                }
            }
        }
        showLog("РАЗМЕР ${completeList.size}")
        showLog("РАЗМЕР2 ${completeList.sumBy { it.size }}")

        myApplication.saveCompleteListOfSpecialties(completeList)
    }
    override fun returnBundleWithListID(listId: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("listId", listId)

        return bundle
    }
}
