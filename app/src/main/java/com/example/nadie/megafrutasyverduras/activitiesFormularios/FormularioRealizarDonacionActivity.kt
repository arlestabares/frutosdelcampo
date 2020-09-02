package com.example.nadie.megafrutasyverduras.activitiesFormularios

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.nadie.megafrutasyverduras.R
import com.example.nadie.megafrutasyverduras.modelo.Registro
import kotlinx.android.synthetic.main.activity_realizar_donaciones.*
import java.lang.Exception

class FormularioRealizarDonacionActivity() : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {


    lateinit var adapterSpinnerFundaciones: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerFruta: ArrayAdapter<CharSequence>
    lateinit var adapterSpinnerVerdura: ArrayAdapter<CharSequence>
    var registroDonacion: ArrayList<Registro>? = null
    lateinit var registro: Registro
    lateinit var builder:AlertDialog.Builder
    lateinit var dialogClickListener: DialogInterface.OnClickListener
    var libras: Int = 0
    var calculado: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_donaciones)

        btnCantidadDonar.setOnClickListener(this)
        btnDonar.setOnClickListener(this)

        //registroDonacion = intent.getParcelableExtra("registro_Desde_Activity_Main")
        registroDonacion = intent.getParcelableArrayListExtra("registroDesdeActivity")
        Log.e("lista donacion", registroDonacion.toString())


        var tipoProducto = arrayOf("Seleccione Opcion Fruta o Verdura", "Fruta", "Verdura")
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipoProducto)
        spinnerOpcionFV.adapter = adapter
        spinnerOpcionFV.onItemSelectedListener = this

        adapterSpinnerFundaciones =
            ArrayAdapter.createFromResource(this, R.array.Lista_fundaciones, android.R.layout.simple_list_item_1)
        adapterSpinnerFruta =
            ArrayAdapter.createFromResource(this, R.array.lista_frutas, android.R.layout.simple_list_item_1)
        adapterSpinnerVerdura =
            ArrayAdapter.createFromResource(this, R.array.lista_verduras, android.R.layout.simple_list_item_1)

        spinnerFundacionParaDonar.adapter = adapterSpinnerFundaciones

    }

    override fun onClick(v: View?) {

        if (v?.id == btnCantidadDonar.id) {

            if (!calculado) {

                calculado = true

                for (registro in registroDonacion!!) {

                    if (registro.tipoOpcion == spinnerOpcionFV.selectedItemPosition
                        && registro.tipoLista == spinnerListaFV.selectedItemPosition && registro.libras > 0
                    ) {

                        libras += registro.libras

                    } else {
                        Toast.makeText(this, "No hay libras en stock para donar = " + libras, Toast.LENGTH_LONG)
                    }
                }
            }
            Toast.makeText(this, "La cantidad de libras disponibles para donar es = " + libras, Toast.LENGTH_LONG)
                .show()
        } else if(v?.id==btnDonar.id)



                if (!ediTxtCantidadLibras.toString().isEmpty()
                    && !spinnerFundacionParaDonar.selectedItem.toString().equals("Seleccione Fundacion Para Donar")
                    && !spinnerOpcionFV.selectedItem.toString().equals("Seleccione Opcion Fruta o Verdura")
                    && !spinnerListaFV.selectedItem.toString().equals("Seleccione Fruta")
                    && !spinnerListaFV.selectedItem.toString().equals("Seleccione Verdura")) {

                    if (ediTxtCantidadLibras.text.toString().toInt() > libras) {
                        Toast.makeText(
                            this, "Debe ingresar un valor menor o igual a las " +
                                    "libras disponibles  que son = " + libras, Toast.LENGTH_LONG
                        ).show()
                    } else {

                        try {
                            registro = Registro()

                            registro.nombreFV = spinnerListaFV.selectedItem.toString()
                            registro.libras = ediTxtCantidadLibras.text.toString().toInt()
                            registro.nombreFundacion = spinnerFundacionParaDonar.selectedItem.toString()

                            registro.tipoOpcion = spinnerOpcionFV.selectedItemPosition
                            registro.tipoLista = spinnerListaFV.selectedItemPosition

                            val intent = Intent()
                            intent.putExtra("registroDonacionRealizada", registro)
                            intent.putExtra("libras", ediTxtCantidadLibras.text.toString().toInt())
                            intent.putExtra("opcionFV", spinnerOpcionFV.selectedItemPosition)
                            intent.putExtra("listaFV", spinnerListaFV.selectedItemPosition)
                            setResult(Activity.RESULT_OK, intent)
                            finish()

                        } catch (e: Exception) {
                            Toast.makeText(this, "Verifique la información ingresada", Toast.LENGTH_LONG).show()

                        }
                    }



            } else {


                Toast.makeText(this, "Debe verificar los valores asociados al registro", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == 0) {
            spinnerListaFV.adapter = null
        } else if (position == 1) {
            spinnerListaFV.adapter = adapterSpinnerFruta

        } else if (position == 2) {
            spinnerListaFV.adapter = adapterSpinnerVerdura

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        builder=AlertDialog.Builder(this)
        builder.setMessage("Esta seguro de abandinar el registro?").setPositiveButton("Si",dialogClickListener)
            .setNegativeButton("No",dialogClickListener).show()
    }
}
