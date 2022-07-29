package sn.lahadproject.gestiondeproduits

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
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

        setupDeletedButton()
        setupEditButton()
    }

    private fun setupEditButton(){
        findViewById<ImageView>(R.id.edit_btn).setOnClickListener {
            val intentUpdateVoiture= Intent(context, updateVoiture::class.java)
            intentUpdateVoiture.putExtra("id", currentVoiture.id)
            intentUpdateVoiture.putExtra("marque", currentVoiture.name)
            intentUpdateVoiture.putExtra("model", currentVoiture.description)
            intentUpdateVoiture.putExtra("energie", currentVoiture.energie)
            intentUpdateVoiture.putExtra("transmission", currentVoiture.transmission)
            intentUpdateVoiture.putExtra("image", currentVoiture.Image)
            context.startActivity(intentUpdateVoiture)

        }
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