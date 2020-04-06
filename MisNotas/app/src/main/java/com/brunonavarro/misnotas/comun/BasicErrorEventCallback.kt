package com.brunonavarro.misnotas.comun

interface BasicErrorEventCallback {
    fun onSuccess()
    fun onError(typeEvent: Int, resMsg: Int)
}