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
import ios.android.installable.youthquare.extension.getNavigationbarHeight__DP
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
            scrollBehavior.peekHeight = height - topY.toInt() + (getNavigationbarHeight__DP().toInt() * 2)
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

        newsList.add(NewsCard("사회", "“편파 판결·불법 촬영 규탄” 마지막 여성 시위…성과·한계는?", "여성에게 불리한 수사와 판결을 한다고 주장하며 불법 촬영 범죄를 규탄하는 여성들의 여섯 번째 집회가 열렸습니다 이번이 사실상 마지막 집회가 됐는데요 그동안 이어진 이 집회의 성과와 한계를 홍진아 기자가 보도합니다", "https://imgnews.pstatic.net/image/origin/056/2018/12/22/10653646.jpg"))
        newsList.add(NewsCard("사회", "[날씨] 일요일 오후부터 기온 '뚝'…미세먼지 차츰 해소", "오늘(22일) 서울에는 초미세먼지 주의보가 내려졌습니다한때 먼지농도가 평소의 두 배 이상 높아지기도 했는데요, 미세먼지는 내일 오전까지는 계속되겠지만 오후에 북쪽에서 찬 바람이 불어오면서 점차 중부지방부터 해소가 되겠습니다. 하지만 일부 남부지방은 내일도 공기가 답답하겠습니다", "https://imgnews.pstatic.net/image/origin/055/2018/12/22/698258.jpg"))
        newsList.add(NewsCard("세계", "미 정부, 결국 '셧다운' 시행…새해까지 이어질 가능성", "도널드 트럼프 미국 대통령이 추진 중인 멕시코 국경장벽 건설을 둘러싼 갈등이 결국 파국을 맞았습니다 건설비용을 포함한 예산안 처리가 무산되면서 연방정부가 부분적으로 문을 닫는 '셧다운'이 시행됐는데요 이번 셧다운은 기존과 다르게 길어질 수 있다는 우려가 나옵니다", "https://imgnews.pstatic.net/image/origin/437/2018/12/22/198981.jpg"))
        newsList.add(NewsCard("세계", "소말리아 대통령궁 인근서 폭탄테러…최소 16명 사망(종합)", "아프리카 소말리아 수도 모가디슈의 대통령궁 근처에서 22일(현지시간) 차량폭탄 테러가 발생해 최소 16명이 숨지고 20여명이 다쳤다고 AP, DPA 통신 등이 현지 경찰을 인용해 보도했다    소말리아 경찰은 이날 아침 폭탄을 실은 차량이 대통령궁 후문 근처의 군 검문소를 덮쳤다고 설명했다    경찰은 사망자에 영국 런던에 본부를 둔 유니버설 TV 방송국 직원 3명이 포함됐고 유명 언론인 아윌 다히르 살라드도 그 중 한명이라고 밝혔다", "https://imgnews.pstatic.net/image/origin/001/2018/12/22/10541260.jpg"))
        newsList.add(NewsCard("경제", "[앵커의 눈] 기름값 7주째 뚝…소비자 ‘반색’·업종별 ‘명암’", "국내 휘발유와 경유 가격이 국제유가 급락 등의 영향으로 7주째 하락했습니다이번 주 전국 주유소에서 팔린 보통 휘발유값은 리터당 천420원대까지 내려와, 2년여 만에 최저치를 기록했고요자동차용 경유값도 천320원 대까지 떨어져 1년여 만에 가장 낮은 수준을 보였습니다", "https://imgnews.pstatic.net/image/origin/056/2018/12/22/10653641.jpg"))
        newsList.add(NewsCard("세계", "성탄휴가 앞두고 프랑스 '노란조끼' 집회규모 크게 줄어", "프랑스에서 서민경제 개선대책을 요구하는 이른바 '노란 조끼' 시위의 6차 집회 규모가 전보다 큰 폭으로 줄었다    22일(현지시간) 파리 최대 번화가인 샹젤리제 거리와 개선문 인근은 소규모의 노란 조끼 집회 참가자들이 시위에 나섰지만 교통운행은 정상적인 흐름을 보였다    일부 시위대는 시내 곳곳에서 행진하며 가로막는 경찰과 대치하기도 했으나 별다른 충돌은 빚어지지 않았다", "https://imgnews.pstatic.net/image/origin/001/2018/12/22/10541308.jpg"))
        newsList.add(NewsCard("사회", "'김용균 추모제' 후 청와대 행진…'대통령 면담' 요구", "한편 오늘(22일) 서울 도심에서는 태안화력발전소 비정규직 노동자 고 김용균 씨 사망사고의 진상 규명을 촉구하는 범국민 추모제가 열렸습니다 참가 시민들은 청와대 앞까지 행진하면서 문 대통령과 면담을 요구하기도 했는데, 현장에 나가 있는 취재기자 연결하겠습니다김민관 기자, 지금 김 기자가 청와대 앞쪽에 나가있죠? 집회는 어떻게 진행되고 있습니까? [기자]오늘 오후 5시 청계광장 주변에서 시작된 고 김용균씨 추모제는 마무리가 되고, 지금은 제가 나와있는 이곳 청와대 사랑채 앞까지 행진한 시민들이 이렇게 모여있습니다", "https://imgnews.pstatic.net/image/origin/437/2018/12/22/198976.jpg"))
        newsList.add(NewsCard("사회", "참사 닷새째…3명 눈 떴지만 3명 아직 의식 못 찾아", "릉 펜션 사고로 입원 중인 학생들, 더디지만 조금씩 회복하고 있습니다원주로 이송된 상태가 위중한 두 명에게는 재활 치료가 시작됐고, 일반병실로 옮겨진 학생들에겐 심리 치료도 병행됐습니다 경찰 수사는 마무리 단계에 접어들었습니다", "https://imgnews.pstatic.net/image/origin/214/2018/12/22/902200.jpg"))
        newsList.add(NewsCard("정치", "與 지도부·을지로위 '406일째' 파인텍 굴뚝농성장 방문", "더불어민주당 지도부와 을지로위원회는 22일 파인텍 노동조합의 고공농성장을 찾아 노사 간 갈등 해결을 시도했다 박주민·이수진 최고위원과 을지로위원장인 박홍근 우원식 의원은 이날 서울 양천구 목동 파인텍 지회 농성장을 찾아 시민사회단체 관계자 등과 면담을 한 뒤 열병합발전소 굴뚝 농성장 현장을 다녀왔다 박홍근 의원은 현장을 다녀온 후 페이스북에 \\\"몸무게가 50kg 이하까지 된 고공의 두 조합원 건강이 정말 걱정된다\\\"며 \\\"지상에서 동조 단식에 돌입한 지회장 등과 신부님, 목사님을 비롯한 시만사회단체 인사들의 간절함이 사회적으로 확산되는 상황\\\"이라고 우려를 표했다", "https://imgnews.pstatic.net/image/origin/421/2018/12/22/3752514.jpg"))
        newsList.add(NewsCard("사회", "카카오 카풀: 택시업계와 승차공유 서비스의 해묵은 갈등 5가지", "18일 새벽 4시부터 24시간 동안 약 20만 대에 달하는 택시가 파업에 들어갔고, 오후 2시에는 광화문에서 4대 택시단체로 이뤄진 '불법 카풀 관련 비상대책위원회'가 '카카오 카풀' 반대 집회를 열 예정이다.", "https://ichef.bbci.co.uk/news/660/cpsprodpb/1487A/production/_103909048_origin_.jpg"))

        return newsList
    }
}