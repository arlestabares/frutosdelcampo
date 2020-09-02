package com.example.nadie.megafrutasyverduras.bd

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.nadie.megafrutasyverduras.modelo.Registro

class ConexionSQLite {

    lateinit var db: SQLiteDatabase
    lateinit var dbRegistroHelper: RegistroSQLHelper

    constructor(context:Context, version:Int){
        dbRegistroHelper = RegistroSQLHelper(context,nombreBD, null, version)
        db = dbRegistroHelper.writableDatabase
    }

    companion object {

        val nombreBD: String = "FrutasVerduras"
        val nombreTabla: String = "Registro"
        val camposTabla: Array<String> = arrayOf("_ID", "ID_firebase", "nombre", "fundacion", "precio", "libras")

        fun crearTabla(): String {
            val sqlQuery: String =
                "CREATE TABLE ${nombreTabla} ( ${camposTabla[0]} INTEGER PRIMARY KEY AUTOINCREMENT, ${camposTabla[1]} TEXT, ${camposTabla[2]} TEXT, ${camposTabla[3]} TEXT, ${camposTabla[4]} INTEGER, ${camposTabla[5]} INTEGER ) "
            return sqlQuery
        }
    }

    fun insertarRegistro(registro:Registro){
        val insertar:String = "INSERT INTO ${nombreTabla} ( ${camposTabla[1]}, ${camposTabla[2]}, ${camposTabla[3]}, ${camposTabla[4]}, ${camposTabla[5]} ) VALUES ( '${registro.id}', '${registro.nombreFV}', '${registro.nombreFundacion}', ${registro.precio}, ${registro.libras} )  "
        db.execSQL(insertar)
    }

    fun obtenerListaRegistros():ArrayList<Registro>{

        var lista:ArrayList<Registro> = ArrayList()
        var c:Cursor = db.query(nombreTabla, camposTabla, null, null, null, null, null)

        if( c.moveToFirst() ){

            do{

                var reg:Registro = Registro()
                reg.id = c.getString(1)
                reg.nombreFV = c.getString(2)
                reg.nombreFundacion = c.getString(3)
                reg.precio = c.getInt(4)
                reg.libras = c.getInt(5)

                lista.add(reg)

            }while(c.moveToNext())

        }

        return lista

    }

}