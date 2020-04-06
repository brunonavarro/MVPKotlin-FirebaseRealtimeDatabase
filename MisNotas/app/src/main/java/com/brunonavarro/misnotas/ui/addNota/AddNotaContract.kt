package com.brunonavarro.misnotas.ui.addNota

import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.ui.addNota.events.AddEventNota

interface AddNotaContract{
    interface View{
        fun enableUIelements()
        fun disableUIelements()
        fun showProgress()
        fun hideProgress()

        fun notaAdded()
        fun showError(msg: Int)
        fun maxvalueError(msg: Int)
    }
    interface Presenter{
        fun onShow()
        fun onDestroy()

        fun addNota(notas: Nota)
        fun onEventListener(addNotaEvent: AddEventNota)
    }
}