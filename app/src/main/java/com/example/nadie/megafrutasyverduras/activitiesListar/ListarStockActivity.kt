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
import com.example.nadie.megafrutasyverduras.adapter.AdapterStock
import com.example.nadie.megafrutasyverduras.modelo.Registro

class ListarStockActivity(var adapter: AdapterStock? = null) : AppCompatActivity() {

    var pos: Int = 0
    var posEliminar: Int = 0
    var registro: Registro? = null
    var registroEliminar: Registro? = null
    lateinit var recyclerView: RecyclerView
    var listaStock: ArrayList<Registro>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_stock)

        //intent proveniente de InterfazPrincipalFragment
        listaStock = intent.getParcelableArrayListExtra("registros")

        recyclerView = findViewById(R.id.recylerRegistroStock)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterStock(this, listaStock!!)
        recyclerView.adapter = adapter


    }

    /**
     * Funcion encargada de
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1825) {
            if (resultCode == Activity.RESULT_OK) {

                val codigo = data?.getIntExtra("codigo", 0)

                if (codigo == 1) {

                    registro = data.getParcelableExtra("actualizarStock")
                    pos = data.getIntExtra("posicion", 0)
                    Log.e("registro_editado", registro.toString() + " " + pos)

                    if (registro != null) {
                        adapter?.actualizarStock(pos, registro!!)
                        adapter?.notifyDataSetChanged()
                    }


                } else if (codigo == 2) {

                    registroEliminar = data.getParcelableExtra("eliminarDonacionRealizada")
                    posEliminar = data.getIntExtra("posicionEliminar", 0)

                    if (registroEliminar != null) {
                        adapter?.eliminarRegistroStock(registroEliminar!!,posEliminar)
                        adapter?.notifyDataSetChanged()
                    }
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

        if (registro != null) {

            val intent = Intent()
            intent.putExtra("registroDesdeListarStock", registro)
            intent.putExtra("posicion", pos)
            setResult(Activity.RESULT_OK, intent)

        }else if (registroEliminar !=null ){
            val intent=Intent()
            intent.putExtra("eliminarDonacionRealizada",registroEliminar)
            intent.putExtra("posicionEliminar",posEliminar)
            setResult(Activity.RESULT_OK, intent)

        }

        super.onBackPressed()
    }
}
