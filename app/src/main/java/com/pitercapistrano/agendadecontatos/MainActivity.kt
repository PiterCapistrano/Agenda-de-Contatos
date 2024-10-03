package com.pitercapistrano.agendadecontatos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pitercapistrano.agendadecontatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        // Configura a cor da barra de status como roxa
        window.statusBarColor = Color.parseColor("#673AB7") // Altera a cor da barra de status para roxa
        window.decorView.systemUiVisibility = 0 // Define a visibilidade do sistema UI para o padrão

        binding.btCadastrar.setOnClickListener {
            val intent = Intent(this, CadastrarContato::class.java)
            startActivity(intent)
        }

    }
}