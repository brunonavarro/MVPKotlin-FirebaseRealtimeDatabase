package com.brunonavarro.misnotas.ui.addNota.events

class AddEventNota {

    val SUCCESS_ADD:Int=1
    val ERROR_SERVER:Int=100
    val ERROR_MAX_VALUE:Int=101


    private var typeEvent:Int?=null
    private var resMsg:Int?=null

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