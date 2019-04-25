package com.sovize.saver

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sovize.saver.utilities.PermissionRequester
import com.sovize.saver.utilities.FileManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val key = "com.sovize.saver.PREFERENCE_FILE"
    private val keymail = "mail"
    private val tag = "MainActivity"
    private val permission = PermissionRequester()
    private val fileKeeper = FileManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO (1) Obtener acceso a Shared Preference
        // TODO (2) Obtener Shared Preference desde la actividad
        // TODO (3) Especificar el nombre del archivo de preferencia (si se esta usando mas de uno)
        // TODO (3.1) Cuando se necesita solamente un archivo de preferencia en el app llamar a getPreferences con un paramentro
        // TODO (4) Asignar que la preferencias se leeran o escribiran en modo privado (Para mantender privado las configuración)
        val sharedPref = getSharedPreferences(key, Context.MODE_PRIVATE)


        bt_save.setOnClickListener {
            // TODO (5) Para escribir valores en shared preference
            with(sharedPref.edit()) {
                putString(keymail, et_option.text.toString()) // TODO (6) Se guardan en formato clave valor
                apply() // TODO (7) Confirma que se guarden todos los elementos añadidos

            }

            tv_data.text = et_option.text.toString() // Solamente para mostrar el valor de inmediato
        }


        // TODO (8) Para leer basta con ejecutar el metodo getXXXX y definir el valor por defecto si no existe
        val email = sharedPref.getString(keymail, "Fail")

        tv_data.text = email


        bt_write_internal.setOnClickListener {
            // TODO (9) openFileOutput crea un archivo y un InputStream para escribir en el
            val filename = "email.txt"
            val fileContent = "email: $email"

            // TODO (10) Al usar use obtenemos el fileInput que devuelve FileOutputStream
            // TODO (11) use cierra el FileOutputStream y maneja la exception a nivel de bloque

            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(fileContent.toByteArray())
            }

        }


        bt_read_internal.setOnClickListener {
            // TODO (12) Abrir un archivo existente
            val filename = "email.txt"
            openFileInput(filename).use {
                val text = it.bufferedReader().readText() // TODO (13) Se lee todo el contenido
                tv_data.text = text
            }
        }

        bt_write_external.setOnClickListener {
            saveExt()
        }
    }

    private fun saveExt() {
        if (permission.hasExtStoragePermission(this)) {
            val file = fileKeeper.makeTxtFIle("saver.txt", applicationContext)
            Log.d(tag, "this is the path: ${file.absolutePath}")
            fileKeeper.writeTxtFIle(file, tv_data.text.toString())
        } else {
            permission.askExtStoragePermission(this)
        }
    }
}
