package com.example.nadie.megafrutasyverduras.modelo

import android.os.Parcel
import android.os.Parcelable

class Donacion() : Parcelable{

    var nombreDelCentro:String =""
    var telefono:String=""
    var correoElectronico:String=""
    var direccion:String=""
    var ciudadDelCentro:String=""


    constructor(parcel: Parcel) : this() {
        nombreDelCentro = parcel.readString()!!
        direccion = parcel.readString()!!
        telefono = parcel.readString()!!
        correoElectronico = parcel.readString()!!
        ciudadDelCentro = parcel.readString()!!

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreDelCentro)
        parcel.writeString(direccion)
        parcel.writeString(telefono)
        parcel.writeString(correoElectronico)
        parcel.writeString(ciudadDelCentro)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Donacion> {
        override fun createFromParcel(parcel: Parcel): Donacion {
            return Donacion(parcel)
        }

        override fun newArray(size: Int): Array<Donacion?> {
            return arrayOfNulls(size)
        }
    }


}