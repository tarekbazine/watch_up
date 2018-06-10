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
import com.example.tarekbaz.watch_up.Adapters.CommentByFilmFilterRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.CommentRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.Mocker

class CommentByFilmFragment : Fragment() {

    var filmAdapter : CommentByFilmFilterRecyclerViewAdapter? = null

    fun switchToCommentView(){
        this.view!!.findViewById<View>(R.id.search_bar).visibility = View.GONE
        this.view!!.findViewById<View>(R.id.evaluation).visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(context)
        val commentRecycler = this.view!!.findViewById<RecyclerView>(R.id.recycler)
        commentRecycler.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(context!!, Mocker.commentList)
        commentRecycler.setAdapter(adapter_comments)
    }

    fun switchToFilterView(){
        this.view!!.findViewById<View>(R.id.search_bar).visibility = View.VISIBLE
        this.view!!.findViewById<View>(R.id.evaluation).visibility = View.GONE

        val layoutManager = LinearLayoutManager(context)
        val filterRecycler = this.view!!.findViewById<RecyclerView>(R.id.recycler)
        filterRecycler.setLayoutManager(layoutManager)
        val adapter_filter_films = CommentByFilmFilterRecyclerViewAdapter(context!!, Mocker.movieList)
        filterRecycler.setAdapter(adapter_filter_films)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewSearch = inflater.inflate(R.layout.fragment_comment_evaluation, container, false)

        val layoutManager = LinearLayoutManager(context)
        val filterRecycler = viewSearch.findViewById<RecyclerView>(R.id.recycler)
        filterRecycler.setLayoutManager(layoutManager)
        val adapter_filter_films = CommentByFilmFilterRecyclerViewAdapter(context!!, Mocker.movieList)
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
