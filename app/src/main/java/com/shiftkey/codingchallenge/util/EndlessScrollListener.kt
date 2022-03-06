package com.shiftkey.codingchallenge.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessScrollListener constructor(
    private val linearLayoutManager: LinearLayoutManager,
    private val action: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = linearLayoutManager.itemCount
        if (totalItemCount == 0) return

        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
        if (lastVisibleItem == totalItemCount - 1) {
            action()
        }
    }
}
