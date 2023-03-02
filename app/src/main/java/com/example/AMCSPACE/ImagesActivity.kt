package com.example.AMCSPACE

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.AMCSPACE.databinding.ActivityImagesBinding
import com.google.firebase.firestore.FirebaseFirestore


class ImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagesBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRecyclerview.apply{
            layoutManager= LinearLayoutManager(this@ImagesActivity)
        }
        fetchdata()
    }
    private fun fetchdata(){
        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val user = documents.toObjects(UserModel::class.java)
                    binding.mainRecyclerview.adapter = UserAdapter(this,user)

                }


            }
            .addOnFailureListener {
                showToast("an error occurred: ${it.localizedMessage}")

            }

    }
}