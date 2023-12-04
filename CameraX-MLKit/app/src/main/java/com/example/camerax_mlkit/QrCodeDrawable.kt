/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.camerax_mlkit

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.MotionEvent
import android.view.View
import com.google.mlkit.vision.barcode.common.Barcode

/**
 * A Drawable that handles displaying a QR Code's data and a bounding box around the QR code.
 */
class QrCodeDrawable(qrCodeViewModel: QrCodeViewModel) : Drawable() {
    private val boundingRectPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.YELLOW
        strokeWidth = 5F
        alpha = 200
    }

    private val contentRectPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
        alpha = 255
    }

    private val contentTextPaint = Paint().apply {
        color = Color.DKGRAY
        alpha = 255
        textSize = 36F
    }

    private val qrCodeViewModel = qrCodeViewModel
    private val contentPadding = 25
    private var textWidth = contentTextPaint.measureText(qrCodeViewModel.qrContent).toInt()

    override fun draw(canvas: Canvas) {
//        canvas.drawRect(qrCodeViewModel.boundingRect, boundingRectPaint)
//        canvas.drawRect(
//            Rect(
//                qrCodeViewModel.boundingRect.left,
//                qrCodeViewModel.boundingRect.bottom + contentPadding/2,
//                qrCodeViewModel.boundingRect.left + textWidth + contentPadding*2,
//                qrCodeViewModel.boundingRect.bottom + contentTextPaint.textSize.toInt() + contentPadding),
//            contentRectPaint
//        )
//        canvas.drawText(
//            qrCodeViewModel.qrContent,
//            (qrCodeViewModel.boundingRect.left + contentPadding).toFloat(),
//            (qrCodeViewModel.boundingRect.bottom + contentPadding*2).toFloat(),
//            contentTextPaint
//        )
        drawBoundaries(canvas)
    }

    override fun setAlpha(alpha: Int) {
        boundingRectPaint.alpha = alpha
        contentRectPaint.alpha = alpha
        contentTextPaint.alpha = alpha
    }

    override fun setColorFilter(colorFiter: ColorFilter?) {
        boundingRectPaint.colorFilter = colorFilter
        contentRectPaint.colorFilter = colorFilter
        contentTextPaint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    fun drawBoundaries(canvas: Canvas) {

        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        paint.isAntiAlias = true

        // Adjust according to your requirements..
        val length = canvas.width * 0.25f
        val corner = length * 0.25f
        val left = 10f
        val top = 10f
        val right = canvas.width - 10f
        val bottom = canvas.height - 10f

        val path = Path()

        // Top-Left corner..
        path.moveTo(left, top + length)
        path.lineTo(left, top + corner)
        path.cubicTo(left, top + corner, left, top, left + corner, top)
        path.lineTo(left + length, top)

        // Top-Right corner..
        path.moveTo(right - length, top)
        path.lineTo(right - corner, top)
        path.cubicTo(right - corner, top, right, top, right, top + corner)
        path.lineTo(right, top + length)

        // Bottom-Right corner..
        path.moveTo(right, bottom - length)
        path.lineTo(right, bottom - corner)
        path.cubicTo(right, bottom - corner, right, bottom, right - corner, bottom)
        path.lineTo(right - length, bottom)

        // Bottom-Left corner..
        path.moveTo(left + length, bottom)
        path.lineTo(left + corner, bottom)
        path.cubicTo(left + corner, bottom, left, bottom, left, bottom - corner)
        path.lineTo(left, bottom - length)

        // Draw path..
        canvas.drawPath(path, paint)
    }
}