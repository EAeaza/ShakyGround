package com.example.appv_3

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.appv_3.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)



        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS.toList())
            }
        }
        return view
    }

    //
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable the back button on the app bar.
        // This allows the user to navigate back to the previous fragment or activity.
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val videoView = view.findViewById<VideoView>(R.id.video_item)
        val vadeUri = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.video)
        videoView.setVideoURI(vadeUri)

        // Add controls to the player
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.requestFocus()

    }

        //
//        val videoView = view.findViewById<VideoView>(R.id.video_item)
//        val vadeUri = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.video)
//        videoView.setVideoURI(vadeUri)
//
//        // Add controls to the player
//        val mediaController = MediaController(requireContext())
//        mediaController.setAnchorView(videoView)
//        videoView.setMediaController(mediaController)
//        videoView.requestFocus()



    override fun onDestroyView() {
        super.onDestroyView()

        // Disable the back button on the app bar when leaving the settings fragment.
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }
    //

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}