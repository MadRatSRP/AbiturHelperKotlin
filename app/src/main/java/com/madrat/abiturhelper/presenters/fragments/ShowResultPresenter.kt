package com.madrat.abiturhelper.presenters.fragments

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

        val faculties = Faculties(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)
        myApplication.saveListOfSpecialtiesWithZeroMinimalScore(faculties)
    }
    override fun checkUNTIForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkFEUForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkFITForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkMTFForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkUNITForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkFEEForZeroMinimalScore(position: Int, scores: Score?): ArrayList<Specialty> {
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
        val scores = myApplication.returnScore()
        //val fittingList = ArrayList<ArrayList<Specialty>>()

        val listUNTI = checkUNTIForFittingSpecialties(0, scores)
        val listFEU = checkFEUForFittingSpecialties(1, scores)
        val listFIT = checkFITForFittingSpecialties(2, scores)
        val listMTF = checkMTFForFittingSpecialties(3, scores)
        val listUNIT = checkUNITForFittingSpecialties(4, scores)
        val listFEE = checkFEEForFittingSpecialties(5, scores)

        showLog("${listUNTI.size} ${listFEU.size} ${listFIT.size} ${listMTF.size}" +
                "${listUNIT.size} ${listFEE.size}")

        val faculties = Faculties(listUNTI, listFEU, listFIT, listMTF, listUNIT, listFEE)
        myApplication.saveListOfFittingSpecialties(faculties)
    }
    override fun checkUNTIForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkFEUForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkFITForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkMTFForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkUNITForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty> {
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
    override fun checkFEEForFittingSpecialties(position: Int, scores: Score?): ArrayList<Specialty> {
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
        return zeroList?.returnAmountOfSpecialties()
    }
    override fun returnAmountOfFittingSpecialties(): Int? {
        val fittingList = myApplication.returnListOfFittingSpecialties()
        return fittingList?.returnAmountOfSpecialties()
    }
}
