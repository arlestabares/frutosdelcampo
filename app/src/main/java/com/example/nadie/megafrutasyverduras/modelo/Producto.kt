package com.example.nadie.megafrutasyverduras.modelo

import android.os.Parcel
import android.os.Parcelable

class Producto() : Parcelable{

    var imagen:Int = 0
    var titulo:String ?= null
    var nombre:String?=null
    var tipo:Int= 0


    constructor( imagen:Int, titulo:String) :this(){

            this.imagen=imagen
            this.titulo=titulo
    }


    constructor(parcel: Parcel) : this() {
        imagen = parcel.readInt()
        titulo = parcel.readString()
        nombre = parcel.readString()
        tipo=parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imagen)
        parcel.writeString(titulo)
        parcel.writeString(nombre)
        parcel.writeInt(tipo)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Producto(imagen=$imagen, titulo=$titulo, nombreFV=$nombre, tipoOpcion$tipo)"
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }



}