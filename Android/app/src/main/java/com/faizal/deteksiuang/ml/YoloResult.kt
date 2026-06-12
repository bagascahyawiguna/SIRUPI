package com.faizal.deteksiuang.ml

import android.graphics.RectF

data class YoloResult(
    val label: String,
    val confidence: Float,
    val box: RectF
)
