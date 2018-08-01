package com.kishordahiwadkar.veronews

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A artcles fragment containing a list of news articles.
 */
class ArticlesFragment : Fragment() {

    private var category : String? = null
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val KEY_CATEGORY = "category"
        const val TOP_HEADLINES = "Headlines"
        val INDIA_CATEGORIES = mutableListOf("Business", "Entertainment", "Health", "Science", "Sports", "Technology")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(KEY_CATEGORY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(activity)
        val articlesFragmentViewModel = ViewModelProviders.of(this).get(ArticlesFragmentViewModel::class.java)
        contentLoading.show()
        articlesFragmentViewModel.result.observe(this, Observer {
            viewAdapter = MyAdapter(it?.articles)
            rvArticles.apply {
                layoutManager = viewManager
                adapter = viewAdapter
                contentLoading.hide()
            }
        })
        articlesFragmentViewModel.getNewsArticles(category)
    }
}