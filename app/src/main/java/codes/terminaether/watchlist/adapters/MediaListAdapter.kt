package codes.terminaether.watchlist.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.terminaether.watchlist.R
import codes.terminaether.watchlist.data.model.Media
import coil.api.load


/**
 * ListAdapter responsible for Media items to be displayed in a RecyclerView.
 *
 * Created by terminaether on 2020-01-09.
 */
class MediaListAdapter(private val mediaSaveListener: MediaSaveListener) :
    ListAdapter<Media, MediaListAdapter.MediaViewHolder>(MediaDiffCallback()) {

    companion object {
        const val PAYLOAD_KEY_POSTER_PATH_CHANGED = "posterPath"
        const val PAYLOAD_KEY_MEDIA_TITLE_CHANGED = "mediaTitle"
        const val PAYLOAD_KEY_CONCISE_OVERVIEW_CHANGED = "conciseOverview"
        const val PAYLOAD_KEY_SAVE_STATE_CHANGED = "hasSaveStateChanged"
    }

    interface MediaSaveListener {
        fun onListItemSaveClick(media: Media)
    }

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
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(
        holder: MediaViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            holder.updateViewUi(getItem(position), mutableListOf())
        } else {
            holder.updateViewUi(getItem(position), payloads)
        }
    }

    inner class MediaViewHolder(mediaView: View) : RecyclerView.ViewHolder(mediaView),
        View.OnClickListener {
        private val context = mediaView.context

        private val poster: ImageView = mediaView.findViewById(R.id.poster)
        private val title: TextView = mediaView.findViewById(R.id.title)
        private val overview: TextView = mediaView.findViewById(R.id.overview)
        private val save: ImageButton = mediaView.findViewById(R.id.save)

        fun updateViewUi(media: Media, payloads: MutableList<Any>) {
            val redrawAll = payloads.isNullOrEmpty()
            val payloadBundle = if (redrawAll) null else payloads[0] as Bundle

            if (redrawAll || payloadBundle!!.containsKey(PAYLOAD_KEY_POSTER_PATH_CHANGED)) {
                //TODO (UI): Dynamically set image size based on device size
                poster.load("https://image.tmdb.org/t/p/w342" + media.posterPath)
            }

            if (redrawAll || payloadBundle!!.containsKey(PAYLOAD_KEY_MEDIA_TITLE_CHANGED)) {
                title.text = media.mediaTitle
            }

            if (redrawAll || payloadBundle!!.containsKey(PAYLOAD_KEY_CONCISE_OVERVIEW_CHANGED)) {
                //Populate the item's overview via a shortened string, handle empty overviews
                var conciseOverview = media.getConciseOverview()
                if (conciseOverview.isBlank()) {
                    conciseOverview = context.getString(R.string.list_item_empty_overview)
                }
                overview.text = conciseOverview
            }

            if (redrawAll || payloadBundle!!.containsKey(PAYLOAD_KEY_SAVE_STATE_CHANGED)) {
                //Indicate an item's presence in the local database
                save.setOnClickListener(this)
                if (media.isSaved) {
                    save.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_save_filled
                        )
                    )
                } else {
                    save.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_save_empty
                        )
                    )
                }
            }
        }

        override fun onClick(p0: View?) {
            //Send a copy of the Media item up the stack, not a reference to the original
            mediaSaveListener.onListItemSaveClick(getItem(adapterPosition).copy())
        }
    }

    private class MediaDiffCallback : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return (oldItem.describeDrawableContents() == newItem.describeDrawableContents())
        }

        override fun getChangePayload(oldItem: Media, newItem: Media): Any? {
            val bundle = Bundle()
            if (oldItem.posterPath != newItem.posterPath) {
                bundle.putBoolean(PAYLOAD_KEY_POSTER_PATH_CHANGED, true)
            }
            if (oldItem.mediaTitle != newItem.mediaTitle) {
                bundle.putBoolean(PAYLOAD_KEY_MEDIA_TITLE_CHANGED, true)
            }
            if (oldItem.getConciseOverview() != newItem.getConciseOverview()) {
                bundle.putBoolean(PAYLOAD_KEY_CONCISE_OVERVIEW_CHANGED, true)
            }
            if (oldItem.isSaved != newItem.isSaved) {
                bundle.putBoolean(PAYLOAD_KEY_SAVE_STATE_CHANGED, true)
            }

            return bundle
        }
    }

}