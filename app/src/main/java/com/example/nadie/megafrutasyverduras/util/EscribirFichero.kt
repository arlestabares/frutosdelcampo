package com.example.nadie.megafrutasyverduras.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


object EscribirFichero {

    fun savePdf(contexto:Context, mostrarText:String) {
        //create object of Document class
        val mDoc = Document()
        //pdf file name
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        //pdf file path
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName +".pdf"
        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //open the document for writing
            mDoc.open()

            //add author of the document (metadata)
            mDoc.addAuthor("Arles Tabares")

            //add paragraph to the document
            mDoc.add(Paragraph(mostrarText))

            //close document
            mDoc.close()

            //show file saved message with file name and path
            Toast.makeText(contexto, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            //if anything goes wrong causing exception, get and show exception message
            Toast.makeText(contexto, e.message, Toast.LENGTH_SHORT).show()
        }
    }


}