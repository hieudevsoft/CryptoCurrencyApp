package com.plcoding.cryptocurrencyappyt.common

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Build.VERSION_CODES.N
import java.util.*

object Utils {
    fun getBackOffDelay(attempt:Int):Int = Constants.INITIAL_BACKOFF *(attempt+1)
}

object LocaleUtils{

    fun onAttachWithBaseContext(context: Context?,languageISO: String):Context?{
        return context?.let {
            setLocale(context,languageISO)
        }?:context
    }

    fun setLocale(context:Context,languageISO:String):Context = updateResources(context,Locale(languageISO))

    private fun updateResources(context: Context, locale:Locale):Context{
        context.resources.apply {
            val configuration = Configuration()
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            if(Build.VERSION.SDK_INT>=N){
                val locales = configuration.locales
                configuration.setLocales(locales)
            }
            return context.createConfigurationContext(configuration)
        }
    }
}