package br.senai.sp.jandira.retrofit_api_livraria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.JsonObject


class CadastroLivroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_livro)
        //DECLARANDO E RECUPERANDO OS OBJETOS DE VIEW
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtPreco = findViewById<EditText>(R.id.txtPreco)
        val txtCategoria = findViewById<EditText>(R.id.txtCategoria)
        val txtDescricao = findViewById<EditText>(R.id.txtLivroDescricao)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrarLivro)

        //tratamento da ação do clique do botão cadastrar

        btnCadastrar.setOnClickListener {

            //inputs
            val titulo = txtTitulo.text.toString()
            val preco = txtPreco.text.toString()
            val categoria = txtCategoria.text.toString()
            val descricao = txtDescricao.text.toString()

            //montagem do json

            val body = JsonObject().apply {
                addProperty("titulo", "$titulo")
                addProperty("preco", "$preco")
                addProperty("categoria", "$categoria")
                addProperty("descricao", "$descricao")
            }

            Log.i("BODY-JSON", "$body")

            //NAVEGAÇÃO PARA A TELA DE IMAGENS DE LIVRO

            val intent = Intent(
                this,
                CadastroLivroImagemActivity::class.java
            ).apply {
                putExtra("bodyJSON", "$body")
            }
            startActivity(intent)
            //
        }
    }
}