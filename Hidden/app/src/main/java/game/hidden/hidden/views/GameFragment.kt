package game.hidden.hidden.views

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import game.hidden.hidden.R
import game.hidden.hidden.async.ProgressUpdateTask
import kotlinx.android.synthetic.main.fragment_game.view.*

const val TOTAL_TIME = 100

class GameFragment : Fragment(), ImagesGridView.OnClickListener, ProgressUpdateTask.OnProgressUpdate {

    lateinit var v : View

    var startTime = System.currentTimeMillis()

    var onFinishListener : OnFinishListener? = null

    var progressUpdateTask: ProgressUpdateTask? = null

    var timeTaken = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnFinishListener) {
            onFinishListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = LayoutInflater.from(context).inflate(R.layout.fragment_game, container, false)
        setImageGridView()
        startTimer()
        return v
    }

    private fun startTimer() {
        progressUpdateTask = ProgressUpdateTask(TOTAL_TIME, this).execute() as ProgressUpdateTask?
    }

    private fun setImageGridView() {
        v.imagesGrid.bindView(onClickListener = this)
    }

    override fun completed() {
        v.tvTimer.text = "Total Score ${(TOTAL_TIME - timeTaken).toString()}"
//        onFinishListener?.completed()

    }

    interface OnFinishListener {
        fun completed()
    }

    override fun onUpdate(update: Int) {
        v.tvTimer.text = update.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressUpdateTask?.cancel(true)
    }

}