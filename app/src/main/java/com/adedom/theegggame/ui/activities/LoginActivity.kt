package com.adedom.theegggame.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.theegggame.data.repositories.PlayerRepository
import com.adedom.theegggame.ui.dialogs.RegisterPlayerDialog
import com.adedom.theegggame.ui.factories.LoginActivityFactory
import com.adedom.theegggame.ui.viewmodels.LoginActivityViewModel
import com.adedom.theegggame.util.BaseActivity
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() { // 2/12/19

    val TAG = "MyTag"
    private lateinit var mViewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val factory = LoginActivityFactory(PlayerRepository(PlayerApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(LoginActivityViewModel::class.java)

        init()
    }

    private fun init() {
        val username = this.getPrefLogin(USERNAME)
        mEdtUsername.setText(username)

        mBtnReg.setOnClickListener {
            RegisterPlayerDialog()
                .show(supportFragmentManager, null)
        }
        mBtnLogin.setOnClickListener { loginToMain() }
        mTvForgotPassword.setOnClickListener { BaseActivity.sContext.failed() }
    }

    private fun loginToMain() {
        // TODO: 20/05/2562 login one user only

        when {
            mEdtUsername.isEmpty(getString(R.string.error_username)) -> return
            mEdtPassword.isEmpty(getString(R.string.error_password)) -> return
        }

        val username = mEdtUsername.text.toString().trim()
        val password = mEdtPassword.text.toString().trim()

        mViewModel.getPlayerId(username, password).observe(this, Observer {
            if (it.playerId == null) {
                Log.d(TAG, ">>if")
//                mEdtPassword.text.clear()
                BaseActivity.sContext.toast(R.string.username_password_incorrect, Toast.LENGTH_LONG)
            } else {
                Log.d(TAG, ">>else")
                this.login(MainActivity::class.java, it.playerId, username)
            }
        })
    }

}
