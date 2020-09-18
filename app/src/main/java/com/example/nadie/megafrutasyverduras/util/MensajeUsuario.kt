package com.example.nadie.megafrutasyverduras.util

import android.app.Application
import android.content.Context
import android.os.Handler
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.activitiesFormularios.MainActivity

class MensajeUsuario {


    fun mensajeUsuarioSnackBar(view: MainActivity) {
        // Snackbar.make(findViewById(R.id.content_main), "mensaje", Snackbar.LENGTH_LONG).show()
        //val snackbar = Dialogo.miSnackBar(view.findViewById(R.id.content_main),"Realizar Donacion de fruta",true)
//        val mySnackBar =  Snackbar.make(view.findViewById(R.id.content_main), "mensaje", Snackbar.LENGTH_LONG)
//        mySnackBar.setAction("Donar producto"){mySnackBar.dismiss()}
//        mySnackBar.show()


        // val mySnackBar =  Snackbar.make(view.findViewById(R.id.content_main), "mensaje", Snackbar.LENGTH_LONG)
        val mySnackBar = Dialogo.miSnackBar(
            view.findViewById(R.id.content_main),
            "Realizar Donacion pronto",
            true
        )
        mySnackBar?.setAction("Realizar Donacion") { mySnackBar.dismiss() }
        mySnackBar?.show()
    }
    private fun ejecutarMensaje() {
        var handler:Handler = Handler()
        var runnable = Runnable {
            mensajeUsuario()
        }
        handler.postDelayed(runnable, 3000)
    }

    private fun mensajeUsuario() {

//        if (listaBancoDonacion.toString() != null) {
//
//            //  Snackbar.make(findViewById(R.id.content_main), "Recuerde Realizar Donacion", Snackbar.LENGTH_LONG).show()
//            val mySnackBar = Dialogo.miSnackBar(
//                findViewById(R.id.content_main), "Realizar Donacion de fruta", true)
//            mySnackBar?.setAction("Donacion Pendiente") { mySnackBar.dismiss() }
//            mySnackBar?.show()
//        }
    }


}























