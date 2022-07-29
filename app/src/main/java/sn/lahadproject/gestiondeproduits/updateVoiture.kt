package sn.lahadproject.gestiondeproduits

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import sn.lahadproject.gestiondeproduits.ModelData.ModelVoiture
import sn.lahadproject.gestiondeproduits.db.CollectionVoitureDB
import sn.lahadproject.gestiondeproduits.fragments.bitmap
import java.io.ByteArrayOutputStream
import java.sql.Blob

var bmp: Bitmap? = null
class updateVoiture : AppCompatActivity() {
    lateinit var btnSave: Button
    lateinit var pickupImg: Button
    lateinit var nom: EditText
    lateinit var description: EditText
    lateinit var energie: EditText
    lateinit var transmission: EditText
    lateinit var image: ImageView
    lateinit var db: CollectionVoitureDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
//        val id: Int = intent.getStringExtra("id")!!.toInt()
        val marque: String = intent.getStringExtra("marque").toString()
        val model: String = intent.getStringExtra("model").toString()
        val eng: String = intent.getStringExtra("energie").toString()
        val boite: String = intent.getStringExtra("transmission").toString()
        val img: ByteArray? = intent.getByteArrayExtra("image")


        btnSave = findViewById(R.id.conf_btn)
        nom = findViewById(R.id.name_input)
        description = findViewById(R.id.desc_input)
        energie = findViewById(R.id.energie_input)
        transmission= findViewById(R.id.trans_input)
        image = findViewById(R.id.preview_Img)
        pickupImg = findViewById(R.id.upload_btn)

        nom.setText(marque)
        description.setText(model)
        energie.setText(eng)
        transmission.setText(boite)
        bmp = BitmapFactory.decodeByteArray(img, 0, img!!.size);
        image.setImageBitmap(bmp);

        val gallerylauncher = registerForActivityResult(ActivityResultContracts.GetContent()){ data ->
            val inputStream = contentResolver.openInputStream(data)
            bmp = BitmapFactory.decodeStream(inputStream)
            image?.setImageBitmap(bmp)
        }
        pickupImg.setOnClickListener { gallerylauncher.launch("image/*") }

        btnSave.setOnClickListener {
            val inp1 = nom.toString()
            val inp2 = description.toString()
            val inp3 = energie.toString()
            val inp4 = transmission.toString()

            val imageblob : ByteArray= getBytes(bmp)
            val ExpleVoiture = ModelVoiture(inp1, inp2, inp3, inp4, imageblob)

            val status = db.editVoiture(ExpleVoiture)

            if (status > -1){
                nom.setText("")
                description.setText("")
                energie.setText("")
                transmission.setText("")
                bmp = null

                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this, "update failed", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun getBytes(bmp: Bitmap?): ByteArray {

        val stream = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()

    }
}