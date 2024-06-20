package com.agrokasa.agrotrypackv2

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale
import com.agrokasa.agrotrypackv2.data.Trabajador
import com.agrokasa.agrotrypackv2.databinding.ActivityMainBinding

class PostValidation : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        setContentView(R.layout.activity_post_validation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //DATE
        val calendario = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es","ES"))
        val formatoFecha = sdf.format(calendario.time)

        findViewById<TextView>(R.id.tvDate).text = formatoFecha

        //Declaracion de variables array para RecyclerView
        val trabajadores = listOf(
            Trabajador("Arturo Aaron Escalante Lovera", "463245", "Supervisor", R.drawable.log ),
            Trabajador("Jhair Daniel Pallin Ramirez", "456202", "Paletizado", R.drawable.log ),
            Trabajador("Erika Magaly Vera Castro", "458539", "Empaque", R.drawable.log ),
            Trabajador("Jean Carlos Mundaca Diaz", "455020", "Calidad", R.drawable.log )
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rvListaTrabajador)
        val adapter = TrabajadorAdapter(trabajadores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }
}