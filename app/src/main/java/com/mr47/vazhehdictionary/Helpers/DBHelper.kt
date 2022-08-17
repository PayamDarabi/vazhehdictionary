package com.mr47.vazhehdictionary.Helpers

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DBHelper(val context: Context){

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "ExpertsDic"

        // below is the variable for database version
        private val DATABASE_VERSION = 1
        val dbPath = "/data/data/com.mr47.vazhehdictionary/databases/"

        // below is the variable for table name
        val TABLE_VOCABS = "Vocabs"
        val TABLE_MAJORS = "Majors"
        val TABLE_MAJORSVOCABS="MajorsVocabs"

        val ID = "Id"
        val VOCAB_ID = "VocabId"
        val MAJOR_ID = "MajorId"

        // below is the variable for name column
        val VOCAB_PERSIAN = "Persian"
        val VOCAB_ENGLISH = "English"
        val MAJOR_ENGLISHTITLE = "EnglishTitle"
        val MAJOR_PERSIANTITLE = "PersianTitle"

        // below is the variable for age column
        val ISFAVORITE = "IsFavorite"

    }

    val database: SQLiteDatabase

    init {
        database = open()
    }

    private fun open(): SQLiteDatabase {
        val dbFile = context.getDatabasePath("$DATABASE_NAME.db")
        if (!dbFile.exists()){
            try {
                val checkDB = context.openOrCreateDatabase("$DATABASE_NAME.db", Context.MODE_PRIVATE,null)
                checkDB.close()
                copyDatabase(dbFile)
            }catch (e: IOException){
                throw RuntimeException("Error opening db")
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase(dbFile: File) {
        val iss = context.assets.open("$DATABASE_NAME.db")
        val os = FileOutputStream(dbFile)

        val buffer = ByteArray(1024)
        while (iss.read(buffer) > 0) {
            os.write(buffer)
        }
        os.flush()
        os.close()
        iss.close()
    }

    fun close() {
        database.close()
    }

    // below method is to get
    // all data from our database
    fun getVocabs(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it

        // below code returns a cursor to
        // read data from the database
        return database.rawQuery("SELECT * FROM " + TABLE_VOCABS, null)

    }
   
    fun getMajors(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it

        // below code returns a cursor to
        // read data from the database
        return database.rawQuery("SELECT * FROM " + TABLE_MAJORS +" ORDER BY " + MAJOR_PERSIANTITLE +" ASC", null)

    }

    fun getMajorsVocabs(majorId : Int): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value fr
        // below code returns a cursor to
        // read data from the database
        return database.rawQuery(
            "SELECT * FROM " + TABLE_VOCABS + " v JOIN " + TABLE_MAJORSVOCABS + " mv on v.Id= mv.VocabId WHERE mv.MajorId=" + majorId,
            null
        )
    }


}