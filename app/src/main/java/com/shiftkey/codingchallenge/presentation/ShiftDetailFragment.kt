package com.shiftkey.codingchallenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shiftkey.codingchallenge.R
import com.shiftkey.codingchallenge.domain.Shift

class ShiftDetailFragment : Fragment() {

    private val viewModel by activityViewModels<ShiftsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shift_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeDetails()
    }

    private fun observeDetails() {
        viewModel.selectedShift.observe(viewLifecycleOwner) {
            updateView(it)
        }
    }

    private fun updateView(shift: Shift) {
        view?.apply {
            findViewById<TextView>(R.id.title).text = shift.localizedSpecialty.name
            findViewById<TextView>(R.id.startDate).text = shift.startTime
            findViewById<TextView>(R.id.endDate).text = shift.endTime
            findViewById<TextView>(R.id.distance).text = shift.distance.toString()
            findViewById<TextView>(R.id.shiftKind).text = shift.shiftKind
            findViewById<TextView>(R.id.skillName).text = shift.skill.name
            findViewById<TextView>(R.id.skillName).setTextColor(shift.skill.color.toColorInt())
            findViewById<TextView>(R.id.covid).visibility = shift.covid.toVisibility()
            findViewById<TextView>(R.id.premium).visibility = shift.premium.toVisibility()
        }
    }

    private fun Boolean.toVisibility() = if (this) View.VISIBLE else View.GONE
}
