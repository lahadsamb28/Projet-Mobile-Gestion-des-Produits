package sn.lahadproject.gestiondeproduits

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import sn.lahadproject.gestiondeproduits.ModelData.ModelVoiture
import sn.lahadproject.gestiondeproduits.adapter.ImageAdapter
import sn.lahadproject.gestiondeproduits.adapter.getBitmap
import sn.lahadproject.gestiondeproduits.db.CollectionVoitureDB


class voiturePopup(
    private val adapter: ImageAdapter,
    private val currentVoiture: ModelVoiture,
    private val values: ArrayList<ModelVoiture>,
    val position: Int
): Dialog(adapter.context) {
    val db = CollectionVoitureDB(context)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_voiture_details)
        setupCompenents()
        setupcloseButton()
        setupstarButton()
        setupDeletedButton()
    }

    private fun setupstarButton() {
            db.incrementLikes(currentVoiture)
    }

    private fun setupcloseButton() {
        findViewById<ImageView>(R.id.close_btn).setOnClickListener {
            //fermer la fenetre popup
            dismiss()
        }
    }

    private fun setupDeletedButton() {
        findViewById<ImageView>(R.id.delete_btn).setOnClickListener {
            val resultDeleted = db.deletedVoiture(currentVoiture.id)
            if (resultDeleted){
                values.removeAt(position)
                adapter.notifyDataSetChanged()
                dismiss()
            }else{
                Toast.makeText(context, "erreur de suppression", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCompenents() {
        val voitureImage = findViewById<ImageView>(R.id.voiture_item)
        //actualiser image
        val bitmap = getBitmap(currentVoiture.Image)
        voitureImage.setImageBitmap(bitmap)
        //actualiser nom
        findViewById<TextView>(R.id.popup_name_voiture).text = currentVoiture.name
        //actualiser description
        findViewById<TextView>(R.id.desc_sub).text = currentVoiture.description
        //actualiser energie et transmission
        findViewById<TextView>(R.id.sub_energiedesc).text = currentVoiture.energie
        findViewById<TextView>(R.id.sub_transmissiondesc).text = currentVoiture.transmission
    }
}