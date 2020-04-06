package com.brunonavarro.misnotas.ui.addNota


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment

import com.brunonavarro.misnotas.R
import com.brunonavarro.misnotas.comun.Utils.Notas.NotasValidate
import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.presenter.addNota.AddNotaPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_nota.*
import org.jetbrains.annotations.NotNull

/**
 * A simple [Fragment] subclass.
 */
class AddNotaFragment : DialogFragment(),DialogInterface.OnShowListener, AddNotaContract.View {

    private var mAddNotaPresenter:AddNotaContract.Presenter?=null
    var contenMain:View?=null
    var edtTitulo:EditText?=null
    var edtDescripcion:EditText?=null

    init {
        mAddNotaPresenter=AddNotaPresenter(this)
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        val builder:AlertDialog.Builder =  AlertDialog.Builder(activity)
            .setTitle("")
            .setPositiveButton("Añadir",null)
            .setNegativeButton("Cancelar",null)

        val view:View=activity?.layoutInflater?.inflate(R.layout.fragment_add_nota,null)!!

        edtTitulo=view.findViewById(R.id.edtTitulo)
        edtDescripcion=view.findViewById(R.id.edtDescripcion)

        builder.setView(view)
        contenMain=view;

        configFocus()
        configEditText()

        val dialog:AlertDialog=builder.create()
        dialog.setOnShowListener(this)
        return dialog
    }

    private fun configEditText() {
        edtTitulo?.requestFocus()

    }

    private fun configFocus() {

    }

    override fun onShow(dialog: DialogInterface?) {
        var alertDialog:AlertDialog= getDialog() as AlertDialog
        if (alertDialog!=null){
            var positivebuton:Button=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            var negativebuton:Button=alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)

            positivebuton.setOnClickListener {

                if (NotasValidate().isValid(activity!!,edtTitulo!!,edtDescripcion!!)) {
                    val nota: Nota = Nota()
                    nota.titulo = edtTitulo?.text.toString().trim()
                    nota.descripcion = edtDescripcion?.text.toString().trim()
                    mAddNotaPresenter?.addNota(nota)
                }
            }

            negativebuton.setOnClickListener {
                dismiss()
            }
        }
        mAddNotaPresenter?.onShow()
    }

    override fun enableUIelements() {
        edtTitulo?.isEnabled=true
        edtDescripcion?.isEnabled=true
    }

    override fun disableUIelements() {
        edtTitulo?.isEnabled=false
        edtDescripcion?.isEnabled=false
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun notaAdded() {
        Toast.makeText(activity,R.string.addnotas_success,Toast.LENGTH_LONG).show()
        dismiss()
    }

    override fun showError(msg: Int) {
        //Toast.makeText(activity,msg,Toast.LENGTH_LONG).show()
        Snackbar.make(contenMain!!,msg,Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.addnotas_action, object: View.OnClickListener{
                override fun onClick(v: View?) {

                }

            }).show()
    }

    override fun maxvalueError(msg: Int) {
        edtDescripcion?.setError(getString(msg))
        edtDescripcion?.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAddNotaPresenter?.onDestroy()
    }

}
