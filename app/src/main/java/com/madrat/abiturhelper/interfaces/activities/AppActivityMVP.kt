package com.madrat.abiturhelper.interfaces.activities

import android.os.Bundle

import androidx.fragment.app.Fragment

interface AppActivityMVP {
    interface View {
        fun setupMVP()
        fun setupActivity()
    }

    interface Presenter {
    }
}
