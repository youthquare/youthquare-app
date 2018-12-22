package ios.android.installable.youthquare.features.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ios.android.installable.youthquare.R
import kotlinx.android.synthetic.main.register_information_activity.*

class RegisterInformationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_information_activity)

        register_information_back_button.setOnClickListener {
            finish()
        }
    }
}