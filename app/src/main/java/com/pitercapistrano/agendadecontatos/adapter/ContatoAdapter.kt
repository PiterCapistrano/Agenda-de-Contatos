package com.pitercapistrano.agendadecontatos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pitercapistrano.agendadecontatos.databinding.ContatoItemBinding
import com.pitercapistrano.agendadecontatos.model.Usuario



class ContatoAdapter(private val context: Context, private val listaUsuarios: MutableList<Usuario>):
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContatoViewHolder(itemLista)
    }

    override fun getItemCount() = listaUsuarios.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.txtNome.text = listaUsuarios[position].nome
        holder.txtSobrenome.text = listaUsuarios[position].sobrenome
        holder.txtEmail.text = listaUsuarios[position].email
        holder.txtTelefone.text = listaUsuarios[position].telefone
    }

    inner class ContatoViewHolder(binding: ContatoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtNome = binding.txtNome
        val txtSobrenome = binding.txtSobrenome
        val txtEmail = binding.txtEmail
        val txtTelefone = binding.txtTelefone
        val btEdit = binding.btEdit
        val btDelete = binding.btDelete

    }
}