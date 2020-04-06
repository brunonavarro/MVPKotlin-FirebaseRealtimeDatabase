package com.brunonavarro.misnotas.model.Home.data.AddNota

import com.brunonavarro.misnotas.R
import com.brunonavarro.misnotas.comun.BasicErrorEventCallback
import com.brunonavarro.misnotas.model.Home.data.DataAdapter
import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.ui.addNota.events.AddEventNota
import com.brunonavarro.misnotas.ui.home.events.HomeEvent
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class RealtimeDatabase {
    private var mDatabaseAPI: DataAdapter?=null

    init {
        mDatabaseAPI = DataAdapter().getInstance()
    }

    fun addNota(nota : Nota, callback: BasicErrorEventCallback){
        mDatabaseAPI?.getNotasReference()?.push()?.setValue(nota, object: DatabaseReference.CompletionListener{
            override fun onComplete(p0: DatabaseError?, p1: DatabaseReference) {
                if (p0==null){
                    callback.onSuccess()
                }else{
                    when (p0!!.getCode()) {
                        DatabaseError.PERMISSION_DENIED -> {callback.onError(
                            AddEventNota().ERROR_MAX_VALUE,
                            R.string.addnotas_error_max_value
                        )
                            System.out.println("REALTIMEDATABASE - REMOVE-ERROR: "+ HomeEvent().ERROR_TO_REMOVE)
                        }
                        else -> {
                            callback.onError(
                                AddEventNota().ERROR_SERVER,
                                R.string.notas_error_server
                            )
                            System.out.println("REALTIMEDATABASE - REMOVE-ERROR: "+ HomeEvent().ERROR_SERVER)
                        }
                    }
                }
            }
        })
    }
}