package com.example.nadie.megafrutasyverduras.activitiesListar

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.adapter.AdapterDonacionesRealizadas
import com.example.nadie.megafrutasyverduras.modelo.Registro

class ListarDonacionesRealizadasActivity() : AppCompatActivity() {


    var pos: Int? = 0
    var posEliminar: Int? = 0
    var registro: Registro? = null
    var registroEliminar: Registro? = null
    lateinit var recyclerView: RecyclerView
    var listaDonacionRealizada: ArrayList<Registro>? = null
    var adapter: AdapterDonacionesRealizadas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_donaciones_realizadas)


        listaDonacionRealizada = intent.getParcelableArrayListExtra("listaParaDonacion")
        recyclerView = findViewById(R.id.recyclerDonacionRealizada)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterDonacionesRealizadas(this, listaDonacionRealizada!!)
        recyclerView.adapter = adapter

    }

}
