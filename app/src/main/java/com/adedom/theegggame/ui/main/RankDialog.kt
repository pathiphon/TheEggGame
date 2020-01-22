package com.adedom.theegggame.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.adedom.library.extension.dialogFragment
import com.adedom.library.extension.recyclerVertical
import com.adedom.library.extension.textChanged
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.extension.playSoundClick

class RankDialog : BaseDialogFragment<MainActivityViewModel>({ R.layout.dialog_rank }) {

    private lateinit var mAdapter: RankAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        init(v)

        fetchPlayers()

        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_rank_small, R.string.rank)
    }

    @SuppressLint("WrongViewCast")
    private fun init(view: View) {
        val etSearch = view.findViewById(R.id.mEtSearch) as EditText
        val btRank10 = view.findViewById(R.id.mBtRank10) as Button
        val btRank50 = view.findViewById(R.id.mBtRank50) as Button
        val btRank100 = view.findViewById(R.id.mBtRank100) as Button
        val recyclerView = view.findViewById(R.id.mRecyclerView) as RecyclerView

        mAdapter = RankAdapter()

        recyclerView.recyclerVertical { it.adapter = mAdapter }

        etSearch.textChanged { fetchPlayers(it) }

        btRank10.setOnClickListener {
            fetchPlayers(limit = "10")
            GameActivity.sContext.playSoundClick()
        }
        btRank50.setOnClickListener {
            fetchPlayers(limit = "50")
            GameActivity.sContext.playSoundClick()
        }
        btRank100.setOnClickListener {
            fetchPlayers(limit = "100")
            GameActivity.sContext.playSoundClick()
        }
    }

    private fun fetchPlayers(search: String = "", limit: String = "") {
        viewModel.rank(search, limit).observe(this, Observer {
            mAdapter.setList(it)
        })
    }
}