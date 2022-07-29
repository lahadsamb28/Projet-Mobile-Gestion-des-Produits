package sn.lahadproject.gestiondeproduits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import sn.lahadproject.gestiondeproduits.fragments.HomeFragment
import sn.lahadproject.gestiondeproduits.fragments.UploadVoiture_fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        val navbar = findViewById<BottomNavigationView>(R.id.navigation_view)
        navbar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_page -> {
                    loadFragment(UploadVoiture_fragment(this), R.string.add_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    }

        private fun loadFragment(fragment: Fragment, string: Int)
        {
            //title setting
            findViewById<TextView>(R.id.home_Title).text = resources.getString(string)

            //injecter les fragments dans le home page.
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmenent_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
}