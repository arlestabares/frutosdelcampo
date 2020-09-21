package com.example.nadie.megafrutasyverduras.activitiesFormularios

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.nadie.megafrutasyverduras.R

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var hdn:Handler = Handler()
        hdn.postDelayed(Runnable {
            var intent:Intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }, 4000)

    }
}
