package sn.lahadproject.gestiondeproduits.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sn.lahadproject.gestiondeproduits.MainActivity
import sn.lahadproject.gestiondeproduits.ModelData.ModelVoiture
import sn.lahadproject.gestiondeproduits.R
import sn.lahadproject.gestiondeproduits.adapter.ImageAdapter
import sn.lahadproject.gestiondeproduits.adapter.itemDecoration
import sn.lahadproject.gestiondeproduits.db.CollectionVoitureDB

class HomeFragment(private val context: MainActivity): Fragment() {
    lateinit var db: CollectionVoitureDB
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        db = CollectionVoitureDB(context)

        val voitureList = db.findVoiture()
        val AllVoiture = db.findVoiture()



        //recuperer voiture recycler view
        val horizontalViewRecycler= view?.findViewById<RecyclerView>(R.id.horizontal_recycleView)
        horizontalViewRecycler?.adapter= ImageAdapter(context, voitureList, R.layout.item_voiture)



        //recuperer model recycler view
        val verticalViewRecycler= view?.findViewById<RecyclerView>(R.id.vertical_recycleView)
        verticalViewRecycler?.adapter= ImageAdapter(context, voitureList, R.layout.item_modelvoiture)
        verticalViewRecycler?.addItemDecoration(itemDecoration())


        return view
    }
}