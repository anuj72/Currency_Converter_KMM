package com.app.currency.android

import android.app.Application
import android.content.Context
import com.app.currency.DatabaseDriverFactory
import com.app.currency.android.screens.MainVm
import com.app.currency.dao.AppDao
import com.app.currency.repo.AppRepo
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module


class MainApplication : Application() {
    companion object {
        private var mContext: Context? = null
        val appContext: Context?
            get() = mContext
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext




        val appRepo = module {
            factory {
               AppRepo(DatabaseDriverFactory(applicationContext))

            }
        }

        val mainActivityVM = module {
            factory {
                MainVm(get())
            }
        }


        startKoin {
            androidLogger()
            //inject Android context
            androidContext(this@MainApplication)
            // use modules

            modules(
                listOf(
                   appRepo, mainActivityVM,

                )
            )

        }


    }
}
