package com.adedom.theegggame.ui.single

import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.*
import com.adedom.library.util.GoogleMapActivity
import com.adedom.library.util.pauseMusic
import com.adedom.theegggame.R
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playMusicGame
import com.adedom.theegggame.util.extension.setSoundMusic
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_map.*

class SingleActivity : GoogleMapActivity(R.id.mapFragment, 5000) {

    private lateinit var viewModel: SingleActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        viewModel = ViewModelProviders.of(this).get(SingleActivityViewModel::class.java)

        init()

        viewModel.itemBonus = 0
    }

    override fun onResume() {
        super.onResume()
        viewModel.switchItem = GameSwitch.ON

        sContext.playMusicGame()
    }

    override fun onPause() {
        super.onPause()
        viewModel.switchItem = GameSwitch.OFF

        pauseMusic()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_map, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_sound_music) {
            sContext.setSoundMusic()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.single_player), true)

        mFloatingActionButton.setOnClickListener {
            sContext.completed()

            //todo backpack item
        }
    }

    override fun onActivityRunning() {
        if (viewModel.switchItem == GameSwitch.ON) {
            viewModel.switchItem = GameSwitch.OFF
            Item(sGoogleMap, viewModel.single, viewModel.markerItems)
        }

        viewModel.rndMultiItem(sLatLng)

        viewModel.rndItemBonus(sLatLng)

        viewModel.checkRadius(sLatLng) { keepItemSingle(it) }

    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)

        sContext.setLocality(mTvLocality, sLatLng)

        Player(sContext, sGoogleMap, sLatLng)

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        sGoogleMap!!.setMarkerConstant(druBkk, druIcon, DRU_TITLE, DRU_SNIPPET)
        sGoogleMap!!.setMarkerConstant(druSp, druIcon, DRU_TITLE, DRU_SNIPPET)
    }

    private fun keepItemSingle(index: Int) {
        val playerId = this.readPrefFile(KEY_PLAYER_ID)
        val (myItem, values) = viewModel.getItemValues(index)
        val lat = sLatLng.latitude
        val lng = sLatLng.longitude
        viewModel.keepItemSingle(playerId, myItem, values, lat, lng)
            .observe(this, Observer {
                if (it.result == KEY_COMPLETED) {
                    sContext.toast(viewModel.detailItem(myItem, values))
                }
            })
    }

}
