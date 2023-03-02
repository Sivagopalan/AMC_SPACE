package com.example.AMCSPACE

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth=FirebaseAuth.getInstance()
        val adminbtn=findViewById<TextView>(R.id.textadmin)
        val registerbtn=findViewById<Button>(R.id.id_register)
        var email = findViewById<EditText>(R.id.id_email)
        var password = findViewById<EditText>(R.id.id_password)
        val loginbtn=findViewById<Button>(R.id.id_login_button)

        val mString = "Admin"
        val mSpannableString = SpannableString(mString)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        adminbtn.text = mSpannableString
        adminbtn.setOnClickListener{
            var intent = Intent(this,AdminLogin::class.java)
            startActivity(intent)
        }



        registerbtn.setOnClickListener{
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        loginbtn.setOnClickListener{
            if(checking()) {
                var enteredemail = email.text.toString()
                var enteredpassword = password.text.toString()
                auth.signInWithEmailAndPassword(enteredemail, enteredpassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                           var intent = Intent(this,ImagesActivity::class.java)

                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                        }

                    }
            }

            else{
                Toast.makeText(this, "enter the details", Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun checking():Boolean{
        var email = findViewById<EditText>(R.id.id_email)
        var password = findViewById<EditText>(R.id.id_password)
        if(email.text.toString().trim{it<=' '}.isNotEmpty() && password.text.toString().trim{it<=' '}.isNotEmpty())
        {
            return true
        }
        return false
    }
}