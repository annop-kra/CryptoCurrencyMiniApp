package com.ankn.cryptocurrencyminiapp

import android.app.Application
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CryptoCurrencyMiniApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoCurrencyMiniApplication)
            modules(
                /*dataModule, domainModule, presentationModule, navigationModule,
                viewModelModule*/
            )
        }
    }
}