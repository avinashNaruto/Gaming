package game.hidden.hidden.utils

import android.content.res.Resources
import game.hidden.hidden.R

fun getScreenWidth() : Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight() : Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun getImagesDrawableList(): ArrayList<Int> {
    return arrayListOf(R.drawable.boy_pencil,R.drawable.cartoon,R.drawable.girlish,R.drawable.girly,R.drawable.mickey,
        R.drawable.monk,R.drawable.monkey,R.drawable.pencil)
}