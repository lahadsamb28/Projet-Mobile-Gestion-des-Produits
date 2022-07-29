package sn.lahadproject.gestiondeproduits.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import sn.lahadproject.gestiondeproduits.MainActivity
import sn.lahadproject.gestiondeproduits.ModelData.ModelVoiture
import sn.lahadproject.gestiondeproduits.R
import sn.lahadproject.gestiondeproduits.db.CollectionVoitureDB
import java.io.ByteArrayOutputStream

var bitmap: Bitmap? = null
class UploadVoiture_fragment(private val context: MainActivity): Fragment(){
    lateinit var btnSave: Button
    lateinit var pickupImg: Button
    lateinit var nom: EditText
    lateinit var description: EditText
    lateinit var energie: EditText
    lateinit var transmission: EditText
//    lateinit var error_empty_fields: TextView
    lateinit var image: ImageView
    lateinit var db: CollectionVoitureDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.activity_upload_voiture, container, false)
        db = CollectionVoitureDB(view!!.context)
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

        btnSave.setOnClickListener {
//
            //recuperer les données
            val marque = nom.text.toString()
            val model = description.text.toString()
            val carburant = energie.text.toString()
            val modeTrans = transmission.text.toString()
            if (marque.isEmpty() || model.isEmpty() || carburant.isEmpty() || modeTrans.isEmpty() || bitmap == null){
                Toast.makeText(context, R.string.error_empty_fields, Toast.LENGTH_LONG).show()
            }else{
                val imagesblob : ByteArray= getBytes(bitmap)

                val modelVoiture = ModelVoiture(marque, model, carburant, modeTrans, imagesblob)
                val status = db.addVoiture(modelVoiture)
                if (status){
                    Toast.makeText(context, "Voiture ajoutée avec succes", Toast.LENGTH_LONG).show()
                    //clear formulaire
                    nom.setText("")
                    description.setText("")
                    energie.setText("")
                    transmission.setText("")
                    bitmap = null
                }else{
                    Toast.makeText(context, "une erreur est survenue", Toast.LENGTH_LONG).show()
                }




//
            }

        }

        return view
    }
    private fun getBytes(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}