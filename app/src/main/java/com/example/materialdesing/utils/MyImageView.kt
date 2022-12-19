package com.example.materialdesing.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * для обратной совместимости добавляется подсказка @JvmOverloads constructor
 * в указанном конструкторе аргументы нужны для AppCompatImageView
 *
 */
class MyImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attributeSet, defStyle) {

    // для то чтобы сделать иконку приложения квадратной меняем высоту на ширину (widthMeasureSpec)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}