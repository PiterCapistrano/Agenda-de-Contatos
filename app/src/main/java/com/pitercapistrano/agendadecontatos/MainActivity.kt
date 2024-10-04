package com.pitercapistrano.agendadecontatos
// Declara o pacote onde a classe MainActivity está localizada.

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
// Define a classe MainActivity, que será a tela principal da aplicação de contatos.

    private lateinit var binding: ActivityMainBinding
    // Declara a variável "binding" para acessar os elementos da interface com view binding.

    private lateinit var usuarioDao: UsuarioDao
    // Declara a variável "usuarioDao" para interagir com o banco de dados.

    private lateinit var contatoAdapter: ContatoAdapter
    // Declara a variável "contatoAdapter" para gerenciar a exibição dos contatos no RecyclerView.

    private val _listaUsuarios = MutableLiveData<MutableList<Usuario>>()
    // Cria uma MutableLiveData que armazenará a lista de usuários e permitirá observar mudanças.

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Método onCreate, que é chamado quando a atividade é criada.

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Ativa a exibição em tela cheia (Edge-to-Edge), usando o espaço das barras de status e navegação.

        binding = ActivityMainBinding.inflate(layoutInflater)
        // Inicializa o binding para inflar o layout da atividade principal.

        setContentView(binding.root)
        // Define o layout da atividade como o conteúdo principal da tela.

        // Adiciona margens para as barras de sistema (status e navegação) no layout principal.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicia uma coroutine para buscar os contatos no banco de dados.
        CoroutineScope(Dispatchers.IO).launch {
            getContatos()
            // Chama a função para buscar contatos do banco de dados.

            // Atualiza a interface na thread principal após obter os dados.
            withContext(Dispatchers.Main) {

                // Observa as mudanças na lista de usuários e atualiza o RecyclerView.
                _listaUsuarios.observe(this@MainActivity) { listaUsuarios ->
                    val recyclerViewContatos = binding.recyclerViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    // Configura o layout do RecyclerView como uma lista vertical.

                    recyclerViewContatos.setHasFixedSize(true)
                    // Define que o tamanho do RecyclerView não mudará.

                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuarios)
                    // Inicializa o adaptador com a lista de usuários.

                    recyclerViewContatos.adapter = contatoAdapter
                    // Define o adaptador do RecyclerView para exibir os contatos.

                    contatoAdapter.notifyDataSetChanged()
                    // Notifica o adaptador para atualizar a exibição dos dados.
                }
            }
        }

        // Configura a cor da barra de status como roxa.
        window.statusBarColor = Color.parseColor("#673AB7")
        // Altera a cor da barra de status para roxa.

        window.decorView.systemUiVisibility = 0
        // Define a visibilidade da interface do sistema para o padrão (barra de status visível).

        // Define o clique do botão para abrir a tela de cadastro de contatos.
        binding.btCadastrar.setOnClickListener {
            val intent = Intent(this, CadastrarContato::class.java)
            // Cria um Intent para abrir a atividade de cadastro de contato.

            startActivity(intent)
            // Inicia a atividade de cadastro de contato.
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        // Método chamado quando a atividade volta a ser exibida (quando retomada).

        super.onResume()

        // Recarrega os contatos quando a atividade é retomada.
        CoroutineScope(Dispatchers.IO).launch {
            getContatos()
            // Chama a função para buscar os contatos.

            withContext(Dispatchers.Main) {
                // Observa e atualiza a lista de contatos na UI.
                _listaUsuarios.observe(this@MainActivity) { listaUsuarios ->
                    val recyclerViewContatos = binding.recyclerViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    // Configura o layout para o RecyclerView.

                    recyclerViewContatos.setHasFixedSize(true)
                    // Mantém o tamanho fixo do RecyclerView.

                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuarios)
                    // Inicializa o adaptador com a lista de usuários.

                    recyclerViewContatos.adapter = contatoAdapter
                    // Define o adaptador no RecyclerView.

                    contatoAdapter.notifyDataSetChanged()
                    // Notifica o adaptador para atualizar os dados exibidos.
                }
            }
        }
    }

    // Função responsável por buscar os contatos no banco de dados.
    private fun getContatos() {
        usuarioDao = AppDatabase.getInstance(this).usuarioDao()
        // Obtém uma instância do DAO para acessar os dados.

        val listaUsuarios: MutableList<Usuario> = usuarioDao.get()
        // Busca a lista de usuários no banco de dados.

        _listaUsuarios.postValue(listaUsuarios)
        // Atualiza o valor da MutableLiveData com a lista de usuários.
    }
}
