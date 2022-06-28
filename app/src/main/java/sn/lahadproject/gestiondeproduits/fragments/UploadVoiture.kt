package sn.lahadproject.gestiondeproduits

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import sn.lahadproject.gestiondeproduits.ModelData.ModelVoiture
import sn.lahadproject.gestiondeproduits.db.CollectionVoitureDB
import java.io.ByteArrayOutputStream

var bitmap: Bitmap? = null
class UploadVoiture : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_voiture)








        btnSave.setOnClickListener {
            //recuperer les données
            val marque = nom.text.toString()
            val model = description.text.toString()
            val carburant = energie.text.toString()
            val modeTrans = transmission.text.toString()
            val imagesblob : ByteArray= getBytes(bitmap)

            val modelVoiture = ModelVoiture(marque, model, carburant, modeTrans, imagesblob)

            db.addVoiture(modelVoiture)
        }
    }

    private fun getBytes(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}

class UploadVoiture_fragment(private val context: MainActivity): Fragment(){
    lateinit var btnSave: Button
    lateinit var pickupImg: Button
    lateinit var nom: EditText
    lateinit var description: EditText
    lateinit var energie: EditText
    lateinit var transmission: EditText
    lateinit var image: ImageView
    lateinit var db: CollectionVoitureDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.activity_upload_voiture, container, false)
        db = CollectionVoitureDB(this)
        btnSave = view!!.findViewById(R.id.conf_btn)
        nom = view.findViewById(R.id.name_input)
        description = view.findViewById(R.id.desc_input)
        energie = view.findViewById(R.id.energie_input)
        transmission= view.findViewById(R.id.trans_input)
        image = view.findViewById(R.id.preview_Img)
        pickupImg = view.findViewById(R.id.upload_btn)

        val gallerylauncher = registerForActivityResult(ActivityResultContracts.GetContent()){ data ->
            val inputStream = context.contentResolver.openInputStream(data)
            bitmap = BitmapFactory.decodeStream(inputStream)
            image?.setImageBitmap(bitmap)
        }
        pickupImg.setOnClickListener { gallerylauncher.launch("image/*") }

        return view
    }
}