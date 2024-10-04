package com.pitercapistrano.agendadecontatos.model
// Declara o pacote onde a classe "Usuario" está localizada, que representa um modelo de dados da aplicação.

import androidx.room.ColumnInfo
// Importa a anotação ColumnInfo do Room, usada para definir e personalizar as colunas da tabela no banco de dados.

import androidx.room.Entity
// Importa a anotação Entity do Room, usada para marcar a classe como uma entidade que mapeia uma tabela no banco de dados.

import androidx.room.PrimaryKey
// Importa a anotação PrimaryKey do Room, usada para definir a chave primária da tabela no banco de dados.

@Entity(tableName = "tabela_usuarios")
// Marca a classe como uma entidade do Room e define que ela representará a tabela chamada "tabela_usuarios" no banco de dados.

data class Usuario (
// Declara uma classe de dados chamada "Usuario", que será usada para mapear os dados da tabela "tabela_usuarios".

    @ColumnInfo(name = "nome") val nome: String,
    // Define que a propriedade "nome" será mapeada para a coluna "nome" da tabela no banco de dados.

    @ColumnInfo(name = "sobrenome") val sobrenome: String,
    // Define que a propriedade "sobrenome" será mapeada para a coluna "sobrenome" da tabela no banco de dados.

    @ColumnInfo(name = "email") val email: String,
    // Define que a propriedade "email" será mapeada para a coluna "email" da tabela no banco de dados.

    @ColumnInfo(name = "telefone") val telefone: String
    // Define que a propriedade "telefone" será mapeada para a coluna "telefone" da tabela no banco de dados.

){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
    // Define a propriedade "uid" como a chave primária da tabela, sendo gerada automaticamente pelo banco de dados para cada novo registro.
}
