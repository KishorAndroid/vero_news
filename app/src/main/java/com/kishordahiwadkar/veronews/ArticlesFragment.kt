package com.kishordahiwadkar.veronews

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A artcles fragment containing a list of news articles.
 */
class ArticlesFragment : Fragment() {

    private var category : String? = null

    companion object {
        const val KEY_CATEGORY = "category"
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
    }
}