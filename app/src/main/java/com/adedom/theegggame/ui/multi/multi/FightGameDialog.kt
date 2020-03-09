package com.adedom.theegggame.ui.multi.multi

import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R

class FightGameDialog : BaseDialogFragment<MultiActivityViewModel>(
    { R.layout.dialog_fight_game },
    { R.drawable.ic_fight_red },
    { R.string.fight_game }
) {

    private lateinit var mTvScore: TextView
    private lateinit var mBtnOne: Button
    private lateinit var mBtnTwo: Button
    private lateinit var mBtnThree: Button
    private lateinit var mBtnFour: Button
    private lateinit var mBtnFive: Button
    private lateinit var mBtnSix: Button
    private lateinit var mBtnSeven: Button
    private lateinit var mBtnEight: Button
    private lateinit var mBtnNine: Button
    private lateinit var mBtnGiveUp: Button
    private val mHandlerRndButton = Handler()

    override fun initDialog(view: View) {
        super.initDialog(view)
        mTvScore = view.findViewById(R.id.mTvScore) as TextView
        mBtnOne = view.findViewById(R.id.mBtOne) as Button
        mBtnTwo = view.findViewById(R.id.mBtTwo) as Button
        mBtnThree = view.findViewById(R.id.mBtThree) as Button
        mBtnFour = view.findViewById(R.id.mBtFour) as Button
        mBtnFive = view.findViewById(R.id.mBtFive) as Button
        mBtnSix = view.findViewById(R.id.mBtSix) as Button
        mBtnSeven = view.findViewById(R.id.mBtSeven) as Button
        mBtnEight = view.findViewById(R.id.mBtEight) as Button
        mBtnNine = view.findViewById(R.id.mBtNine) as Button
        mBtnGiveUp = view.findViewById(R.id.mBtGiveUp) as Button

        mBtnOne.setOnClickListener { v -> setScore(v) }
        mBtnTwo.setOnClickListener { v -> setScore(v) }
        mBtnThree.setOnClickListener { v -> setScore(v) }
        mBtnFour.setOnClickListener { v -> setScore(v) }
        mBtnFive.setOnClickListener { v -> setScore(v) }
        mBtnSix.setOnClickListener { v -> setScore(v) }
        mBtnSeven.setOnClickListener { v -> setScore(v) }
        mBtnEight.setOnClickListener { v -> setScore(v) }
        mBtnNine.setOnClickListener { v -> setScore(v) }
        mBtnGiveUp.setOnClickListener {
            mHandlerRndButton.removeCallbacks(mRunnableRndButton)
            dialog!!.dismiss()
        }

        mRunnableRndButton.run()
    }

    private fun setScore(view: View) {
        //todo first win only

        val s = view.tag.toString().toInt()
        if (s == 1) {
            val num = mTvScore.text.toString().toInt() + 1
            mTvScore.text = num.toString()
        } else if (s == 0) {
            var num = mTvScore.text.toString().toInt()
            if (num <= 0) {
                mTvScore.text = "0"
            } else {
                num -= 1
                mTvScore.text = num.toString()
            }
        }
    }

    private val mRunnableRndButton = object : Runnable {
        override fun run() {
            mHandlerRndButton.postDelayed(this, 600)
            rndFightGame()
        }
    }

    private fun rndFightGame() {
        setButtonBackground(mBtnOne, 0)
        setButtonBackground(mBtnTwo, 0)
        setButtonBackground(mBtnThree, 0)
        setButtonBackground(mBtnFour, 0)
        setButtonBackground(mBtnFive, 0)
        setButtonBackground(mBtnSix, 0)
        setButtonBackground(mBtnSeven, 0)
        setButtonBackground(mBtnEight, 0)
        setButtonBackground(mBtnNine, 0)

        var num = 0
        while (num == 0) {
            num = (Math.random() * 10).toInt()
        }

        when (num) {
            1 -> setButtonBackground(mBtnOne, 1)
            2 -> setButtonBackground(mBtnTwo, 1)
            3 -> setButtonBackground(mBtnThree, 1)
            4 -> setButtonBackground(mBtnFour, 1)
            5 -> setButtonBackground(mBtnFive, 1)
            6 -> setButtonBackground(mBtnSix, 1)
            7 -> setButtonBackground(mBtnSeven, 1)
            8 -> setButtonBackground(mBtnEight, 1)
            9 -> setButtonBackground(mBtnNine, 1)
        }
    }

    private fun setButtonBackground(button: Button, image: Int) {
        val drawable: Int
        if (image == 0) {
            drawable = R.drawable.shape_btn_fight_gray
            button.tag = 0
        } else {
            drawable = R.drawable.shape_btn_fight_game
            button.tag = 1
        }
        button.background = ContextCompat.getDrawable(context!!, drawable)
    }
}
