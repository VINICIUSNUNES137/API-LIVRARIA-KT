package br.senai.sp.jandira.retrofit_api_livraria

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CadastroLivroImagemActivity : AppCompatActivity() {


    //ATRIBUTOS DE MANIPULAÇÃO DE ENDEREÇOS DAS IMAGENS
    private var imageUriPEQ: Uri? = null
    private var imageUriGRD: Uri? = null


    /* CONFIGURAÇÕES DO FIREBASE */

    //DECLARAÇÃO DO STORAGE
    private lateinit var storageRef: StorageReference

    //DECLARAÇÃO DO FIRESTORE DATABASE
    private lateinit var firebaseFirestore: FirebaseFirestore

    /* OBJETOS DE VIEW NA TELA */

    //IMAGEVIEW
    private var btnImgGRD: ImageView? = null
    private var btnImgPEQ: ImageView? = null

    //BUTTON
    private var btnUpload: Button? = null


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_livro_imagem)
        val json = intent.getStringExtra("bodyJSON")

        initVars()
        Log.i("JSONIMG", "$json")

        //RECUPERA OS ELEMENTOS DE VIEW DE IMAGENS
        btnImgGRD = findViewById<ImageView>(R.id.imgGRD)
        btnImgPEQ = findViewById<ImageView>(R.id.imgPEQ)

        //RECUPERA OS ELEMENTO DE BUTTON
        btnUpload = findViewById<Button>(R.id.btnCadastrarLivro)

        //TRATAMENTO DO EVENTO DE CLICK DO BOTÃO DE IMAGEM GRANDE
        btnImgGRD?.setOnClickListener {
            Toast.makeText(this, "BOTÃO DA IMAGEM GRANDE", Toast.LENGTH_LONG).show()
            resultLauncherGRD.launch("image/*")
        }
        //TRATAMENTO DO EVENTO DE CLICK DO BOTÃO DE IMAGEM PEQUENA
        btnImgPEQ?.setOnClickListener {
            Toast.makeText(this, "BOTÃO DA IMAGEM PEQUENA", Toast.LENGTH_LONG).show()
            resultLauncherPEQ.launch("image/*")
        }
        //TRATAMENTO DO EVENTO DE CLICK DO BOTÃO DE IMAGEM CADASTRO/UPLOAD
        btnUpload?.setOnClickListener {
            Toast.makeText(this, "BOTÃO DE UPLOAD", Toast.LENGTH_LONG).show()
            UploadImageGRD()
            UploadImagePEQ()
        }
    }

    //LANÇADOR PARA RECUPERAR IMAGEM GRANDE DA GALERIA PARA O UPLOAD
    private val resultLauncherGRD =registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUriGRD = it
        btnImgGRD?.setImageURI(imageUriGRD)
        Log.e("IMG-GRD", imageUriGRD.toString())
    }
    //LANÇADOR PARA RECUPERAR IMAGEM GRANDE DA GALERIA PARA O UPLOAD
    private val resultLauncherPEQ =registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUriPEQ = it
        btnImgPEQ?.setImageURI(imageUriPEQ)
        Log.e("IMG-PEQ", imageUriPEQ.toString())
    }
    //INICIALIZAÇÃO DAS VARIÁVEIS DO FIREBASE
    private fun initVars() {
        storageRef = FirebaseStorage.getInstance().reference.child("images")
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    /* FUNÇÃO DE UPLOAD */

    //UPLOAD IMAGEM GRANDE
    private fun UploadImageGRD(){
        imageUriGRD?.let {
            val riversRef = storageRef.child("${it.lastPathSegment}-${System.currentTimeMillis()}.jpg")
            val uploadTask = riversRef.putFile(it)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    riversRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()
                        firebaseFirestore.collection("images").add(map).addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful){
                                Toast.makeText(this, "UPLOAD IMAGEM GRANDE OK!", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            btnImgGRD?.setImageResource(R.drawable.upload)
                        }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    btnImgGRD?.setImageResource(R.drawable.upload)
                }
            }
        }
    }

    //UPLOAD IMAGEM PEQUENA
    private fun UploadImagePEQ(){
        imageUriPEQ?.let {
            val riversRef = storageRef.child("${it.lastPathSegment}-${System.currentTimeMillis()}.jpg")
            val uploadTask = riversRef.putFile(it)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    riversRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()
                        firebaseFirestore.collection("images").add(map).addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful){
                                Toast.makeText(this, "UPLOAD IMAGEM PEQUENA OK!", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            btnImgPEQ?.setImageResource(R.drawable.upload)
                        }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    btnImgPEQ?.setImageResource(R.drawable.upload)
                }
            }
        }
    }
}