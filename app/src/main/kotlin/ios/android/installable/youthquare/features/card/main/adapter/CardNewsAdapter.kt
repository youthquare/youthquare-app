package ios.android.installable.youthquare.features.card.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ios.android.installable.youthquare.R
import ios.android.installable.youthquare.features.card.main.model.NewsCard
import java.lang.Exception

class CardStackAdapter(
    private var newses: List<NewsCard> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    interface CardNewsClickListener {
        fun onCardClicked(news: NewsCard, position: Int, holder: ViewHolder)
    }

    private lateinit var onClickListener: CardNewsClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.card_news_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newses[position]

        Picasso.get().load(news.backgroundURL).into(holder.backgroundImage, object: Callback {
            override fun onSuccess() {
                holder.backgroundImage.scaleType = ImageView.ScaleType.CENTER_CROP
            }

            override fun onError(e: Exception?) {

            }

        })
        holder.categoryText.text = news.category
        holder.titleText.text = news.title
        holder.threeCommentText.text = news.threeSummary

        ViewCompat.setTransitionName(holder.categoryContainer, news.category)
        ViewCompat.setTransitionName(holder.titleText, news.title)
        ViewCompat.setTransitionName(holder.threeCommentText, news.threeSummary)
        ViewCompat.setTransitionName(holder.backgroundImage, news.backgroundURL)

        holder.mainCard.setOnClickListener {
            onClickListener.onCardClicked(news, position, holder)
        }
    }

    override fun getItemCount(): Int {
        return newses.size
    }

    fun setOnClickListener(listener: CardNewsClickListener) {
        onClickListener = listener
    }

    fun setNewsList(newsList: List<NewsCard>) {
        newses = newsList
        notifyDataSetChanged()
    }

    fun getNewsList(): List<NewsCard> {
        return newses
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mainCard: CardView = view.findViewById(R.id.card_news_root_layout)
        val progressBar: ProgressBar = view.findViewById(R.id.card_news_progress_bar)
        val backgroundImage: ImageView = view.findViewById(R.id.news_card_background_image)
        val categoryContainer: RelativeLayout = view.findViewById(R.id.card_news_category_container)
        val categoryText: TextView = view.findViewById(R.id.card_news_category_text)
        val titleText: TextView = view.findViewById(R.id.card_news_title_text)
        val threeCommentText: TextView = view.findViewById(R.id.three_summary_text)
    }
}