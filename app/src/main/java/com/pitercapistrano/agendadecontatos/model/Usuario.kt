package com.pitercapistrano.agendadecontatos.model // Declara o pacote onde a classe está localizada

import androidx.room.ColumnInfo // Importa a anotação ColumnInfo do Room, usada para definir as colunas da tabela
import androidx.room.Entity // Importa a anotação Entity do Room, usada para definir a entidade do banco de dados
import androidx.room.PrimaryKey // Importa a anotação PrimaryKey do Room, usada para definir a chave primária da tabela

@Entity(tableName = "tabela_usuarios") // Anota a classe como uma entidade do Room, representando a tabela "tabela_usuarios" no banco de dados
data class Usuario ( // Declara a classe de dados Usuario, que representa a entidade no banco de dados
    @ColumnInfo(name = "nome") val nome: String, // Define a coluna "nome" da tabela e mapeia para a propriedade nome da classe
    @ColumnInfo(name = "sobrenome") val sobrenome: String, // Define a coluna "sobrenome" da tabela e mapeia para a propriedade sobrenome
    @ColumnInfo(name = "idade") val idade: String, // Define a coluna "idade" da tabela e mapeia para a propriedade idade
    @ColumnInfo(name = "telefone") val telefone: String // Define a coluna "telefone" da tabela e mapeia para a propriedade telefone
){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0 // Define a chave primária da tabela, o valor será gerado automaticamente
}
