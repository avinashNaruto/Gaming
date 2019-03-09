package game.hidden.hidden.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import game.hidden.hidden.R
import game.hidden.hidden.utils.getImagesDrawableList
import game.hidden.hidden.utils.getScreenHeight
import game.hidden.hidden.utils.getScreenWidth
import kotlinx.android.synthetic.main.view_container.view.*
import java.util.*

const val defaultNumberOfRows = 3
const val defaultNumberOfColumns = 2

class ImagesGridView : FrameLayout {

    constructor (context: Context) : super(context)

    constructor (context: Context, attrs: AttributeSet) : super(context,attrs)

    constructor (context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    var rows = defaultNumberOfRows
    var columns = defaultNumberOfColumns
    var onClickListener : OnClickListener? = null
    var imagesMap : MutableMap<Int, Int> = getImagesDrawableList().map { it to 0 }.toMap().toMutableMap()
    var idList : ArrayList<Int> = getImagesDrawableList()
    var currentCount = 0
    var firstClickedId = -1
    var secondClickedId = -2
    var previousClickedView : ImageView? = null
    var completedPair = 0

    init {
        idList  = getImagesDrawableList()
        imagesMap  = getImagesDrawableList().map { it to 0 }.toMap().toMutableMap()
    }

    fun bindView(rows : Int  = defaultNumberOfRows, column : Int = defaultNumberOfColumns, onClickListener : OnClickListener? = null ) {
        this.onClickListener = onClickListener
        this.rows = rows
        this.columns = columns
        renderGrid()
    }

    fun calculateBlockWidth() : Int {
        return getScreenWidth() / columns
    }

    fun calculateBlockHeight() : Int {
        return getScreenHeight() / rows
    }

    fun renderGrid() {
        val container = LayoutInflater.from(context).inflate(R.layout.view_container, this, true)
        for(k in 1..rows) {
            val row = LayoutInflater.from(context).inflate(R.layout.row_container, null, false) as LinearLayout
            for (i in 1..columns) {
                val itemView = LayoutInflater.from(context).inflate(R.layout.images_grid_item_view, row, false) as ImageView
                itemView.layoutParams.apply {
                    width = calculateBlockWidth()
                    height = calculateBlockHeight()
                }
                val drawableId = getDrawableId()
                if(drawableId != -1) {
                    itemView.tag = drawableId
                    itemView.setImageResource(R.drawable.placeholder)
                    itemView.setOnClickListener { onClick(drawableId, itemView)}
                    row.addView(itemView)
                }
            }
            container.completeLayout.addView(row)
        }
    }

    fun getDrawableId(): Int {
        var i = 0
        while (i < 100) {
            val rand = Random()
            val index = rand.nextInt(rows * columns / 2)
            val id = idList[index]
            val drawableCount = imagesMap[id]
            if (drawableCount != null && drawableCount < 2) {
                imagesMap[id] = imagesMap[id]?.plus(1) ?: 0
                return id
            }
            i++
        }
        for ((key, value) in imagesMap) {
            if(value < 2) {
                imagesMap[key] = imagesMap[key]?.plus(1) ?: 0
                return key
            }
        }
        return -1
    }

    fun onClick(id : Int?, view: ImageView) {
        revealImage(id, view)
        if(id != null && id > 0) {
            if (currentCount == 0) {
                firstClickedId = id
                previousClickedView = view
                currentCount++
            } else {
                if(previousClickedView?.tag == view.tag) {
                    completedPair++
                    blockImage(view)
                    blockImage(previousClickedView)
                    if(completedPair * 2 == rows*columns) {
                        onClickListener?.completed()
                    }
                } else {
                    resetImage(view)
                    resetImage(previousClickedView)
                }
                previousClickedView = null
                currentCount = 0
            }
        } else {

        }
    }

    private fun revealImage(id: Int?, view: ImageView?) {
        if(id != null && id > 0) {
            view?.setImageResource(id)
        }
    }

    private fun resetImage(view: ImageView?) {
        view?.setImageResource(R.drawable.placeholder)
    }

    private fun blockImage(view: ImageView?) {
        view?.setImageDrawable(null)
        view?.setBackgroundColor(resources.getColor(R.color.black))
        view?.isEnabled = false
    }

    interface OnClickListener {
        fun completed()
    }

}