package sn.lahadproject.gestiondeproduits.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import sn.lahadproject.gestiondeproduits.ModelData.ModelVoiture
import kotlin.math.E


class CollectionVoitureDB(
    context: Context
): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {



    override fun onCreate(db: SQLiteDatabase?) {
        val createTableColl = """
            CREATE TABLE $COLVOITURE_TABLE(
            $ID INTEGER PRIMARY KEY,
            $NAME VARCHAR(50),
            $DESCRIPTION VARCHAR(50),
            $ENERGIE VARCHAR(50),
            $TRANSMISSION VARCHAR(50),
            $IMAGE BLOB,
            $LIKES INTEGER
            )
            """.trimIndent()

        db?.execSQL(createTableColl)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $COLVOITURE_TABLE")
        onCreate(db)
    }

    fun addVoiture(modelVoiture: ModelVoiture): Boolean{
        val db = writableDatabase

        val values = ContentValues()
        values.put(NAME, modelVoiture.name)
        values.put(DESCRIPTION, modelVoiture.description)
        values.put(ENERGIE, modelVoiture.energie)
        values.put(TRANSMISSION, modelVoiture.transmission)
        values.put(IMAGE, modelVoiture.Image)
        values.put(LIKES, 0)

        val result = db.insert(COLVOITURE_TABLE, null, values).toInt()
        db.close()

        return result != -1
    }

    fun findVoiture(): ArrayList<ModelVoiture> {
        val posts = ArrayList<ModelVoiture>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $COLVOITURE_TABLE"

        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                    val marque = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                    val model = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))
                    val energie = cursor.getString(cursor.getColumnIndexOrThrow(ENERGIE))
                    val transmission = cursor.getString(cursor.getColumnIndexOrThrow(TRANSMISSION))
                    val img = cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGE))
                    val likes = cursor.getInt(cursor.getColumnIndexOrThrow(LIKES))
                    val post =ModelVoiture(id, marque, model, energie, transmission, img, likes)
                    posts.add(post)
                }while (cursor.moveToNext())
            }
        }

        db.close()
        return posts
    }

    fun deletedVoiture(id: Int): Boolean{
        val db = writableDatabase

        val rowDeleted = db.delete(COLVOITURE_TABLE, "id=?", arrayOf(id.toString()))
        return rowDeleted>0

    }

    fun incrementLikes(currentVoiture: ModelVoiture) {
        val db = this.writableDatabase

        val newLikesCount = currentVoiture.likes+1
        val values = ContentValues()
        values.put(ID, currentVoiture.id)
        values.put(LIKES, newLikesCount)

        db.update(COLVOITURE_TABLE, values, "id=?", arrayOf("${currentVoiture.id}"))
        db.close()
    }

    companion object{
        const val DB_VERSION = 2
        const val DB_NAME = "CollectionVoiture.db"
        const val COLVOITURE_TABLE = "VoitureModel"
        private val ID = "id"
        private val NAME = "name"
        private val DESCRIPTION = "description"
        private val ENERGIE = "energie"
        private val TRANSMISSION = "transmission"
        private val IMAGE = "image"
        private val LIKES = "likes"
    }
}
