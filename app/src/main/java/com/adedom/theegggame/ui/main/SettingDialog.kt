package com.adedom.theegggame.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.login.LoginActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.PLAYER
import com.adedom.utility.extension.dialog
import com.adedom.utility.extension.exitDialog
import com.adedom.utility.extension.login

class SettingDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_setting, null)

        init(view)

        return AlertDialog.Builder(activity!!).dialog(view, R.drawable.ic_setting, R.string.setting)
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
            bundle.putParcelable(PLAYER, MainActivity.sPlayer)

            val dialog = ChangePasswordDialog()
            dialog.arguments = bundle
            dialog.show(activity!!.supportFragmentManager, null)
        }

        btLogout.setOnClickListener {
            activity!!.login(
                LoginActivity::class.java,
                username = MainActivity.sPlayer.username!!
            )
        }

        btExit.setOnClickListener {
            AlertDialog.Builder(activity!!).exitDialog { GameActivity.sActivity.finishAffinity() }
        }
    }
}
