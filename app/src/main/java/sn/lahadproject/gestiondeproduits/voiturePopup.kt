package sn.lahadproject.gestiondeproduits

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import sn.lahadproject.gestiondeproduits.adapter.ImageAdapter

class voiturePopup (private val adapter: ImageAdapter, private val currentVoiture: VoitureModel): Dialog(adapter.context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_voiture_details)
        setupCompenents()
        setupcloseButton()
        setupstarButton()
    }

    private fun setupstarButton() {
            val starBtn = findViewById<ImageView>(R.id.fav_btn)
        if (currentVoiture.favori){
            starBtn.setImageResource(R.drawable.ic_unstar)
        }else{
            starBtn.setImageResource(R.drawable.ic_star)
        }

        starBtn.setOnClickListener {
            currentVoiture.favori = !currentVoiture.favori
        }
    }

    private fun setupcloseButton() {
        findViewById<ImageView>(R.id.close_btn).setOnClickListener {
            //fermer la fenetre popup
            dismiss()
        }
    }

    private fun setupCompenents() {
        val voitureImage = findViewById<ImageView>(R.id.voiture_item)
        //actualiser image
        Glide.with(adapter.context).load(Uri.parse(currentVoiture.ImageUrl)).into(voitureImage)
        //actualiser nom
        findViewById<TextView>(R.id.popup_name_voiture).text = currentVoiture.name
        //actualiser description
        findViewById<TextView>(R.id.desc_sub).text = currentVoiture.description
        //actualiser categorie energie et transmission
        findViewById<TextView>(R.id.sub_categoriedesc).text = currentVoiture.categorie
        findViewById<TextView>(R.id.sub_energiedesc).text = currentVoiture.energie
        findViewById<TextView>(R.id.sub_transmissiondesc).text = currentVoiture.transmission
    }
}