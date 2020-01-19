package com.adedom.theegggame.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.adedom.library.extension.dialogFragment
import com.adedom.library.extension.dialogNegative
import com.adedom.library.util.BaseDialogFragment
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.login.LoginActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.KEY_PLAYER
import com.adedom.theegggame.util.extension.loginSuccess

class SettingDialog : BaseDialogFragment<MainActivityViewModel>({ R.layout.dialog_setting }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        init(v)

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_setting, R.string.setting)
    }

    private fun init(view: View) {
        //todo sound

        //todo update name & profile

        val btChangePassword = view.findViewById(R.id.mBtChangePassword) as Button
        val btLogout = view.findViewById(R.id.mBtLogout) as Button
        val btExit = view.findViewById(R.id.mBtExit) as Button

        btChangePassword.setOnClickListener {
            dialog!!.dismiss()

            val bundle = Bundle()
            bundle.putParcelable(KEY_PLAYER, MainActivity.sPlayer)

            val dialog = ChangePasswordDialog()
            dialog.arguments = bundle
            dialog.show(activity!!.supportFragmentManager, null)
        }

        btLogout.setOnClickListener {
            activity!!.loginSuccess(
                LoginActivity::class.java,
                KEY_EMPTY,
                MainActivity.sPlayer.username!!
            )
        }

        btExit.setOnClickListener {
            AlertDialog.Builder(activity!!).dialogNegative(
                R.string.exit,
                R.string.exit_message,
                R.drawable.ic_exit
            ) { GameActivity.sActivity.finishAffinity() }
        }
    }
}
