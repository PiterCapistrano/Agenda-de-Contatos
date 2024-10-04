package com.pitercapistrano.agendadecontatos.dao
// Declara o pacote onde a classe UsuarioDao está localizada, relacionada ao acesso de dados da aplicação.

import androidx.room.Dao
// Importa a anotação Dao do Room, que marca a interface como um Data Access Object (DAO).

import androidx.room.Delete
// Importa a anotação Delete para indicar que um método é responsável por excluir dados no banco de dados.

import androidx.room.Insert
// Importa a anotação Insert, usada para definir métodos que inserem dados no banco de dados.

import androidx.room.Query
// Importa a anotação Query, usada para definir consultas SQL para selecionar, atualizar ou excluir dados.

import com.pitercapistrano.agendadecontatos.model.Usuario
// Importa a classe Usuario, que representa a entidade de usuários no banco de dados da aplicação.

@Dao
// Anota a interface como um DAO (Data Access Object), usada para definir as operações de banco de dados relacionadas à entidade Usuario.

interface UsuarioDao {
    // Declara a interface UsuarioDao, que conterá os métodos para manipulação de dados no banco de dados Room.

    @Insert
    // Anota o método abaixo como uma operação de inserção no banco de dados.

    fun inserir(listaUsuarios: MutableList<Usuario>)
    // Define o método para inserir uma lista mutável de objetos Usuario no banco de dados.

    @Query("SELECT * FROM tabela_usuarios ORDER BY nome ASC")
    // Declara uma consulta SQL para selecionar todos os registros da tabela de usuários, ordenando os resultados pelo nome de forma crescente.

    fun get(): MutableList<Usuario>
    // Método que retorna uma lista mutável de objetos Usuario com todos os registros do banco de dados.

    @Query("UPDATE tabela_usuarios SET nome = :novoNome, sobrenome = :novoSobrenome, email = :novoEmail, telefone = :novoTelefone WHERE uid = :id")
    // Declara uma consulta SQL para atualizar os dados de um usuário específico, identificado pelo UID, com base nos novos valores fornecidos.

    fun atualizar(id: Int, novoNome: String, novoSobrenome: String, novoEmail: String, novoTelefone: String)
    // Define o método para atualizar os campos nome, sobrenome, email e telefone de um usuário no banco de dados, usando o ID como referência.

    @Query("DELETE FROM tabela_usuarios WHERE uid = :id")
    // Declara uma consulta SQL para excluir um registro da tabela de usuários com base no UID (identificador único).

    fun delete(id: Int)
    // Define o método para excluir um usuário do banco de dados, identificado pelo UID.
}
