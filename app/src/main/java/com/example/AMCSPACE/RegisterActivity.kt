package com.example.AMCSPACE

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        val submitBtn=findViewById<Button>(R.id.id_reg_submit)
        var email=findViewById<EditText>(R.id.id_reg_email)
        var password=findViewById<EditText>(R.id.id_reg_password)
        var name = findViewById<EditText>(R.id.id_reg_name)
        var phone = findViewById<EditText>(R.id.id_reg_phone)
        submitBtn.setOnClickListener{
            if(checking())
            {
                var emailreg=email.text.toString()
                var passwordreg=password.text.toString()
                var namereg=name.text.toString()
                var phonereg=phone.text.toString()
                val user= hashMapOf(
                    "Name" to namereg,
                    "Phone" to phonereg,
                    "Email" to emailreg
                )
                val users=db.collection("USERS")
                val query=users.whereEqualTo("Email",emailreg).get()
                    .addOnSuccessListener {
                        tasks ->
                        if(tasks.isEmpty)
                        {
                           auth.createUserWithEmailAndPassword(emailreg,passwordreg)
                               .addOnCompleteListener(this) {
                                   task ->
                                   if (task.isSuccessful)
                                   {
                                       users.document(emailreg).set(user)
                                       val intent= Intent(this,ImagesActivity::class.java)
                                       startActivity(intent)
                                       finish()
                                   }
                                   else
                                   {
                                       Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
                                   }
                               }
                        }
                        else
                        {
                            Toast.makeText(this, "User already registered", Toast.LENGTH_LONG).show()
                            val intent= Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                
                        
            }
            else
            {
                Toast.makeText(this, "enter the details", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun checking():Boolean
    {
        var name = findViewById<EditText>(R.id.id_reg_name)
        var phone = findViewById<EditText>(R.id.id_reg_phone)
        var email = findViewById<EditText>(R.id.id_reg_email)
        var password = findViewById<EditText>(R.id.id_reg_password)
        return if(name.text.toString().trim{it<=' '}.isNotEmpty()
            && phone.text.toString().trim{it<=' '}.isNotEmpty()
            && email.text.toString().trim{it<=' '}.isNotEmpty()
            && password.text.toString().trim{it<=' '}.isNotEmpty()) {
            true
        } else{
            false
        }
    }
}