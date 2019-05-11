package com.madrat.abiturhelper.model

data class Student(
        val studentId: String,
        val lastName: String,
        val firstName: String,
        val patronymic: String,
        val documentsDate: String,
        val getWay: String,
        val status: String,
        val cancelReason: String,
        val admissions: String,
        val category: String,
        val specialtyFirst: String,
        val specialtySecond: String,
        val specialtyThird: String,
        val russian: Int = 0,
        val maths: Int = 0,
        val physics: Int?,
        val computerScience: Int?,
        val socialScience: Int?,
        val additionalScore: Int?,
        val isCertificateAvailable: Boolean,
        val isChargeAvailable: Boolean,
        val priority: Int?
)