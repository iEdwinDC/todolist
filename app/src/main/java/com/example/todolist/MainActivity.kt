package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import java.io.File

class MainActivity : AppCompatActivity(), AdapterView.OnItemLongClickListener{

    private lateinit var listView: ListView
    private var stringArray: ArrayList<String> = arrayListOf()
    private var wordToAdd: String = ""
    private val fileToSave = FileToSave(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {

        if(fileToSave.readFile().isNotEmpty()){
            stringArray = fileToSave.readFile()
        }

        listView = findViewById(R.id.listview)
        val button: ImageButton = findViewById(R.id.button_plus)
        val editText: EditText = findViewById(R.id.editText_addWord)
        val listViewAdapter = ArrayAdapter(this, R.layout.list_view_item, stringArray)

        editText.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                wordToAdd = it.toString()
            }
        }

        button.setOnClickListener {
            if (wordToAdd.isNotEmpty()) {
                updateListView(wordToAdd)
                wordToAdd=""
                editText.text = Editable.Factory().newEditable(wordToAdd)
            }
        }

        listView.onItemLongClickListener = this

        listView.adapter = listViewAdapter
    }

    private fun updateListView(word: String) {
        stringArray.add(word)
        val listViewAdapter = ArrayAdapter(this, R.layout.list_view_item, stringArray)
        listView.adapter = listViewAdapter
        fileToSave.saveFile(stringArray)
        Log.d("file", "${fileToSave.readFile()}")
    }

    override fun onItemLongClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long): Boolean {
        stringArray.removeAt(position)

        val listViewAdapter = ArrayAdapter(this, R.layout.list_view_item, stringArray)
        listView.adapter = listViewAdapter

        fileToSave.saveFile(stringArray)
        Log.d("file", "${fileToSave.readFile()}")

        return true
    }

}