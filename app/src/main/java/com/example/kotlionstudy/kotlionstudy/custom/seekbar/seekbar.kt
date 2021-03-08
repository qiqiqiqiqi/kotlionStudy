package com.example.kotlionstudy.kotlionstudy.custom.seekbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import kotlin.math.*
import kotlin.properties.Delegates


/**
 * @author BaoQi
 * Date : 2021/3/6
 * Des:
 */
class CircularSeekBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr), ScaleGestureDetector.OnScaleGestureListener {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val mPaint: Paint = Paint()
    private val mCenterPoint: PointF = PointF()
    private val mPoint: PointF = PointF()
    private val mShowPaint: Paint = Paint()
    private val scaleGestureDetector: ScaleGestureDetector
    private val gestureDetector: GestureDetector
    private var mRadius by Delegates.notNull<Float>()
    private var mAngel = 135.0
    private val canvasBitmap by lazy {
        Bitmap.createBitmap(
            measuredWidth,
            measuredHeight,
            Bitmap.Config.ARGB_8888
        )
    }
    private val bitmapCanvas by lazy {
        Canvas(canvasBitmap)
    }

    private val mRingColors: IntArray = intArrayOf(
        Color.argb(0xFF, 0x00, 0xAD, 0xEF),
        Color.argb(0xFF, 0x10, 0xBD, 0xDE),
        Color.argb(0xFF, 0x3F, 0xCD, 0xB2),
        Color.argb(0xFF, 0xB1, 0xC0, 0x4F),
        Color.argb(0xFF, 0xF9, 0xB5, 0x12)
    )
    private val mRingColorsPosition: FloatArray =
        floatArrayOf(0.1875f, 0.375f, 0.5625f, 0.75f, 1.0f)

    init {
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.textSize = sp2px(18f)
        val typeFace = Typeface.createFromAsset(context.assets, "fonts/digital.ttf")
        mShowPaint.typeface = typeFace
        mShowPaint.textSize = sp2px(64f)
        mShowPaint.isAntiAlias = true
        mShowPaint.style = Paint.Style.FILL
        mShowPaint.color = Color.WHITE
        scaleGestureDetector = ScaleGestureDetector(context, this)
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onScroll(
                    e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float
                ): Boolean {
                    val calculatePosition = calculatePosition(PointF(e2.x, e2.y))
                    mPoint.set(calculatePosition)
                    invalidate()
                    return super.onScroll(e1, e2, distanceX, distanceY)
                }

            })


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mCenterPoint.x = measuredWidth / 2.0F
        mCenterPoint.y = measuredHeight / 2.0F
        mRadius = (measuredWidth - (paddingLeft + paddingRight)) / 2f
        mPoint.y = -mRadius

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        bitmapCanvas.save()
        bitmapCanvas.translate(
            mCenterPoint.x,
            mCenterPoint.y
        )
        canvas.save()
        drawScale(bitmapCanvas)
        drawRing(bitmapCanvas)
        drawCenterCircle(bitmapCanvas, canvasBitmap)
        drawPointer(bitmapCanvas, canvasBitmap)
        canvas.drawBitmap(canvasBitmap, 0f, 0f, null)
        canvas.restore()
        bitmapCanvas.restore()
    }

    private fun drawRing(canvas: Canvas) {
        canvas.save()
        canvas.rotate(-270f)
        val sweepGradient = SweepGradient(
            0f,
            0f,
            mRingColors,
            mRingColorsPosition
        )
        mPaint.style = Paint.Style.STROKE
        mPaint.shader = sweepGradient
        mPaint.strokeWidth = dp2px(20f)
        val ringRect = RectF(
            (-(measuredWidth - (paddingLeft + paddingRight)) / 2).toFloat(),
            (-(measuredHeight - (paddingTop + paddingBottom)) / 2).toFloat(),
            ((measuredWidth - (paddingLeft + paddingRight)) / 2).toFloat(),
            ((measuredHeight - (paddingTop + paddingBottom)) / 2).toFloat()
        )
        canvas.drawArc(ringRect, 45f, 270.0f, false, mPaint)
        mPaint.shader = null
        canvas.restore()

    }

    private fun drawScale(canvas: Canvas) {
        canvas.save()

        mPaint.style = Paint.Style.FILL
        canvas.rotate(-135f)//???
        val sweepGradient = SweepGradient(
            0f,
            0f,
            mRingColors,
            null
        )
        mPaint.shader = sweepGradient
        for (index in 10..30) {
            if (index % 5 == 0) {
                drawScaleText(canvas, index)
            } else {
                drawScaleLine(canvas, index)
            }
        }
        mPaint.shader = null
        canvas.restore()
    }

    private val drawScaleText = { canvas: Canvas, i: Int ->
        canvas.save()
        canvas.rotate((i - 10) * (270f / 20))
        val measureTextWidth: Float = mPaint.measureText("$i")
        canvas.drawText(
            "$i",
            -measureTextWidth / 2,
            -(mPaint.descent() - mPaint.ascent()) / 2
                    - ((measuredHeight - (paddingTop + paddingBottom)) / 2).toFloat()
                    - dp2px(10f),
            mPaint
        )
        canvas.restore()
    }
    private val drawScaleLine = { canvas: Canvas, i: Int ->
        canvas.save()
        canvas.rotate((i - 10) * (270f / 20))
        val bottom = -((measuredHeight - (paddingTop + paddingBottom)) / 2).toFloat() - dp2px(20f)
        val top = bottom - dp2px(10f)
        val left = -2f
        val right = +2f
        val rectF = RectF(left, top, right, bottom)
        canvas.drawRoundRect(rectF, 2f, 2f, mPaint)
        canvas.restore()
    }

    private val drawCenterCircle = { canvas: Canvas, bitmap: Bitmap ->
        canvas.save()
        val pixel = bitmap.getPixel(
            (mPoint.x + measuredWidth / 2f).toInt(),
            (mPoint.y + measuredWidth / 2f).toInt()
        )
        mPaint.style = Paint.Style.FILL
        mPaint.color = pixel
        canvas.drawCircle(
            0f,
            0f,
            ((measuredHeight - (paddingTop + paddingBottom)) / 2 * 0.65f),
            mPaint
        )
        val currentValue = (20 * mAngel / 270).roundToInt() + 10
        val measureTextWidth = mShowPaint.measureText("$currentValue℃")
        canvas.drawText(
            "$currentValue℃",
            -measureTextWidth / 2,
            -(mShowPaint.descent() + mShowPaint.ascent()) / 2,
            mShowPaint
        )
        canvas.restore()
    }

    private fun drawPointer(canvas: Canvas, bitmap: Bitmap) {
        canvas.save()
        val pixel = bitmap.getPixel(
            (mPoint.x + measuredWidth / 2f).toInt(),
            (mPoint.y + measuredWidth / 2f).toInt()
        )
        mPaint.color = pixel
        canvas.drawCircle(mPoint.x, mPoint.y, dp2px(15f), mPaint)
        canvas.restore()

    }


    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {

        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }

    fun calculatePosition(touchPointF: PointF): PointF {
        val pointF = PointF()
        val dx = touchPointF.x - mCenterPoint.x
        val dy = touchPointF.y - mCenterPoint.y
        if ((dx != 0f)) {
            val tan = dy / dx
            var angle = atan(tan).absoluteValue.toDouble()
            when {
                (dx > 0f) and (dy >= 0f) -> {
                    if (angle >= PI / 4) {
                        angle = PI / 4
                    }
                    mAngel = 225 + angle * 180 / PI
                    pointF.x = (mRadius * cos(angle)).toFloat()
                    pointF.y = (mRadius * sin(angle)).toFloat()

                }
                (dx > 0f) and (dy < 0f) -> {

                    mAngel = 225 - angle * 180 / PI
                    pointF.x = (mRadius * cos(angle)).toFloat()
                    pointF.y = -(mRadius * sin(angle)).toFloat()

                }
                (dx < 0f) and (dy >= 0f) -> {
                    if (angle >= PI / 4) {
                        angle = PI / 4
                    }
                    mAngel = 45 - angle * 180 / PI
                    pointF.x = -(mRadius * cos(angle)).toFloat()
                    pointF.y = (mRadius * sin(angle)).toFloat()

                }
                (dx < 0f) and (dy < 0f) -> {
                    mAngel = 45 + angle * 180 / PI
                    pointF.x = -(mRadius * cos(angle)).toFloat()
                    pointF.y = -(mRadius * sin(angle)).toFloat()
                }
            }

        } else {
            pointF.x = 0f
            pointF.y = if (dy > 0) {
                mAngel = 0.0
                mRadius
            } else {
                mAngel = 135.0
                -mRadius
            }
        }
        if (mAngel > 270) {
            mAngel = 270.0
        }
        if (mAngel < 0) {
            mAngel = 0.0
        }
        Log.e(
            this.javaClass.simpleName,
            "calculatePosition():dx=$dx,dy=$dy,pointF=$pointF,mAngel=$mAngel"
        )
        return pointF
    }

    private fun dp2px(dp: Float): Float {
        val density: Float = context.resources.displayMetrics.density
        return dp * density
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     */
    private fun sp2px(sp: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return sp * fontScale
    }

}