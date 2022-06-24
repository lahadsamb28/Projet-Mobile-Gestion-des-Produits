package sn.lahadproject.gestiondeproduits

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblVoiture = """
            CREATE TABLE $TBL_Voiture(
            $ID INTEGER PRIMARY KEY,
             $NAME TEXT,
             $DESCRIPTION TEXT, 
             $IMAGE BLOB
            )
            """.trimIndent()
        db?.execSQL(createTblVoiture)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TBL_Voiture")
        onCreate(db)
    }
//    fun addVoiture(voiture: Voiture): Boolean{
//        val db = writableDatabase
//
//        val values = ContentValues();
//        values.put(NAME, voiture.name)
//        values.put(DESCRIPTION, voiture.description)
//        values.put(IMAGE, voiture.image)
//
//        val insertResult = db.insert(TBL_Voiture, null, values).toInt()
//        db.close()
//
//        return insertResult != -1
//    }



    companion object{
        private val DB_VERSION = 1
        private val DB_NAME = "CollectionVoiture"
        private val TBL_Voiture = "ModelVoiture"
        private val ID = "id"
        private val NAME = "name"
        private val DESCRIPTION = "description"
        private val IMAGE= "image"
    }
}