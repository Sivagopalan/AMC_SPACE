package com.example.AMCSPACE

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.AMCSPACE.databinding.ActivityLoggedInBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class LoggedIn : AppCompatActivity() {
    private lateinit var binding: ActivityLoggedInBinding
    private lateinit var storageRef:StorageReference
    private lateinit var firebaseFirestore:FirebaseFirestore
    private var imageUri:   Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoggedInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var list= ArrayList<String>().apply {
            add("DEPT OF TAMIL")
            add("DEPT OF ENGLISH")
            add("DEPT OF HINDI")
            add("DEPT OF FRENCH")
            add("DEPT OF ENGLISH")
            add("DEPT OF MATHEMATICS")
            add("DEPT OF PHYSICS")
            add("DEPT OF CHEMISTRY")
            add("DEPT OF BOTANY")
            add("DEPT OF ZOOLOGY")
            add("DEPT OF ECONOMICS")
            add("DEPT OF COMMERCE")
            add("DEPT OF PHYSICAL EDUCATION")
            add("DEPT OF COMPUTER SCIENCE")
            add("DEPT OF DATA SCIENCE")
            add("DEPT OF MICROBIOLOGY")
            add("DEPT OF BIOCHEMISTRY")
            add("DEPT OF BUSINESS ADMINISTRATION")
            add("DEPT OF MANAGEMENT STUDIES")
            add("DEPT OF SOCIAL WORK")
            add("DEPT OF PSYCHOLOGY")
            add("DEPT OF VISUAL COMMUNICATION")
            add("DEPT OF FOOD SCIENCE & NUTRITION")
            add("DEPT OF BIOTECHNOLOGY")
            add("DEPT OF RELIGION, PHILOSOPHY & SOCIOLOGY")
        }

        val adapter = ArrayAdapter(this,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,list)


        val spintest = findViewById<Spinner>(R.id.spnTest)!!

        spintest.adapter=adapter

        spintest.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                var item:String = list[position]
                registerClickEvents(item)
            }
        }

        initVars()

    }
    private fun registerClickEvents(item:String) {
        binding.uploadBtn.setOnClickListener {
            uploadImage(item)
        }

        binding.showAllBtn.setOnClickListener {
            startActivity(Intent(this, ImagesActivity::class.java))
        }

        binding.imageView.setOnClickListener {
            resultLauncher.launch("users/*")
        }
    }
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        binding.imageView.setImageURI(it)
    }

    private fun initVars() {

        storageRef = FirebaseStorage.getInstance().reference.child("users")
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun uploadImage(item:String) {
        binding.progressBar.visibility = View.VISIBLE





        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        val map = HashMap<String, Any>()
                        map["userImage"] = uri.toString()
                        map["userName"] = item


                        firebaseFirestore.collection("users").add(map).addOnCompleteListener { firestoreTask ->

                            if (firestoreTask.isSuccessful){
                                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()

                            }else{
                                Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()

                            }
                            binding.progressBar.visibility = View.GONE
                            binding.imageView.setImageResource(R.drawable.upload)

                        }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    binding.imageView.setImageResource(R.drawable.upload)
                }
            }
        }
    }




















}

