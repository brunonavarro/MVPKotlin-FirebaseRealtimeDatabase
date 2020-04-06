package com.brunonavarro.misnotas.model.Home.interactor.Home

import com.brunonavarro.misnotas.comun.BasicErrorEventCallback
import com.brunonavarro.misnotas.model.Home.data.NotasEventListener
import com.brunonavarro.misnotas.model.Home.data.Home.RealtimeDatabase
import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.ui.home.events.HomeEvent
import org.greenrobot.eventbus.EventBus

class HomeInteractorImpl: HomeInteractor {
    private var mDatabase: RealtimeDatabase?=null

    init {
        mDatabase = RealtimeDatabase()
    }

    override fun subscribeToNotas() {
        mDatabase?.suscribeToFestividades(object : NotasEventListener {
            override fun onChildAdded(nota: Nota) {
                post(nota, HomeEvent().SUCCESS_ADD)
                System.out.println("INTERACTOR - Subscribe Add: "+HomeEvent().SUCCESS_ADD)
            }

            override fun onChildUpdated(nota: Nota) {
                post(nota, HomeEvent().SUCCESS_UPDATE)
            }

            override fun onChildRemoved(nota: Nota) {
                post(nota, HomeEvent().SUCCESS_REMOVE)
                System.out.println("INTERACTOR - Subscribe Remove: "+HomeEvent().SUCCESS_REMOVE)
            }

            override fun onError(resMsg: Int) {
                post(HomeEvent().ERROR_SERVER, resMsg)
            }
        })
    }

    override fun unsubscribeToNotas() {
        mDatabase?.unsubscribeToNotas()
    }

    override fun removeNotas(nota: Nota) {
        mDatabase?.removeNotas(nota, object : BasicErrorEventCallback {
            override fun onSuccess() {
                post(HomeEvent().SUCCESS_REMOVE)
                System.out.println("INTERACTOR - RemoveNota: "+HomeEvent().SUCCESS_REMOVE)
            }

            override fun onError(typeEvent: Int, resMsg: Int) {
                post(typeEvent, resMsg)
                System.out.println("INTERACTOR - RemoveNotaError: "+HomeEvent().ERROR_TO_REMOVE)
            }
        })
    }

    private fun post(typeEvent: Int) {
        post(null, typeEvent, 0)
    }

    private fun post(typeEvent: Int, resMsg: Int) {
        post(null, typeEvent, resMsg)
    }

    private fun post(nota: Nota, typeEvent: Int) {
        post(nota, typeEvent, 0)
    }

    private fun post(nota: Nota?, typeEvent: Int, resMsg: Int) {
        val event = HomeEvent()
        event.setNotas(nota!!)
        event.setTypeEvent(typeEvent)
        event.setResMsg(resMsg)
        EventBus.getDefault().post(event)
    }
}