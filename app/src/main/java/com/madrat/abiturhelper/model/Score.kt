package com.madrat.abiturhelper.model

data class Score(
        var maths: Int = 0,
        var russian: Int = 0,
        var physics: Int,
        var computerScience: Int,
        var socialScience: Int,
        var additionalScore: Int = 0
)