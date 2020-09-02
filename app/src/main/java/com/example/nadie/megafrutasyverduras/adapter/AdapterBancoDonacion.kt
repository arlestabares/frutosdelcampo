package com.example.nadie.megafrutasyverduras.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.editarFormularios.EditarDonacionActivity
import com.example.nadie.megafrutasyverduras.modelo.Registro

class AdapterBancoDonacion(var contexto:Activity, var listaBancoDonacion: ArrayList<Registro>) :
    RecyclerView.Adapter<AdapterBancoDonacion.ViewHolderRegistro>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderRegistro {

        val vista = LayoutInflater.from(p0.context).inflate(R.layout.molde_registro_standar, p0, false)

        return ViewHolderRegistro(vista)
    }
    override fun getItemCount(): Int {

        return listaBancoDonacion.size
    }

    override fun onBindViewHolder(holder: ViewHolderRegistro, p1: Int) {

        holder.bindItem(listaBancoDonacion[p1],p1)
    }

    /**
     * @param pos Posicion en la cual sera actualizada la lista
     * @param registro Registro que contiene un objeto con los datos que sera actualizado
     * en la posicion pos
     *
     */
    fun actualizarDonacion(pos:Int, registro: Registro){
        listaBancoDonacion.set(pos,registro)
    }

    fun eliminarDonacion(pos: Int){
        listaBancoDonacion.removeAt(pos)

    }

    inner class ViewHolderRegistro(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        lateinit var registroParaDonacion: Registro
        var pos: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            /*Intent con destino a la actividad EditarDonacionActivity, cuyo
            resultado que devolvera dicha actividad le llegara primero a la actividad ListarDescomposicionActivity,
            para su respectiva operacion, la cual aplicara los cambios para notificarle al AdapterDescompsosicion
            de dichos cambios y actualizar el CarView, o la lista de registros. */
            var intent = Intent(contexto, EditarDonacionActivity::class.java)
            intent.putExtra("listaDesdeAdapter", registroParaDonacion)
            intent.putExtra("posicionObjeto",pos)
            contexto.startActivityForResult(intent,1118)



        }


        fun bindItem(data: Registro, pos: Int) {

            val nombre: TextView = itemView.findViewById(R.id.txtNombre)
            val libras: TextView = itemView.findViewById(R.id.txtLibras)
            val bultos: TextView = itemView.findViewById(R.id.txtBultos)
            val fechaRegistro: TextView = itemView.findViewById(R.id.txtNombreFundacion)

            nombre.text = data.nombreFV
            libras.text = data.libras.toString()
            bultos.text = data.bultos.toString()
            fechaRegistro.text = data.fechaRegistro

            registroParaDonacion = data
            this.pos = pos

        }
    }

}