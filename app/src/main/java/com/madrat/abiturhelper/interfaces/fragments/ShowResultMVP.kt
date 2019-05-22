package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

interface ShowResultMVP {
    interface View {
        fun setupMVP()
    }

    interface Presenter {
        fun returnAmountOfSpecialtiesWithZeroMinimalScore(): Int?

    }
}
