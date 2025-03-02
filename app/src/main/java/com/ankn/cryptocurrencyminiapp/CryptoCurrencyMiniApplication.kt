package com.ankn.cryptocurrencyminiapp

import android.app.Application
import com.ankn.cryptocurrencyminiapp.di.dataModule
import com.ankn.cryptocurrencyminiapp.di.domainModule
import com.ankn.cryptocurrencyminiapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CryptoCurrencyMiniApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoCurrencyMiniApplication)
            modules(
                dataModule, domainModule, viewModelModule
            )
        }
    }
}
