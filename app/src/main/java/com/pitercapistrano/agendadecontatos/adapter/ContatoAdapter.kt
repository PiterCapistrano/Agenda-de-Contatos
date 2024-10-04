package com.pitercapistrano.agendadecontatos.adapter // Declara o pacote onde o adaptador está localizado

import android.annotation.SuppressLint // Importa a anotação para suprimir avisos específicos do compilador
import android.content.Context // Importa a classe Context, necessária para acessar recursos do sistema e do app
import android.content.Intent // Importa a classe Intent, usada para navegar entre atividades
import android.view.LayoutInflater // Importa o LayoutInflater, usado para inflar layouts em views
import android.view.ViewGroup // Importa o ViewGroup, usado como container para as views do RecyclerView
import android.widget.Toast // Importa a classe Toast, usada para exibir mensagens curtas ao usuário
import androidx.recyclerview.widget.RecyclerView // Importa o RecyclerView para exibir listas de itens de forma otimizada
import com.pitercapistrano.agendadecontatos.AppDatabase // Importa o banco de dados da aplicação
import com.pitercapistrano.agendadecontatos.AtualizarUsuario // Importa a atividade para atualizar os dados do usuário
import com.pitercapistrano.agendadecontatos.dao.UsuarioDao // Importa a interface de acesso a dados do usuário (DAO)
import com.pitercapistrano.agendadecontatos.databinding.ContatoItemBinding // Importa o binding para acessar os componentes de layout
import com.pitercapistrano.agendadecontatos.model.Usuario // Importa o modelo Usuario, representando o objeto da lista
import kotlinx.coroutines.CoroutineScope // Importa a biblioteca de corrotinas para processamento assíncrono
import kotlinx.coroutines.Dispatchers // Importa os dispatchers que controlam em quais threads as corrotinas são executadas
import kotlinx.coroutines.launch // Importa a função launch para iniciar corrotinas
import kotlinx.coroutines.withContext // Importa a função withContext para mudar o contexto da corrotina (thread)

class ContatoAdapter(private val context: Context, private val listaUsuarios: MutableList<Usuario>):
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {
    // Classe do adaptador, que recebe o contexto e a lista de usuários para exibição no RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        // Método para inflar o layout do item de contato quando um novo ViewHolder é criado
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context), parent, false) // Infla o layout usando o binding
        return ContatoViewHolder(itemLista) // Retorna um novo ViewHolder com o layout inflado
    }

    override fun getItemCount() = listaUsuarios.size
    // Retorna o tamanho da lista de usuários, ou seja, o número total de itens que serão exibidos

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        // Método chamado para vincular os dados de um item de usuário à interface do ViewHolder
        holder.txtNome.text = listaUsuarios[position].nome // Define o nome do usuário na TextView correspondente
        holder.txtSobrenome.text = listaUsuarios[position].sobrenome // Define o sobrenome do usuário
        holder.txtEmail.text = listaUsuarios[position].email // Define o email do usuário
        holder.txtTelefone.text = listaUsuarios[position].telefone // Define o telefone do usuário

        holder.btEdit.setOnClickListener {
            // Define o comportamento para o botão de editar o contato
            val intent = Intent(context, AtualizarUsuario::class.java) // Cria uma intent para abrir a atividade de atualização
            intent.putExtra("nome", listaUsuarios[position].nome) // Passa o nome do usuário como extra para a nova atividade
            intent.putExtra("sobrenome", listaUsuarios[position].sobrenome) // Passa o sobrenome
            intent.putExtra("email", listaUsuarios[position].email) // Passa o email
            intent.putExtra("telefone", listaUsuarios[position].telefone) // Passa o telefone
            intent.putExtra("uid", listaUsuarios[position].uid) // Passa o UID (identificador único)

            context.startActivity(intent) // Inicia a atividade de atualização
        }

        holder.btDelete.setOnClickListener {
            // Define o comportamento para o botão de deletar o contato
            CoroutineScope(Dispatchers.IO).launch {
                // Inicia uma corrotina para realizar a operação de exclusão no banco de dados em uma thread de I/O
                val usuario = listaUsuarios[position] // Pega o usuário na posição atual
                val usuarioDao: UsuarioDao = AppDatabase.getInstance(context).usuarioDao() // Obtém o DAO do banco de dados
                usuarioDao.delete(usuario.uid) // Deleta o usuário com base no UID
                listaUsuarios.remove(usuario) // Remove o usuário da lista local

                withContext(Dispatchers.Main){
                    // Muda o contexto da corrotina para a thread principal para atualizar a interface
                    notifyDataSetChanged() // Notifica o RecyclerView sobre a remoção do item
                    Toast.makeText(context, "Contato deletado com sucesso!", Toast.LENGTH_SHORT).show() // Exibe uma mensagem de sucesso
                }
            }
        }
    }

    inner class ContatoViewHolder(binding: ContatoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Classe ViewHolder para gerenciar a exibição de cada item do RecyclerView
        val txtNome = binding.txtNome // Referência para o TextView que exibe o nome
        val txtSobrenome = binding.txtSobrenome // Referência para o TextView que exibe o sobrenome
        val txtEmail = binding.txtEmail // Referência para o TextView que exibe o email
        val txtTelefone = binding.txtTelefone // Referência para o TextView que exibe o telefone
        val btEdit = binding.btEdit // Referência para o botão de editar
        val btDelete = binding.btDelete // Referência para o botão de deletar
    }
}
