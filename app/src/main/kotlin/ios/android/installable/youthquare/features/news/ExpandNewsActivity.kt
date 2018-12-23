package ios.android.installable.youthquare.features.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ios.android.installable.youthquare.R
import kotlinx.android.synthetic.main.expand_news_activity.*
import java.lang.Exception
import android.view.ViewTreeObserver
import androidx.core.app.ActivityCompat
import com.squareup.picasso.RequestCreator
import ios.android.installable.youthquare.extension.getStatusbarHeight__DP
import ios.android.installable.youthquare.features.news.opinion.list.adapter.OpinionListAdapter
import ios.android.installable.youthquare.features.news.opinion.list.model.CardOpinion
import androidx.recyclerview.widget.LinearLayoutManager
import ios.android.installable.youthquare.features.opinion.OpinionDetailActivity


class ExpandNewsActivity: AppCompatActivity() {
    private val opinionAdapter by lazy { OpinionListAdapter(createOpinionList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expand_news_activity)

        val params = expand_news_statusbar_container.layoutParams
        params.height = getStatusbarHeight__DP().toInt() //getSoftButtonsBarHeight() / 2
        expand_news_statusbar_container.layoutParams = params

        expand_news_category_text.text = intent.getStringExtra("category")
        expand_news_title_text.text = intent.getStringExtra("title")

        ViewCompat.setTransitionName(expand_news_background_image, intent.getStringExtra("backgroundURL"))
        ViewCompat.setTransitionName(expand_news_category_container, intent.getStringExtra("category"))
        ViewCompat.setTransitionName(expand_news_title_text, intent.getStringExtra("title"))

        supportPostponeEnterTransition()

        val requestCreator = Picasso.get().load(intent.getStringExtra("backgroundURL")).centerCrop().fit().noFade()
        requestCreator.into(expand_news_background_image, callback)

        expand_news_back_button.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }

        opinionAdapter.setOnClickListener(object: OpinionListAdapter.ClickEventListener {
            override fun addButtonClicked() {

            }

            override fun clickEvent(opinion: CardOpinion, position: Int) {
                val detailIntent = Intent(this@ExpandNewsActivity, OpinionDetailActivity::class.java)
                startActivity(detailIntent)
            }

            override fun agreeButtonClicked(opinion: CardOpinion, position: Int) {

            }

            override fun disAgreeButtonClicked(opinion: CardOpinion, position: Int) {

            }
        })

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        expand_news_opinion_list.layoutManager = layoutManager
        expand_news_opinion_list.adapter = opinionAdapter
    }

    private val callback = object : Callback {
        override fun onError(e: Exception?) {

        }

        override fun onSuccess() {
            expand_news_background_image.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    expand_news_background_image.viewTreeObserver.removeOnPreDrawListener(this)
                    this@ExpandNewsActivity.startPostponedEnterTransition()
                    return true
                }
            })
        }
    }

    private fun createOpinionList(): List<CardOpinion> {
        val opinionList = ArrayList<CardOpinion>()

        opinionList.add(CardOpinion("", "", "", "", "", 0, 0))
        opinionList.add(CardOpinion("‘카풀 앱은 시민들의 공공 편의를 위해 절대로\n" +
                "중단되면 안됩니다.’", "http://cfs7.tistory.com/upload_control/download.blog?fhandle=YmxvZzgyMzM1QGZzNy50aXN0b3J5LmNvbTovYXR0YWNoLzAvMDYwMDAwMDAwMDAwLmpwZw%3D%3D",
                "이상훈", "2018년 12월 23일 일요일", " 카풀은 시민들의 공공편의를 위하여 반드시 중단되서는 안됩니다. 여객자동차 운수사업법 제 73조에 1항에 따르면 승용자동차를 출퇴근시에 함께 타는 경우는 사업용 … 더보기외의 자동차도 유상으로 운송용에 제공하거나 임대할 수 있습니다. 카풀 서비스 금지는 이러한 법에 전면으로 부딪히는 것 입니다. 그리고 택시에 대한 부정적인 국민적 감정도 카풀 찬성에 큰 힘을 실어주고 있습니다. 첨부된 자료는 최근 5년간 시민들에게 택시 서비스 만족을 조사한 설문지로써, 불만을 갖은 손님의 비율이 점점 더 늘어나고 있습니다.",
            0, 0))
        opinionList.add(CardOpinion("카풀은 불법일뿐만 아니라 위험합니다.", "http://cfs7.tistory.com/upload_control/download.blog?fhandle=YmxvZzgyMzM1QGZzNy50aXN0b3J5LmNvbTovYXR0YWNoLzAvMDYwMDAwMDAwMDAwLmpwZw%3D%3D",
            "이상하", "2018년 12월 23일 일요일", " 카풀은 불법이며, 법적으로도 보호되지 않는 위험한 수단입니다. 그러므로 반대해야합니다.",
            0, 0))
//        opinionList.add(CardOpinion("‘카풀 앱은 시민들의 공공 편의를 위해 절대로\n" +
//                "중단되면 안됩니다.’", "http://cfs7.tistory.com/upload_control/download.blog?fhandle=YmxvZzgyMzM1QGZzNy50aXN0b3J5LmNvbTovYXR0YWNoLzAvMDYwMDAwMDAwMDAwLmpwZw%3D%3D",
//            "이상훈", "2018년 12월 23일 일요일", " 카풀은 시민들의 공공편의를 위하여 반드시 중단되서는 안됩니다. 여객자동차 운수사업법 제 73조에 1항에 따르면 승용자동차를 출퇴근시에 함께 타는 경우는 사업용 … 더보기외의 자동차도 유상으로 운송용에 제공하거나 임대할 수 있습니다. 카풀 서비스 금지는 이러한 법에 전면으로 부딪히는 것 입니다. 그리고 택시에 대한 부정적인 국민적 감정도 카풀 찬성에 큰 힘을 실어주고 있습니다. 첨부된 자료는 최근 5년간 시민들에게 택시 서비스 만족을 조사한 설문지로써, 불만을 갖은 손님의 비율이 점점 더 늘어나고 있습니다.",
//            42195, 1034))
//        opinionList.add(CardOpinion("‘카풀 앱은 시민들의 공공 편의를 위해 절대로\n" +
//                "중단되면 안됩니다.’", "http://cfs7.tistory.com/upload_control/download.blog?fhandle=YmxvZzgyMzM1QGZzNy50aXN0b3J5LmNvbTovYXR0YWNoLzAvMDYwMDAwMDAwMDAwLmpwZw%3D%3D",
//            "이상훈", "2018년 12월 23일 일요일", " 카풀은 시민들의 공공편의를 위하여 반드시 중단되서는 안됩니다. 여객자동차 운수사업법 제 73조에 1항에 따르면 승용자동차를 출퇴근시에 함께 타는 경우는 사업용 … 더보기외의 자동차도 유상으로 운송용에 제공하거나 임대할 수 있습니다. 카풀 서비스 금지는 이러한 법에 전면으로 부딪히는 것 입니다. 그리고 택시에 대한 부정적인 국민적 감정도 카풀 찬성에 큰 힘을 실어주고 있습니다. 첨부된 자료는 최근 5년간 시민들에게 택시 서비스 만족을 조사한 설문지로써, 불만을 갖은 손님의 비율이 점점 더 늘어나고 있습니다.",
//            42195, 1034))
//        opinionList.add(CardOpinion("‘카풀 앱은 시민들의 공공 편의를 위해 절대로\n" +
//                "중단되면 안됩니다.’", "http://cfs7.tistory.com/upload_control/download.blog?fhandle=YmxvZzgyMzM1QGZzNy50aXN0b3J5LmNvbTovYXR0YWNoLzAvMDYwMDAwMDAwMDAwLmpwZw%3D%3D",
//            "이상훈", "2018년 12월 23일 일요일", " 카풀은 시민들의 공공편의를 위하여 반드시 중단되서는 안됩니다. 여객자동차 운수사업법 제 73조에 1항에 따르면 승용자동차를 출퇴근시에 함께 타는 경우는 사업용 … 더보기외의 자동차도 유상으로 운송용에 제공하거나 임대할 수 있습니다. 카풀 서비스 금지는 이러한 법에 전면으로 부딪히는 것 입니다. 그리고 택시에 대한 부정적인 국민적 감정도 카풀 찬성에 큰 힘을 실어주고 있습니다. 첨부된 자료는 최근 5년간 시민들에게 택시 서비스 만족을 조사한 설문지로써, 불만을 갖은 손님의 비율이 점점 더 늘어나고 있습니다.",
//            42195, 1034))


        return opinionList
    }
}