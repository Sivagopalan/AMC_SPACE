package com.example.AMCSPACE

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AdminLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        var submit = findViewById<Button>(R.id.submit_btn_al)
        submit.setOnClickListener {
            var password = findViewById<EditText>(R.id.admin_password)
            var enteredpassword = password.text.toString()
            var pass_1 = "AMC1881ADMINLOGIN"
            if (enteredpassword == pass_1) {
                var intent = Intent(this, LoggedIn::class.java)
                startActivity(intent)
                finish()
            }
            else {
                    Toast.makeText(this,"Wrong Password",Toast.LENGTH_LONG).show()
                }

        }

    }
}