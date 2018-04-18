package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast

class CommentByFilmFragment : Fragment() {

    var filmAdapter : CommentByFilmFilterRecyclerViewAdapter? = null

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

    //todo Models
    val userNames: List<String> = mutableListOf(
            "Cinema Paris Salle 0012", "Larousse Cinema", "Cinema des Rois -Marseille-",
            "Cinema Paris Salle 0012", "Larousse Cinema", "Cinema des Rois -Marseille-"
    )
    val userImages: List<Int> = mutableListOf(
            R.drawable.person1, R.drawable.person2, R.drawable.person1, R.drawable.film5,
            R.drawable.film4, R.drawable.film5, R.drawable.serie1, R.drawable.film5
    )

    val commentFor: List<String> = mutableListOf(
            "Le throne de Fer", "LaCasa de Papel", "Breaking Bad", "LaCasa de Papel",
            "Le throne de Fer", "LaCasa de Papel", "Breaking Bad", "LaCasa de Papel"
    )

    val comments: List<String> = mutableListOf(
            "Space excelsive", "From mars", "Live from the sea", "Directly from desert",
            "Space excelsive", "From mars", "Live from the sea", "Directly from desert"
    )

    val commentDates: List<String> = mutableListOf(
            "7/7 de 8:00 à 23:00", "24/24 sauf samedi de 8:00 à 23:00", "Toujours 10:00 à 23:00", "de 8:00 à 20:00 sauf lundi",
            "7/7 de 8:00 à 23:00", "24/24 sauf samedi de 8:00 à 23:00", "Toujours 10:00 à 23:00", "de 8:00 à 20:00 sauf lundi"
    )


    fun switchToCommentView(){
        this.view!!.findViewById<View>(R.id.search_bar).visibility = View.GONE
        this.view!!.findViewById<View>(R.id.evaluation).visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(context)
        val commentRecycler = this.view!!.findViewById<RecyclerView>(R.id.recycler)
        commentRecycler.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(context, userNames, userImages, comments, commentFor, commentDates)
        commentRecycler.setAdapter(adapter_comments)
    }

    fun switchToFilterView(){
        this.view!!.findViewById<View>(R.id.search_bar).visibility = View.VISIBLE
        this.view!!.findViewById<View>(R.id.evaluation).visibility = View.GONE

        val layoutManager = LinearLayoutManager(context)
        val filterRecycler = this.view!!.findViewById<RecyclerView>(R.id.recycler)
        filterRecycler.setLayoutManager(layoutManager)
        val adapter_filter_films = CommentByFilmFilterRecyclerViewAdapter(context, filmNames, imageFilmsUrls, filmDirectors, filmCinema)
        filterRecycler.setAdapter(adapter_filter_films)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewSearch = inflater.inflate(R.layout.fragment_comment_evaluation, container, false)

        val layoutManager = LinearLayoutManager(context)
        val filterRecycler = viewSearch.findViewById<RecyclerView>(R.id.recycler)
        filterRecycler.setLayoutManager(layoutManager)
        val adapter_filter_films = CommentByFilmFilterRecyclerViewAdapter(context, filmNames, imageFilmsUrls, filmDirectors, filmCinema)
        filterRecycler.setAdapter(adapter_filter_films)

        this.filmAdapter = adapter_filter_films
        viewSearch.findViewById<SearchView>(R.id.search_bar).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    filmAdapter!!.filter("")
                } else {
                    filmAdapter!!.filter(newText)
                }

                return true
            }
        })

        return viewSearch
    }


    companion object {

        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): CommentByFilmFragment {
            val fragment = CommentByFilmFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}
