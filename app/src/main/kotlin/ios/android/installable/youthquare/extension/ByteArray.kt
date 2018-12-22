package ios.android.installable.youthquare.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun ByteArray.bitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, size)
}