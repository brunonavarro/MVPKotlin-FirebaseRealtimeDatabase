package com.brunonavarro.misnotas.model.Home.data.Home


import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.brunonavarro.misnotas.ui.home.events.HomeEvent
import com.brunonavarro.misnotas.R
import com.brunonavarro.misnotas.comun.BasicErrorEventCallback
import com.brunonavarro.misnotas.model.Home.data.DataAdapter
import com.brunonavarro.misnotas.model.Home.data.NotasEventListener
import com.brunonavarro.misnotas.model.Home.entidad.Nota

class RealtimeDatabase {

    private var mDatabaseAPI: DataAdapter?=null

    private var mNotasChildEventListener: ChildEventListener?=null

    init {
        mDatabaseAPI = DataAdapter().getInstance()
    }



    fun suscribeToFestividades(listener : NotasEventListener){
        if (mNotasChildEventListener == null) {
            mNotasChildEventListener=object : ChildEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    when (p0.getCode()) {
                        DatabaseError.PERMISSION_DENIED -> listener.onError(R.string.notas_error_permiso_denegado)
                        else -> listener.onError(R.string.notas_error_server)
                    }
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    listener.onChildUpdated(getNotas(p0))
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    listener.onChildAdded(getNotas(p0))
                    System.out.println("RealtimeDatabase - subscribe childAdd: "+ getNotas(p0))
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    listener.onChildRemoved(getNotas(p0))
                    System.out.println("RealtimeDatabase - subscribe childRemove: "+ getNotas(p0))
                }
            }
        }
        mDatabaseAPI?.getNotasReference()?.addChildEventListener(mNotasChildEventListener!!)
    }

    fun getNotas(dataSnapshot: DataSnapshot): Nota {
        val nota: Nota=dataSnapshot.getValue(Nota::class.java)!!
        if (nota!=null){
            nota.id=(dataSnapshot.getKey()!!)
            System.out.println("RealtimeDatabase - getNota: "+ nota.id)
        }
        return nota
    }

    fun unsubscribeToNotas(){
        if (mNotasChildEventListener!=null){
            mDatabaseAPI?.getNotasReference()?.removeEventListener(mNotasChildEventListener!!)
            System.out.println("RealtimeDatabase - unSubscribe: "+ mDatabaseAPI?.getNotasReference()?.removeEventListener(mNotasChildEventListener!!))
        }
    }

    fun removeNotas(festividades:Nota, callback: BasicErrorEventCallback){
        mDatabaseAPI?.getNotasReference()?.child(festividades.id!!)
            ?.removeValue(object : DatabaseReference.CompletionListener {
                override fun onComplete(p0: DatabaseError?, p1: DatabaseReference) {
                    if (p0==null){
                        callback.onSuccess()
                        System.out.println("REALTIMEDATABASE - REMOVE: "+festividades.id)
                    }else {
                        when (p0!!.getCode()) {
                            DatabaseError.PERMISSION_DENIED -> {callback.onError(
                                    HomeEvent().ERROR_TO_REMOVE,
                                    R.string.notas_error_to_remove
                                )
                                System.out.println("REALTIMEDATABASE - REMOVE-ERROR: "+HomeEvent().ERROR_TO_REMOVE)
                            }
                            else -> {
                                callback.onError(
                                    HomeEvent().ERROR_SERVER,
                                    R.string.notas_error_server
                                )
                                System.out.println("REALTIMEDATABASE - REMOVE-ERROR: "+HomeEvent().ERROR_SERVER)
                            }
                        }

                    }
                }
            })
    }

}