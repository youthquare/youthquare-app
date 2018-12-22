package ios.android.installable.youthquare.features.register

import android.Manifest
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ios.android.installable.youthquare.R
import ios.android.installable.youthquare.extension.getStatusbarHeight__DP
import kotlinx.android.synthetic.main.gonvernment_id_camera_activity.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import android.util.DisplayMetrics
import android.os.Build
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Handler
import android.util.Log
import android.view.View
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.Flash
import ios.android.installable.youthquare.extension.bitmap

@RuntimePermissions
class GovernmentIDVerificationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gonvernment_id_camera_activity)

        val params = government_id_camera_statusbar_margin_container.layoutParams
        params.height = getStatusbarHeight__DP().toInt() //getSoftButtonsBarHeight() / 2
        // government_id_camera_statusbar_margin_container.requestLayout()
        government_id_camera_statusbar_margin_container.layoutParams = params

        cameraView.setLifecycleOwner(this)
        cameraView.flash = Flash.OFF

        camera_shutter_view.setOnClickListener {
            cameraView.capturePicture()
        }

        cameraView.addCameraListener(object: CameraListener() {
            override fun onPictureTaken(jpeg: ByteArray?) {
                verifyIDCards(jpeg!!.bitmap())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraView.stop()
    }

    override fun onPause() {
        cameraView.stop()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        cameraView.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun initCamera() {

    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onPermissionDeniedCamera() {
        val dialog = AlertDialog.Builder(this@GovernmentIDVerificationActivity)
        dialog.setTitle("오류")
        dialog.setMessage("카메라 권한이 허용되지 않았습니다.\n인증이 되지 않았으므로 앱을 종료합니다.")
        dialog.setCancelable(false)
        dialog.setPositiveButton("확인") { dialog, which ->
            finish()
        }
        dialog.show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        val dialog = AlertDialog.Builder(this@GovernmentIDVerificationActivity)
        dialog.setTitle("오류")
        dialog.setMessage("카메라 권한이 허용되지 않았습니다.\n환경설정 > 앱 에서 카메라 권한을 허용해주세요.")
        dialog.setCancelable(false)
        dialog.setPositiveButton("확인") { dialog, which ->
            finish()
        }
        dialog.show()
    }

    private fun verifyIDCards(bitmap: Bitmap) {
        progress_overlay.visibility = View.VISIBLE

        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val firebaseVisionTextDetector = FirebaseVision.getInstance().cloudTextRecognizer

        firebaseVisionTextDetector.processImage(image)
            .addOnSuccessListener {
                val words = it.text.split("\n")

                for (word in words) {
                    Log.e("TEST", word)
                }

                if (words[0] == "주민등록증") {
                    val dialog = AlertDialog.Builder(this@GovernmentIDVerificationActivity)

                    val name = words[1].split("(")[0]
                    val birthYear = words[2].split("-")[0].substring(0, 2)
                    val birthMonth = words[2].split("-")[0].substring(2, 4)
                    val birthDay = words[2].split("-")[0].substring(4, 6)

                    dialog.setMessage("${name} (${birthYear}년 ${birthMonth}월 ${birthDay}일생)\n확인되었습니다.")
                    dialog.setCancelable(false)
                    dialog.setPositiveButton("확인") { dialog, which ->
                        progress_overlay.visibility = View.GONE
                        dialog.dismiss()
                    }
                    dialog.show()
                } else if (words[0].startsWith("학생증")) {
                    try {
                        val name = words[2]
                        val birthYear = words[6].replace(" ", "").split("생년월일")[1].split("/")[0]
                        val birthMonth = words[6].replace(" ", "").split("생년월일")[1].split("/")[1]
                        val birthDay = words[6].replace(" ", "").split("생년월일")[1].split("/")[2]

                        val dialog = AlertDialog.Builder(this@GovernmentIDVerificationActivity)

                        dialog.setMessage("${name} (${birthYear}년 ${birthMonth}월 ${birthDay}일생)\n확인되었습니다.")
                        dialog.setCancelable(false)
                        dialog.setPositiveButton("확인") { dialog, which ->
                            progress_overlay.visibility = View.GONE
                            dialog.dismiss()
                        }
                        dialog.show()
                    } catch(e: Exception) {
                        val dialog = AlertDialog.Builder(this@GovernmentIDVerificationActivity)

                        dialog.setMessage("신분증이 화면 꽉차게 보이게 해주세요.")
                        dialog.setCancelable(false)
                        dialog.setPositiveButton("확인") { dialog, which ->
                            progress_overlay.visibility = View.GONE
                            dialog.dismiss()
                        }
                        dialog.show()
                    }
                } else {
                    val dialog = AlertDialog.Builder(this@GovernmentIDVerificationActivity)
                    dialog.setTitle("인식 오류")
                    dialog.setMessage("신분증을 인식할 수 없" +
                            "습니다.\n올바른 신분증인지 확인 후 다시 시도해주세요.")
                    dialog.setCancelable(false)
                    dialog.setPositiveButton("확인") { dialog, which ->
                        progress_overlay.visibility = View.GONE
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }.addOnFailureListener {
                val dialog = AlertDialog.Builder(this@GovernmentIDVerificationActivity)
                dialog.setTitle("인식 오류")
                dialog.setMessage("신분증을 인식할 수 없" +
                        "습니다.\n올바른 신분증인지 확인 후 다시 시도해주세요.")
                dialog.setCancelable(false)
                dialog.setPositiveButton("확인") { dialog, which ->
                    progress_overlay.visibility = View.GONE
                    dialog.dismiss()
                }
                dialog.show()
            }
    }
}