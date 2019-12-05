package com.adedom.theegggame.ui.multi.room

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.ui.dialogs.createroom.CreateRoomDialog
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.multi.getready.GetReadyActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.MyGrid
import com.adedom.utility.toast
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : GameActivity() { // 5/12/19

    private lateinit var mViewModel: RoomActivityViewModel
    private lateinit var mAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val factory = RoomActivityFactory(
            MultiRepository(MultiApi())
        )
        mViewModel = ViewModelProviders.of(this, factory).get(RoomActivityViewModel::class.java)

        init()

        freshRoom()

        gameLoop = { freshRoom() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        toolbar.title = "Multi player"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAdapter = RoomAdapter()

        mRecyclerView.also {
            it.layoutManager = GridLayoutManager(baseContext, 2)
            it.addItemDecoration(MyGrid(2, MyGrid.dpToPx(10, resources), true))
            it.adapter = mAdapter
        }

        mFloatingActionButton.setOnClickListener {
            CreateRoomDialog().show(supportFragmentManager, null)
        }

        mAdapter.onItemClick = { room ->
            val playerId = MainActivity.sPlayerItem.playerId!!
            mViewModel.joinRoom(room.room_no!!, playerId).observe(this, Observer {
                if (it.result == "completed") {
                    startActivity(
                        Intent(baseContext, GetReadyActivity::class.java)
                            .putExtra("room", Room(null, room.room_no, room.name, room.people, "T"))
                    )
                } else {
                    baseContext.toast(R.string.full, Toast.LENGTH_LONG)
                }
            })
        }
    }

    private fun freshRoom() {
        //todo off room

        mViewModel.getRooms().observe(this, Observer {
            mAdapter.setList(it)
        })
    }

}
