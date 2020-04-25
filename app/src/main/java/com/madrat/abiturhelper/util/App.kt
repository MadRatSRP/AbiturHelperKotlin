package com.madrat.abiturhelper.util

import android.app.Application

class App: Application() {
    companion object { val instance = App() }
}