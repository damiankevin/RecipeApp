package com.android.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_data.*
import kotlinx.android.synthetic.main.activity_list_activity.*
import java.util.ArrayList

class ListActivity : AppCompatActivity() {

    private val TAG = "ListDataActivity"

    internal var mDatabaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_activity)
        setSupportActionBar(toolbarList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mDatabaseHelper = DatabaseHelper(this)
        populateListView()

    }

    override fun onResume() {
        super.onResume()
        populateListView()
    }
    private fun populateListView() {
        //get the data and append to a list
        val data = mDatabaseHelper?.data
        val listData = ArrayList<String>()
        val listRecipe = ArrayList<String>()
        val listStep = ArrayList<String>()
        val listUrl = ArrayList<String>()
        while (data?.moveToNext()!!) {
            listData.add(data.getString(1)!!)
            listRecipe.add(data.getString(2)!!)
            listStep.add(data.getString(3)!!)
            listUrl.add(data.getString(4)!!)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listData)
        listView.adapter = adapter

        //set an onItemClickListener to the ListView
        listView.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val name = listData[i]
            val recipe = listRecipe[i]
            val step = listStep[i]
            val url = listUrl[i]

            Log.d(TAG, "onItemClick: You Clicked on $name")

            val data = mDatabaseHelper?.getItemID(name) //get the id associated with that name
            var itemID = -1
            while (data?.moveToNext()!!) {
                itemID = data.getInt(0)
            }
            if (itemID > -1) {
                Log.d(TAG, "onItemClick: The ID is: $itemID")
                val editScreenIntent = Intent(this@ListActivity, EditDataActivity::class.java)
                editScreenIntent.putExtra("id", itemID)
                editScreenIntent.putExtra("name", name)
                editScreenIntent.putExtra("recipe",recipe)
                editScreenIntent.putExtra("step",step)
                editScreenIntent.putExtra("url",url)

                startActivity(editScreenIntent)
            } else {
                toastMessage("No ID associated with that name")
            }
        })
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

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
