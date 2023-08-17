package com.example.marvelcharacters.core.utils

import android.graphics.Rect
import android.util.LayoutDirection
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridItemSpacingDecorator(
    private val horizontalSpacing: Int = 0,
    private val topSpacing: Int? = null,
    private val bottomSpacing: Int? = null,
    private val isPaginatedList: Boolean = false
) : RecyclerView.ItemDecoration() {

    private lateinit var layoutManager: GridLayoutManager

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        layoutManager = parent.layoutManager as GridLayoutManager

        val spanCount = layoutManager.spanCount

        val gridlayoutPrams = view.layoutParams as GridLayoutManager.LayoutParams
        val isLTR = view.resources.configuration.layoutDirection == LayoutDirection.LTR
        val pos = gridlayoutPrams.viewLayoutPosition

        val isFirstRow = pos < spanCount
        if (!isFirstRow) topSpacing?.let { outRect.top = it }

        val isLastRow: Boolean = state.itemCount - 1 - pos < spanCount
        if (isPaginatedList || !isLastRow) bottomSpacing?.let { outRect.bottom = it }

        if (spanCount > 1) addHorizontalSpacing(
            outRect,
            gridlayoutPrams.spanIndex,
            horizontalSpacing.div(2),
            isLTR
        )
    }

    private fun addHorizontalSpacing(
        outRect: Rect, spanIndex: Int, spacing: Int, isLTR: Boolean
    ) {
        when (spanIndex) {
            0 -> {
                if (isLTR) outRect.right = spacing
                else outRect.left = spacing
            }
            layoutManager.spanCount - 1 -> {
                if (isLTR) outRect.left = spacing
                else outRect.right = spacing
            }
            else -> {
                outRect.left = spacing.div(2)
                outRect.right = spacing.div(2)
            }
        }
    }

}