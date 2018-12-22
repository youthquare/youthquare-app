package ios.android.installable.youthquare.features.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import ios.android.installable.youthquare.R
import ios.android.installable.youthquare.features.register.RegisterInformationActivity
import kotlinx.android.synthetic.main.splash_activity.*
import java.util.*

class SplashActivity: AppCompatActivity() {
    private lateinit var facebookLoginCallbackManager: CallbackManager
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        facebookLoginCallbackManager = CallbackManager.Factory.create()
        firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            firebaseAuth.currentUser?.let {
                /* 임시 */
                val registerIntent = Intent(this@SplashActivity, RegisterInformationActivity::class.java)
                startActivity(registerIntent)

                finish()
            } ?: kotlin.run {
                splash_activity_facebook_login_btn.animate().setListener(object: Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {

                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {
                        splash_activity_facebook_login_btn.alpha = 0f
                        splash_activity_facebook_login_btn.visibility = View.VISIBLE
                    }
                }).alpha(1f).setDuration(250).start()

                splash_activity_facebook_login_btn.setOnClickListener {
                    LoginManager.getInstance().logInWithReadPermissions(this@SplashActivity, Arrays.asList("email", "user_photos", "public_profile"))
                    LoginManager.getInstance().registerCallback(facebookLoginCallbackManager, object:
                        FacebookCallback<LoginResult> {
                        override fun onSuccess(result: LoginResult?) {
                            result?.let { rt ->
                                handleFacebookAccessToken(rt.accessToken)
                            }
                        }

                        override fun onCancel() {

                        }

                        override fun onError(error: FacebookException?) {

                        }
                    })
                }
            }
        }, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookLoginCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser
                    Log.w("FBLogin", user!!.displayName)
                    splash_activity_facebook_login_btn.visibility = View.GONE

                    // 서버 로직 필요

                    val registerIntent = Intent(this@SplashActivity, RegisterInformationActivity::class.java)
                    startActivity(registerIntent)
                } else {
                    Log.w("FBLogin", "signInWithCredential:failure", task.exception)
                }
            }
    }
}