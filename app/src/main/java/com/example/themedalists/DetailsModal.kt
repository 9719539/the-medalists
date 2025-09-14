package com.example.themedalists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.themedalists.models.Medalist
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsModal : BottomSheetDialogFragment() {
    companion object {
        val TAG = "Details Modal"

        fun newInstance(medalist: Medalist) = BottomSheetDialogFragment().apply {
            val fragment = DetailsModal()
            val bundle = Bundle()
            // pass data as arguments
            bundle.putParcelable("medalist", medalist)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.details_modal, container, false)

        // get medalist data
        val medalist = arguments?.getParcelable("medalist") as? Medalist

        // get text views
        val countryTextView = view.findViewById<TextView>(R.id.modalCountry)
        val iocCodeTextView = view.findViewById<TextView>(R.id.modalCode)
        val goldCountTextView = view.findViewById<TextView>(R.id.modalGolds)
        val silverCountTextView = view.findViewById<TextView>(R.id.modalSilvers)
        val bronzeCountTextView = view.findViewById<TextView>(R.id.modalBronzes)

        // populate the views with medalist data
        medalist?.let {
            countryTextView.text = it.country
            iocCodeTextView.text = it.iocCode
            goldCountTextView.text = "${it.goldMedals.toString()} gold medals"
            silverCountTextView.text = "${it.silverMedals.toString()} silver medals"
            bronzeCountTextView.text = "${it.bronzeMedals.toString()} bronze medals"
        }

        return view
    }
}