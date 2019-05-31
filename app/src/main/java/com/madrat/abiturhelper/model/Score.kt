package com.madrat.abiturhelper.model

data class Score(
        var maths: Int,
        var russian: Int,
        var physics: Int,
        var computerScience: Int,
        var socialScience: Int,
        var additionalScore: Int = 0
)