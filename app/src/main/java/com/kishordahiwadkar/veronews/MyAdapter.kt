package com.kishordahiwadkar.veronews

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.my_text_view.view.*

class MyAdapter(private val myDataset: List<Article?>?) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.my_text_view, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.imageNews.loadUrl(myDataset?.get(position)?.urlToImage)
        holder.view.textTitle.text = myDataset?.get(position)?.title
        holder.view.textDescription.text = myDataset?.get(position)?.description
        holder.view.textDateTime.text = myDataset?.get(position)?.publishedAt
        holder.view.textSourceName.text = myDataset?.get(position)?.source?.name
        holder.view.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(myDataset?.get(position)?.url))
            holder.view.context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset?.size ?: 0
}