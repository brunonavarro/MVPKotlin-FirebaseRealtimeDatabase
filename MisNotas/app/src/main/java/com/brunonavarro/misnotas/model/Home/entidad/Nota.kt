package com.brunonavarro.misnotas.model.Home.entidad

import com.google.firebase.database.Exclude

class Nota {

    @Exclude
    @get:Exclude
    @set:Exclude
    var id: String? = null
    var titulo: String? = null
    var descripcion: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as Nota?

        return if (id != null) id == that!!.id else that!!.id == null
    }

    override fun hashCode(): Int {
        return if (id != null) id!!.hashCode() else 0
    }

    companion object {
        val ID = "id"
        val TITULO = "titulo"
        val DESCRIPCION = "descripcion"
    }
}
