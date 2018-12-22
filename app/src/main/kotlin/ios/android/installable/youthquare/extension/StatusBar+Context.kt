package ios.android.installable.youthquare.extension

import android.content.Context
import android.util.DisplayMetrics

fun Context.getStatusbarHeight__DP(): Float {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result.toFloat()
}

internal fun Context.convertPixelsToDp(px: Float): Float {
    return px / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}