package game.hidden.hidden

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import game.hidden.hidden.views.GameFragment

class GameActivity : AppCompatActivity(), GameFragment.OnFinishListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transactFragment()
    }

    private fun transactFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, GameFragment()).commit()
    }

    override fun completed() {
        transactFragment()
    }
}
