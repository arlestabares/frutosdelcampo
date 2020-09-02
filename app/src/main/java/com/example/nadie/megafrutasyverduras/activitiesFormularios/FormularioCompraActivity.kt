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
import com.example.nadie.megafrutasyverduras.bd.ConexionSQLite
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_formulario_compra.*
import java.lang.Exception
import java.util.*

class FormularioCompraActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    lateinit var registroCompra: Registro
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var adaptadorSpinnerVerduras: ArrayAdapter<CharSequence>
    lateinit var adaptadorSpinnerCiudad: ArrayAdapter<CharSequence>
    lateinit var dialogClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder
    lateinit var txtViewFechaRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_compra)

        txtViewFechaRegistro = findViewById(R.id.ediTxtFechaRegistroFC)
        btnRegistrarFC.setOnClickListener(this)
        btnCancelarFC.setOnClickListener(this)
        btnFechaFS.setOnClickListener(this)
        txtViewFechaRegistro.setOnClickListener(this)

        cargarRegistro()
        mostrarCalendario()
        mostrarCuadroDialogo()

    }

    /**
     *Funcion encargada de llevar a cabo la asignacion  e inicializacion de valores a las variables
     *asociados al XML relacionado con esta actividad,para el correpto despliegue y
     * funcionamiento de la misma
     */
    fun cargarRegistro() {

        var tipoProducto = arrayOf("Seleccione Opcion", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionFC.adapter = adapter
        spinnerOpcionFC.onItemSelectedListener = this

        adapterSpinnerFrutas =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)

        adaptadorSpinnerVerduras =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)

        adaptadorSpinnerCiudad =
            ArrayAdapter.createFromResource(this, R.array.lista_ciudades, android.R.layout.simple_list_item_1)
        spinnerCiudadFC.adapter = adaptadorSpinnerCiudad


    }


    /**
     * @dialogClickListener Variable que contiene la accion del DialogInterface.OnclickListener y
     * si la accion al boton del cuadro de dialogo mostrado es positivo o SI, llama a finish() y sale de
     * la actividad de edicion del registro en el que se encuentre.
     * La accion se lleva a cabo dentro de la funcion onClick cuando se presiona el
     * boton btnCancelar.
     * Funcion que contiene la variable que lleva a cabo la accion antes mencionada
     */
    fun mostrarCuadroDialogo() {

        dialogClickListener = DialogInterface.OnClickListener { dialog, which ->

            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    finish()
                }
            }
        }
    }


    /**
     * Funcion encargada de generar una accion al click de cualquiera de los dos botones.
     * Del boton btnRegistrar,accion que es la de  asignar nuevos valores a el objeto
     * registroCompra con los valores asociados en las variables del xml, enviando el objeto a la
     * InterfazPrincipalFragment para ser enviado a las actividades que lo requieran. La accion
     * del boton btnCancelar finaliza la actividad y regresa la llamada a la actividad
     * que la invoco.
     * @builder Variable de tipo AlertDialog.Builder encargada de construir el cuadro de dialogo que
     * le notifica al usuario en caso de seleccionar el boton cancelar edicion mediante un cuadro
     * de dialogo si desea abandonar la edicion actual o por el contrario decide seguir en dicha actividad
     */
    override fun onClick(v: View?) {


        if (v?.id == btnRegistrarFC.id) {


            if (!spinnerOpcionFC.selectedItem.toString().equals("Seleccione Opcion")
                && !spinnerListaFC.selectedItem.toString().equals("Seleccione Fruta")
                && !spinnerListaFC.selectedItem.toString().equals("Seleccione Verdura")
                && !ediTxtPrecioLibraFC.text.isEmpty()
                && !ediTxtLibrasFC.text.isEmpty()
                && !ediTxtBultosFC.text.isEmpty()
                && !txtViewFechaRegistro.text.isEmpty()
                && !spinnerCiudadFC.selectedItem.toString().equals("Seleccione Ciudad de Procedencia")
            ) {


                try {
                    /*El objeto registroCompra sera enviado mediante el intent al MainActivity que contiene la logica del negocio
               con sus valores asociados para ser enviado desde alli  a las actividades que lo requieran*/

                    registroCompra = Registro()

                    registroCompra.nombreFV = spinnerListaFC.selectedItem.toString()
                    registroCompra.precio = ediTxtPrecioLibraFC.text.toString().toInt()
                    registroCompra.libras = ediTxtLibrasFC.text.toString().toInt()
                    registroCompra.bultos = ediTxtBultosFC.text.toString().toInt()
                    registroCompra.fechaRegistro = txtViewFechaRegistro.text.toString()
                    registroCompra.procedencia = spinnerCiudadFC.selectedItem.toString()
                    registroCompra.tipoOpcion = spinnerOpcionFC.selectedItemPosition
                    registroCompra.tipoLista = spinnerListaFC.selectedItemPosition

                    var sqlite = ConexionSQLite(this, 1)
                    sqlite.insertarRegistro(registroCompra)

                    var intent = Intent()
                    intent.putExtra("registroCompra", registroCompra)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Verifique la información ingresada", Toast.LENGTH_LONG).show()

                }


            } else {
                Toast.makeText(this, "Debe Ingresar los valores en cada uno de los items", Toast.LENGTH_LONG).show()
            }

        } else if (v?.id == btnCancelarFC.id) {
            builder = AlertDialog.Builder(this)
            builder.setMessage("Esta seguro de Cancelar el registro").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
        } else if (v?.id == btnFechaFS.id) {
            mostrarCalendario()

        }
    }


    /**
     * Funcion encargada de mostrar el calendario para realizar el registro
     * del año, mes y dia de la fecha asociada a la compra
     */
    fun mostrarCalendario() {

        val c = Calendar.getInstance()
        val v_year = c.get(Calendar.YEAR)
        val v_month = c.get(Calendar.MONTH)
        val v_day = c.get(Calendar.DAY_OF_MONTH)

        btnFechaFS.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                txtViewFechaRegistro.setText("" + dayOfMonth + "/" + month + "/" + year)

            }, v_year,v_month, v_day)
            dpd.show()
        }
    }

    /**
     * @param position La posicion asociada al arrayOf de elementos llevada a cabo en
     * la funcion cargarRegistro.
     * @spinnerCompra Nombre del Spinner del XML relacionado con esta actividad que contiene
     * valores asociados para ser desplegados en funcion de la posicion seleccionada dentro
     * de esta actividad.
     * Funcion encargada de escuchar y desplegar las acciones relacionadas al evento asociado
     * al spinnerCompraVF llevado a cabo en la funcion cargarRegistro
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaFC.adapter = null
        } else if (position == 1) {
            spinnerListaFC.adapter = adapterSpinnerFrutas
        } else if (position == 2) {
            spinnerListaFC.adapter = adaptadorSpinnerVerduras
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        builder = AlertDialog.Builder(this)
        builder.setMessage("Esta seguro de abandonar el registro?").setPositiveButton("Si",dialogClickListener)
            .setNegativeButton("No",dialogClickListener).show()
    }
}
