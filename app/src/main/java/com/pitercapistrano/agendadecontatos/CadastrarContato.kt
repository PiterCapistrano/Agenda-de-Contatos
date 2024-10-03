package com.pitercapistrano.agendadecontatos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pitercapistrano.agendadecontatos.dao.UsuarioDao
import com.pitercapistrano.agendadecontatos.databinding.ActivityCadastrarContatoBinding
import com.pitercapistrano.agendadecontatos.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastrarContato : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarContatoBinding
    private var usuarioDao: UsuarioDao? = null
    private val listaUsuarios: MutableList<Usuario> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastrarContatoBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.btCadastrarContato.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {

                val nome = binding.editNome.text.toString()
                val sobrenome = binding.editSobrenome.text.toString()
                val email = binding.editEmail.text.toString()
                val telefone = binding.editTelefone.text.toString()
                val mensagem: Boolean

                if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || telefone.isEmpty()){
                    mensagem = false
                }else{
                    mensagem = true
                    cadastrar(nome, sobrenome, email, telefone)
                }
                    withContext(Dispatchers.Main){
                        if (mensagem == true){
                            Toast.makeText(applicationContext, "Sucesso ao cadastrar contato!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(applicationContext, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun cadastrar(nome: String, sobrenome: String, email: String, telefone: String){
        val usuario = Usuario(nome, sobrenome, email, telefone)

        listaUsuarios.add(usuario)

        usuarioDao = AppDatabase.getInstance(this).usuarioDao()
        usuarioDao!!.inserir(listaUsuarios)
    }
}