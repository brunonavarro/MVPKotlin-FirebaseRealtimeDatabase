package com.brunonavarro.misnotas.ui.home.events

import com.brunonavarro.misnotas.model.Home.entidad.Nota

class HomeEvent() {
     val SUCCESS_ADD:Int=1
     val SUCCESS_UPDATE:Int=2
     val SUCCESS_REMOVE:Int=3
     val ERROR_SERVER:Int=100
     val ERROR_TO_REMOVE:Int=101

    private var nota:Nota?=null
    private var typeEvent:Int?=null
    private var resMsg:Int?=null

    fun getNotas(): Nota {
        return nota!!
    }

    fun setNotas(nota: Nota?) {
        this.nota = nota;
    }

    fun getTypeEvent():Int {
        return typeEvent!!
    }

    fun setTypeEvent(typeEvent:Int) {
        this.typeEvent = typeEvent;
    }

    fun getResMsg():Int {
        return resMsg!!;
    }

    fun setResMsg(resMsg:Int) {
        this.resMsg = resMsg;
    }

}