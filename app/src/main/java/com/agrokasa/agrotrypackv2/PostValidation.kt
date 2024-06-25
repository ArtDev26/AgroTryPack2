package com.agrokasa.agrotrypackv2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agrokasa.agrotrypackv2.data.Trabajador
import com.agrokasa.agrotrypackv2.databinding.ActivityMainBinding
import com.agrokasa.agrotrypackv2.databinding.ActivityPostValidationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale




class PostValidation : AppCompatActivity() {

    private lateinit var binding: ActivityPostValidationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostValidationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Actualizar la fecha
        val calendario = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
        val formatoFecha = sdf.format(calendario.time)
        binding.tvDate.text = formatoFecha

        // Obtener datos del API y actualizar el RecyclerView
        lifecycleScope.launch {
            val (numeroGrupo, trabajadores) = fetchGrupoYTrabajadores()
            updateRecyclerView(numeroGrupo, trabajadores)
        }
    }

    private suspend fun fetchGrupoYTrabajadores(): Pair<String, List<Trabajador>> {
        return withContext(Dispatchers.IO) {
            // Realiza la solicitud al API aquí y parsea los resultados
            val numeroGrupo = "1"  // Placeholder, reemplaza con el valor real
            val trabajadores = listOf(
                Trabajador("Arturo Aaron Escalante Lovera", "463245", "Supervisor", R.drawable.log),
                Trabajador("Jhair Daniel Pallin Ramirez", "456202", "Paletizado", R.drawable.log),
                Trabajador("Erika Magaly Vera Castro", "458539", "Empaque", R.drawable.log),
                Trabajador("Jean Carlos Mundaca Diaz", "455020", "Calidad", R.drawable.log)
            )
            Pair(numeroGrupo, trabajadores)
        }
    }

    private fun updateRecyclerView(numeroGrupo: String, trabajadores: List<Trabajador>) {
        val adapter = TrabajadorAdapter(trabajadores, numeroGrupo, {
            // Nuevo Grupo clicked
        }, {
            // Historial clicked
        })
        binding.rvListaTrabajador.layoutManager = LinearLayoutManager(this)
        binding.rvListaTrabajador.adapter = adapter

    }
}
