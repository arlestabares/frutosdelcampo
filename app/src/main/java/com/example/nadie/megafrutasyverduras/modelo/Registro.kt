package com.example.nadie.megafrutasyverduras.modelo

import android.os.Parcel
import android.os.Parcelable

class Registro() : Parcelable {

    var nombreFV: String = ""
    var nombreFundacion: String = ""
    var precio: Int = 0
    var libras: Int = 0
    var bultos: Int = 0
    var procedencia: String = ""
    var fechaRegistro: String = ""
    var tipoOpcion: Int = 0
    var tipoLista: Int = 0
    var id: String = ""

    var producto: Producto? = null
    var proveedor: Proveedor? = null


    constructor(nombre: String, libras: Int, bultos: Int, fechaRegistro: String) : this() {
        this.nombreFV = nombre
        this.libras = libras
        this.bultos = bultos
        this.fechaRegistro = fechaRegistro
    }

    constructor(nombreFV: String, libras: Int, nombreFundacion: String) : this(){
        this.nombreFV = nombreFV
        this.libras = libras
        this.nombreFundacion = nombreFundacion

    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        nombreFV = parcel.readString()!!
        nombreFundacion = parcel.readString()!!
        precio = parcel.readInt()
        libras = parcel.readInt()
        bultos = parcel.readInt()
        procedencia = parcel.readString()!!
        fechaRegistro = parcel.readString()!!
        tipoOpcion = parcel.readInt()
        tipoLista = parcel.readInt()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombreFV)
        parcel.writeString(nombreFundacion)
        parcel.writeInt(precio)
        parcel.writeInt(libras)
        parcel.writeInt(bultos)
        parcel.writeString(procedencia)
        parcel.writeString(fechaRegistro)
        parcel.writeInt(tipoOpcion)
        parcel.writeInt(tipoLista)

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Registro(nombreFV='$nombreFV',nombreFundacion='$nombreFundacion' precio=$precio, libras=$libras, " +
                "bultos=$bultos, procedencia='$procedencia', " +
                "fechaRegistro='$fechaRegistro', tipoOpcion=$tipoOpcion, tipoLista=$tipoLista)"
    }

    companion object CREATOR : Parcelable.Creator<Registro> {
        override fun createFromParcel(parcel: Parcel): Registro {
            return Registro(parcel)
        }

        override fun newArray(size: Int): Array<Registro?> {
            return arrayOfNulls(size)
        }
    }


}