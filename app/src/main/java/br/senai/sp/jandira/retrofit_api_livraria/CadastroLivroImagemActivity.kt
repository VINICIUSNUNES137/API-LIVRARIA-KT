package br.senai.sp.jandira.retrofit_api_livraria

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class CadastroLivroImagemActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_livro_imagem)
        val json = intent.getStringExtra("bodyJSON")

        Log.i("JSONIMG", "$json")
    }
}