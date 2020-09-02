package com.example.nadie.megafrutasyverduras.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro


class AdapterDonacionesRealizadas(var contexto: Activity, var listaDonacionRealizada: ArrayList<Registro>) :
    RecyclerView.Adapter<AdapterDonacionesRealizadas.ViewHolderRegistroStock>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdapterDonacionesRealizadas.ViewHolderRegistroStock {

        val vista: View = LayoutInflater.from(p0.context).inflate(R.layout.molde_donaciones_realizadas, p0, false)
        return ViewHolderRegistroStock(vista)
    }


    override fun getItemCount(): Int {

        return listaDonacionRealizada.size
    }

    override fun onBindViewHolder(holder: ViewHolderRegistroStock, p1: Int) {

        holder.bindItem(listaDonacionRealizada[p1],p1)
    }

    /**
     * Funcion encargada de actualizar la lista de registros contenidos en el stock
     * actual
     *
     */
    fun actualizarDonacion(pos:Int, registro: Registro){
        listaDonacionRealizada.set(pos,registro)
    }

    /**
     * Funcion encargada de eliminar el registro seleccionado por el usuario en la
     * interfaz de registros mostrados al usuario contenido en ListarStockActivity
     */
    fun eliminarDonacionRealizada(registro: Registro, pos: Int){
        listaDonacionRealizada.removeAt(pos)
    }

    inner class ViewHolderRegistroStock(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        lateinit var registroDonacion: Registro
        var pos: Int = 0

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {

            /*Intent con destino a la actividad EditarStockActivity, que sera la encargada de actualizar
              los registros que le llegan con este intent, y de regresar un registro a la
              actividad ListarStockActivity para notificarle alAdapterStock y que sean actualizados
              e inflar la lista con los nuevos valores de ese carView editado */

          /*  var intent = Intent(contexto, EditarStockActivity::class.java)
            intent.putExtra("listaRegistrosStockAdapter", registroDonacion)
            intent.putExtra("posicion", pos)
            contexto.startActivityForResult(intent, 1825)*/
        }


        fun bindItem(data: Registro,pos:Int) {

            val nombre: TextView = itemView.findViewById(R.id.txtNombre)
            val libras: TextView = itemView.findViewById(R.id.txtLibras)
            val nombreFundacion: TextView = itemView.findViewById(R.id.txtNombreFundacion)


            nombre.text = data.nombreFV
            libras.text = data.libras.toString()
            nombreFundacion.text = data.nombreFundacion

            registroDonacion = data
            this.pos=pos

        }
    }

}