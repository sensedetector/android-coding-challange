package com.shiftkey.codingchallenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shiftkey.codingchallenge.R
import com.shiftkey.codingchallenge.util.EndlessScrollListener

class ShiftListFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShiftsAdapter

    private val viewModel by activityViewModels<ShiftsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.shift_list_fragment, container, false)
        adapter = ShiftsAdapter { shiftId ->
            viewModel.selectShift(shiftId)
            view.findNavController().navigate(R.id.action_shiftListFragment_to_shiftDetailFragment)
        }

        recyclerView = view.findViewById<RecyclerView?>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ShiftListFragment.adapter
            addOnScrollListener(EndlessScrollListener(layoutManager as LinearLayoutManager) {
                viewModel.fetchShifts()
            })
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeShifts()
        viewModel.fetchShifts()
    }

    private fun observeShifts() {
        viewModel.shifts.observe(viewLifecycleOwner) {
            adapter.setShifts(it)
        }
    }
}
