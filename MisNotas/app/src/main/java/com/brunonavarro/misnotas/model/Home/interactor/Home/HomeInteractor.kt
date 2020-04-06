package com.brunonavarro.misnotas.model.Home.interactor.Home

import com.brunonavarro.misnotas.model.Home.entidad.Nota

interface HomeInteractor {
    fun subscribeToNotas()
    fun unsubscribeToNotas()

    fun removeNotas(nota: Nota)
}