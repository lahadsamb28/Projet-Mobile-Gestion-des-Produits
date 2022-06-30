package sn.lahadproject.gestiondeproduits.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sn.lahadproject.gestiondeproduits.MainActivity
import sn.lahadproject.gestiondeproduits.ModelData.ModelVoiture
import sn.lahadproject.gestiondeproduits.R
import sn.lahadproject.gestiondeproduits.db.CollectionVoitureDB
import sn.lahadproject.gestiondeproduits.voiturePopup

class ImageAdapter(val context: MainActivity, private val ListItem: ArrayList<ModelVoiture>, private val layoutId: Int) : RecyclerView.Adapter<ImageAdapter.ViewHolder>(){
    //controller images libraries
    val db = CollectionVoitureDB(context)
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
       val bitmap = getBitmap(currentVoiture.Image)
        holder.voitureImage.setImageBitmap(bitmap)

        //mettre jour les infos de la voiture
        holder.nameVoiture?.text= currentVoiture.name

        holder.descVoiture?.text= currentVoiture.description

        //liked or not
        if (currentVoiture.likes == 1){
            holder.star_icon.setImageResource(R.drawable.ic_unstar)
        }else{
            holder.star_icon.setImageResource(R.drawable.ic_star)
        }

        holder.star_icon.setOnClickListener{

            currentVoiture.likes != currentVoiture.likes
            db.incrementLikes(currentVoiture)
        }

        //Control popup onclick

        holder.itemView.setOnClickListener{
            voiturePopup(this, currentVoiture, ListItem, position).show()
        }

    }


    override fun getItemCount(): Int = ListItem.size
}
fun getBitmap(byteArray: ByteArray): Bitmap{
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    return bitmap
}