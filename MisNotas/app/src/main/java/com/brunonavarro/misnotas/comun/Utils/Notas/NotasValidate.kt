package com.brunonavarro.misnotas.comun.Utils.Notas

import android.content.Context
import android.widget.EditText

class NotasValidate {

    fun isValid(context: Context,titulo:EditText,descripcion:EditText):Boolean{
        var isValid:Boolean=true
        if (titulo.text.toString().trim().isEmpty()){
            titulo.setError("Campo requerido")
            titulo.requestFocus()
            isValid=false
        }else if (descripcion.text.toString().trim().isEmpty()){
            descripcion.setError("Campo requerido")
            descripcion.requestFocus()
            isValid=false
        }
        return isValid
    }
}