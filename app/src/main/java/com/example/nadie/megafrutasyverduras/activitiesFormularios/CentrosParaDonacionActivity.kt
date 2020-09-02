package com.example.nadie.megafrutasyverduras.activitiesFormularios

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro

/**
 * Clase encargada de mostrar los centros que hay en la aplicacion para asi
 * escoger cual sera al que
 * se le donara
 */
class CentrosParaDonacionActivity : AppCompatActivity(), View.OnClickListener {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centros_para_donacion)

       // cardViewDonacion1.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
