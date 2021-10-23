package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var taskLists = mutableListOf<String>()
    lateinit var adapter : TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val onClickListener = object : TaskAdapter.onLongClickListener {
            override fun onItemLongClicked(position: Int) {
                taskLists.removeAt(position)

                adapter.notifyDataSetChanged()

                saveItems()
            }
        }


        // Button on-click | <Widget Attribute>(Element)
        findViewById<Button>(R.id.button).setOnClickListener {
            // onClick Event

        }

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.listsOfTasks)
        adapter = TaskAdapter(taskLists, onClickListener)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        val inputText = findViewById<EditText>(R.id.addTask)
        // Set up the button and input field
        findViewById<Button>(R.id.button).setOnClickListener{

            val text = inputText.text.toString()
            taskLists.add(text)

            adapter.notifyItemInserted(taskLists.size - 1)

            inputText.setText("")
            saveItems()
        }
    }

    // Save the data that the user has inputted
    fun getDatafile(): File {
        return File(filesDir, "data.txt")
    }

    // LoadItems
    fun loadItems() {
        try {
            taskLists = FileUtils.readLines(getDatafile(), Charset.defaultCharset())
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }


    // Create a method to ge the file we need
    fun saveItems() {
        try {
            FileUtils.writeLines(getDatafile(), taskLists)
        } catch (ioExecption: IOException) {
            ioExecption.printStackTrace()
        }
    }
}