package com.kishordahiwadkar.veronews

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        var api_key : String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            readApiKey()
        } else {
            signInAnonymously()
        }
    }

    private fun readApiKey() {
        val database = FirebaseDatabase.getInstance()
        val apiKeyRef = database.getReference("api_key")

        apiKeyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("MainActivity", "Value is: " + value!!)
                api_key = value
                showNews()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException())
                Snackbar.make(main_content, "Error : " + error.message, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun showNews() {
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, getNewsCategories(), getCategoryTitles())

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        tabs.setupWithViewPager(container)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    private fun signInAnonymously() {
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = firebaseAuth.currentUser
                        readApiKey()
                    } else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(main_content, "Authentication failed", Snackbar.LENGTH_LONG).show()
                    }
                })
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
