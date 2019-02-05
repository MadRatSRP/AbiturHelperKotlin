package com.madrat.abiturhelper.ui.result

import android.os.Bundle

interface ResultVP {
    interface View {
        fun setMVP()

        fun setEgeScore(): String
    }

    interface Presenter {
        fun addEgeScore(): String
    }
}
