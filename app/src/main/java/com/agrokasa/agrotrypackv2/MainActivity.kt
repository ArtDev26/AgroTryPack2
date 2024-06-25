package com.agrokasa.agrotrypackv2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.agrokasa.agrotrypackv2.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var edtUsuario: EditText
    private lateinit var edtConstrasena: EditText
    private lateinit var btnConfirmar: Button
    private lateinit var btnScan: Button

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // APLICACION SIN MARCO
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // INICIALIZA VISTAS
        edtUsuario = findViewById(R.id.edtUsuario)
        edtConstrasena = findViewById(R.id.edtConstraseña)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnScan = findViewById(R.id.btnScan)

        btnConfirmar.setOnClickListener {
            val username = edtUsuario.text.toString()
            val password = edtConstrasena.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    loginUser(username, password)
                }
            } else {
                Toast.makeText(this, "Por favor, ingrese todas las credenciales", Toast.LENGTH_SHORT).show()
            }
        }

        // BOTON SCAN
        btnScan.setOnClickListener {
            startScan()
        }
    }

    // SCANEAR
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

    // CONEXION LOGIN CON API REST
    private suspend fun loginUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://agkwebagro.agrokasa.pe/WSRESTSincronizacion/api/login/authenticate"
            val client = OkHttpClient()

            val jsonMediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = "".toRequestBody(jsonMediaType)

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("username", username)
                .addHeader("password", password)
                .build()

            try {
                Log.d("LoginUser", "URL: $url")
                Log.d("LoginUser", "Request Headers: ${request.headers}")

                val response = client.newCall(request).execute()
                val responseData = response.body?.string()

                if (response.isSuccessful && responseData != null) {
                    Log.d("LoginUser", "Response Data: $responseData")

                    // Aquí guardamos la respuesta como cadena en lugar de deserializarla como JSON
                    withContext(Dispatchers.Main) {
                        saveResponseAsString(responseData)
                        navigateToPostValidation()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("LoginUser", "Request failed with code: ${response.code}")
                        Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    // Maneja el error de conexión con el servidor
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveResponseAsString(responseData: String) {
        // Aquí puedes almacenar la respuesta como una cadena en SharedPreferences o donde lo necesites
        val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        sharedPreferences.edit().putString("response_data", responseData).apply()
    }

    private fun navigateToPostValidation() {
        val intent = Intent(this, PostValidation::class.java)
        startActivity(intent)
    }

}
