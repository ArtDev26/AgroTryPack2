package com.agrokasa.agrotrypackv2

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.agrokasa.agrotrypackv2.databinding.CardviewBinding
import com.agrokasa.agrotrypackv2.data.Trabajador

class TrabajadorAdapter(private val trabajadores: List<Trabajador>):
    RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        val binding = CardviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrabajadorViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        holder.bind(trabajadores[position])
    }
    override fun getItemCount(): Int = trabajadores.size

    class TrabajadorViewHolder(private val binding: CardviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(trabajador:Trabajador){
            binding.tvNombreTrabajadorCV.text = trabajador.nombre
            binding.tvCodigoTrabajadorCV.text = trabajador.codigo
            binding.tvLaborTrabajadorCV.text = trabajador.labor
            binding.imgTrabajadorCV.setImageResource(trabajador.imagen)

        }
    }




}