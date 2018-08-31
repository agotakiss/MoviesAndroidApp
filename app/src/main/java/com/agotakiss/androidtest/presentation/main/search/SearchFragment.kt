package com.agotakiss.androidtest.presentation.main.search


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.base.MovieApplication
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.detail.DetailsActivity
import com.agotakiss.androidtest.presentation.main.MovieAdapter
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.MOVIE
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import javax.inject.Inject


class SearchFragment : Fragment(), SearchView {

    val movieApplication: MovieApplication get() = MovieApplication.get()
    val applicationComponent by lazy { movieApplication.applicationComponent.plus(SearchModule(this)) }

    @Inject
    lateinit var presenter: SearchPresenter

    internal var searchResultList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: MovieAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        applicationComponent.inject(this)
        presenter.onViewReady(this)
        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onSearchQueryChanged(s.toString())
            }
        })
    }

    override fun showSearchResults(newSearchResults: List<Movie>) {
        val layoutManager = LinearLayoutManager(context)
        searchResultsRecyclerView.layoutManager = layoutManager
        adapter = MovieAdapter(newSearchResults, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onScrollEndReached()
            }
        }, { movie, view ->
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(MOVIE, movie)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity::class, view,
//                POSTER_TRANSITION_NAME)
//            startActivity(intent, options.toBundle())
            startActivity(intent)
        }, presenter::onFavoriteButtonClicked)

        searchResultsRecyclerView.adapter = adapter
    }

    override fun showNextPage(newSearchResults: List<Movie>) {
        searchResultList.addAll(newSearchResults)
        adapter.notifyItemRangeInserted(searchResultList.size - newSearchResults.size, newSearchResults.size)
    }

    override fun showNoResult() {
        Toast.makeText(activity, "Sorry, we couldn't find anything :(", Toast.LENGTH_LONG).show()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(activity, "An error occured while searching. Try again!", Toast.LENGTH_LONG).show()
    }

    override fun updateListItem(position: Int) {
        adapter.notifyItemChanged(position)
    }
}
