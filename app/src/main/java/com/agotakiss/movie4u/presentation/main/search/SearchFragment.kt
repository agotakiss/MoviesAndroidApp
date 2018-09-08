package com.agotakiss.movie4u.presentation.main.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.base.MovieApplication
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.presentation.DETAILS_ACTIVITY_RESULT_CODE
import com.agotakiss.movie4u.presentation.MOVIE
import com.agotakiss.movie4u.presentation.POSTER_TRANSITION_NAME
import com.agotakiss.movie4u.presentation.SEARCH_FRAGMENT_REQUEST_CODE
import com.agotakiss.movie4u.presentation.detail.DetailsActivity
import com.agotakiss.movie4u.presentation.main.MainPageFragment
import com.agotakiss.movie4u.presentation.main.OnEndReachedListener
import com.agotakiss.movie4u.presentation.main.PopularMovieAdapter
import kotlinx.android.synthetic.main.fragment_search.camera_av
import kotlinx.android.synthetic.main.fragment_search.search_cancel_iv
import kotlinx.android.synthetic.main.fragment_search.search_et
import kotlinx.android.synthetic.main.fragment_search.search_results_rv
import java.util.ArrayList
import javax.inject.Inject

class SearchFragment : MainPageFragment(), SearchView {

    val movieApplication: MovieApplication get() = MovieApplication.get()
    val applicationComponent by lazy { movieApplication.applicationComponent.plus(SearchModule(this)) }

    @Inject
    lateinit var presenter: SearchPresenter

    internal var searchResultList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: PopularMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        applicationComponent.inject(this)
        presenter.onViewReady(this)

        search_cancel_iv.setOnClickListener { onCancelButtonClicked() }
        search_et.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
        search_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onSearchQueryChanged(s.toString())
            }
        })
    }

    override fun onPageShow() {
        super.onPageShow()
        if (searchResultList.isEmpty()) {
            search_et.requestFocus()
            showKeyboard()
        }
    }

    override fun onPageHide() {
        super.onPageHide()
        search_et.clearFocus()
        hideKeyboard()
    }

    override fun showSearchResults(newSearchResults: List<Movie>) {
        hideKeyboard()
        camera_av.visibility = View.GONE
        searchResultList.addAll(newSearchResults)
        val layoutManager = LinearLayoutManager(context)
        search_results_rv.layoutManager = layoutManager
        adapter = PopularMovieAdapter(newSearchResults, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onScrollEndReached()
            }
        }, { movie, view ->
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(MOVIE, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity, view,
                POSTER_TRANSITION_NAME
            )
            startActivityForResult(intent, SEARCH_FRAGMENT_REQUEST_CODE, options.toBundle())
        }, presenter::onFavoriteButtonClicked)

        search_results_rv.adapter = adapter
    }

    override fun showNextPage(newSearchResults: List<Movie>) {
        searchResultList.addAll(newSearchResults)
        adapter.notifyItemRangeInserted(searchResultList.size - newSearchResults.size, newSearchResults.size)
    }

    override fun showNoResult() {
        Toast.makeText(activity, "Sorry, we couldn't find anything :(", Toast.LENGTH_LONG).show()
        showKeyboard()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(activity, "An error occurred while searching. Try again!", Toast.LENGTH_LONG).show()
    }

    override fun updateListItem(position: Int) {
        adapter.notifyItemChanged(position)
    }

    private fun onCancelButtonClicked() {
        search_et.text.clear()
        showKeyboard()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SEARCH_FRAGMENT_REQUEST_CODE && resultCode == DETAILS_ACTIVITY_RESULT_CODE) {
            val chosenMovie = data!!.getSerializableExtra(MOVIE) as Movie
            val position = searchResultList.indexOfFirst { it.id == chosenMovie.id }
            searchResultList[position].isFavorite = chosenMovie.isFavorite
            updateListItem(position)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun showKeyboard() {
        val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(search_et, 0)
    }
}
