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
import kotlin.properties.Delegates


/**
 * @author BaoQi
 * Date : 2021/3/7
 *
 */
//https://juejin.cn/post/6844903509263908871
//https://lumeng.blog.csdn.net/article/details/109075416
class ScaleMap(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val gestureDetector: GestureDetector
    private var mapBitmap: Bitmap by Delegates.observable(
        BitmapFactory.decodeResource(
            context.resources,
            R.mipmap.scale
        )
    ) { _, _, _ ->
        invalidate()
    }
    private val pointBitmap: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
    private val mScaleDrawMatrix: Matrix = Matrix()
    private var scaleGestureDetector: ScaleGestureDetector
    private var scaleValue: Float = 1.0f
    private var width: Float by Delegates.notNull()
    private var height: Float by Delegates.notNull()

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

                    mScaleDrawMatrix.postScale(
                        scaleFactor,
                        scaleFactor,
                        0f,
                        0f
                    )
                    val matrixValues = FloatArray(9)
                    mScaleDrawMatrix.getValues(matrixValues)
                    scaleValue = matrixValues[0]
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
                    //先平移，再缩放，并且平移的大小要除以当前缩放
                    mScaleDrawMatrix.preTranslate(
                        -distanceX / scaleValue,
                        -distanceY / scaleValue
                    )
                    invalidate()
                    return true
                }
            })
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mapBitmap?.let { drawMap(canvas) }


    }

    private fun printMatrix(matrix: Matrix) {
        // 下面的代码是为了查看matrix中的元素
        val matrixValues = FloatArray(9)
        matrix.getValues(matrixValues)
        for (i in 0..2) {
            var temp = String()
            for (j in 0..2) {
                temp += matrixValues[3 * i + j].toString() + "\t"
            }
            Log.e(this@ScaleMap.javaClass.simpleName, "printMatrix():temp=$temp")
        }
        Log.d(this@ScaleMap.javaClass.simpleName, "printMatrix()")
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
        width = dest.width()
        height = dest.height()
        canvas.concat(mScaleDrawMatrix)
        printMatrix(mScaleDrawMatrix)
        canvas.drawBitmap(mapBitmap, src, dest, null)
        val pointF = offset()
        canvas.translate(pointF.x, pointF.y)
        if (scaleValue > 1) {
            canvas.scale(1 / scaleValue, 1 / scaleValue)

        }

        canvas.drawBitmap(pointBitmap, -pointBitmap.width / 2f, -pointBitmap.height / 2f, null)
        canvas.restore()
    }

    private fun offset(): PointF {
        val pointF = PointF()
        pointF.x = (0.75f * width - width / 2f)
        pointF.y = (0.75f * height - height / 2f)
        return pointF
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }


}

class CoordinateTool {

    companion object {
        /**
         * 转换slam坐标至地图像素坐标
         *
         * @param {slamOffset} slam坐标x  137,296
         * @param {number} height 所在地图高度
         * @param {Coordinate} origin 地图原点信息
         * @param {number} resolution 地图分辨率
         */
        fun slam2pixel(
            slamOffset: PointF,
            origin: PointF,
            height: Float = 658f,
            resolution: Float = 0.05f
        ): PointF {
            if (resolution <= 0) {
                return PointF(0f, 0f)
            }
            val offset = PointF(
                (slamOffset.x - origin.x) / resolution,
                height - (slamOffset.y - origin.y) / resolution
            );
            Log.d("CoordinateTool", "slam2pixel():offset=$offset")
            return offset
        }
    }

}
