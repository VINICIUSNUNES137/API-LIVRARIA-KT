package br.senai.sp.jandira.retrofit_api_livraria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class Cadastro_Categoria : AppCompatActivity() {

    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_categoria)
        //Conecta apiService com a API Rest através da classe RetrofitHelper
        //E seu método getInstace()
        apiService = RetrofitHelper.getInstance().create(ApiService::class.java)

        //Trata a ação de click ou toque do botão cadastrar
        val txtCategoria = findViewById<EditText>(R.id.txtCategoria)
        findViewById<Button>(R.id.btnCadastrarCategoria).setOnClickListener {
            //Recupera o dado digitado pelo usuário
            val nomeCategoria = txtCategoria.text
            //E envia a requisição de cadastro para a API
            createCategory(nomeCategoria.toString())
        }
    }

    //Implementação do método CreateCategory()
    private fun createCategory(nome_categoria: String){
        lifecycleScope.launch {
            //Montagem do corpo de dados em JSON
            val body = JsonObject().apply {
                addProperty("nome_categoria", nome_categoria)
            }
            //Envio da requisição de cadastro de categoria
            val result = apiService.createCategory(body)

            //Verifica a resposta da requisição
            if(result.isSuccessful){
                val msg = result.body()?.get("mensagemStatus")
                Log.e("CREATE-CATEGORY", "STATUS: $msg")
            }
            else{
                Log.e("CREATE-CATEGORY", "ERROR: ${result}")
            }
        }
    }
}