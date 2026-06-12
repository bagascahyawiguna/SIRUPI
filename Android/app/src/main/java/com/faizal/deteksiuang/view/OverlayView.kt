package com.faizal.deteksiuang.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.faizal.deteksiuang.ml.YoloResult

class OverlayView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paintBox = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    private val paintText = Paint().apply {
        color = Color.WHITE
        textSize = 42f
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private var results: List<YoloResult> = emptyList()

    fun setResults(res: List<YoloResult>) {
        results = res
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (r in results) {
            canvas.drawRect(r.box, paintBox)
            canvas.drawText(
                "${r.label} ${(r.confidence * 100).toInt()}%",
                r.box.left,
                r.box.top - 10,
                paintText
            )
        }
    }
}
