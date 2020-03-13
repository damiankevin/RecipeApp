package com.android.recipeapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import kotlinx.android.synthetic.main.activity_edit_data.*
import kotlinx.android.synthetic.main.activity_main.*

class EditDataActivity : AppCompatActivity() {


    private val TAG = "EditDataActivity"
    internal var mDatabaseHelper: DatabaseHelper? = null

    private var selectedUrl: String? = ""
    private var selectedStep: String? = ""
    private var selectedIngredient: String? = ""
    private var selectedName: String? = ""
    private var selectedID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data)
        setSupportActionBar(toolbarEditData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mDatabaseHelper = DatabaseHelper(this)
        getIntentBundle()
        passDataIntent()

        btnEditDelete.setOnClickListener {
            mDatabaseHelper!!.deleteId(selectedID)
            Toast.makeText(baseContext,"Removed from database",Toast.LENGTH_SHORT).show()
            finish()
        }

        btnEditSave.setOnClickListener {
            if (etEditName.text.toString() == "") {
                Toast.makeText(baseContext, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (etEditRecipe.text.toString() == "") {
                Toast.makeText(baseContext, "Recipe cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (etEditStep.text.toString() == "") {
                Toast.makeText(baseContext, "Step cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (etEditUrlImage.text.toString() == "") {
                Toast.makeText(baseContext, "Image cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                mDatabaseHelper!!.update(etEditName.text.toString(),
                    selectedID,
                    selectedName.toString(),
                    etEditRecipe.text.toString(),
                    selectedIngredient.toString(),
                    etEditStep.text.toString(),
                    selectedStep.toString(),
                    etEditUrlImage.text.toString(),
                    selectedUrl.toString())

                Toast.makeText(baseContext,"Success update",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun passDataIntent() {
        etEditName.setText(selectedName)
        etEditRecipe.setText(selectedIngredient)
        etEditStep.setText(selectedStep)
        selectedUrl
        etEditUrlImage.setText(selectedUrl)
    }

    private fun getIntentBundle() {
        //get the intent extra from the ListDataActivity
        val receivedIntent = intent
        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id", -1) //NOTE: -1 is just the default value
        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name")
        selectedIngredient = receivedIntent.getStringExtra("recipe")
        selectedStep = receivedIntent.getStringExtra("step")
        selectedUrl = receivedIntent.getStringExtra("url")

        Glide.with(this)
            .load(selectedUrl)
            .apply(
                RequestOptions.bitmapTransform(RoundedCorners(25)).placeholder(
                    this.resources.getDrawable(R.drawable.food_logo)
                )
            )
            .into(civEditLogo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
