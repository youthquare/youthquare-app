package ios.android.installable.youthquare.features.card.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ios.android.installable.youthquare.R
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.y
import android.R.attr.x
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Point
import android.view.Display
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import ios.android.installable.youthquare.extension.getStatusbarHeight__DP
import ios.android.installable.youthquare.features.card.main.adapter.CardStackAdapter
import ios.android.installable.youthquare.features.card.main.model.NewsCard
import ios.android.installable.youthquare.features.news.ExpandNewsActivity


class MainActivity : AppCompatActivity(), CardStackListener {
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(createNewsList()) }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        val list = adapter.getNewsList().toMutableList()
        val lastList = list[0]

        list.removeAt(0)
        list.add(lastList)

        adapter.setNewsList(list)
    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardRewound() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val params = activity_main_statusbar_container.layoutParams
        params.height = getStatusbarHeight__DP().toInt() //getSoftButtonsBarHeight() / 2
        activity_main_statusbar_container.layoutParams = params


        activity_main_swipe_up_text.post {
            val topY = activity_main_swipe_up_text.y

            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val height = size.y

            val scrollBehavior = BottomSheetBehavior.from(card_main_scroll_view)
            scrollBehavior.peekHeight = height - topY.toInt() + 350
            scrollBehavior.isHideable = false
        }

        manager.setStackFrom(StackFrom.Bottom)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(arrayListOf(Direction.Top))
        manager.setCanScrollHorizontal(false)
        manager.setCanScrollVertical(true)
        card_stack_view.layoutManager = manager
        card_stack_view.adapter = adapter
        card_stack_view.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

        adapter.setOnClickListener(object: CardStackAdapter.CardNewsClickListener {
            override fun onCardClicked(news: NewsCard, position: Int, holder: CardStackAdapter.ViewHolder) {
                val expandIntent = Intent(this@MainActivity, ExpandNewsActivity::class.java)
                expandIntent.putExtra("category", news.category)
                expandIntent.putExtra("title", news.title)
                expandIntent.putExtra("backgroundURL", news.backgroundURL)
                expandIntent.putExtra("news_id", 0)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity,
                        Pair.create(holder.backgroundImage, news.backgroundURL),
                        Pair.create(holder.categoryText, news.category),
                        Pair.create(holder.titleText, news.title))

                startActivity(expandIntent, options.toBundle())
            }
        })
    }

    private fun createNewsList(): List<NewsCard> {
        val newsList = ArrayList<NewsCard>()

        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))
        newsList.add(NewsCard("사회", "서울 천호동 성매매 업소에 불...2명 사망·3명 부상", "어제 오전 11시쯤 서울 천호동에 있는 성매매 업소에서 불이 나 50살 박 모 씨 등 2명이 숨졌습니다.", "https://image.ytn.co.kr/general/jpg/2018/1018/201810180709549951_t.jpg"))

        return newsList
    }
}