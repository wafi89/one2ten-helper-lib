package com.one2ten.helper.parseserver

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject

abstract class ParseServerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        registerParseSubclass().forEach {
            ParseObject.registerSubclass(it)
        }

        if (getParseConfigs().isNotEmpty()) {
            Parse.initialize(
                Parse.Configuration.Builder(this)
                    .applicationId(getParseConfigs().parseAppId)
                    .clientKey(getParseConfigs().parseClientKey)
                    .server(getParseConfigs().parseServerUrl)
                    .build()
            )
        }
    }

    abstract fun getParseConfigs() : ParseConfigs

    abstract fun registerParseSubclass() : List<Class<ParseObject>>

}

data class ParseConfigs(
    val parseAppId : String,
    val parseClientKey : String,
    val parseServerUrl : String
) {
    fun isNotEmpty() : Boolean{
        return parseAppId.isNotEmpty() && parseClientKey.isNotEmpty() && parseServerUrl.isNotEmpty()
    }
}