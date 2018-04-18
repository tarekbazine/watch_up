package com.example.tarekbaz.watch_up

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.drawer_activity.*
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View


open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.drawer_activity)
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_cinema -> {
                val intent = Intent(this, CinemaActivity::class.java)
                // To pass any data to next activity
//                intent.putExtra("keyIdentifier", value)
                // start your next activity
                startActivity(intent)
            }
            R.id.nav_series -> {
                //TODO change it
                val intent = Intent(this, SeriesActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_personnes -> {
                val intent = Intent(this, PersonnesActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_fan -> {
                val intent = Intent(this, FanActivity::class.java)
                startActivity(intent, null)
            }
            R.id.nav_evaluations -> {
                val intent = Intent(this, CommentEvaluationActivity::class.java)
                startActivity(intent)
            }
        }

        finish()

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    override fun setContentView(layoutResID: Int) {
        if (hosting_layout != null) {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val lp = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            val stubView = inflater.inflate(layoutResID, hosting_layout, false)
            hosting_layout.addView(stubView, lp)
        }
    }

    override fun setContentView(view: View) {
        if (hosting_layout != null) {
            val lp = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            hosting_layout.addView(view, lp)
        }
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        if (hosting_layout != null) {
            hosting_layout.addView(view, params)
        }
    }

}
