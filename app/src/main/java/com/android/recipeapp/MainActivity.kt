package com.android.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mDatabaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDatabaseHelper = DatabaseHelper(this)
        btnAddData.setOnClickListener {
            checkData()
        }

        btnViewData.setOnClickListener {
            val intent = Intent(this@MainActivity, ListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkData() {
        if (etName.text.toString() == "") {
            Toast.makeText(baseContext, "Name cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (etRecipe.text.toString() == "") {
            Toast.makeText(baseContext, "Recipe cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (etStep.text.toString() == "") {
            Toast.makeText(baseContext, "Step cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (etUrlImage.text.toString() == "") {
            Toast.makeText(baseContext, "Image cannot be empty", Toast.LENGTH_SHORT).show()
        }else{
            addData(etName.text.toString(),etRecipe.text.toString(),etStep.text.toString(),etUrlImage.text.toString())
        }
    }

    fun addData(
        name: String,
        recipe: String,
        step: String,
        url: String
    ) {
        var insertData = mDatabaseHelper?.addData(name,recipe,step,url)
        if (insertData!!) {
            Toast.makeText(baseContext, "Data Successfully Inserted!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(baseContext, "Something went wrong", Toast.LENGTH_SHORT).show()


        }
    }
}
