package com.faizal.deteksiuang.ml

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

data class DetectionResult(
    val label: String,
    val confidence: Float
)

class MoneyYoloDetector(context: Context) {

    private val labels = listOf("1k","2k","5k","10k","20k","50k","100k")
    private val inputSize = 640
    private val confThreshold = 0.25f

    private val interpreter: Interpreter

    init {
        val afd = context.assets.openFd("best_float32.tflite")
        val input = afd.createInputStream()
        val modelBytes = input.readBytes()

        val buffer = ByteBuffer.allocateDirect(modelBytes.size)
        buffer.order(ByteOrder.nativeOrder())
        buffer.put(modelBytes)
        buffer.rewind()

        interpreter = Interpreter(buffer)
    }

    fun detect(bitmap: Bitmap): DetectionResult? {

        // 🔥 RESIZE AJA (AMAN)
        val scaled = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)

        val inputBuffer =
            ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4)
        inputBuffer.order(ByteOrder.nativeOrder())

        for (y in 0 until inputSize) {
            for (x in 0 until inputSize) {
                val px = scaled.getPixel(x, y)
                inputBuffer.putFloat(((px shr 16) and 0xFF) / 255f)
                inputBuffer.putFloat(((px shr 8) and 0xFF) / 255f)
                inputBuffer.putFloat((px and 0xFF) / 255f)
            }
        }

        // output: [1, 11, 8400]
        val output = Array(1) { Array(11) { FloatArray(8400) } }
        interpreter.run(inputBuffer, output)

        val out = output[0]

        var bestScore = 0f
        var bestClass = -1

        for (i in 0 until 8400) {
            for (c in labels.indices) {
                val score = out[4 + c][i]
                if (score > bestScore) {
                    bestScore = score
                    bestClass = c
                }
            }
        }

        return if (bestScore >= confThreshold) {
            DetectionResult(labels[bestClass], bestScore)
        } else {
            null
        }
    }
}
