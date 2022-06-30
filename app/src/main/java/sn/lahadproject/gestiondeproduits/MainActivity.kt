package sn.lahadproject.gestiondeproduits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sn.lahadproject.gestiondeproduits.fragments.HomeFragment
import sn.lahadproject.gestiondeproduits.fragments.UploadVoiture_fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //injecter les fragments dans le home page.
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmenent_container, HomeFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()

        //base de données


    }
}