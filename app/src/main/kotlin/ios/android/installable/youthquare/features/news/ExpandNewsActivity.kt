package ios.android.installable.youthquare.features.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ios.android.installable.youthquare.R
import kotlinx.android.synthetic.main.expand_news_activity.*
import java.lang.Exception
import android.view.ViewTreeObserver
import com.squareup.picasso.RequestCreator





class ExpandNewsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expand_news_activity)

        expand_news_category_text.text = intent.getStringExtra("category")
        expand_news_title_text.text = intent.getStringExtra("title")

        ViewCompat.setTransitionName(expand_news_background_image, intent.getStringExtra("backgroundURL"))
        ViewCompat.setTransitionName(expand_news_category_container, intent.getStringExtra("category"))
        ViewCompat.setTransitionName(expand_news_title_text, intent.getStringExtra("title"))

        supportPostponeEnterTransition()

        val requestCreator = Picasso.get().load(intent.getStringExtra("backgroundURL")).centerCrop().fit().noFade()
        requestCreator.into(expand_news_background_image, callback)
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
}