package com.madrat.abiturhelper.presenters.fragments

import android.os.Bundle
import com.madrat.abiturhelper.interfaces.fragments.ShowResultMVP
import com.madrat.abiturhelper.model.Faculties
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.showLog

class ShowResultPresenter : ShowResultMVP.Presenter {
    private val myApplication = MyApplication.instance

    // Пятый этап
    override fun checkForZeroMinimalScore() {
        val scores = myApplication.returnScore()
        //val zeroList = ArrayList<ArrayList<Specialty>>()

        val listUNTI = checkUNTIForZeroMinimalScore(0, scores)
        val listFEU = checkFEUForZeroMinimalScore(1, scores)
        val listFIT = checkFITForZeroMinimalScore(2, scores)
        val listMTF = checkMTFForZeroMinimalScore(3, scores)
        val listUNIT = checkUNITForZeroMinimalScore(4, scores)
        val listFEE = checkFEEForZeroMinimalScore(5, scores)

        val collection = arrayListOf(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)

        //val faculties = Faculties(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)
        myApplication.saveListOfSpecialtiesWithZeroMinimalScore(collection)
    }
    override fun checkUNTIForZeroMinimalScore(position: Int, scores: Score?)
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
    override fun checkFEUForZeroMinimalScore(position: Int, scores: Score?)
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
    override fun checkFITForZeroMinimalScore(position: Int, scores: Score?)
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
    override fun checkMTFForZeroMinimalScore(position: Int, scores: Score?)
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
    override fun checkUNITForZeroMinimalScore(position: Int, scores: Score?)
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
    override fun checkFEEForZeroMinimalScore(position: Int, scores: Score?)
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
        val fittingList = returnFittingSpecialties()
        myApplication.saveListOfFittingSpecialties(fittingList)
    }
    override fun returnFittingSpecialties()
            : ArrayList<ArrayList<Specialty>> {
        val scores = myApplication.returnScore()

        val listUNTI = checkUNTIForFittingSpecialties(0, scores)
        val listFEU = checkFEUForFittingSpecialties(1, scores)
        val listFIT = checkFITForFittingSpecialties(2, scores)
        val listMTF = checkMTFForFittingSpecialties(3, scores)
        val listUNIT = checkUNITForFittingSpecialties(4, scores)
        val listFEE = checkFEEForFittingSpecialties(5, scores)

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
    override fun checkUNTIForFittingSpecialties(position: Int, scores: Score?)
            : ArrayList<Specialty> {
        val list = getSpecialtiesListByPosition(position)
        var listWithFittingSpecialties = ArrayList<Specialty>()

        scores?.let {
            val mathsAndPhysics: Int = scores.maths + scores.physics//scores?.let { scores.maths + scores.physics }
            val mathsAndComputerScience = scores.maths + scores.computerScience
            val mathsAndSocialScience = scores.maths + scores.socialScience

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
    override fun checkFEUForFittingSpecialties(position: Int, scores: Score?)
            : ArrayList<Specialty> {
        val list = getSpecialtiesListByPosition(position)
        var listWithFittingSpecialties = ArrayList<Specialty>()

        scores?.let {
            val mathsAndPhysics: Int = scores.maths + scores.physics//scores?.let { scores.maths + scores.physics }
            val mathsAndComputerScience = scores.maths + scores.computerScience
            val mathsAndSocialScience = scores.maths + scores.socialScience

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
    override fun checkFITForFittingSpecialties(position: Int, scores: Score?)
            : ArrayList<Specialty> {
        val list = getSpecialtiesListByPosition(position)
        var listWithFittingSpecialties = ArrayList<Specialty>()

        scores?.let {
            val mathsAndPhysics: Int = scores.maths + scores.physics//scores?.let { scores.maths + scores.physics }
            val mathsAndComputerScience = scores.maths + scores.computerScience
            val mathsAndSocialScience = scores.maths + scores.socialScience

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
    override fun checkMTFForFittingSpecialties(position: Int, scores: Score?)
            : ArrayList<Specialty> {
        val list = getSpecialtiesListByPosition(position)
        var listWithFittingSpecialties = ArrayList<Specialty>()

        scores?.let {
            val mathsAndPhysics: Int = scores.maths + scores.physics//scores?.let { scores.maths + scores.physics }
            val mathsAndComputerScience = scores.maths + scores.computerScience
            val mathsAndSocialScience = scores.maths + scores.socialScience

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
    override fun checkUNITForFittingSpecialties(position: Int, scores: Score?)
            : ArrayList<Specialty> {
        val list = getSpecialtiesListByPosition(position)
        var listWithFittingSpecialties = ArrayList<Specialty>()

        scores?.let {
            val mathsAndPhysics: Int = scores.maths + scores.physics//scores?.let { scores.maths + scores.physics }
            val mathsAndComputerScience = scores.maths + scores.computerScience
            val mathsAndSocialScience = scores.maths + scores.socialScience

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
    override fun checkFEEForFittingSpecialties(position: Int, scores: Score?)
            : ArrayList<Specialty> {
        val list = getSpecialtiesListByPosition(position)
        var listWithFittingSpecialties = ArrayList<Specialty>()

        scores?.let {
            val mathsAndPhysics: Int = scores.maths + scores.physics//scores?.let { scores.maths + scores.physics }
            val mathsAndComputerScience = scores.maths + scores.computerScience
            val mathsAndSocialScience = scores.maths + scores.socialScience

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
    override fun returnAmountOfSpecialtiesWithZeroMinimalScore(): Int? {
        val zeroList = myApplication.returnListOfSpecialtiesWithZeroMinimalScore()
        return zeroList?.sumBy { it.size }
    }
    override fun returnAmountOfFittingSpecialties(): Int? {
        val fittingList = myApplication.returnListOfFittingSpecialties()
        return fittingList?.sumBy { it.size }
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
