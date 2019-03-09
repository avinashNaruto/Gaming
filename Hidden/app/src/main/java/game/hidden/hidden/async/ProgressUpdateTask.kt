package game.hidden.hidden.async

import android.os.AsyncTask

//Score will be the remaining time i.e

class ProgressUpdateTask(val totalTime : Int, val onProgressUpdate : OnProgressUpdate? = null) : AsyncTask<Void, Int, Unit>() {

    override fun doInBackground(vararg p0: Void?) {
        for(i in 0..totalTime){

            publishProgress(i)
            try {
                Thread.sleep(1000);
            } catch (e : InterruptedException) {
                e.printStackTrace();
            }
            if(isCancelled){
                break
            }
        }

    }

    override fun onCancelled() {
        super.onCancelled()
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
    }

    override fun onProgressUpdate(vararg values: Int?) {
        if(values?.isNotEmpty() && values[0] != null) {
            onProgressUpdate?.onUpdate(values[0]!!)
        }
    }

    interface OnProgressUpdate {
        fun onUpdate(update : Int)
    }
}