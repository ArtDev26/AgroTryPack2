package com.agrokasa.agrotrypackv2

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.agrokasa.agrotrypackv2.databinding.CardviewBinding
import com.agrokasa.agrotrypackv2.databinding.RecyclerViewHeaderBinding
import com.agrokasa.agrotrypackv2.data.Trabajador
import com.agrokasa.agrotrypackv2.databinding.DialogOptionsBinding

class TrabajadorAdapter(
    private val trabajadores: List<Trabajador>,
    private val numeroGrupo: String,
    private val onNuevoGrupoClick: () -> Unit,
    private val onHistorialClick: () -> Unit,
    private val onCambiarGrupoClick: (Trabajador) -> Unit,
    private val onEliminarClick: (Trabajador) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val binding = RecyclerViewHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(binding)
        } else {
            val binding = CardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TrabajadorViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(numeroGrupo, onNuevoGrupoClick, onHistorialClick)
        } else if (holder is TrabajadorViewHolder) {
            holder.bind(trabajadores[position - 1])
            holder.itemView.setOnClickListener{
                showDialog(holder.itemView.context, trabajadores[position - 1])
            }
        }
    }

    private fun showDialog(context: Context, trabajador: Trabajador) {
        val dialogBinding = DialogOptionsBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context)
            .setView(dialogBinding.root)
            .create()
        dialogBinding.btnCambiarGrupo.setOnClickListener{
            onCambiarGrupoClick(trabajador)
            dialog.dismiss()
        }
        dialogBinding.btnEliminar.setOnClickListener{
            onEliminarClick(trabajador)
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun getItemCount(): Int = trabajadores.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    class HeaderViewHolder(private val binding: RecyclerViewHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(numeroGrupo: String, onNuevoGrupoClick: () -> Unit, onHistorialClick: () -> Unit) {
            binding.tvNumeroGrupo.text = "Grupo: $numeroGrupo"
            binding.btnNuevoGrupo.setOnClickListener { onNuevoGrupoClick() }
            binding.btnHistorial.setOnClickListener { onHistorialClick() }
        }
    }

    class TrabajadorViewHolder(private val binding: CardviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trabajador: Trabajador) {
            binding.tvNombreTrabajadorCV.text = trabajador.nombre
            binding.tvCodigoTrabajadorCV.text = trabajador.codigo
            binding.tvLaborTrabajadorCV.text = trabajador.labor
            binding.imgTrabajadorCV.setImageResource(trabajador.imagen)
        }
    }
}
