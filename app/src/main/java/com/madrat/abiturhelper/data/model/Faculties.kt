package com.madrat.abiturhelper.data.model

data class Faculties(
        // УНТИ
    var listUNTI: ArrayList<Specialty>,
        // ФЭУ
    var listFEU: ArrayList<Specialty>,
        // ФИТ
    var listFIT: ArrayList<Specialty>,
        // МТФ
    var listMTF: ArrayList<Specialty>,
        // УНИТ
    var listUNIT: ArrayList<Specialty>,
        // ФЭЭ
    var listFEE: ArrayList<Specialty>
) {
    fun returnAmountOfSpecialties(): Int = listUNTI.size + listFEU.size + listFIT.size+
                listMTF.size+ listUNIT.size + listFEE.size
}
