package com.example.nadie.megafrutasyverduras.activitiesFormularios

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_formulario_stock.*
import java.util.*

/**
 * Clase encargada de agregar los datos o registros asociados a el formulario StockActivity
 *
 * actualizacio para demostracion
 */
class FormularioStockActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    lateinit var registroStock: Registro
    lateinit var adapterSpinnerVerduras: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var dialogOnClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder
    lateinit var textViewFechaRegistroFS: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_stock)

        textViewFechaRegistroFS = findViewById(R.id.textViewFechaRegistroFS)

        btnRegistrarFS.setOnClickListener(this)
        btnCancelarFS.setOnClickListener(this)

        cargarRegistros()
        mostrarCalendario()
        mostarCuadroDeDialogo()

    }


    /**
     *Funcion encargada de llevar a cabo la asignacion  e inicializacion de valores a las variables
     *asociados al XML relacionado con esta actividad,para el correpto despliegue y
     * funcionamiento de la misma
     */
    fun cargarRegistros() {

        var tipoProducto = arrayOf("Seleccione opcion", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionFS.adapter = adapter
        spinnerOpcionFS.onItemSelectedListener = this


        adapterSpinnerVerduras =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)

        adapterSpinnerFrutas =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)

    }


    /**
     * @dialogClickListener Variable que contiene la accion del DialogInterface.OnclickListener y
     * si la accion al boton del cuadro de dialogo mostrado es positivo o SI, llama a finish() y sale de
     * la actividad de edicion del registro en el que se encuentre.
     * La accion se lleva a cabo dentro de la funcion onClick cuando se presiona el
     * boton btnCancelar.
     * Funcion que contiene la variable que lleva a cabo la accion antes mencionada
     */
    fun mostarCuadroDeDialogo() {
        dialogOnClickListener = DialogInterface.OnClickListener { dialog, which ->


            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    finish()
                }

            }
        }

    }


    /**
     * Funcion encargada de disparar los eventos sobre los botones
     * dentro de la interfaz con sus respectivas acciones asociadas a este contexto
     */
    override fun onClick(v: View?) {


        if (v?.id == btnRegistrarFS.id) {


            if (!spinnerOpcionFS.selectedItem.toString().equals("Seleccione opcion")
                && !spinnerListaFS.selectedItem.toString().equals("Seleccione Fruta")
                && !spinnerListaFS.selectedItem.toString().equals("Seleccione Verdura")
                && !ediTxtLibrasFS.text.isEmpty()
                && !ediTxtBultosFS.text.isEmpty()
                && !textViewFechaRegistroFS.text.toString().isEmpty()) {


                try {
                    registroStock = Registro()

                    registroStock.nombreFV = spinnerListaFS.selectedItem.toString()
                    registroStock.libras = ediTxtLibrasFS.text.toString().toInt()
                    registroStock.bultos = ediTxtBultosFS.text.toString().toInt()
                    registroStock.fechaRegistro = textViewFechaRegistroFS.text.toString()

                    registroStock.tipoOpcion = spinnerOpcionFS.selectedItemPosition
                    registroStock.tipoLista = spinnerListaFS.selectedItemPosition

                    var intent = Intent()
                    intent.putExtra("registroFormularioStock", registroStock)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                } catch (e: Exception) {

                    Toast.makeText(this, "Verifique la información ingresada", Toast.LENGTH_LONG).show()
                }


            } else {
                Toast.makeText(this, "Debe Ingresar los valores en cada uno de los items", Toast.LENGTH_LONG).show()
            }


        } else if (v?.id == btnCancelarFS.id) {
            builder = AlertDialog.Builder(this)
            builder.setMessage("Esta seguro de abandonar el registro?").setPositiveButton("OK", dialogOnClickListener)
                .setNegativeButton("No", dialogOnClickListener).show()

        }
    }

    /**
     * Funcion encargada de mostrar el calendario al usuario para que realice el
     * registro con la fecha necesaria para ello
     */
    fun mostrarCalendario() {

        val c = Calendar.getInstance()
        val v_year = c.get(Calendar.YEAR)
        val v_month = c.get(Calendar.MONTH)
        val v_day = c.get(Calendar.DAY_OF_MONTH)

        btnFechaFS.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                textViewFechaRegistroFS.setText("" + dayOfMonth + "/" + month + "/" + year)
            }, v_year, v_month,v_day)
            dpd.show()
        }

    }

    /**
     * Funcion encargada de las escuchas de los eventos asociados a los spinner
     * del XML asociado a esta actividad.
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaFS.adapter = null
        } else if (position == 1) {
            spinnerListaFS.adapter = adapterSpinnerFrutas
        } else if (position == 2) {
            spinnerListaFS.adapter = adapterSpinnerVerduras
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        builder=AlertDialog.Builder(this)
        builder.setMessage("Esta seguro de abandonar el registro?").setPositiveButton("Si",dialogOnClickListener)
            .setNegativeButton("No",dialogOnClickListener).show()

    }
}
