package com.pitercapistrano.agendadecontatos // Declara o pacote onde o código está localizado

import android.content.Context // Importa a classe Context, que fornece informações sobre o ambiente do app
import androidx.room.Database // Importa a anotação Database do Room, usada para definir o banco de dados
import androidx.room.Room // Importa a classe Room, usada para construir o banco de dados
import androidx.room.RoomDatabase // Importa a classe RoomDatabase, que é a classe base para o banco de dados Room
import com.pitercapistrano.agendadecontatos.dao.UsuarioDao // Importa a interface UsuarioDao para realizar operações no banco de dados
import com.pitercapistrano.agendadecontatos.model.Usuario // Importa a entidade Usuario, que representa a tabela do banco de dados

@Database(entities = [Usuario::class], version = 1) // Anota a classe como um banco de dados do Room, com a entidade Usuario e versão 1
abstract class AppDatabase: RoomDatabase() { // Declara a classe AppDatabase como uma subclasse de RoomDatabase

    abstract fun usuarioDao(): UsuarioDao // Declara um método abstrato que retorna uma instância de UsuarioDao

    companion object{ // Declara um bloco companion para definir membros estáticos (compartilhados)

        private const val DATABASE_NOME: String = "DB_USUARIOS" // Define uma constante com o nome do banco de dados

        @Volatile // Anota a variável para garantir que as alterações sejam visíveis em todas as threads
        private var INSTANCE: AppDatabase? = null // Variável que armazena a instância única do banco de dados

        fun getInstance(context: Context): AppDatabase{ // Método para obter a instância única do banco de dados
            return INSTANCE ?: synchronized(this){ // Verifica se já existe uma instância, senão sincroniza para criar uma
                val instance = Room.databaseBuilder( // Cria o banco de dados usando o Room
                    context.applicationContext, // Usa o contexto da aplicação para evitar vazamento de memória
                    AppDatabase::class.java, // Define a classe do banco de dados
                    DATABASE_NOME // Define o nome do banco de dados
                ).build() // Constrói o banco de dados

                INSTANCE = instance // Atribui a instância recém-criada à variável INSTANCE
                instance // Retorna a instância do banco de dados
            }
        }
    }
}
