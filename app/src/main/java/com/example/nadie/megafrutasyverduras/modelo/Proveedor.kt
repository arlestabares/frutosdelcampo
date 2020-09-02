package com.example.nadie.megafrutasyverduras.modelo

import android.os.Parcel
import android.os.Parcelable

class Proveedor() :Parcelable {

    lateinit var nombre:String
    lateinit var telefono:String
    lateinit var ciudad:String
    lateinit var fechaRegistro:String
    var id:String = ""

    constructor(parcel: Parcel) : this() {
        nombre = parcel.readString()!!
        telefono = parcel.readString()!!
        ciudad = parcel.readString()!!
        fechaRegistro = parcel.readString()!!
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(telefono)
        parcel.writeString(ciudad)
        parcel.writeString(fechaRegistro)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Proveedor> {
        override fun createFromParcel(parcel: Parcel): Proveedor {
            return Proveedor(parcel)
        }

        override fun newArray(size: Int): Array<Proveedor?> {
            return arrayOfNulls(size)
        }
    }


}