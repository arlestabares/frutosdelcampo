package com.example.nadie.megafrutasyverduras.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.editarFormularios.EditarProveedorActivity
import com.example.nadie.megafrutasyverduras.modelo.Proveedor

class AdapterProveedores(var contexto: Activity, var listaProveedores: ArrayList<Proveedor>) :
    RecyclerView.Adapter<AdapterProveedores.ViewHolderProducto>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderProducto {

        val v = LayoutInflater.from(p0.context).inflate(R.layout.molde_registro_proveedor, p0, false)

        return ViewHolderProducto(v)
    }

    override fun getItemCount(): Int {

        return listaProveedores.size
    }

    override fun onBindViewHolder(holder: AdapterProveedores.ViewHolderProducto, position: Int) {

        holder.bindItems(listaProveedores[position],position)

    }

    /**
     * Funcion encargada de actualizar un proveedor con la posicion y el registro que
     * le llegan desde la actividad ListarProveedoresActivity.
     */
    fun actualizarProveedor(pos:Int, registro:Proveedor){
        listaProveedores.set(pos,registro)

    }

    /**
     * Funcion encargada de eliminar el registro que contiene un proveedor
     */
    fun eliminarProveedor(pos: Int, proveedor:Proveedor){

      //  listaProveedores.remove(proveedor)
        listaProveedores.removeAt(pos)
        //Log.e("Desde Adapter Proveedor", proveedor.toString()+"")

    }
    /**
     * Clase ViewHolderOpciones que sirve de tipoOpcion en parametros de las funciones,onBindViewHolder,
     * y la funcion onCreateViewHolder
     *
     */
    inner class ViewHolderProducto(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        lateinit var registro: Proveedor
        var pos: Int = 0

        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val intent = Intent(contexto, EditarProveedorActivity::class.java)
            intent.putExtra("listaDesdeProveedorAdapter", registro)
            intent.putExtra("posicion",pos)
            contexto.startActivityForResult(intent,2534)

        }

        /**
         *Este metodo bindItems recibe los datos que se agregaran dentro de la vista
         *Creamos variables para las vistas
         * @image
         * @nombreFV
         */
        fun bindItems(data: Proveedor, pos: Int) {

            val nombre: TextView = itemView.findViewById(R.id.txtNombre)
            val ciudad: TextView = itemView.findViewById(R.id.txtCiudad)
            val telefono: TextView = itemView.findViewById(R.id.txtTelefono)
            val fechaRegistro: TextView = itemView.findViewById(R.id.txtNombreFundacion)


            nombre.text = data.nombre
            ciudad.text = data.ciudad
            telefono.text = data.telefono
            fechaRegistro.text = data.fechaRegistro

            registro = data
            this.pos=pos

        }
    }
}