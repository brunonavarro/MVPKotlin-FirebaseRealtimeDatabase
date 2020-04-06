package com.brunonavarro.misnotas.model.Home.data

import com.brunonavarro.misnotas.model.Home.entidad.Nota

interface NotasEventListener {
    fun onChildAdded(nota: Nota)
    fun onChildUpdated(nota: Nota)
    fun onChildRemoved(nota: Nota)

    fun onError(resMsg: Int)
}