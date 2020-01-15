package codes.terminaether.watchlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.terminaether.watchlist.R
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import coil.api.load

/**
 * ListAdapter responsible for Media items to be displayed in a RecyclerView.
 *
 * Created by terminaether on 2020-01-09.
 */
class MediaListAdapter(private val mediaSaveListener: MediaSaveListener) :
    ListAdapter<Media, MediaListAdapter.MediaViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediaViewHolder {
        return MediaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_media,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun swapData(data: List<Media>) {
        submitList(data.toMutableList())
    }

    private class MediaDiffCallback : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return (oldItem == newItem)
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return (oldItem.id == newItem.id)
        }
    }

    inner class MediaViewHolder(mediaView: View) : RecyclerView.ViewHolder(mediaView),
        View.OnClickListener {
        private val context = mediaView.context

        private val poster: ImageView = mediaView.findViewById(R.id.poster)
        private val title: TextView = mediaView.findViewById(R.id.title)
        private val overview: TextView = mediaView.findViewById(R.id.overview)
        private val save: ImageButton = mediaView.findViewById(R.id.save)

        fun bind(media: Media) {
            //TODO (UI): Dynamically set image size based on device size
            //Populate the item's poster
            poster.load("https://image.tmdb.org/t/p/w342" + media.posterPath)

            //Populate the item's title
            var titleText: String? = null
            if (media is Movie) titleText = media.title
            if (media is Show) titleText = media.name
            title.text = titleText

            //Populate the item's overview via a shortened string, handle empty overviews
            var overviewSubString = media.overview?.substringBefore(".")
            if (!overviewSubString.isNullOrBlank()) {
                if (overviewSubString.length <= 60) {
                    overviewSubString += "."
                    //If the overview is too short, extend to the end of the next sentence
                    val extendedSubString = media.overview!!.substringAfter(".")
                    overviewSubString += extendedSubString.substringBefore(".")
                }
            } else {
                overviewSubString = context.getString(R.string.list_item_empty_overview)
            }
            overviewSubString += "."
            overview.text = overviewSubString

            //TODO (UI): Set image resource based on item's presence in database
            save.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            mediaSaveListener.onListItemSaveClick(adapterPosition)
        }
    }

    interface MediaSaveListener {
        fun onListItemSaveClick(itemPosition: Int)
    }

}