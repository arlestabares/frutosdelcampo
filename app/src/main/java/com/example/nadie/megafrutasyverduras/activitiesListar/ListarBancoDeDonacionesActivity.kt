package com.example.nadie.megafrutasyverduras.activitiesListar

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.adapter.AdapterBancoDonacion
import com.example.nadie.megafrutasyverduras.modelo.Registro

class ListarBancoDeDonacionesActivity : AppCompatActivity() {


    var pos: Int? = 0
    var posEliminar:Int? = 0
    var registro: Registro? = null
    var regEliminar: Registro? = null
    lateinit var recyclerView: RecyclerView
    var listaBancoDonacion: ArrayList<Registro>? = null
    var adapter: AdapterBancoDonacion? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_descomposicon)


        //intent proveniente de MainActivity
        listaBancoDonacion = intent.getParcelableArrayListExtra("registros")
        //  listaProductos=intent.getParcelableArrayListExtra("registroCompra productos")
        Log.e("DonacionesActivity", listaBancoDonacion.toString())

        recyclerView = findViewById(R.id.recyclerRegistroDescomposicionFV)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterBancoDonacion(this, listaBancoDonacion!!)
        recyclerView.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*requestCode 1118 generado en AdapterBancoDonacion en la funcion Onclick(),y que es enviado  mediante
          el intent a la actividad  EditarDonacionActivity,y es dicha actividad la encargada de enviar la
          respuesta de ActivityResult_OK  a este contexto o ListarBancoDeDonacionesActivity,
          mediante la llave actualizarListaDonacion recibido en el siguiente  if */
        if (requestCode == 1118) {
            if (resultCode == Activity.RESULT_OK) {

                val codigo = data?.getIntExtra("codigo", 0)

                if (codigo == 1) {
                    registro = data.getParcelableExtra("actualizarListaDonacion")
                    pos = data.getIntExtra("posicion", 0)
                    Log.e("registro_editadoListarD", registro.toString() + " la posicion = " + pos)
                    adapter?.actualizarDonacion(pos!!, registro!!)
                    adapter?.notifyDataSetChanged()

                } else if (codigo == 2) {

                    posEliminar = data.getIntExtra("eliminarPosicion", 0)
                    regEliminar = data.getParcelableExtra("eliminarDonacion")
                    adapter?.eliminarDonacion(posEliminar!!)
                    adapter?.notifyDataSetChanged()

                }

            }

        }
    }

    /**
     * @registro Contiene la lista de objetos
     * @pos Posicion en la cual sera actualizado el registro que contiene los objetos
     * Funcion encargada de llevar a cabo el envio del objeto registro al MainActivity
     * para su actualizacion correspondiente luego de ser editado dicho registro, ya que
     * al presionar el boton o ir  hacia atras en la pila de actividades
     * èsta aun esta sin resolver o sin actualizar, y para ello se remite nuevamente dicho objeto
     * para tales fines
     */
    override fun onBackPressed() {

        if (registro != null && pos != null) {
            val intent = Intent()
            intent.putExtra("registroDesdeListarDonacion", registro!!)
            intent.putExtra("posicion", pos!!)
            setResult(Activity.RESULT_OK, intent)
        }else if (regEliminar != null && posEliminar!=null){
            val intent=Intent()
            intent.putExtra("eliminarRegistroDonacion",regEliminar)
            intent.putExtra("posicionEliminar",posEliminar!!)
            setResult(Activity.RESULT_OK,intent)
        }

        super.onBackPressed()

    }
}
