package com.narvenia.submission2storyapp.ui.maps

import android.content.ContentValues.TAG
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.narvenia.submission2storyapp.R
import com.narvenia.submission2storyapp.data.Result
import com.narvenia.submission2storyapp.data.local.session.UserSession
import com.narvenia.submission2storyapp.data.remote.response.ListStoryItem
import com.narvenia.submission2storyapp.data.remote.response.LoginResult
import com.narvenia.submission2storyapp.databinding.ActivityMapsBinding
import com.narvenia.submission2storyapp.utils.ViewModelFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var _binding: ActivityMapsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapsViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var pref: UserSession
    private lateinit var user: LoginResult
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = UserSession(this)
        user = pref.getToken()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setMapStyle()

        viewModel.getMaps("Bearer ${user.token}").observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val mapsData = it.data.listStory
                        addManyMarker(mapsData!!)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            resources.getString(R.string.story_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setMapStyle() {
        try {
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, resources.getString(R.string.style_not_success))
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, resources.getString(R.string.style_not_found), exception)
        }
    }

    private fun addManyMarker(maps: List<ListStoryItem>) {
        maps.forEach {
            if (it.lat != null && it.lon != null) {
                val latLng = LatLng(it.lat, it.lon)
                mMap.addMarker(MarkerOptions().position(latLng).title(it.name))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            }
        }
        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}