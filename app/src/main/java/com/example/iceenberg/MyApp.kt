package com.example.iceenberg

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import java.util.Locale

class MyApp : Application() {

    companion object {
        private const val PREFS_NAME = "MySharedPreferences"
        private const val KEY_SELECTED_LANGUAGE = "seleccionSpinnerIdiomas"
    }

    override fun onCreate() {
        super.onCreate()
        initLanguage()
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks())
    }

    fun initLanguage() {
        val idioma = getSavedLanguage()

        Log.d("idioma inicio", idioma)

        cambiarIdioma(idioma)
    }

    fun cambiarIdioma(idioma: String) {
        val locale = Locale(idioma)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

    }

    private fun getSavedLanguage(): String {
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val position = prefs.getInt(KEY_SELECTED_LANGUAGE, 0) ?: 0

        Log.d("position", position.toString())

        var idioma : String = ""

        if (position == 0) {
            idioma = "en"
        } else if (position == 1) {
            idioma = "es"
        } else if (position == 2) {
            idioma = "it"
        }

        return idioma
    }

    fun saveLanguage(idioma: String) {
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(KEY_SELECTED_LANGUAGE, idioma)
        editor.apply()
    }

    inner class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            // Aplicar el cambio de idioma en la creación de cada actividad
            cambiarIdioma(getSavedLanguage())
        }

        override fun onActivityStarted(activity: Activity) {
            // No es necesario implementar
        }

        override fun onActivityResumed(activity: Activity) {
            // No es necesario implementar
        }

        override fun onActivityPaused(activity: Activity) {
            // No es necesario implementar
        }

        override fun onActivityStopped(activity: Activity) {
            // No es necesario implementar
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            // No es necesario implementar
        }

        override fun onActivityDestroyed(activity: Activity) {
            // No es necesario implementar
        }
    }

    fun getWorkManagerConfiguration(): Configuration {
        return Configuration()
    }
}