package codes.terminaether.watchlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.terminaether.watchlist.R
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import codes.terminaether.watchlist.data.repo.GenreRepository
import coil.api.load

/**
 * ListAdapter responsible for Media items to be displayed in a RecyclerView.
 *
 * Created by terminaether on 2020-01-09.
 */
class MediaListAdapter : ListAdapter<Media, MediaListAdapter.MediaViewHolder>(MediaDiffCallback()) {

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

    inner class MediaViewHolder(mediaView: View) : RecyclerView.ViewHolder(mediaView) {
        private val context = mediaView.context

        private val poster: ImageView = mediaView.findViewById(R.id.poster)
        private val title: TextView = mediaView.findViewById(R.id.title)
        private val overview: TextView = mediaView.findViewById(R.id.overview)
        private val genre: TextView = mediaView.findViewById(R.id.genre)

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
                if (overviewSubString.length <= 20) {
                    //If the overview is too short, extend to the end of the next sentence
                    val extendedSubString = media.overview!!.substringAfter(".")
                    overviewSubString += extendedSubString.substringBefore(".")
                }
            } else {
                overviewSubString = context.getString(R.string.list_item_empty_overview)
            }
            overviewSubString += "."
            overview.text = overviewSubString

            //Populate the item's genre tag via GenreRepository if necessary, handle empty values
            if (!media.genres.isNullOrEmpty()) {
                genre.text = media.genres[0].name
            } else if (media.genreIds != null && media.genreIds.isNotEmpty()) {
                genre.text = GenreRepository().getGenreNameById(media.genreIds[0])
            } else {
                genre.text = context.getString(R.string.list_item_empty_genre)
            }
        }
    }

}