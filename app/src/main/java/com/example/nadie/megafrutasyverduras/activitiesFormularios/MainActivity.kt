package com.example.nadie.megafrutasyverduras.activitiesFormularios

import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.activitiesListar.*
import com.example.nadie.megafrutasyverduras.bd.ConexionSQLite
import com.example.nadie.megafrutasyverduras.modelo.Producto
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import com.example.nadie.megafrutasyverduras.modelo.Registro
import com.example.nadie.megafrutasyverduras.util.Dialogo
import com.example.nadie.megafrutasyverduras.util.EscribirFichero
import com.example.nadie.megafrutasyverduras.util.ManagerFireBase
import com.example.nadie.megafrutasyverduras.util.MensajeUsuario
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast


/**
 * @author Arles de Jesus Tabares Carvajal
 * Clase Principal que maneja todas las operaciones relacionadas con la logica del negocio,
 * es la encargada de inicializar las listas  que seran enviadas a las correspondientes
 * clases para su manipulacion y ejecucion, asi como de mantener los registros actualizados
 * para su correspondiente verificacion y mantenibilidad en la base de datos FireBase en linea.
 * como en la memoria del telefono
 *
 */
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    ManagerFireBase.onActualizarAdaptador {

    lateinit var listaProductos: ArrayList<Producto>
    lateinit var listaCompra: ArrayList<Registro>
    lateinit var listaProveedores: ArrayList<Proveedor>
    lateinit var listaStock: ArrayList<Registro>
    lateinit var listaBancoDonacion: ArrayList<Registro>
    lateinit var listaDonacionRealizada: ArrayList<Registro>
    lateinit var managerFB: ManagerFireBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        ManagerFireBase.instanciar(this)
        managerFB = ManagerFireBase.instant!!
        managerFB.escucharEventoFireBase()


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        listaProductos = ArrayList()
        listaCompra = ArrayList()
        listaProveedores = ArrayList()
        listaBancoDonacion = ArrayList()
        listaStock = ArrayList()
        listaDonacionRealizada = ArrayList()


        /*var bundle = Bundle()
        bundle.putParcelableArrayList("listaProductos", listaProductos)
        bundle.putParcelableArrayList("listaCompra", listaCompra)
        bundle.putParcelableArrayList("listaDonacionRealizada", listaDonacionRealizada)
        bundle.putParcelableArrayList("listaProveedores", listaProveedores)
        bundle.putParcelableArrayList("listaDonacionFV", listaBancoDonacion)*/

        cardViewRC.setOnClickListener(this)
        cardViewLRC.setOnClickListener(this)
        cardViewRStock.setOnClickListener(this)
        cardViewLRStock.setOnClickListener(this)
        cardViewRD.setOnClickListener(this)
        cardViewLD.setOnClickListener(this)
        cardViewRP.setOnClickListener(this)
        cardViewLP.setOnClickListener(this)
        cardViewIA.setOnClickListener(this)

        //Esta variable bundle lleva consigo una lista de listaProductos y listaCompra la cual se envia
        // al fragment miFragment
        //var bundle = Bundle()

        val sqlite = ConexionSQLite(this, 1)
        Log.e("lista", sqlite.obtenerListaRegistros().toString())


            ejecutarMensaje()


    }

    private fun ejecutarMensaje() {
         var handler:Handler = Handler()
            var runnable = Runnable {
                mensajeUsuario()
            }
            handler.postDelayed(runnable, 3000)
        }

    private fun mensajeUsuario() {

        if (listaBancoDonacion.toString() != null) {

            //  Snackbar.make(findViewById(R.id.content_main), "Recuerde Realizar Donacion", Snackbar.LENGTH_LONG).show()
            val mySnackBar = Dialogo.miSnackBar(
                findViewById(R.id.content_main), "Realizar Donacion de fruta", true)
            mySnackBar?.setAction("Donacion Pendiente") { mySnackBar.dismiss() }
            mySnackBar?.show()
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            //super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     *
     * Metodo encargado de los eventos o escuchas de los item seleccionados
     * del menu superior derecho de la interfaz principal de  opciones
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.itemId == R.id.informacionAplicacion) {
            val intent = Intent(this, InformacionFuncionalidadActivity::class.java)
            startActivity(intent)

        } else if (item.itemId == R.id.centrosParaDonacion) {
            val intent = Intent(this, CentrosParaDonacionActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.realizarDonacion) {
            val intent = Intent(this, FormularioRealizarDonacionActivity::class.java)
            intent.putParcelableArrayListExtra("registroDesdeActivity", listaBancoDonacion)
            // intent.putExtra("registro_Desde_Activity_Main", listaBancoDonacion)
            //   Log.e("Mensaje_desde_activity", listaBancoDonacion.toString())
            startActivityForResult(intent, 100)

        } else if (item.itemId == R.id.donacionRealizada) {
            if (listaDonacionRealizada.isEmpty()) {
                toast("La lista se encuentra vacia en este momento")

            } else {
                val intent = Intent(this, ListarDonacionesRealizadasActivity::class.java)
                intent.putParcelableArrayListExtra("listaParaDonacion", listaDonacionRealizada)
                startActivity(intent)

            }
        }

        /*else if (item.itemId == R.id.listarServicio) {

                   val intent = Intent(this, FormularioProveedorActivity::class.java)
                   startActivity(intent)

                   //con startActivityForResult se inicia una actividad esperando
                   // que nos devuelva algo,
                   //cuando la actividad envia su respuesta se invoca el método onActivityResult
               } else if (item.itemId == R.id.registrarCompraFV) {
                   val intent = Intent(this, FormularioCompraActivity::class.java)
                   //  intent.putExtra("registros",listaCompra)
                   // intent.putExtra("productos",listaProductos)
                   startActivity(intent)

               } else if (item.itemId == R.id.registroProveedor) {
                   val intent = Intent(this, FormularioProveedorActivity::class.java)
                   startActivity(intent)

               } else if (item.itemId == R.id.registrarDescomposicion) {
                   var intent = Intent(this, FormularioBancoDonacionActivity::class.java)
                   // intent.putExtra("registros", listaCompra)
                   // intent.putExtra("productos", listaProductos)
                   startActivity(intent)

               } else if (item.itemId == R.id.registrarStock) {
                   val intent = Intent(this, FormularioStockActivity::class.java)
                   intent.putExtra("registros", listaCompra)
                   //intent.putExtra("productos",listaProductos)
                   startActivity(intent)

               }*/


        when (item.itemId) {
            /* R.id.action_settings -> return true
             else -> return super.onOptionsItemSelected(item)*/
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        var isFragmentSelection: Boolean? = false
        val miFragment: Fragment? = null



        when (item.itemId) {
            R.id.menu_opciones -> {
                // Handle the camera action
                val intent = Intent(this, CentrosParaDonacionActivity::class.java)
                startActivity(intent)
                //  miFragment = InterfazPrincipalFragment()
                isFragmentSelection = true
                //fm.beginTransaction().replace(R.id.content_main,miFragment).commit()
            }
            /*   R.id.verificarDonacion -> {
                   if (listaBancoDonacion.isEmpty()) {
                       toast("la lista se encuentra vacia, ingrese datos por primera vez")

                   } else {
                       val intent = Intent(this, ListarBancoDeDonacionesActivity::class.java)
                       startActivity(intent)
                   }
               }*/
            R.id.donar -> {

                val intent = Intent(this, FormularioRealizarDonacionActivity::class.java)
                startActivity(intent)

            }
            /*  R.id.cerrarAplicacion -> {
                  intent = Intent(ACTION_MAIN)
                  intent.addCategory(CATEGORY_HOME)
                  intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
                  startActivity(intent)
              }*/

            R.id.nav_manage -> {
                intent = Intent(ACTION_MAIN)
                intent.addCategory(CATEGORY_HOME)
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            R.id.nav_share -> {


                var cadena: String = ""

                for (registro in listaCompra) {
                    cadena += "Nombre:   " + registro.nombreFV + "\n"
                    cadena += "Precio Por Kilo: " + registro.precio + " pesos\n"
                    cadena += "Ciudad de procedencia: " + registro.procedencia + "\n"
                    cadena += "Fecha de  Registro: " + registro.fechaRegistro + "\n"
                    cadena += "-----------------------------------------------" + "\n"
                }

                EscribirFichero.savePdf(this, cadena)

            }
            R.id.nav_send -> {

            }

        }

        if (isFragmentSelection == true) {
            if (miFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, miFragment)
                    .addToBackStack(null)
                    .commit()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     *Funcion encargada de registrar los eventos de registros
     */
    fun guardarRegistro(registro: Registro, bandera: Int) {

        if (bandera == 12) {
            //   listaCompra.add(registro)
            managerFB.registrarCompra(registro)
        } else if (bandera == 14) {
            //  listaDonacionRealizada.add(registro)
            managerFB.registrarStock(registro)
        } else if (bandera == 16) {
            //  listaBancoDonacion.add(registro)
            managerFB.registrarDonacion(registro)
        } else if (bandera == 100) {
            listaDonacionRealizada.add(registro)
            managerFB.registrarDonacionRealizada(registro)

        }
    }

    /**
     * Funcion encargada de editar el registro seleccionado por el usuario en
     * la lista de items existentes llamada ListarCompraActivity
     */
    fun actualizarRegistro(pos: Int, registro: Registro, bandera: Int) {

        if (bandera == 13) {
            listaCompra.set(pos, registro)
            managerFB.actualizarCompra(registro)
        } else if (bandera == 15) {
            listaStock.set(pos, registro)
            managerFB.actualizarStock(registro)
        } else if (bandera == 17) {
            listaBancoDonacion.set(pos, registro)
            managerFB.actualizarListaDonacion(registro)
        }
    }

    /**
     * Funcion encargada de eliminar los registros asociados a las listas
     * creadas en cada una de las Listas de activitys (Listar......)
     */
    fun eliminarRegistro(pos: Int, registro: Registro, bandera: Int) {

        if (bandera == 13) {
            listaCompra.removeAt(pos)
            managerFB.eliminarCompra(registro)
        } else if (bandera == 15) {
            listaStock.removeAt(pos)
            managerFB.eliminarStock(registro)
        } else if (bandera == 17) {
            listaBancoDonacion.removeAt(pos)
            managerFB.eliminarDonacion(registro)
        }
    }

    /**
     * Funcion encargada de registrar un proveedor ingresado por el usuario
     * en la activity FormularioCompraActivity
     */
    fun registrarProveedor(registro: Proveedor) {
        //listaProveedores.add(registro)
        managerFB.registrarProveedor(registro)

    }

    fun actualizarProveedor(pos: Int, registro: Proveedor) {
        managerFB.actualizarProveedor(registro)
        // listaProveedores.set(pos, registro)
    }

    fun eliminarProveedor(pos: Int, reg: Proveedor) {
        // listaProveedores.removeAt(pos)
        managerFB.eliminarProveedor(reg)
    }


    /**
     * Funcion sobreescrita perteneciente a la interfaz ManagerFireBase
     */

    override fun actualizarListaCompra(registro: Registro) {
        listaCompra.add(registro)
    }

    override fun actualizarListaDonacion(registro: Registro) {
        listaBancoDonacion.add(registro)
    }

    override fun actualizarListaStock(registro: Registro) {
        listaStock.add(registro)

    }

    override fun actualizarListaProveedor(proveedor: Proveedor) {
        listaProveedores.add(proveedor)

    }


    /**
     * Funcion encargada de disparar los eventos asociados a las escuchas en cada una de las
     * variables asignadas en el XML asociadas a  esta activity
     */
    override fun onClick(v: View?) {
        if (v == cardViewRC) {
            val intent = Intent(this, FormularioCompraActivity::class.java)
            // intent.putParcelableArrayListExtra("registros", listaCompra)
            startActivityForResult(intent, 12)
        } else if (v == cardViewLRC) {

            if (listaCompra.isEmpty()) {
                toast("Debe ingresar Informacion a la lista por primera vez")
            } else {
                val intent = Intent(this, ListarCompraActivity::class.java)
                intent.putParcelableArrayListExtra("registros", listaCompra)
                startActivityForResult(intent, 13)
            }

        } else if (v == cardViewRStock) {
            val intent = Intent(this, FormularioStockActivity::class.java)
            // intent.putParcelableArrayListExtra("registros", listaDonacionRealizada)
            startActivityForResult(intent, 14)
        } else if (v == cardViewLRStock) {
            if (listaStock.isEmpty()) {
                toast("Debe ingresar Informacion a la lista por primera vez")
            } else {
                val intent = Intent(this, ListarStockActivity::class.java)
                intent.putParcelableArrayListExtra("registros", listaStock)
                startActivityForResult(intent, 15)
            }
        } else if (v == cardViewRD) {
            val intent = Intent(this, FormularioBancoDonacionActivity::class.java)
            // intent.putParcelableArrayListExtra("resgistros", listaBancoDonacion)
            startActivityForResult(intent, 16)
        } else if (v == cardViewLD) {
            if (listaBancoDonacion.isEmpty()) {
                toast("Debe ingresar Informacion a la lista por primera vez")
            } else {
                val intent = Intent(this, ListarBancoDeDonacionesActivity::class.java)
                intent.putParcelableArrayListExtra("registros", listaBancoDonacion)
                Log.e("registroInterfazPricipa", listaBancoDonacion.toString())
                startActivityForResult(intent, 17)
            }
        } else if (v == cardViewRP) {
            val intent = Intent(this, FormularioProveedorActivity::class.java)
            // intent.putExtra("registros", listaProveedores)
            startActivityForResult(intent, 18)
        } else if (v == cardViewLP) {
            if (listaProveedores.isEmpty()) {
                toast("Debe ingresar Informacion a la lista por primera vez")
            } else {
                val intent = Intent(this, ListarProveedoresActivity::class.java)
                intent.putParcelableArrayListExtra("registros", listaProveedores)
                startActivityForResult(intent, 19)
            }
        } else if (v == cardViewIA) {
            val intent = Intent(this, InformacionFuncionalidadActivity::class.java)
            //  intent.putExtra("registros", listaCompra)
            startActivity(intent)
        }
    }

    /**
     * Funcion encargada de recibir los valores asoiciados al requestCode y el resulCode que le envian
     * las actividades para realizar las operaciones correspondientes
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 12) {
            if (resultCode == Activity.RESULT_OK) {
                val registroNuevo = data?.getParcelableExtra<Registro>("registroCompra")
                guardarRegistro(registroNuevo!!, 12)
            }
        } else if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {

                if (data != null) {

                    val actualizarCompra = data.getParcelableExtra<Registro>("editarRegistro")
                    val pos = data.getIntExtra("posicion", 0)


                    val registroEliminar = data.getParcelableExtra<Registro>("registroEliminar")
                    val posEliminar = data.getIntExtra("posEliminar", 0)
                    if (actualizarCompra != null) {
                        actualizarRegistro(pos, actualizarCompra, 13)
                    } else if (registroEliminar != null) {
                        eliminarRegistro(posEliminar, registroEliminar, 13)

                    }

                }
            }

        } else if (requestCode == 14) {
            if (resultCode == Activity.RESULT_OK) {
                val registrarStock = data?.getParcelableExtra<Registro>("registroFormularioStock")
                guardarRegistro(registrarStock!!, 14)
                Log.e("registrostock", registrarStock.toString())
            }
        } else if (requestCode == 15) {
            if (resultCode == Activity.RESULT_OK) {

                if (data != null) {
                    val editarStock = data.getParcelableExtra<Registro>("registroDesdeListarStock")
                    val pos = data.getIntExtra("posicion", 0)

                    val eliminarRegistroStock =
                        data.getParcelableExtra<Registro>("eliminarDonacionRealizada")
                    val posEliminar = data.getIntExtra("posicionEliminar", 0)

                    if (editarStock != null) {
                        actualizarRegistro(pos, editarStock, 15)
                    } else if (eliminarRegistroStock != null) {
                        eliminarRegistro(posEliminar, eliminarRegistroStock, 15)

                    }
                }
            }

        } else if (requestCode == 16) {
            if (resultCode == Activity.RESULT_OK) {
                val registrarDonacion =
                    data?.getParcelableExtra<Registro>("registroDesdeFormularioDonacion")
                guardarRegistro(registrarDonacion!!, 16)
                Log.e("registroParaDonacionnn", registrarDonacion.toString())
            }

        } else if (requestCode == 17) {
            if (resultCode == Activity.RESULT_OK) {
                val actualizarDonacion =
                    data?.getParcelableExtra<Registro>("registroDesdeListarDonacion")
                val posicion = data?.getIntExtra("posicion", 0)
                val posEliminar = data?.getIntExtra("posicionEliminar", 0)
                val registroEliminar =
                    data?.getParcelableExtra<Registro>("eliminarRegistroDonacion")

                if (actualizarDonacion != null && posicion != null) {
                    actualizarRegistro(posicion, actualizarDonacion, 17)
                } else if (posEliminar != null && registroEliminar != null) {
                    eliminarRegistro(posEliminar, registroEliminar, 17)

                }

            }
        } else if (requestCode == 18) {
            if (resultCode == Activity.RESULT_OK) {
                val registroProveedor = data?.getParcelableExtra<Proveedor>("registroProveedor")
                registrarProveedor(registroProveedor!!)
            }
        } else if (requestCode == 19) {
            if (resultCode == Activity.RESULT_OK) {

                val actualizarRegistro =
                    data?.getParcelableExtra<Proveedor>("registroDesdeListarProveedor")
                val pos = data?.getIntExtra("posicion", 0)

                val posEliminar = data?.getIntExtra("posEliminar", 0)
                val eliminarRegistroProveedor =
                    data?.getParcelableExtra<Proveedor>("registroEliminar")

                if (actualizarRegistro != null && pos != null) {
                    actualizarProveedor(pos, actualizarRegistro)

                } else if (posEliminar != null && eliminarRegistroProveedor != null) {
                    eliminarProveedor(posEliminar, eliminarRegistroProveedor)
                }
            }

        } else if (requestCode == 100) {

            if (resultCode == Activity.RESULT_OK) {

                val libras = data?.getIntExtra("libras", 0)
                val opcionFV = data?.getIntExtra("opcionFV", 0)
                val listaFV = data?.getIntExtra("listaFV", 0)
                val registro = data?.getParcelableExtra<Registro>("registroDonacionRealizada")

                Log.e("libras_r", libras.toString())

                for (registro in listaBancoDonacion) {
                    if (registro.tipoOpcion == opcionFV && registro.tipoLista == listaFV) {
                        if (registro.libras - libras!! >= 0) {
                            registro.libras = registro.libras - libras

                        }
                        else{
                            Toast.makeText(this, "Debe Ingresar valor menor a las libras que existen", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                if (registro != null) {
                    guardarRegistro(registro, 100)

                }

            }

        }

    }

}



