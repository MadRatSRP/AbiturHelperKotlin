package com.madrat.abiturhelper.model

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
        val entriesAmount: Int,
        val enrolledAmount: Int,
        val availableEntries: Int = entriesAmount - enrolledAmount
)