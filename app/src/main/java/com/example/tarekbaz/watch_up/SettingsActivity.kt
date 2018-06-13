package com.example.tarekbaz.watch_up

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import com.example.tarekbaz.watch_up.Models.Genre
import com.example.tarekbaz.watch_up.Models.Store
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar_settings)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }


        val checkBoxList = ArrayList<CheckBox>()

        for (i in 0 until Genre.movieGenres.size) {
            val cb = CheckBox(this)
            cb.setTextColor(Color.WHITE)
            cb.text = Genre.movieGenres[i].name
            cb.id = Genre.movieGenres[i].id
            if (Store.preferedGenres.get(cb.id) != null) cb.isChecked = true
            cb.setOnCheckedChangeListener { _cb, b ->
                if (b) {
                    Store.preferedGenres.put(_cb.id, Genre(_cb.id, _cb.text.toString()))
                } else {
                    Store.preferedGenres.remove(_cb.id)
                }
            }
            checkBoxList.add(cb)

            if (i > (Genre.movieGenres.size / 2))
                checkBoxContainer2.addView(cb)
            else
                checkBoxContainer1.addView(cb)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
