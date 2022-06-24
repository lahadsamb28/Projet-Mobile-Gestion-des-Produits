package sn.lahadproject.gestiondeproduits.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sn.lahadproject.gestiondeproduits.MainActivity
import sn.lahadproject.gestiondeproduits.R
import sn.lahadproject.gestiondeproduits.VoitureModel
import sn.lahadproject.gestiondeproduits.voiturePopup

class ImageAdapter(val context: MainActivity, private val ListItem: List<VoitureModel>, private val layoutId: Int) : RecyclerView.Adapter<ImageAdapter.ViewHolder>(){
    //controller images libraries
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val voitureImage = view.findViewById<ImageView>(R.id.image_item)
        //recuperation des infos
        val nameVoiture: TextView? = view.findViewById(R.id.name_voiture)
        val descVoiture: TextView? = view.findViewById(R.id.desc_voiture)
        val star_icon = view.findViewById<ImageView>(R.id.fav_voiture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentVoiture = ListItem[position]
        Glide.with(context).load(Uri.parse(currentVoiture.ImageUrl)).into(holder.voitureImage)

        //mettre jour les infos de la voiture
        holder.nameVoiture?.text= currentVoiture.name

        holder.descVoiture?.text= currentVoiture.description

        //liked or not
        if (currentVoiture.favori){
            holder.star_icon.setImageResource(R.drawable.ic_unstar)
        }else{
            holder.star_icon.setImageResource(R.drawable.ic_star)
        }

        //Control popup onclick

        holder.itemView.setOnClickListener{
            voiturePopup(this, currentVoiture).show()
        }

    }

    override fun getItemCount(): Int = ListItem.size
}