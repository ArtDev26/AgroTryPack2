package com.agrokasa.agrotrypackv2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var edtUsuario: EditText
    private lateinit var edtConstrasena: EditText
    private lateinit var btnConfirmar: Button
    private lateinit var btnScan: Button
    private lateinit var btnSkip: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        edtUsuario = findViewById(R.id.edtUsuario)
        edtConstrasena = findViewById(R.id.edtConstraseña)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnScan = findViewById(R.id.btnScan)
        btnSkip = findViewById(R.id.btnSkip)

        btnConfirmar.setOnClickListener {
            validateUser()
        }

        //Scanear
        btnScan.setOnClickListener {
            startScan()
        }

        //Skip prueba
        btnSkip.setOnClickListener(){
            val skip = Intent(this, PostValidation::class.java)
            startActivity(skip)

        }
    }

    //Scanear
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            edtUsuario.setText(result.contents)
        }
    }

    private fun startScan() {
        val options = ScanOptions()
        options.setPrompt("Scan a barcode")
        options.setBeepEnabled(true)
        options.setOrientationLocked(true)
        options.setBarcodeImageEnabled(true)
        barcodeLauncher.launch(options)
    }


    //Conexion API Rest
    private fun validateUser() {
        val username = edtUsuario.text.toString().trim()
        val password = edtConstrasena.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Snackbar.make(findViewById(R.id.main), "Ingresa Usuario y Contraseña", Snackbar.LENGTH_LONG).show()
            return
        }

        val apiService = ApiClient.getClient().create(ApiService::class.java)
        val call = apiService.login(username, password)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    println(loginResponse)

                    if (loginResponse != null && loginResponse.success) {
                        // Autenticación exitosa
                        val intent = Intent(this@MainActivity, PostValidation::class.java)
                        startActivity(intent)
                    } else {
                        Snackbar.make(findViewById(R.id.main), "Credenciales Invalidas", Snackbar.LENGTH_LONG).show()
                    }
                } else {
                    Snackbar.make(findViewById(R.id.main), "Error: ${response.code()}", Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Snackbar.make(findViewById(R.id.main), "Ocurrio un Error", Snackbar.LENGTH_LONG).show()
            }
        })
    }
}
