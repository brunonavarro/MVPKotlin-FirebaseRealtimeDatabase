package com.brunonavarro.misnotas.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.R

class HomeAdapter(
    private val festividad: MutableList<Nota>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_notas, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val festividades = festividad[position]

        holder.setOnClickListener(festividades, listener)

        holder.titulo.text = context!!.getString(
            R.string.item_notas_titulo,
            festividades.titulo
        )
        holder.descripcion.text = context!!.getString(
            R.string.item_notas_descripcion,
            festividades.descripcion
        )
    }

    override fun getItemCount(): Int {
        return festividad.size
    }

    fun add(festividades: Nota) {
        if (!festividad.contains(festividades)) {
            festividad.add(festividades)
            notifyItemInserted(festividad.size - 1)
            println("FESTIVIDAD-ADAPTER - Add: $festividades")
        } else {
            update(festividades)
        }
    }

    fun update(festividades: Nota) {
        if (festividad.contains(festividades)) {
            val pos = festividad.indexOf(festividades)
            festividad[pos] = festividades
            notifyItemChanged(pos)
            println("FESTIVIDAD-ADAPTER - Update: $pos")
        }
    }

    fun remove(festividades: Nota) {
        if (festividad.contains(festividades)) {
            val pos = festividad.indexOf(festividades)
            festividad.removeAt(pos)
            notifyItemRemoved(pos)
            println("FESTIVIDAD-ADAPTER - Remove: $pos")
        }
    }

    inner class ViewHolder internal constructor(private val view: View) :
        RecyclerView.ViewHolder(view) {
        /*@BindView(R.id.titulo)
        TextView titulo;
        @BindView(R.id.fecha)
        TextView fecha;*/
        var titulo: TextView
        var descripcion: TextView

        init {
            //ButterKnife.bind(this,itemView);
            titulo = view.findViewById<View>(R.id.txtTitulo) as TextView
            descripcion = view.findViewById<View>(R.id.txtDescripcion) as TextView
        }

        internal fun setOnClickListener(festividades: Nota, listener: OnItemClickListener) {
            view.setOnClickListener { listener.onItemClick(festividades) }
            view.setOnLongClickListener {
                listener.onLongItemClick(festividades)
                true
            }
        }
    }
}
