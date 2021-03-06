package com.udacity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnRepeat
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    private var widthSize = resources.getDimension(R.dimen.download_button_width)
    private var heightSize = resources.getDimension(R.dimen.download_button_height)

    private var loadingButton: LoadingButton
    private lateinit var buttonLabel: String

    private lateinit var buttonAnimator: ValueAnimator
    private lateinit var circleAnimator: ValueAnimator

    private var loadingPercentage = 0f
    private var circleAngle = 0f

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { _, _, new ->
        when (new) {
            ButtonState.Loading -> {
                buttonFillAnimator()
                circleFillAnimator()
            }
            ButtonState.Completed -> {
                animationEnd()
            }
            else -> animationEnd()
        }
    }

    init {
        isClickable = true
        loadingButton = findViewById(R.id.download_button)
    }

    //public methods to set the state of the button
    fun setState(buttonState: ButtonState) {
        this.buttonState = buttonState
    }

    private fun ValueAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawButton(canvas)
        if (buttonState == ButtonState.Loading) {
            buttonLabel = resources.getString(R.string.button_loading)
            drawLoadingFill(canvas)
            drawLoadingCircle(canvas)
            drawText(canvas)

        } else {
            buttonLabel = resources.getString(R.string.button_label)
            drawText(canvas)
        }
    }

    private fun drawButton(canvas: Canvas) {
        val buttonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.colorPrimary, null)
        }
        canvas.drawRect(0f, 0f, widthSize, heightSize, buttonPaint)
    }

    //button animator
    private fun buttonFillAnimator() {
        buttonAnimator = ValueAnimator.ofFloat(0f, widthSize).apply {
            duration = 2000
            repeatCount = 3
            repeatMode = RESTART
            addUpdateListener { ValueAnimator ->
                loadingPercentage = ValueAnimator.animatedValue as Float
                //get the current animated value and then set to the property you want and then call invalidate() which will trigger onDraw again
                invalidate()
            }
        }
        buttonAnimator.disableViewDuringAnimation(loadingButton)
        buttonAnimator.start()
    }

    //circle animator
    private fun circleFillAnimator() {
        var repeatTimes = 0
        circleAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 2000
            repeatCount = 3
            repeatMode = RESTART
            addUpdateListener { ValueAnimator ->
                circleAngle = ValueAnimator.animatedValue as Float
                invalidate()
                //updateListener should only be used to extract the animatedValue
                // and invalidate the view so that next onDraw uses the stored animatedValue to draw the animation frame.
            }
        }
        circleAnimator.disableViewDuringAnimation(loadingButton)
        circleAnimator.start()
        circleAnimator.doOnRepeat {
            repeatTimes++
            if (repeatTimes == circleAnimator.repeatCount) {
                repeatTimes = 0
                buttonState = ButtonState.Completed
            }

        }
    }

    //draw method will use var "loadingPercentage" to update loading bar shape
    private fun drawLoadingFill(canvas: Canvas) {
        val loadingFillPaint = Paint().apply {
            color = resources.getColor(R.color.colorPrimaryDark, null)
        }
        canvas.drawRect(0f, 0f, loadingPercentage, heightSize, loadingFillPaint)
    }

    //draw method will use var "circleAngle" to update circle shape
    private fun drawLoadingCircle(canvas: Canvas) {
        val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.colorAccent, null)
        }
        canvas.drawArc((widthSize - 150f), (heightSize / 2) - 40f, (widthSize - 70f),
            (heightSize / 2) + 40f, 0f, circleAngle, true, circlePaint)
    }

    private fun drawText(canvas: Canvas) {
        val buttonTextPaint = Paint().apply {
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            textSize = 80.0f
            typeface = Typeface.create("", Typeface.BOLD)
            color = Color.WHITE
        }
        canvas.drawText(buttonLabel,
            widthSize / 2,
            heightSize / 2 - ((buttonTextPaint.descent() + buttonTextPaint.ascent()) / 2),
            buttonTextPaint)
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
//        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
//        val h: Int = resolveSizeAndState(
//            MeasureSpec.getSize(w),
//            heightMeasureSpec,
//            0
//        )
//        widthSize = w.toFloat()
//        heightSize = h.toFloat()
//        setMeasuredDimension(w, h)
//    }

    private fun animationEnd() {
        buttonLabel = resources.getString(R.string.button_label)
        buttonAnimator.end()
        circleAnimator.end()
        loadingPercentage = 0f
        circleAngle = 0f
        invalidate()
    }
}















