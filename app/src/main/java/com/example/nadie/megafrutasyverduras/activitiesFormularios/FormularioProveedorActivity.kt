package com.example.nadie.megafrutasyverduras.activitiesFormularios

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import kotlinx.android.synthetic.main.activity_formulario_proveedor.*
import java.lang.Exception
import java.util.*

class FormularioProveedorActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    lateinit var registroProveedor: Proveedor
    lateinit var spinnerCiudadProveedor: ArrayAdapter<CharSequence>
    lateinit var dialogOnClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_proveedor)



        btnRegistrarFS.setOnClickListener(this)
        btnCancelarFS.setOnClickListener(this)
        btnFechaFS.setOnClickListener(this)
        textViewFechaRegistroFS.setOnClickListener(this)



        cargarRegistros()
        mostrarCalendario()
        mostrarCuadroDeDialogo()
    }


    /**
     * Funcion encargada de cargar los datos de la aplicacion y de las escuchas de
     * los botones para sus correspondientes acciones
     */

    fun cargarRegistros() {


        spinnerCiudadP.onItemSelectedListener = this

        spinnerCiudadProveedor =
            ArrayAdapter.createFromResource(this, R.array.lista_ciudades, android.R.layout.simple_list_item_1)
        spinnerCiudadP.adapter = spinnerCiudadProveedor
    }

    /**
     * Funcion encargada de mostrar el calendario cuando el usuario seleccione el boton de seleccionFecha
     */
    fun mostrarCalendario() {

        val c = Calendar.getInstance()
        val v_year = c.get(Calendar.YEAR)
        val v_month = c.get(Calendar.MONTH)
        val v_day = c.get(Calendar.DAY_OF_MONTH)

        btnFechaFS.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                val ediTxtFechaRegistroFP:TextView=findViewById(R.id.textViewFechaRegistroFS)
                ediTxtFechaRegistroFP.setText("" + dayOfMonth + "/" + month + "/" + year)
                //ediTxtFechaRegistroFP

            }, v_year, v_month, v_day)
            dpd.show()
        }

    }

    /**
     * Funcion encargada de escuchar los eventos asociados a cada boton y de realizar
     * sus correspondientes acciones e incocaciones a funciones o metodos si los hubiera
     */
    override fun onClick(v: View?) {


        if (v?.id == btnRegistrarFS.id) {

            val ediTxtFechaRegistroFP:TextView=findViewById(R.id.textViewFechaRegistroFS)

            if (!ediTxtNombreP.text.isEmpty() && !ediTxtTelefonoP.text.isEmpty()
                && !spinnerCiudadP.selectedItem.toString().equals("Seleccione Ciudad de Procedencia")
                && !ediTxtFechaRegistroFP.text.isEmpty()) {


                try {
                    registroProveedor = Proveedor()


                    registroProveedor.nombre = ediTxtNombreP.text.toString()
                    registroProveedor.ciudad = spinnerCiudadP.selectedItem.toString()
                    registroProveedor.telefono = ediTxtTelefonoP.text.toString()
                    registroProveedor.fechaRegistro = ediTxtFechaRegistroFP.text.toString()

                    //  listaProveedores.add(registroProveedor)
                    var intent = Intent()
                    intent.putExtra("registroProveedor", registroProveedor)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                }catch (e:Exception){
                    Toast.makeText(this, "Verifique la información ingresada", Toast.LENGTH_LONG).show()
                }


            } else {
                Toast.makeText(this, "Debe Ingresar los valores en cada uno de los items", Toast.LENGTH_LONG).show()
            }


        } else if (v?.id == btnCancelarFS.id) {
            builder = AlertDialog.Builder(this)
            builder.setMessage("Esta seguro de abandonar el registro?").setPositiveButton("Si", dialogOnClickListener)
                .setNegativeButton("No", dialogOnClickListener).show()

        }
    }

    /**
     * @dialogClickListener Variable que contiene la accion del DialogInterface.OnclickListener y
     * si la accion al boton del cuadro de dialogo mostrado es positivo o SI, llama a finish() y sale de
     * la actividad de edicion del registro en el que se encuentre.
     * La accion se lleva a cabo dentro de la funcion onClick cuando se presiona el
     * boton btnCancelar.
     * Funcion que contiene la variable que lleva a cabo la accion antes mencionada
     */
    fun mostrarCuadroDeDialogo() {

        dialogOnClickListener=DialogInterface.OnClickListener { dialog, which ->

            when(which){
                DialogInterface.BUTTON_POSITIVE ->
                    finish()

            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            // spinnerCiudades.adapter=spinnerCiudadProveedor

        }
    }

    override fun onBackPressed() {
        builder=AlertDialog.Builder(this)
        builder.setMessage("Esta seguro de abandonar el registro?").setPositiveButton("Si",dialogOnClickListener)
            .setNegativeButton("No",dialogOnClickListener).show()

    }
}
