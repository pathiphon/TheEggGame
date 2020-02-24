package com.adedom.theegggame.ui.multi.multi

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Score
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivityViewModel
import com.adedom.theegggame.util.*

class EndGameDialog : BaseDialogFragment<RoomInfoActivityViewModel>(
    { R.layout.dialog_end_game },
    { R.drawable.the_egg_game },
    { R.string.end_game }
) {

    override fun initDialog(view: View) {
        super.initDialog(view)
        val team = arguments!!.getString(TEAM)!!
        val (teamA, teamB) = arguments!!.getParcelable<Score>(SCORE) as Score

        val tvTeamA = view.findViewById(R.id.tvTeamA) as TextView
        val tvTeamB = view.findViewById(R.id.tvTeamB) as TextView
        val tvScoreA = view.findViewById(R.id.tvScoreA) as TextView
        val tvScoreB = view.findViewById(R.id.tvScoreB) as TextView
        val btEndGame = view.findViewById(R.id.btEndGame) as Button

        tvScoreA.text = teamA.toString()
        tvScoreB.text = teamB.toString()

        val teamWin: String
        when {
            teamA > teamB -> {
                tvTeamA.text = getString(R.string.win)
                tvTeamB.text = getString(R.string.lose)
                teamWin = TEAM_A
            }
            teamA < teamB -> {
                tvTeamA.text = getString(R.string.lose)
                tvTeamB.text = getString(R.string.win)
                teamWin = TEAM_B
            }
            else -> {
                tvTeamA.text = getString(R.string.always)
                tvTeamB.text = getString(R.string.always)
                teamWin = TEAM_ALWAYS
            }
        }

        if (team == teamWin) {
            btEndGame.setText(R.string.bonus)
            btEndGame.setOnClickListener {
                dialog!!.dismiss()
                BonusGameDialog().show(activity!!.supportFragmentManager, null)
            }
        } else btEndGame.setOnClickListener { GameActivity.sActivity.finish() }
    }

}
