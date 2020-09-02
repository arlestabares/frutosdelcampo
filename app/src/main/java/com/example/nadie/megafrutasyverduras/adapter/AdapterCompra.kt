package com.example.nadie.megafrutasyverduras.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.editarFormularios.EditarCompraActivity
import com.example.nadie.megafrutasyverduras.modelo.Registro

class AdapterCompra(var contexto: Activity, var listaCompra: ArrayList<Registro>) :
    RecyclerView.Adapter<AdapterCompra.ViewHolderRegistro>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdapterCompra.ViewHolderRegistro {


        var v = LayoutInflater.from(p0.context).inflate(R.layout.molde_registro_compra, p0, false)
        return ViewHolderRegistro(v)
    }

    /**
     * Funcion encargada de devolver el tamaño de la listaOpciones
     */
    override fun getItemCount(): Int {
        return listaCompra.size
    }

    /**
     * Funcion
     */
    override fun onBindViewHolder(holder: ViewHolderRegistro, p1: Int) {
        holder.bindItem(listaCompra[p1], p1)

    }

    /**
     * @pos Posicion que sera actualizada dentro de la listaCompra.
     * @registro Registro que contiene un objeto con todos los datos que sera actualizado en la
     * posicion pos
     */
    fun actualizarCompra(pos: Int, registro: Registro) {
        listaCompra.set(pos, registro)
    }


    fun eliminarCompra(pos:Int){
        listaCompra.removeAt(pos)

    }

    /**
     * Clase encargada de poner los valores asociados a cada variable de tipoOpcion Producto
     */
    inner class ViewHolderRegistro(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        lateinit var registroCompra: Registro
        var pos: Int = 0


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            /*La respuesta de esta peticion del intent la dara la actividad EditarCompraActivity
              cuando se edite el registro deseado y se seleccione el boton editar en la
              funcion onClick(), ese mensaje llevara el registro y la posicion del mismo,recogiendo
              el resultado de OK la actividad ListarCompraActivity con requestCode 118,
              para notificarselo al AdapterCompra para que actualice el registro e infle la
              lista nuevamente pero actualizada*/
            var intent = Intent(contexto, EditarCompraActivity::class.java)
            intent.putExtra("listaDesdeAdapterCompra", registroCompra)
            intent.putExtra("posicionObjeto", pos)
            contexto.startActivityForResult(intent, 118)
        }

        /**
         * @data variable que contendra los datos de tipo Registro
         * @pos Variable necesaria para indicar la posicion dentro del ArrayList
         * para ser editado el registro seleccionado
         */
        fun bindItem(data: Registro, pos: Int) {

            val nombre: TextView = itemView.findViewById(R.id.txtNombre)
            val precioLibra: TextView = itemView.findViewById(R.id.txtPrecioLibras)
            val libras: TextView = itemView.findViewById(R.id.txtLibras)
            val bultos: TextView = itemView.findViewById(R.id.txtBultos)
            val procedencia: TextView = itemView.findViewById(R.id.txtProcedencia)
            val fechaRegistro: TextView = itemView.findViewById(R.id.txtNombreFundacion)

            nombre.text = data.nombreFV
            precioLibra.text = data.precio.toString()
            libras.text = data.libras.toString()
            bultos.text = data.bultos.toString()
            procedencia.text = data.procedencia
            fechaRegistro.text = data.fechaRegistro

            registroCompra = data
            this.pos = pos

        }

    }

}


