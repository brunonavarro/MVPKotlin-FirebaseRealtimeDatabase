package com.brunonavarro.misnotas.model.Home.interactor.AddNota

import com.brunonavarro.misnotas.comun.BasicErrorEventCallback
import com.brunonavarro.misnotas.model.Home.data.AddNota.RealtimeDatabase
import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.ui.addNota.events.AddEventNota
import org.greenrobot.eventbus.EventBus

class AddNotaInteractorImpl: AddNotaInteractor {

    private var mDatabase:RealtimeDatabase?=null

    init {
        mDatabase = RealtimeDatabase()
    }

    override fun addNota(notas: Nota) {

        mDatabase?.addNota(notas,object : BasicErrorEventCallback{
            override fun onSuccess() {
                post(AddEventNota().SUCCESS_ADD)
            }

            override fun onError(typeEvent: Int, resMsg: Int) {
                post(typeEvent,resMsg)
            }

        })
    }

    private fun post(typeEvent: Int) {
        post(typeEvent, 0)
    }

    private fun post(typeEvent: Int, resMsg: Int) {
        val event = AddEventNota()
        //event.setNotas(nota!!)
        event.setTypeEvent(typeEvent)
        event.setResMsg(resMsg)
        EventBus.getDefault().post(event)
    }
}