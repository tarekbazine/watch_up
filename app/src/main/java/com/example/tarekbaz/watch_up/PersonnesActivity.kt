package com.example.tarekbaz.watch_up

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tarekbaz.watch_up.API.Responses.ListPaginatedResponse
import com.example.tarekbaz.watch_up.API.Service
import com.example.tarekbaz.watch_up.Adapters.PersonneRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.Person
import com.example.tarekbaz.watch_up.Models.Store
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_personnes.*
import kotlinx.android.synthetic.main.drawer_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PersonnesActivity : BaseActivity() {

    var tabActeur: ActorsFragment? = null
    var tabRealisateur: ProducersFragment? = null

    var dialog : AlertDialog? = null

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

        dialog = showDialog()

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
                    tabRealisateur?.adapter_producers?.filter("")
                    tabActeur?.adapter_person?.filter("")
                } else {
                    tabRealisateur?.adapter_producers?.filter(newText)
                    tabActeur?.adapter_person?.filter(newText)
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

//        val directors = Mocker.directorList//todo
        val directors = Store.acteurs.shuffled().take(Store.acteurs.size)

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_listing_cards, container, false)

            val layoutManager = LinearLayoutManager(context)
            val personneRecycler = rootView.findViewById<RecyclerView>(R.id.recyclerView)
            personneRecycler.setLayoutManager(layoutManager)
            adapter_producers = PersonneRecyclerViewAdapter(context!!, directors, isActor = false)
            personneRecycler.setAdapter(adapter_producers)

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

        var actors:List<Person> = ArrayList<Person>()

        var rootView: View? = null

        var alreadyRequestedPages = 1

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            rootView = inflater.inflate(R.layout.fragment_listing_cards, container, false)

            if(Store.acteurs.size == 0){
                getActorsAPI()
            }else{
                actors = Store.acteurs
                setUpLayout()
            }
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

        fun getActorsAPI() {
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            val service = retrofit.create<Service>(Service::class.java!!)
            service.getPersons(1).enqueue(object: Callback<ListPaginatedResponse<Person>> {

                override fun onResponse(call: Call<ListPaginatedResponse<Person>>, response: retrofit2.Response<ListPaginatedResponse<Person>>?) {
                    if ((response != null) && (response.code() == 200)) {

                        // init actors
                        actors = response.body()!!.results
                        // Save actors
                        Store.acteurs = ArrayList(actors)
                        setUpLayout()
                    }
                }

                override fun onFailure(call: Call<ListPaginatedResponse<Person>>?, t: Throwable?){
                    Toast.makeText(activity, "Erreur de connexion", Toast.LENGTH_LONG).show()
                }
            })
        }

        fun getActors(page : Int){
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            val service = retrofit.create<Service>(Service::class.java!!)
            service.getPersons(page).enqueue(object: Callback<ListPaginatedResponse<Person>> {

                override fun onResponse(call: Call<ListPaginatedResponse<Person>>, response: retrofit2.Response<ListPaginatedResponse<Person>>?) {
                    if ((response != null) && (response.code() == 200)) {
                        val _actors = response.body()!!.results
                        // Save actors
                        Store.acteurs.addAll(_actors)
                        actors = Store.acteurs

                        Log.i("actorlog",""+page)
                        adapter_person!!.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ListPaginatedResponse<Person>>?, t: Throwable?){
                    Toast.makeText(activity, "Erreur de pag "+ page, Toast.LENGTH_LONG).show()
                }
            })
        }

        fun setUpLayout(){
            val layoutManager = LinearLayoutManager(activity)
            val personneRecycler = rootView?.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
            personneRecycler.setLayoutManager(layoutManager)
            adapter_person = PersonneRecyclerViewAdapter(context, actors, isActor = true)
            personneRecycler.setAdapter(adapter_person)
            (activity as PersonnesActivity).hideDialog( (activity as PersonnesActivity).dialog)

            personneRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                var pastVisiblesItems: Int = 0
                var visibleItemCount: Int = 0
                var totalItemCount: Int = 0

                override fun onScrolled(recyclerView: RecyclerView?,
                                        dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                    val availablePages = (totalItemCount/Config.ITEMS_PER_PAGE)

                    if (visibleItemCount + pastVisiblesItems >= (totalItemCount - Config.NEXT_PAGE_LIMIT)
                            && alreadyRequestedPages <= availablePages){
                        alreadyRequestedPages = availablePages + 1
                    Log.i("ActorLog","next p "+availablePages+" "+visibleItemCount +" " +pastVisiblesItems+" "+totalItemCount)
                        getActors(availablePages + 1)
                    }
                }
            })

        }

    }

    // this function shows a dialog_progress dialogue
    fun showDialog(): AlertDialog {
        //Loading spinner
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE  ) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_progress,null)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        return dialog
    }

    fun hideDialog(dialog: AlertDialog?){
        //Loading spinner
        dialog!!.dismiss()
    }


}
