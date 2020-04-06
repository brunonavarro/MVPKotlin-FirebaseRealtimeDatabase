package com.brunonavarro.misnotas.ui.home

import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.ui.home.events.HomeEvent

interface HomeContract {
    interface View{
        fun showProgress()
        fun hideProgress()

        fun add(nota: Nota)
        fun update(nota: Nota)
        fun remove(nota: Nota)

        fun removeFail()
        fun onShowError(resMsg: Int)
    }

    interface Presenter{
        fun onCreate()
        fun onPause()
        fun onResume()
        fun onDestroy()

        fun remove(nota: Nota)
        fun onEventListener(event: HomeEvent)
    }
}