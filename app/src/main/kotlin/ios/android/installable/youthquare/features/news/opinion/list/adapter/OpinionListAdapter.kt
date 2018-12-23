package ios.android.installable.youthquare.features.news.opinion.list.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import ios.android.installable.youthquare.R
import ios.android.installable.youthquare.features.card.main.model.NewsCard
import ios.android.installable.youthquare.features.news.opinion.list.model.CardOpinion
import java.lang.Exception

class OpinionListAdapter(
    private var opinions: List<CardOpinion> = emptyList()
) : RecyclerView.Adapter<OpinionListAdapter.ViewHolder>() {

    interface ClickEventListener {
        fun addButtonClicked()
        fun clickEvent(opinion: CardOpinion, position: Int)
        fun agreeButtonClicked(opinion: CardOpinion, position: Int)
        fun disAgreeButtonClicked(opinion: CardOpinion, position: Int)
    }

    private lateinit var onClickListener: ClickEventListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.opinion_card_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val opinion = opinions[position]

        if (opinion.title == "") {
            holder.addOpinionButton.visibility = View.VISIBLE
            holder.opinionContainer.visibility = View.GONE

            holder.addOpinionButton.setOnClickListener {
                onClickListener.addButtonClicked()
            }
        } else {
            holder.addOpinionButton.visibility = View.GONE
            holder.opinionContainer.visibility = View.VISIBLE

            holder.opinionTitle.text = opinion.title
            Picasso.get().load(opinion.profileURL).fit().centerCrop().into(holder.profileImage)
            holder.author.text = opinion.nickName
            holder.createdAt.text = opinion.createdAt
            holder.simpleOpinion.text = opinion.summary
            holder.agreeCountText.text = opinion.likeCount.toString()
            holder.disagreeCountText.text = opinion.dislikeCount.toString()

            holder.opinionContainer.setOnClickListener {
                onClickListener.clickEvent(opinion, position)
            }

            holder.agreeButton.setOnClickListener {
                onClickListener.agreeButtonClicked(opinion, position)
            }

            holder.disagreeButton.setOnClickListener {
                onClickListener.disAgreeButtonClicked(opinion, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return opinions.size
    }

    fun setOnClickListener(listener: ClickEventListener) {
        onClickListener = listener
    }

    fun setOpinionList(opinionList: List<CardOpinion>) {
        opinions = opinionList
        notifyDataSetChanged()
    }

    fun getOpinionList(): List<CardOpinion> {
        return opinions
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val addOpinionButton: RelativeLayout = view.findViewById(R.id.opinion_card_add_button)

        val opinionContainer: RelativeLayout = view.findViewById(R.id.opinion_card_list_op_container)
        val opinionTitle: TextView = view.findViewById(R.id.opinion_card_summary_title_text)
        val profileImage: CircleImageView = view.findViewById(R.id.opinion_card_summary_circle_profile)
        val author: TextView = view.findViewById(R.id.opinion_card_summary_author_text)
        val createdAt: TextView = view.findViewById(R.id.opinion_card_summary_created_at)
        val simpleOpinion: TextView = view.findViewById(R.id.opinion_card_summary_three_text)
        val agreeButton: LinearLayout = view.findViewById(R.id.opinion_card_agree_button)
        val agreeCountText: TextView = view.findViewById(R.id.opinion_card_agree_count_text)
        val disagreeButton: LinearLayout = view.findViewById(R.id.opinion_card_disagree_button)
        val disagreeCountText: TextView = view.findViewById(R.id.opinion_card_disagree_count_text)
    }
}