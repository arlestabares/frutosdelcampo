package com.example.nadie.megafrutasyverduras.activitiesFormularios

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
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_formulario_donacion.*
import java.util.*

class FormularioBancoDonacionActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    lateinit var registro: Registro
    lateinit var adapterSpinnerFrutas: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerVerduras: ArrayAdapter<CharSequence>
    lateinit var dialogClickListener: DialogInterface.OnClickListener
    lateinit var builder: AlertDialog.Builder
    lateinit var textViewFechaRegistroFD: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_donacion)

        btnRegistrarFD.setOnClickListener(this)
        btnCancelarFD.setOnClickListener(this)

        textViewFechaRegistroFD = findViewById(R.id.txtViewFechaRegistroFD)

        cargarRegistros()
        mostrarCalendario()
        mostrarCuadroDeDialogo()

    }

    /**
     * Funcion encargada de cargar los registros iniciales del XML asociado a esta actividad,lo
     * mismo que de iniciar las escuchas necesarias para la ejecucion de los valores y registros asociados
     * a cada variable.
     *
     */
    fun cargarRegistros() {

        var tipoProducto = arrayOf("Seleccione Opcion", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)

        spinnerOpcionFD.adapter = adapter
        spinnerOpcionFD.onItemSelectedListener = this
        adapterSpinnerFrutas =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)
        adapterSpinnerVerduras =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)


    }

    /**
     * funcion encargada de disparar los eventos asociados a los botones
     * de la interfaz del XML de esta activity con sus correspondientes acciones
     */
    override fun onClick(v: View?) {


        if (v?.id == btnRegistrarFD.id) {

            if (!spinnerOpcionFD.selectedItem.toString().equals("Seleccione Opcion")
                && !spinnerListaFD.selectedItem.toString().equals("Seleccione Fruta")
                && !spinnerListaFD.selectedItem.toString().equals("Seleccione Verdura")
                && !ediTxtLibrasFD.text.isEmpty()
                && !ediTxtBultosFD.text.isEmpty()
                && !textViewFechaRegistroFD.text.isEmpty()
            ) {


                try {

                    registro = Registro()

                    registro.nombreFV = spinnerListaFD.selectedItem.toString()
                    registro.libras = ediTxtLibrasFD.text.toString().toInt()
                    registro.bultos = ediTxtBultosFD.text.toString().toInt()
                    registro.fechaRegistro = textViewFechaRegistroFD.text.toString()

                    registro.tipoOpcion = spinnerOpcionFD.selectedItemPosition
                    registro.tipoLista = spinnerListaFD.selectedItemPosition

                    /*intent con destino a  MainActivity , quien se encarga de gestionar los registros guaradandolos
                    en cada una de las listas para ser enviadas hacia la actividad que lo requiera */
                    var intent = Intent()
                    intent.putExtra("registroDesdeFormularioDonacion", registro)
                    setResult(Activity.RESULT_OK, intent)
                    finish()


                } catch (e: Exception) {
                    Toast.makeText(this, "Verifique la información ingresada", Toast.LENGTH_LONG).show()

                }


            } else {
                Toast.makeText(this, "Debe Ingresar los valores en cada uno de los items", Toast.LENGTH_LONG).show()
            }


        } else if (v?.id == btnCancelarFD.id) {

            builder = AlertDialog.Builder(this)
            builder.setMessage("Esta seguro de cancelar el registro?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()

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
                val txtViewFechaRegistro: TextView = findViewById(R.id.txtViewFechaRegistroFD)

                txtViewFechaRegistro.setText("" + dayOfMonth + "/" + month + "/" + year)

            }, v_year, v_month,v_day)
            dpd.show()
        }

    }

    /**
     * @dialogClickListener Variable que contiene la accion del DialogInterface.OnclickListener y
     * si la accion al boton del cuadro de dialogo mostrado es positivo , llama a finish() y sale de
     * la actividad  del registro en el que se encuentre.
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaFD.adapter = null
        } else if (position == 1) {
            spinnerListaFD.adapter = adapterSpinnerFrutas
        } else if (position == 2) {
            spinnerListaFD.adapter = adapterSpinnerVerduras
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        builder = AlertDialog.Builder(this)
        builder.setMessage("Esta seguro de abandonar el registro?").setPositiveButton("Si", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

}
