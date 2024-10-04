package com.pitercapistrano.agendadecontatos
// Declara o pacote onde a classe CadastrarContato está localizada.

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
// Define a classe CadastrarContato, responsável pelo cadastro de novos usuários.

    private lateinit var binding: ActivityCadastrarContatoBinding
    // Declara a variável "binding" para acesso aos elementos da interface (usando view binding).

    private var usuarioDao: UsuarioDao? = null
    // Declara a variável "usuarioDao", usada para interagir com o banco de dados.

    private val listaUsuarios: MutableList<Usuario> = mutableListOf()
    // Cria uma lista mutável de usuários para armazenar temporariamente os dados.

    override fun onCreate(savedInstanceState: Bundle?) {
        // Método onCreate que inicializa a atividade quando ela é criada.

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Ativa o modo de exibição de interface Edge-to-Edge, que utiliza toda a tela, incluindo as barras de status e navegação.

        binding = ActivityCadastrarContatoBinding.inflate(layoutInflater)
        // Inicializa o binding para inflar o layout da tela de cadastro de contato.

        setContentView(binding.root)
        // Define o layout da atividade como o conteúdo da tela.

        // Define o comportamento do botão "Cadastrar", que inicia o processo de cadastro.
        binding.btCadastrarContato.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // Cria uma coroutine para realizar operações de banco de dados em uma thread de I/O.

                val nome = binding.editNome.text.toString()
                // Recupera o valor digitado no campo de nome.

                val sobrenome = binding.editSobrenome.text.toString()
                // Recupera o valor digitado no campo de sobrenome.

                val email = binding.editEmail.text.toString()
                // Recupera o valor digitado no campo de e-mail.

                val telefone = binding.editTelefone.text.toString()
                // Recupera o valor digitado no campo de telefone.

                val mensagem: Boolean
                // Variável para verificar se os campos estão preenchidos corretamente.

                // Verifica se algum campo está vazio.
                if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                    mensagem = false
                    // Se houver campos vazios, define a mensagem como falsa.
                } else {
                    mensagem = true
                    // Se todos os campos estão preenchidos, define a mensagem como verdadeira e chama a função cadastrar.
                    cadastrar(nome, sobrenome, email, telefone)
                }

                withContext(Dispatchers.Main) {
                    // Retorna para a thread principal para exibir mensagens ao usuário.

                    if (mensagem == true) {
                        // Se a mensagem for verdadeira, exibe um Toast de sucesso e redireciona para a MainActivity.

                        Toast.makeText(
                            applicationContext,
                            "Sucesso ao cadastrar contato!",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        // Inicia a MainActivity após o cadastro.
                    } else {
                        // Se a mensagem for falsa, exibe um Toast pedindo para preencher todos os campos.
                        Toast.makeText(
                            applicationContext,
                            "Preencha todos os campos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        // Configura o layout para se ajustar às margens das barras de sistema, como a barra de navegação.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Função para cadastrar o contato no banco de dados.
    private fun cadastrar(nome: String, sobrenome: String, email: String, telefone: String) {
        val usuario = Usuario(nome, sobrenome, email, telefone)
        // Cria um novo objeto Usuario com os dados fornecidos.

        listaUsuarios.add(usuario)
        // Adiciona o novo usuário à lista de usuários.

        usuarioDao = AppDatabase.getInstance(this).usuarioDao()
        // Obtém a instância do DAO de usuário a partir do banco de dados.

        usuarioDao!!.inserir(listaUsuarios)
        // Insere a lista de usuários no banco de dados através do método inserir do DAO.
    }
}
