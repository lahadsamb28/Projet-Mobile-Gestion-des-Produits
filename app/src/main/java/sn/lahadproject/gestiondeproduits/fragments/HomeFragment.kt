package sn.lahadproject.gestiondeproduits.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import sn.lahadproject.gestiondeproduits.MainActivity
import sn.lahadproject.gestiondeproduits.R
import sn.lahadproject.gestiondeproduits.VoitureModel
import sn.lahadproject.gestiondeproduits.adapter.ImageAdapter
import sn.lahadproject.gestiondeproduits.adapter.itemDecoration

class HomeFragment(private val context: MainActivity): Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        //stockage liste de voiture
        val voitureList= arrayListOf<VoitureModel>()

        //enregistrer la premiere item de la liste
        voitureList.add(
            VoitureModel(
            "Dodge",
            "charge srt hellcat",
                "https://cdn.pixabay.com/photo/2012/11/02/13/02/car-63930_960_720.jpg",
                "muscle",
                "hybride",
                "manuel",
            false
        )
        )
        //enregistrer la deuxieme item de la liste
        voitureList.add(
            VoitureModel(
                "Lamborghini",
                "uracan evo",
                "https://cdn.pixabay.com/photo/2012/04/12/23/48/car-30990_960_720.png",
                "SUV",
                "electrique",
                "automatique",
                true
            )
        )

        //enregistrer la troisieme item de la liste
        voitureList.add(
            VoitureModel(
                "Merco",
                "a35 amg SUV",
                "https://www.mercedes-benz.com/content/dam/brandhub/mbsocialcar/mbsocialcar-mercedes-amg-s-63-4matic-coupe/01-Mercedes-AMG-S-63-4MATIC-Coupe.zip_extracted/01-Mercedes-AMG-S-63-4MATIC%2B-Coupe-4K.jpg",
                "sport",
                "essence",
                "automatique",
                false
            )
        )

        //enregistrer la qutatrieme item de la liste
        voitureList.add(
            VoitureModel(
                "mercedes",
                "e45 amg",
                "https://www.hdcarwallpapers.com/walls/mercedes_amg_sl_55_4matic__2022_4k-HD.jpg",
                "berline",
                "gasoil",
                "automatique",
                false
            )
        )

        //recuperer voiture recycler view
        val horizontalViewRecycler= view.findViewById<RecyclerView>(R.id.horizontal_recycleView)
        horizontalViewRecycler.adapter= ImageAdapter(context, voitureList, R.layout.item_voiture)

        //recuperer model recycler view
        val verticalViewRecycler= view.findViewById<RecyclerView>(R.id.vertical_recycleView)
        verticalViewRecycler.adapter= ImageAdapter(context, voitureList, R.layout.item_modelvoiture)
        verticalViewRecycler.addItemDecoration(itemDecoration())

        return view
    }
}