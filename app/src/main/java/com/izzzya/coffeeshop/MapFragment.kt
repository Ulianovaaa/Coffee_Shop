package com.izzzya.coffeeshop

import android.graphics.PointF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.izzzya.coffeeshop.classes.SessionManager
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MapFragment : Fragment() {
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<TextView>(R.id.headerTV).text = getString(R.string.on_map)

        MapKitFactory.initialize(requireContext())
        mapView = view.findViewById(R.id.mapView)

        mapView.map.move(
            CameraPosition(
                Point(SessionManager.locationsList[1].point!!.latitude, SessionManager.locationsList[0].point!!.longitude),
                /* zoom = */ 8.5f,
                /* azimuth = */ 150.0f,
                /* tilt = */ 30.0f
            )
        )

        for (item in SessionManager.locationsList){
            val placemark = mapView.map.mapObjects.addPlacemark().apply {
                geometry = Point(item.point!!.latitude, item.point!!.longitude)
                setIcon(ImageProvider.fromResource(requireContext(), R.drawable.coffee_ico))
                setText(item.name)
                setIconStyle(
                    IconStyle().apply {
                        anchor = PointF(0.5f, 1.0f)
                        scale = 0.25f
                        zIndex = 10f
                    }
                )
            }

        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}