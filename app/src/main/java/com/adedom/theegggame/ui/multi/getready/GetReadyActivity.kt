package com.adedom.theegggame.ui.multi.getready

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfoItem
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.MyGrid
import kotlinx.android.synthetic.main.activity_get_ready.*
import kotlinx.android.synthetic.main.item_rv_room.*

class GetReadyActivity : AppCompatActivity() { // 21/7/62

    val TAG = "GetReadyActivity"
    private lateinit var mViewModel: GetReadyActivityViewModel
    private lateinit var mRoom: Room
    private val mRoomInfoItem = arrayListOf<RoomInfoItem>()
    //    private val mHandlerRefresh = Handler()
//    private var mName = ""
//    private var mPeople = ""
//    private var mHeadRoom = ""
//    private var mReady = "0"
//    private var mIsPause = false
//    private var mIsStartGame = false

    companion object {
        var mNoRoom = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_ready)

        val factory = GetReadyActivityFactory(MultiRepository(MultiApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(GetReadyActivityViewModel::class.java)

//        mIsPause = true
//        mIsStartGame = true

        mRoom = intent.getParcelableExtra("room") as Room

        init()
    }

    private fun init() {
        toolbar.title = getString(R.string.multi_player)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mRecyclerView.also {
            it.layoutManager = GridLayoutManager(baseContext, 2)
            it.addItemDecoration(MyGrid(2, MyGrid.dpToPx(10, resources), true)) // init
        }

        mTvRoomNo.text = mRoom.room_no
        mTvName.text = mRoom.name
        mTvPeople.text = mRoom.people
        if (mRoom.status == "H") mBtnGo.text = getString(R.string.go)

//        mBtnGo.setOnClickListener { getReadyToStartGame() }
//        mImgTeamA.setOnClickListener { setTeam("A") }
//        mImgTeamB.setOnClickListener { setTeam("B") }
    }

//    override fun onResume() {
//        super.onResume()
//        mRunnableRefresh.run()
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    //    private fun getReadyToStartGame() {
//        if (mHeadRoom == "1") {
//            var count = 0
//            var teamA = 0
//            var teamB = 0
//            for (item in mRoomInfoItem) {
//                if (item.status_id == "0") {
//                    count += 1
//                }
//
//                if (item.team == "A") {
//                    teamA += 1
//                } else {
//                    teamB += 1
//                }
//            }
//
//            if (teamA == 0 || teamB == 0) {
//                baseContext.toast(R.string.least_one_person_per_team, Toast.LENGTH_LONG)
//                return
//            }
//
//            if (count <= 1 && mRoomInfoItem.size != 1) {
//                onReady()
//            } else {
//                baseContext.toast(R.string.player_not_ready,Toast.LENGTH_LONG)
//            }
//        } else {
//            onReady()
//        }
//    }
//
//    private fun onReady() {
//        mReady = if (mReady == "0") {
//            "1"
//        } else {
//            "0"
//        }
//
//        val sql = "UPDATE tbl_room_info \n" +
//                "SET status_id = '${mReady.trim()}'\n" +
//                "WHERE player_id = '${MainActivity.sPlayerItem.playerId}'"
//        MyConnect.executeQuery(sql)
//    }
//
//    private fun setTeam(team: String) {
//        val sql = "UPDATE tbl_room_info \n" +
//                "SET team = '${team.trim()}'\n" +
//                "WHERE player_id = '${MainActivity.sPlayerItem.playerId}'"
//        MyConnect.executeQuery(sql)
//    }
//
//    private fun deletePlayerRoomInfo(playerId: String) {
//        val sql = "DELETE FROM tbl_room_info \n" +
//                "WHERE player_id = '${playerId.trim()}'"
//        MyConnect.executeQuery(sql)
//
//        // TODO: 30/05/2562 finish this class
//    }
//
    override fun onBackPressed() {
//        if (mHeadRoom == "1") {
//            for ((room_no, playerId) in mRoomInfoItem) {
//                deletePlayerRoomInfo(playerId)
//            }
//        }
//        mHandlerRefresh.removeCallbacks(mRunnableRefresh)
//        deletePlayerRoomInfo(MainActivity.sPlayerItem.playerId!!)
//        finish()

        val playerId = MainActivity.sPlayerItem.playerId
        mViewModel.deletePlayer(mRoom.room_no!!, playerId!!).observe(this, Observer {
            if (it.result == "completed") {
                super.onBackPressed()
            }
        })

    }
//
//    override fun onPause() {
//        super.onPause()
//        if (mIsPause) {
//            mHandlerRefresh.removeCallbacks(mRunnableRefresh)
//        }
//    }
//
//    private val mRunnableRefresh = object : Runnable {
//        override fun run() {
//            feedRoomInfo()
//            mHandlerRefresh.postDelayed(this, 1000)
//        }
//    }
//
//    private fun feedRoomInfo() {
//        mRoomInfoItem.clear()
//        val sql =
//            "SELECT ri.room_no,p.id,p.name,p.image,p.level,ri.latitude,ri.longitude,ri.team,ri.status_id\n" +
//                    "FROM tbl_room_info AS ri , tbl_player AS p\n" +
//                    "WHERE ri.room_no = '$mNoRoom' AND ri.player_id = p.id\n" +
//                    "ORDER BY ri.id ASC"
//        MyConnect.executeQuery(sql, object : MyResultSet {
//            override fun onResponse(rs: ResultSet) {
//                while (rs.next()) {
//                    deletePlayer(rs.getString(2))
//                    val item = RoomInfoItem(
//                        rs.getString(1),
//                        rs.getString(2),
//                        rs.getString(3),
//                        rs.getString(4),
//                        rs.getInt(5),
//                        rs.getDouble(6),
//                        rs.getDouble(7),
//                        rs.getString(8),
//                        rs.getString(9)
//                    )
//                    mRoomInfoItem.add(item)
//                }
//                mRecyclerView.adapter = CustomAdapter()
//                startGame()
//            }
//        })
//    }
//
//    private fun deletePlayer(playerId: String) {
//        val sql = "SELECT COUNT(*) FROM tbl_room_info WHERE player_id = '${playerId.trim()}'"
//        MyConnect.executeQuery(sql, object : MyResultSet {
//            override fun onResponse(rs: ResultSet) {
//                if (rs.next()) {
//                    if (rs.getInt(1) > 1) {
//                        val sql = "DELETE FROM tbl_room_info " +
//                                "WHERE player_id = '${playerId.trim()}' LIMIT 1"
//                        MyConnect.executeQuery(sql)
//                    }
//                }
//            }
//        })
//    }
//
//    private fun startGame() {
//        var count = 0
//        for ((_, _, _, _, _, _, _, _, status_id) in mRoomInfoItem) {
//            if (status_id == "0") {
//                count += 1
//            }
//        }
//
//        // TODO: 23/05/2562 and room info > 0
//        if (count == 0) {
//            mIsPause = false
//            if (mIsStartGame) {
//                mIsStartGame = false
//
//                val sql = "UPDATE tbl_room \n" +
//                        "SET status_id = '0'\n" +
//                        "WHERE no = '${mNoRoom.trim()}'"
//                MyConnect.executeQuery(sql)
//
//                mHandlerRefresh.removeCallbacks(mRunnableRefresh)
//                startActivityForResult(Intent(baseContext, MultiActivity::class.java), 1111)
//                baseContext.toast(R.string.start_game)
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_CANCELED) {
//            deletePlayerRoomInfo(MainActivity.sPlayerItem.playerId!!)
//            finish()
//        }
//
//        if (requestCode == 1111 && resultCode == RESULT_OK) {
//            mIsStartGame = true
//            mRunnableRefresh.run()
//
//            val sqlRoom = "UPDATE tbl_room \n" +
//                    "SET status_id = '1'\n" +
//                    "WHERE no = '${mNoRoom.trim()}'"
//            MyConnect.executeQuery(sqlRoom)
//
//            val sqlRoomInfo = "UPDATE tbl_room_info \n" +
//                    "SET status_id = '0'\n" +
//                    "WHERE room_no = ${mNoRoom.trim()}"
//            MyConnect.executeQuery(sqlRoomInfo)
//
//            val numA = data!!.getStringExtra("values1")
//            val numB = data!!.getStringExtra("values2")
//
//            val score = "Team A : $numA\nTeam B : $numB"
//            baseContext.toast(score, Toast.LENGTH_LONG)
//        }
//    }
//
//    inner class CustomAdapter : RecyclerView.Adapter<CustomHolder>() {
//        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CustomHolder {
//            val view = LayoutInflater.from(viewGroup.context)
//                .inflate(R.layout.item_rv_player, viewGroup, false)
//            return CustomHolder(view)
//        }
//
//        override fun getItemCount(): Int {
//            return mRoomInfoItem.size
//        }
//
//        override fun onBindViewHolder(holder: CustomHolder, i: Int) {
//            if (mRoomInfoItem[i].team == "A") {
//                holder.mBgPlayer.background = ContextCompat.getDrawable(
//                    baseContext,
//                    R.drawable.shape_bg_player_red
//                )
//            } else {
//                holder.mBgPlayer.background = ContextCompat.getDrawable(
//                    baseContext,
//                    R.drawable.shape_bg_player_blue
//                )
//            }
//
//            if (i == 0) {
//                holder.mImgKing.visibility = View.VISIBLE
//            }
//
//            if (mRoomInfoItem[i].status_id == "0") {
//                holder.mImgReady.background = ContextCompat.getDrawable(
//                    baseContext,
//                    R.drawable.shape_oval_red
//                )
//            } else {
//                holder.mImgReady.background = ContextCompat.getDrawable(
//                    baseContext,
//                    R.drawable.shape_oval_green
//                )
//            }
//
//            if (mRoomInfoItem[i].image.isNotEmpty()) {
//                holder.mImgProfile.loadProfile(mRoomInfoItem[i].image)
//            }
//            holder.mTvMyName.text = mRoomInfoItem[i].name
//            holder.mTvLevel.text = "Level : ${mRoomInfoItem[i].level}"
//        }
//    }
//
//    inner class CustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val mBgPlayer: LinearLayout = itemView.mBgPlayer
//        val mImgProfile: ImageView = itemView.mImgProfile
//        val mTvMyName: TextView = itemView.mTvMyName
//        val mTvLevel: TextView = itemView.mTvLevel
//        val mImgReady: ImageView = itemView.mImgReady
//        val mImgKing: ImageView = itemView.mImgKing
//
//        init {
//            itemView.setOnLongClickListener(object : View.OnLongClickListener {
//                override fun onLongClick(p0: View?): Boolean {
//                    if (MainActivity.sPlayerItem.playerId != mRoomInfoItem[adapterPosition].playerId && mHeadRoom == "1") {
//                        dialogRoomInfo()
//                        return true
//                    }
//                    return false
//                }
//            })
//        }
//
//        private fun dialogRoomInfo() {
//            val bundle = Bundle()
//            bundle.putParcelable("data", mRoomInfoItem[adapterPosition])
//
//            val dialog = RoomInfoDialog()
//            dialog.arguments = bundle
//            dialog.show(supportFragmentManager, null)
//        }
//    }
}