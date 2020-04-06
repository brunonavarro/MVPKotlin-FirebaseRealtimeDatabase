package com.brunonavarro.misnotas.model.Home.data

import com.brunonavarro.misnotas.ui.home.events.HomeEvent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataAdapter {
    private val PATH_NOTAS = "notas"

    private var mReference: DatabaseReference?=null

    private var INSTANCE: DataAdapter? = null

    init {
        mReference = FirebaseDatabase.getInstance().reference
    }

    fun getInstance():DataAdapter{
        if (INSTANCE==null){
            INSTANCE =  DataAdapter();
        }
        System.out.println("DATA ADAPTER - INSTANCE: "+ INSTANCE)
        return INSTANCE!!;
    }

    fun getmReference(): DatabaseReference{
        System.out.println("DATA ADAPTER - REFERENCE: "+ mReference)
        return mReference!!
    }

    fun getNotasReference(): DatabaseReference{
        return getmReference()?.child(PATH_NOTAS)!!
    }
}