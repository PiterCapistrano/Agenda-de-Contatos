package com.pitercapistrano.agendadecontatos.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pitercapistrano.agendadecontatos.AppDatabase
import com.pitercapistrano.agendadecontatos.AtualizarUsuario
import com.pitercapistrano.agendadecontatos.dao.UsuarioDao
import com.pitercapistrano.agendadecontatos.databinding.ContatoItemBinding
import com.pitercapistrano.agendadecontatos.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContatoAdapter(private val context: Context, private val listaUsuarios: MutableList<Usuario>):
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContatoViewHolder(itemLista)
    }

    override fun getItemCount() = listaUsuarios.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.txtNome.text = listaUsuarios[position].nome
        holder.txtSobrenome.text = listaUsuarios[position].sobrenome
        holder.txtEmail.text = listaUsuarios[position].email
        holder.txtTelefone.text = listaUsuarios[position].telefone

        holder.btEdit.setOnClickListener {
           val intent = Intent(context, AtualizarUsuario::class.java)
            intent.putExtra("nome", listaUsuarios[position].nome)
            intent.putExtra("sobrenome", listaUsuarios[position].sobrenome)
            intent.putExtra("email", listaUsuarios[position].email)
            intent.putExtra("telefone", listaUsuarios[position].telefone)
            intent.putExtra("uid", listaUsuarios[position].uid)

            context.startActivity(intent)
        }

        holder.btDelete.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val usuario = listaUsuarios[position]
                val usuarioDao: UsuarioDao = AppDatabase.getInstance(context).usuarioDao()
                usuarioDao.delete(usuario.uid)
                listaUsuarios.remove(usuario)

                withContext(Dispatchers.Main){
                    notifyDataSetChanged()
                    Toast.makeText(context, "Contato deletado com sucesso!", Toast.LENGTH_SHORT).show()
                }
            }
        }
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