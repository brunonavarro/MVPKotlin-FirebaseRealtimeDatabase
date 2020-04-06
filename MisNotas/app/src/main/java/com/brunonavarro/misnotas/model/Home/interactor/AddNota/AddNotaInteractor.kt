package com.brunonavarro.misnotas.model.Home.interactor.AddNota

import com.brunonavarro.misnotas.model.Home.entidad.Nota

interface AddNotaInteractor {
    fun addNota(notas: Nota)
}