package com.pitercapistrano.agendadecontatos.dao // Declara o pacote onde a classe está localizada

import androidx.room.Dao // Importa a anotação Dao do Room, usada para definir a interface de acesso ao banco de dados
import androidx.room.Delete
import androidx.room.Insert // Importa a anotação Insert para inserir dados no banco de dados
import androidx.room.Query
import com.pitercapistrano.agendadecontatos.model.Usuario // Importa a classe Usuario, que representa a entidade do banco de dados

@Dao // Anota a interface como um DAO (Data Access Object) para a entidade Usuario
interface UsuarioDao { // Declara a interface UsuarioDao para manipular dados no banco de dados

    @Insert // Anota o método como uma operação de inserção de dados no banco
    fun inserir(listaUsuarios: MutableList<Usuario>) // Método que insere uma lista mutável de objetos Usuario no banco de dados

    @Query("SELECT * FROM tabela_usuarios ORDER BY nome ASC")
    fun get(): MutableList<Usuario>

    @Query("UPDATE tabela_usuarios SET nome = :novoNome, sobrenome = :novoSobrenome, email = :novoEmail, telefone = :novoTelefone " +
    "WHERE uid = :id")
    fun atualizar(id: Int, novoNome: String, novoSobrenome: String, novoEmail: String, novoTelefone: String)

    @Query("DELETE FROM tabela_usuarios WHERE uid = :id")
    fun delete(id: Int)
}