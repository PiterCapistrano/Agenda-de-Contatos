package com.pitercapistrano.agendadecontatos
// Declara o pacote onde a classe AtualizarUsuario está localizada.

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
// Define a classe AtualizarUsuario que herda de AppCompatActivity, responsável pela atualização de dados de um usuário.

    private lateinit var binding: ActivityAtualizarUsuarioBinding
    // Declara a variável "binding" para acessar os componentes da interface (view binding).

    private lateinit var usuarioDao: UsuarioDao
    // Declara a variável "usuarioDao" para acessar os métodos do DAO de usuário.

    override fun onCreate(savedInstanceState: Bundle?) {
        // Método onCreate que inicializa a atividade.

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Habilita o modo Edge-to-Edge para a interface da aplicação.

        binding = ActivityAtualizarUsuarioBinding.inflate(layoutInflater)
        // Inicializa o binding para inflar o layout da tela de atualização.

        setContentView(binding.root)
        // Define o layout que será exibido como conteúdo da atividade.

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Ajusta a interface para o modo Edge-to-Edge, aplicando margens nas áreas de sistema (barras de navegação e status).

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recupera os dados passados pela intent da atividade anterior.
        val nomeRecuperado = intent.extras?.getString("nome")
        val sobrenomeRecuperado = intent.extras?.getString("sobrenome")
        val emailRecuperado = intent.extras?.getString("email")
        val telefoneRecuperado = intent.extras?.getString("telefone")
        val uid = intent.extras!!.getInt("uid")
        // Recupera o ID do usuário (uid) passado pela intent.

        // Preenche os campos de texto com os dados recuperados.
        binding.editNome.setText(nomeRecuperado)
        binding.editSobrenome.setText(sobrenomeRecuperado)
        binding.editEmail.setText(emailRecuperado)
        binding.editTelefone.setText(telefoneRecuperado)

        // Define a ação do botão "Atualizar" para iniciar o processo de atualização.
        binding.btAtualizarContato.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                // Utiliza coroutines para realizar a operação de atualização em uma thread de I/O.

                val nome = binding.editNome.text.toString()
                val sobrenome = binding.editSobrenome.text.toString()
                val email = binding.editEmail.text.toString()
                val telefone = binding.editTelefone.text.toString()

                val mensagem: Boolean
                // Variável usada para verificar se os campos foram preenchidos corretamente.

                if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                    mensagem = false
                    // Se algum campo estiver vazio, define a mensagem como falsa.
                } else {
                    mensagem = true
                    // Se todos os campos estiverem preenchidos, define a mensagem como verdadeira.
                    atualizarContato(uid, nome, sobrenome, email, telefone)
                    // Chama o método que atualiza o contato no banco de dados.
                }

                withContext(Dispatchers.Main) {
                    // Retorna à thread principal para exibir um Toast com o resultado da operação.

                    if (mensagem == true) {
                        Toast.makeText(
                            this@AtualizarUsuario,
                            "Sucesso ao atualizar o usuário!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        // Fecha a atividade após a atualização.
                    } else {
                        Toast.makeText(
                            this@AtualizarUsuario,
                            "Preencha todos os campos!",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Exibe mensagem de erro se houver campos vazios.
                    }
                }
            }
        }
    }

    private fun atualizarContato(uid: Int, nome: String, sobrenome: String, email: String, telefone: String) {
        // Função para realizar a atualização de dados do contato no banco de dados.

        usuarioDao = AppDatabase.getInstance(this).usuarioDao()
        // Inicializa o DAO de usuário a partir da instância do banco de dados.

        usuarioDao.atualizar(uid, nome, sobrenome, email, telefone)
        // Chama o método de atualização do DAO, passando os novos dados.
    }
}
