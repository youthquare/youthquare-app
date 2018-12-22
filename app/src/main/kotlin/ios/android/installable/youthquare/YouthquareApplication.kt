package ios.android.installable.youthquare

import android.app.Application
import com.facebook.FacebookSdk
import com.google.firebase.FirebaseApp

class YouthquareApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}