package com.pitercapistrano.agendadecontatos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pitercapistrano.agendadecontatos.dao.UsuarioDao
import com.pitercapistrano.agendadecontatos.databinding.ActivityAtualizarUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtualizarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarUsuarioBinding
    private lateinit var usuarioDao: UsuarioDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAtualizarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val nomeRecuperado = intent.extras?.getString("nome")
        val sobrenomeRecuperado = intent.extras?.getString("sobrenome")
        val emailRecuperado = intent.extras?.getString("email")
        val telefoneRecuperado = intent.extras?.getString("telefone")
        val uid = intent.extras!!.getInt("uid")

        binding.editNome.setText(nomeRecuperado)
        binding.editSobrenome.setText(sobrenomeRecuperado)
        binding.editEmail.setText(emailRecuperado)
        binding.editTelefone.setText(telefoneRecuperado)

        binding.btAtualizarContato.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val nome = binding.editNome.text.toString()
                val sobrenome = binding.editSobrenome.text.toString()
                val email = binding.editEmail.text.toString()
                val telefone = binding.editTelefone.text.toString()
                val mensagem: Boolean

                if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                    mensagem = false
                } else {
                    mensagem = true
                    atualizarContato(uid, nome, sobrenome, email, telefone)
                }

                withContext(Dispatchers.Main) {
                    if (mensagem == true) {
                        Toast.makeText(
                            this@AtualizarUsuario,
                            "Sucesso ao atualizar o usuário!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@AtualizarUsuario,
                            "Preencha todos os campos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun atualizarContato(uid: Int, nome: String, sobrenome: String, email: String, telefone: String){
        usuarioDao = AppDatabase.getInstance(this).usuarioDao()
        usuarioDao.atualizar(uid, nome, sobrenome, email, telefone)
    }
}