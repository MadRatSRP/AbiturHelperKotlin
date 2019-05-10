package com.madrat.abiturhelper.model

import java.io.Serializable

data class Specialty(
        val shortName: String,
        val fullName: String,
        val specialty: String,
        val profileTerm: String,
        val educationForm: String,
        val educationLevel: String,
        val graduationReason: String,
        val receptionFeatures: String,
        val faculty: String,
        val entriesTotal: Int,
        val enrolledAmount: Int,
        val entriesFree: Int = entriesTotal - enrolledAmount,
        var amountOfStatements: Int = 0
) : Serializable