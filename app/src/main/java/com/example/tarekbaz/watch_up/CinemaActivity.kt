package com.example.tarekbaz.watch_up

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_cinema.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_activity.*

class CinemaActivity : BaseActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    var tabFilm : FilmFragment? = null
    var tabSalle : SalleFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinema)
        setSupportActionBar(toolbar_activity)

        //Add drawer button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar_activity, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (TextUtils.isEmpty(newText)) {
                    tabSalle!!.adapter_salle!!.filter("")
                    tabFilm!!.adapter_films!!.filter("")
                } else {
                    tabSalle!!.adapter_salle!!.filter(newText)
                    tabFilm!!.adapter_films!!.filter(newText)
                }

                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if (position == 0){
                tabFilm = FilmFragment.newInstance(position + 1)
                return tabFilm as FilmFragment
            }

            tabSalle = SalleFragment.newInstance(position + 1)
            return tabSalle as SalleFragment

        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class FilmFragment : Fragment() {

        var adapter_films : FilmCinemaRecyclerViewAdapter? = null

        //TODO USE Models
        val filmNames: List<String> = mutableListOf(
                "La Belle et La Bète", "Hunger Game", "Drone", "Hunger Game 2"
        )

        val imageFilmsUrls: List<Int> = mutableListOf(
                R.drawable.film4, R.drawable.film5, R.drawable.serie1, R.drawable.film5
        )

        val filmDirectors: List<String> = mutableListOf(
                "Le throne de Fer", "LaCasa de Papel", "Breaking Bad", "LaCasa de Papel"
        )

        val filmCinema: List<String> = mutableListOf(
                "Space excelsive", "From mars", "Live from the sea", "Directly from desert"
        )

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_listing_cards, container, false)

            val layoutManager = LinearLayoutManager(context)
            val filmRecycler = rootView.findViewById<RecyclerView>(R.id.recyclerView)
            filmRecycler.setLayoutManager(layoutManager)
            val adapter_films = FilmCinemaRecyclerViewAdapter(context, filmNames, imageFilmsUrls, filmDirectors, filmCinema)
            filmRecycler.setAdapter(adapter_films)

            this.adapter_films = adapter_films

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): FilmFragment {
                val fragment = FilmFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    class SalleFragment : Fragment() {

        var adapter_salle : SalleCinemaRecyclerViewAdapter? = null

        //TODO USE Models
        val salleNames: List<String> = mutableListOf(
                "Vandome","salle 2","salle 3","salle 4"
        )

        val salleImages: List<Int> = mutableListOf(
                R.drawable.cinema1, R.drawable.cinema2, R.drawable.cinema3, R.drawable.cinema1
        )

        val salleAddress: List<String> = mutableListOf(
                "Paris, oued elsemar !", "Alger !LaCasa de Papel", "Oran !Breaking Bad", "Ghardaia !LaCasa de Papel"
        )

        val salleOpennings: List<String> = mutableListOf(
                "7/7 de 8:00 à 23:00", "24/24 sauf samedi de 8:00 à 23:00","Toujours 10:00 à 23:00","de 8:00 à 20:00 sauf lundi"
        )

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_listing_cards, container, false)

            val layoutManager = LinearLayoutManager(context)
            val salleRecycler = rootView.findViewById<RecyclerView>(R.id.recyclerView)
            salleRecycler.setLayoutManager(layoutManager)
            val adapter_salle = SalleCinemaRecyclerViewAdapter(context, salleNames, salleImages, salleAddress, salleOpennings)
            salleRecycler.setAdapter(adapter_salle)

            this.adapter_salle = adapter_salle

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): SalleFragment {
                val fragment = SalleFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
