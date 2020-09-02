package com.example.nadie.megafrutasyverduras.editarFormularios

import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.adapter.AdapterProveedores
import com.example.nadie.megafrutasyverduras.modelo.Proveedor
import kotlinx.android.synthetic.main.activity_editar_proveedor.*
import java.lang.Exception
import java.util.*

class EditarProveedorActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    var posicion: Int = 0
    lateinit var registroProveedor: Proveedor
    lateinit var adapter: AdapterProveedores
    lateinit var adapterSpinnerCiudad: ArrayAdapter<CharSequence>
    lateinit var dialogClickListener: DialogInterface.OnClickListener
    lateinit var build: AlertDialog.Builder
    lateinit var txtViewFechaRegistroEP: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_proveedor)

        btnCancelarEP.setOnClickListener(this)
        btnEditarProveedorEP.setOnClickListener(this)
        btnEliminarProveedor.setOnClickListener(this)

        txtViewFechaRegistroEP = findViewById(R.id.txtViewFechaRegistroEP)

        cargarRegistros()
        mostarCalendario()
        mostrarCuadroDeDialogo()

    }

    /**
     *Funcion encargada de iniciar los correspondientes registros asociados a las
     * variables de este contexto
     */
    fun cargarRegistros() {

        //intent proveniente desde AdapterProveedores
        registroProveedor = intent.getParcelableExtra("listaDesdeProveedorAdapter")
        posicion = intent.getIntExtra("posicion", 0)

        ediTxtNombreEP.setText(registroProveedor.nombre)
        ediTxtTelefonoEP.setText(registroProveedor.telefono)
        txtViewFechaRegistroEP.setText(registroProveedor.fechaRegistro)

        adapterSpinnerCiudad =
            ArrayAdapter.createFromResource(this, R.array.lista_ciudades, android.R.layout.simple_list_item_1)
        spinnerCiudadEP.adapter = adapterSpinnerCiudad

        var posCiudad = adapterSpinnerCiudad.getPosition(registroProveedor.ciudad)
        spinnerCiudadEP.setSelection(posCiudad)
    }


    override fun onClick(v: View?) {

        if (v?.id == btnEditarProveedorEP.id) {


            if (!ediTxtNombreEP.text.isEmpty() && !spinnerCiudadEP.selectedItem.toString().isEmpty()
                && !ediTxtTelefonoEP.text.isEmpty() && !txtViewFechaRegistroEP.text.isEmpty()
            ) {

                try {
                    registroProveedor.nombre = ediTxtNombreEP.text.toString()
                    registroProveedor.ciudad = spinnerCiudadEP.selectedItem.toString()
                    registroProveedor.telefono = ediTxtTelefonoEP.text.toString()
                    registroProveedor.fechaRegistro = txtViewFechaRegistroEP.text.toString()

                    val intent = Intent()
                    intent.putExtra("codigo", 1)
                    intent.putExtra("registro", registroProveedor)
                    intent.putExtra("posicion", posicion)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this, "Verifique la información ingresada", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Debe Ingresar los valores en cada uno de los items", Toast.LENGTH_LONG).show()
            }

        } else if (v?.id == btnCancelarEP.id) {

            build = AlertDialog.Builder(this)
            build.setMessage("Esta seguro de abandonar el registro?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()

        } else if (v?.id == btnEliminarProveedor.id) {
            val intent = Intent()
            intent.putExtra("codigo", 2)
            intent.putExtra("registroEliminar", registroProveedor)
            intent.putExtra("posicionEliminar", posicion)
            setResult(Activity.RESULT_OK, intent)
            finish()

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

        dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {

                    finish()
                }
            }

        }

    }

    /**
     * Funcion encargada de mostrar el calendario para realizar el registro
     * del año, mes y dia de la fecha asociada a la compra
     */
    fun mostarCalendario() {

        val c = Calendar.getInstance()
        val v_year = c.get(Calendar.YEAR)
        val v_month = c.get(Calendar.MONTH)
        val v_day = c.get(Calendar.DAY_OF_MONTH)


        btnFechaFS.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                txtViewFechaRegistroEP.setText("" + dayOfMonth + "/" + month + "/" + year)

            }, v_year, v_month, v_day)
            dpd.show()
        }

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}
