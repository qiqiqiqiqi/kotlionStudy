package com.example.kotlionstudy.kotlionstudy.custom.scale

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.example.kotlionstudy.R

/**
 * @author BaoQi
 * Date : 2021/3/7
 * Des:https://juejin.cn/post/6844903509263908871
 */
class ScaleMap(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val gestureDetector: GestureDetector
    private val mapBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.scale)
    private val pointBitmap: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
    private val mSuppMatrix: Matrix = Matrix()
    private val mDrawMatrix: Matrix = Matrix()
    private var scaleGestureDetector: ScaleGestureDetector

    init {
        scaleGestureDetector = ScaleGestureDetector(
            context,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    Log.d(
                        this@ScaleMap.javaClass.simpleName,
                        "onScale():detector.currentSpan=${detector.currentSpan}" +
                                ",detector.currentSpanX=${detector.currentSpanX}" +
                                ",detector.currentSpanY=${detector.currentSpanY}" +
                                ",detector.scaleFactor=${detector.scaleFactor}"
                    )
                    val scaleFactor = detector.scaleFactor
                    if (java.lang.Float.isNaN(scaleFactor) || java.lang.Float.isInfinite(scaleFactor)) {
                        return false
                    }

                    mSuppMatrix.postScale(
                        scaleFactor,
                        scaleFactor,
                        measuredWidth / 2f,
                        measuredHeight / 2f
                    )
                    mDrawMatrix.set(mSuppMatrix)
                    invalidate()
                    return true
                }

                override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                    return true
                }

            })
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent?): Boolean {
                    return true
                }

                override fun onScroll(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {
                    Log.d(
                        this@ScaleMap.javaClass.simpleName,
                        "onScroll():e2.x=${e2.x},e2.y=${e2.y}"
                    )
                    mSuppMatrix.preTranslate(-distanceX, -distanceY)
                    mDrawMatrix.set(mSuppMatrix)
                    invalidate()
                    return super.onScroll(e1, e2, distanceX, distanceY)
                }
            })
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawMap(canvas)

    }

    private fun drawMap(canvas: Canvas) {
        canvas.save()
        canvas.translate(measuredWidth / 2f, measuredHeight / 2f)
        val mapBitmapWHRatio: Float = (mapBitmap.width.toFloat() / mapBitmap.height)
        val widgetWHRatio: Float = (measuredWidth.toFloat() / measuredHeight)
        val left: Float
        val top: Float
        val right: Float
        val bottom: Float
        if (mapBitmap.width <= measuredWidth && mapBitmap.height <= measuredHeight) {
            //图片内容在Widget的范围内
            left = -mapBitmap.width / 2f
            top = -mapBitmap.height / 2f
            right = mapBitmap.width / 2f
            bottom = mapBitmap.height / 2f
        } else if (mapBitmapWHRatio >= widgetWHRatio) {
            val mapBitmapW: Float = measuredWidth.toFloat()
            val mapBitmapH: Float = mapBitmapW / mapBitmapWHRatio
            left = -mapBitmapW / 2f
            top = -mapBitmapH / 2f
            right = mapBitmapW / 2f
            bottom = mapBitmapH / 2f
        } else {
            val mapBitmapH: Float = measuredHeight.toFloat()
            val mapBitmapW = mapBitmapH * mapBitmapWHRatio
            left = -mapBitmapW / 2f
            top = -mapBitmapH / 2f
            right = mapBitmapW / 2f
            bottom = mapBitmapH / 2f
        }
        val src = Rect(0, 0, mapBitmap.width, mapBitmap.height)
        val dest = RectF(left, top, right, bottom)
        canvas.concat(mDrawMatrix)
        canvas.drawBitmap(mapBitmap, src, dest, null)
        canvas.drawBitmap(pointBitmap, -pointBitmap.width / 2f, -pointBitmap.height / 2f, null)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

}

class CoordinateTool {

    companion object {
        /**
         * 转换slam坐标至地图像素坐标
         *
         * @param {slamOffset} slam坐标x
         * @param {number} height 所在地图高度
         * @param {Coordinate} origin 地图原点信息
         * @param {number} resolution 地图分辨率
         */
        fun slam2pixel(
            slamOffset: PointF,
            origin: PointF,
            height: Float,
            resolution: Float
        ): PointF {
            if (resolution <= 0) {
                return PointF(0f, 0f)
            }
            val offset = PointF(
                (slamOffset.x - origin.x) / resolution,
                height - (slamOffset.y - origin.y) / resolution
            );
            Log.d("CoordinateTool", "slam2pixel():offset=$offset");
            return offset;
        }
    }

}
