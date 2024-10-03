package com.pitercapistrano.agendadecontatos

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.pitercapistrano.agendadecontatos.adapter.ContatoAdapter
import com.pitercapistrano.agendadecontatos.dao.UsuarioDao
import com.pitercapistrano.agendadecontatos.databinding.ActivityMainBinding
import com.pitercapistrano.agendadecontatos.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioDao: UsuarioDao
    private lateinit var contatoAdapter: ContatoAdapter
    private val _listaUsuarios = MutableLiveData<MutableList<Usuario>>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        CoroutineScope(Dispatchers.IO).launch {
            getContatos()

            withContext(Dispatchers.Main){

                _listaUsuarios.observe(this@MainActivity){ listaUsuarios ->
                    val recyclerViewContatos = binding.recyclerViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContatos.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuarios)
                    recyclerViewContatos.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()
                }
            }
        }

        // Configura a cor da barra de status como roxa
        window.statusBarColor = Color.parseColor("#673AB7") // Altera a cor da barra de status para roxa
        window.decorView.systemUiVisibility = 0 // Define a visibilidade do sistema UI para o padrão

        binding.btCadastrar.setOnClickListener {
            val intent = Intent(this, CadastrarContato::class.java)
            startActivity(intent)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {
            getContatos()

            withContext(Dispatchers.Main){

                _listaUsuarios.observe(this@MainActivity){ listaUsuarios ->
                    val recyclerViewContatos = binding.recyclerViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContatos.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuarios)
                    recyclerViewContatos.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getContatos(){
        usuarioDao = AppDatabase.getInstance(this).usuarioDao()
        val listaUsuarios: MutableList<Usuario> = usuarioDao.get()

        _listaUsuarios.postValue(listaUsuarios)
    }
}