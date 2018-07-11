package com.kishordahiwadkar.veronews

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    companion object {
        var apiKey: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        val mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.apiKey.observe(this, Observer { _ ->
            showNews(mainActivityViewModel.apiKey.value)
        })
        mainActivityViewModel.errorMessage.observe(this, Observer {

        })
    }

    private fun showNews(value: String?) {
        apiKey = value
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, getNewsCategories(), getCategoryTitles())

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        tabs.setupWithViewPager(container)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    private fun getNewsCategories(): ArrayList<ArticlesFragment> {
        var newsCategories = ArrayList<ArticlesFragment>()
        for (category in ArticlesFragment.INDIA_CATEGORIES) {
            val articlesFragment = ArticlesFragment()
            val bundle = Bundle()
            bundle.putString(ArticlesFragment.KEY_CATEGORY, category)
            articlesFragment.arguments = bundle
            newsCategories.add(articlesFragment)
        }
        return newsCategories
    }

    private fun getCategoryTitles(): ArrayList<String> {
        var categoryTitles = ArrayList<String>()
        for (category in ArticlesFragment.INDIA_CATEGORIES) {
            categoryTitles.add(category)
        }
        return categoryTitles
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
