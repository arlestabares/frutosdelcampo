package com.example.nadie.megafrutasyverduras.activitiesListar

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.adapter.AdapterCompra
import com.example.nadie.megafrutasyverduras.modelo.Registro
import org.jetbrains.anko.toast

class ListarCompraActivity() : AppCompatActivity() {


    var pos: Int? = 0
    var posEliminar: Int = 0
    var registro: Registro? = null
    var regEliminar: Registro? = null
    lateinit var recyclerView: RecyclerView
    var listaCompra: ArrayList<Registro>? = null
    var adapter: AdapterCompra? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_compra)

        //intent proveniente de InterfazPrincipalFragment
        listaCompra = intent.getParcelableArrayListExtra("registros")

        recyclerView = findViewById(R.id.recyclerRegistroAnteriorFV)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterCompra(this, listaCompra!!)
        recyclerView.adapter = adapter

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 118) {
            if (resultCode == Activity.RESULT_OK) {
                if (data !=null) {

                    val codigo = data.getIntExtra("codigo", 0)

                    if (codigo == 1) {
                        registro = data.getParcelableExtra<Registro>("actualizarCompra")
                        pos = data.getIntExtra("posicion", 0)
                        //Log.e("registro_editado", registro.toString() + " " + pos)
                        adapter?.actualizarCompra(pos!!, registro!!)
                        adapter?.notifyDataSetChanged()

                    } else if (codigo == 2) {
                        regEliminar = data.getParcelableExtra("registroEliminar")
                        posEliminar = data.getIntExtra("posicionEliminar", 0)
                        adapter?.eliminarCompra(posEliminar)
                        adapter?.notifyDataSetChanged()

                    }

                }else{
                    toast("Debe ingresar la informacion solicitada en el formulario")
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
            var intent = Intent()
            intent.putExtra("editarRegistro", registro!!)
            intent.putExtra("posicion", pos!!)
            setResult(Activity.RESULT_OK, intent)

        }else if ( regEliminar !=null){
            val intent=Intent()
            intent.putExtra("registroEliminar",regEliminar)
            intent.putExtra("posEliminar",posEliminar)
            setResult(Activity.RESULT_OK,intent)

        }

        super.onBackPressed()

    }

}
