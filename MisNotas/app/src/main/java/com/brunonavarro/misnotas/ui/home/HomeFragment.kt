package com.brunonavarro.misnotas.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.misnotas.R
import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.presenter.Home.HomePresenter
import com.brunonavarro.misnotas.ui.addNota.AddNotaFragment
import com.brunonavarro.misnotas.ui.home.adapter.HomeAdapter
import com.brunonavarro.misnotas.ui.home.adapter.OnItemClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.add_notas_dialog.view.*
import java.util.ArrayList

class HomeFragment : Fragment(),HomeContract.View, OnItemClickListener {

    private var homePresenter:HomePresenter?=null
    private var notaAdapter:HomeAdapter?=null

    private var rvNotas:RecyclerView?=null
    private var progressBar:ProgressBar?=null

    var contentMain:View?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        contentMain=root
        val fbutton: FloatingActionButton=root.findViewById(R.id.floatingActionButton2)
        rvNotas=root.findViewById(R.id.rv_listNotas)
        progressBar=root.findViewById(R.id.progressBar)
        rvNotas?.layoutManager = LinearLayoutManager(context)

        configAdapter()
        configRecyclerView()

        homePresenter=HomePresenter(this)
        homePresenter?.onCreate()

        fbutton.setOnClickListener {
            cuadroDialogo()
        }
        return root
    }

    private fun configAdapter() {
        notaAdapter=HomeAdapter(ArrayList<Nota>(),this)
    }

    private fun configRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        rvNotas?.setLayoutManager(linearLayoutManager)
        rvNotas?.setAdapter(notaAdapter)
    }

    private fun cuadroDialogo(){
        AddNotaFragment().show(fragmentManager,"Titulo")
    }

    override fun onItemClick(nota: Nota) {
        Snackbar.make(contentMain!!,"CLICK: "+nota.titulo,Snackbar.LENGTH_LONG).show()
    }

    override fun onLongItemClick(nota: Nota) {
        Snackbar.make(contentMain!!,"LONG CLICK: "+nota.titulo,Snackbar.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressBar?.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility=View.GONE
    }

    override fun add(nota: Nota) {
        notaAdapter?.add(nota)
    }

    override fun update(nota: Nota) {
        notaAdapter?.update(nota)
    }

    override fun remove(nota: Nota) {
        notaAdapter?.remove(nota)
    }

    override fun removeFail() {
        Snackbar.make(contentMain!!, R.string.notas_error_to_remove, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onShowError(resMsg: Int) {
        Snackbar.make(contentMain!!, resMsg, Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        homePresenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        homePresenter?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter?.onDestroy()
    }

}