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
import codes.terminaether.watchlist.data.model.Movie
import coil.api.load

/**
 * ListAdapter responsible for Movie items to be displayed in a RecyclerView.
 *
 * Created by terminaether on 2020-01-09.
 */
class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun swapData(data: List<Movie>) {
        submitList(data.toMutableList())
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return (oldItem == newItem)
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return (oldItem.id == newItem.id)
        }
    }

    inner class MovieViewHolder(movieView: View) : RecyclerView.ViewHolder(movieView) {
        private val poster: ImageView = movieView.findViewById(R.id.poster)
        private val title: TextView = movieView.findViewById(R.id.title)

        fun bind(movie: Movie) {
            //TODO (UI): Dynamically set image size based on device size
            poster.load("https://image.tmdb.org/t/p/w342" + movie.posterPath)
            title.text = movie.title
        }
    }

}