package com.example.nadie.megafrutasyverduras.util

import android.app.Activity
import android.util.Log
import com.example.nadie.megafrutasyverduras.modelo.Producto
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import com.example.nadie.megafrutasyverduras.modelo.Registro
import com.google.firebase.database.*

/**
 *
 */
object ManagerFireBase {


    var database: FirebaseDatabase? = null
    var dataRef: DatabaseReference? = null
    var instant: ManagerFireBase? = null
    var listener: onActualizarAdaptador? = null


    interface onActualizarAdaptador {
        fun actualizarListaCompra(registro: Registro)
        fun actualizarListaStock(registro: Registro)
        fun actualizarListaDonacion(registro: Registro)
        fun actualizarListaProveedor(proveedor: Proveedor)
    }

    /**
     *
     */
    fun inicializar(actividad: Activity): ManagerFireBase {
        val instant: ManagerFireBase = ManagerFireBase
        instant.database = FirebaseDatabase.getInstance()
        instant.dataRef = database!!.reference

        listener = actividad as onActualizarAdaptador

        return instant

    }

    /**
     *
     */
    fun instanciar(actividad: Activity) {
        if (instant == null) {
            instant = inicializar(actividad)

        }
    }

    /**
     *Funcion ecargada de guardar o ingresar el registro asociado a cada uno de los carView
     * seleccionadas en la lista
     */
    fun registrarCompra(registro: Registro) {
        dataRef!!.child("registro").push().setValue(registro)

    }
    fun registrarStock(registro: Registro) {
        dataRef!!.child("stock").push().setValue(registro)

    }

    fun registrarDonacion(registro: Registro) {
        dataRef!!.child("donacion").push().setValue(registro)
    }

    fun registrarProveedor(proveedor: Proveedor) {
        dataRef!!.child("proveedor").push().setValue(proveedor)

    }
    fun registrarDonacionRealizada(registro: Registro){
        dataRef!!.child("donacionRealizada").push().setValue(registro)
    }

    /**
     * Funcion encargada de actualizar o editar los registros asociados a la lista de cada uno de
     * los carView.
     */
    fun actualizarCompra(registro: Registro) {
        Log.e("registro", registro.toString())
        dataRef!!.child("registro").child(registro.id).setValue(registro)
    }
    fun actualizarStock(registro:Registro){
        dataRef!!.child("stock").child(registro.id).setValue(registro)

    } fun actualizarListaDonacion(registro: Registro){
        dataRef!!.child("donacion") .child(registro.id).setValue(registro)
    }

    fun actualizarProveedor(proveedor: Proveedor) {
        dataRef!!.child("proveedor").child(proveedor.id).setValue(proveedor)

    }

    fun actualizarListaDonacionRealizada(registro: Registro){
        dataRef!!.child("donacionRealizada").child(registro.id).setValue(registro)
    }

    /**
     * Funcion encargada de eliminar los registros asociados a la lista de degistrarCompra
     */
    fun eliminarCompra(registro: Registro){
        dataRef!!.child("registro").child(registro.id).removeValue()

    }
    fun eliminarStock(registro: Registro){
        dataRef!!.child("stock").child(registro.id).removeValue()
    }
    fun eliminarDonacion(registro: Registro){
        dataRef!!.child("donacion").child(registro.id).removeValue()
    }

    fun eliminarProveedor(proveedor: Proveedor) {
        dataRef!!.child("proveedor").child(proveedor.id).removeValue()
    }
    fun eliminarDonacionRealizada(registro: Registro){
        dataRef!!.child("donacionRealizada").child(registro.id).removeValue()
    }

    /**
     * Funcion encargada de escuchar los eventos para val reg:Proveedor = p0.getValue(Proveedor::class.java)!!
    listener!!.eliminarProveedor(reg)actualizar la base de datos FireBase online
     * con cada uno de los registros
     */
    fun escucharEventoFireBase() {
        dataRef!!.child("registro").addChildEventListener(object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            /*
            Lee el evento de inserciòn en firebase, captura la llave que se genera automàticamente para el registro
             */
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val reg: Registro = p0.getValue(Registro::class.java)!!
                reg.id = p0.key!!
                listener!!.actualizarListaCompra(reg)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
        dataRef!!.child("donacion").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            /*
            Lee el evento de inserciòn en firebase, captura la llave que se genera automàticamente para el registro
             */
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val reg: Registro = p0.getValue(Registro::class.java)!!
                reg.id = p0.key!!
                listener!!.actualizarListaDonacion(reg)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
        dataRef!!.child("stock").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            /*
            Lee el evento de inserciòn en firebase, captura la llave que se genera automàticamente para el registro
             */
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val reg: Registro = p0.getValue(Registro::class.java)!!
                reg.id = p0.key!!
                listener!!.actualizarListaStock(reg)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })

        dataRef!!.child("proveedor").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                
            }

            /*
            Lee el evento de inserciòn en firebase, captura la llave que se genera automàticamente para el registro
             */
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val reg: Proveedor = p0.getValue(Proveedor::class.java)!!
                reg.id = p0.key!!
                listener!!.actualizarListaProveedor(reg)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })


    }

}