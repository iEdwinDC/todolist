package com.example.todolist

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileToSave(private val context: Context) {

    fun readFile(): ArrayList<String> {
        try {
            val path: File = context.filesDir
            val file: File = File(path, "list.txt")

            val length = file.length().toInt()
            val bytes = ByteArray(length)
            val readFile = FileInputStream(file)
            try {
                readFile.read(bytes)
            } finally {
                readFile.close()
            }
            val contents = String(bytes)

            return arrayConvert(contents)
        } catch (exception: Exception) {
            return arrayListOf()

        }
    }

    fun saveFile(arrayList: ArrayList<String>) {
        try {
            val path: File = context.filesDir
            val file: File = File(path, "list.txt")

            val stream = FileOutputStream(file)
            try {
                stream.write(stringConvert(arrayList).toByteArray())
            } finally {
                stream.close()
            }
        } catch (exception: Exception) {


        }
    }

    private fun stringConvert(arraylist: ArrayList<String>): String {

        var res: String = ""
        arraylist.map {
            res += "$it|"
        }
        return res
    }

    private fun arrayConvert(fileString: String): ArrayList<String> {

        val res: ArrayList<String> = arrayListOf()
        val stringList = fileString.split('|')
        stringList.map {
            res.add(it)
        }
        res.remove("")
        return res

    }
}