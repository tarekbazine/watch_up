package com.example.tarekbaz.watch_up

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_comment_evaluation.*
import kotlinx.android.synthetic.main.popup_add_comment.*


class CommentEvaluationActivity : AppCompatActivity() {

    private var popupAddComment: Dialog? = null

    var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_evaluation)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        popupAddComment = Dialog(this)

        fab.setOnClickListener {
            showCommentPopUp()
        }

    }

    private fun showCommentPopUp() {
        popupAddComment!!.setContentView(R.layout.popup_add_comment)
        popupAddComment!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupAddComment!!.show()
        popupAddComment!!.btn_comment.setOnClickListener { view ->
            Snackbar.make(view, "comment", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }



    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {

            if (position == 0)
                return LastFragment.newInstance(position + 1)

            if (position == 1)
                return CommentByFilmFragment.newInstance(position + 1)

            return CommentBySerieFragment.newInstance(position + 1)

        }

        override fun getCount(): Int {
            return 3
        }

        fun switchFilmFragmentToCommentView(){
            val page = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.container + ":1")
            (page as CommentByFilmFragment).switchToCommentView()
        }
    }


    class LastFragment : Fragment() {

        //todo Models
        val userNames: List<String> = mutableListOf(
                "Cinema Paris Salle 0012", "Larousse Cinema", "Cinema des Rois -Marseille-"
        )
        val userImages: List<Int> = mutableListOf(
                R.drawable.film4, R.drawable.film5, R.drawable.serie1, R.drawable.film5
        )

        val commentFor: List<String> = mutableListOf(
                "Le throne de Fer", "LaCasa de Papel", "Breaking Bad", "LaCasa de Papel"
        )

        val comments: List<String> = mutableListOf(
                "Space excelsive", "From mars", "Live from the sea", "Directly from desert"
        )

        val commentDates: List<String> = mutableListOf(
                "7/7 de 8:00 à 23:00", "24/24 sauf samedi de 8:00 à 23:00", "Toujours 10:00 à 23:00", "de 8:00 à 20:00 sauf lundi"
        )

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_listing_cards, container, false)

            val layoutManager = LinearLayoutManager(context)
            val lastRecycler = rootView.findViewById<RecyclerView>(R.id.recyclerView)
            lastRecycler.setLayoutManager(layoutManager)
            val adapter_films = CommentRecyclerViewAdapter(context, userNames, userImages, comments, commentFor, commentDates)
            lastRecycler.setAdapter(adapter_films)

            return rootView
        }

        companion object {

            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): LastFragment {
                val fragment = LastFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

}
