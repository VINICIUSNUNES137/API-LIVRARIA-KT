package br.senai.sp.jandira.retrofit_api_livraria
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitHelper {
    object RetrofitHelper {

        private const val baseurl = "http://localhost:3000"

        fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}