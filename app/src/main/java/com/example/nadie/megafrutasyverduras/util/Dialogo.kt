package com.example.nadie.megafrutasyverduras.util

import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import java.time.Duration

class Dialogo {



    companion object {
        //funcion para generar un tosta personalizado

        fun miToast(context: Context, message: CharSequence, duration: Int = Toast.LENGTH_LONG){
            Toast.makeText(context,message,duration).show()

        }
        fun miSnackBar(view:View, mensaje :String, accion:Boolean,tiempo:Int = if(!accion) Snackbar.LENGTH_LONG else Snackbar.LENGTH_INDEFINITE):Snackbar?{
                    return  Snackbar.make(view,mensaje,tiempo).setActionTextColor(Color.RED)
        }
    }
}