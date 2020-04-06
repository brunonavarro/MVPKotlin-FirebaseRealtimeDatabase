package com.brunonavarro.misnotas.ui.home.adapter


import com.brunonavarro.misnotas.model.Home.entidad.Nota

interface OnItemClickListener {
    fun onItemClick(nota:Nota?)
    fun onLongItemClick(nota: Nota?)
}
