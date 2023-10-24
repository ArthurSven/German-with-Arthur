package com.devapps.germanwitharthur.Application

import android.app.Application
import com.devapps.germanwitharthur.Data.db.GermanWithTuuriDatabase

class GermanWithArthurApplication : Application() {
    val database : GermanWithTuuriDatabase by lazy {
        GermanWithTuuriDatabase.getDatabase(this)
    }
}