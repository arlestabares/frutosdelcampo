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
import com.example.nadie.megafrutasyverduras.adapter.AdapterProveedores
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import org.jetbrains.anko.toast

class ListarProveedoresActivity : AppCompatActivity() {

    var posicion: Int? = 0
    var posEliminar: Int? = 0
    var registro: Proveedor? = null
    var regEliminar: Proveedor? = null
    var resgistroEliminar: Proveedor? = null

    lateinit var recyclerView: RecyclerView
    var listaProveedores: ArrayList<Proveedor>? = null
    var adapter: AdapterProveedores? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_proveedor)

        //intent proveniente de InterfazPrincipalFragment
        listaProveedores = intent.getParcelableArrayListExtra("registros")

        recyclerView = findViewById(R.id.recyclerViewRegistroP)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)

        adapter = AdapterProveedores(this, listaProveedores!!)
        recyclerView.adapter = adapter


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2534) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    val codigo = data.getIntExtra("codigo", 0)

                    if (codigo == 1) {

                        registro = data.getParcelableExtra("registro")
                        posicion = data.getIntExtra("posicion", 0)

                        if (registro != null && posicion != null) {
                            Log.e("registro_editado", registro.toString() + " " + posicion)

                            adapter?.actualizarProveedor(posicion!!, registro!!)
                            adapter?.notifyDataSetChanged()

                        }

                    } else if (codigo == 2) {

                        regEliminar = data.getParcelableExtra("registroEliminar")
                        posEliminar = data.getIntExtra("posicionEliminar", 0)

                        if (posEliminar != null && regEliminar != null) {
                            adapter?.eliminarProveedor(posEliminar!!, regEliminar!!)
                            adapter?.notifyDataSetChanged()
                        }
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
     * Funcion encargada de llevar a cabo el envio del objregistroEliminareto registro al MainActivity
     * para su actualizacion correspondiente luego de ser editado dicho registro, ya que
     * al presionar el boton o ir  hacia atras en la pila de actividades
     * èsta aun esta sin resolver o sin actualizar, y para ello se remite nuevamente dicho objeto
     * para tales fines
     */
    override fun onBackPressed() {

        if (posicion != null && registro != null) {
            val intent = Intent()
            intent.putExtra("registroDesdeListarProveedor", registro!!)
            intent.putExtra("posicion", posicion!!)
            setResult(Activity.RESULT_OK, intent)

        } else if (posEliminar != null && regEliminar != null) {
            val intent = Intent()
            intent.putExtra("registroEliminar", regEliminar!!)
            intent.putExtra("posEliminar", posEliminar!!)
            setResult(Activity.RESULT_OK, intent)
        }
        super.onBackPressed()
    }
}
