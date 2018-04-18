package com.example.tarekbaz.watch_up

import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_personnes.*
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_activity.*
import android.support.v7.widget.SearchView


class PersonnesActivity : BaseActivity() {

    var tabActeur: ActorsFragment? = null
    var tabRealisateur: ProducersFragment? = null

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnes)
        setSupportActionBar(toolbar_personnes)
        //Add drawer button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar_personnes, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
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
                    tabRealisateur!!.adapter_producers!!.filter("")
                    tabActeur!!.adapter_person!!.filter("")
                } else {
                    tabRealisateur!!.adapter_producers!!.filter(newText)
                    tabActeur!!.adapter_person!!.filter(newText)
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

            if (position == 0) {
                tabActeur = ActorsFragment.newInstance(position + 1)
                return tabActeur as ActorsFragment
            }

            tabRealisateur = ProducersFragment.newInstance(position + 1)
            return tabRealisateur as ProducersFragment
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }

    /**
     * Actors fragment containing a simple view.
     */
    class ProducersFragment : Fragment() {

        var adapter_producers: PersonneRecyclerViewAdapter? = null

        //TODO USE Models
        val personneNames: List<String> = mutableListOf(
                "Aissa Achor", "Ilyes BATATA", "Halima Kacemi", "Maradona2"
        )

        val imagePersonneUrls: List<Int> = mutableListOf(
                R.drawable.person1, R.drawable.person2, R.drawable.person1, R.drawable.person2
        )

        val personneIsIndicator: List<Boolean> = mutableListOf(
                true, false, false, true
        )


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_listing_cards, container, false)

            val layoutManager = LinearLayoutManager(context)
            val personneRecycler = rootView.findViewById<RecyclerView>(R.id.recyclerView)
            personneRecycler.setLayoutManager(layoutManager)
            val adapter_producers = PersonneRecyclerViewAdapter(context, personneNames, imagePersonneUrls, personneIsIndicator)
            personneRecycler.setAdapter(adapter_producers)

            this.adapter_producers = adapter_producers

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
            fun newInstance(sectionNumber: Int): ProducersFragment {
                val fragment = ProducersFragment()
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
    class ActorsFragment : Fragment() {

        var adapter_person: PersonneRecyclerViewAdapter? = null

        //TODO USE Models
        val personneNames: List<String> = mutableListOf(
                "Bakir Achor", "Ilyes Tebbakh", "Aissa Kacem", "Maradona"
        )

        val imagePersonneUrls: List<Int> = mutableListOf(
                R.drawable.person1, R.drawable.person2, R.drawable.person1, R.drawable.person2
        )

        val personneIsIndicator: List<Boolean> = mutableListOf(
                false, true, true, false
        )


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_listing_cards, container, false)

            val layoutManager = LinearLayoutManager(context)
            val personneRecycler = rootView.findViewById<RecyclerView>(R.id.recyclerView)
            personneRecycler.setLayoutManager(layoutManager)
            val adapter_person = PersonneRecyclerViewAdapter(context, personneNames, imagePersonneUrls, personneIsIndicator)
            personneRecycler.setAdapter(adapter_person)

            this.adapter_person = adapter_person

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
            fun newInstance(sectionNumber: Int): ActorsFragment {
                val fragment = ActorsFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
