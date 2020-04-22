package com.nencer.thesieure.ext

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

/**
 * Created by nguyentrung on 2/23/19
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE


@Suppress("UNCHECKED_CAST")
fun <T : Fragment> FragmentManager.findFragmentByClass(clazz: Class<T>): T? {
    fragments.forEach {
        if (it.javaClass == clazz) {
            return it as? T
        }
    }
    return null
}

fun ImageView.setImageUrl(url: CharSequence?) {
    Glide.with(context).load(url).into(this)
}

fun Spinner.overrideClickListener(onClickListener: (View) -> Unit) {
    setOnTouchListener { view, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            onClickListener(view)
        }
        return@setOnTouchListener true
    }
}

fun ViewPager.setOnPageChangeListener(listener: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            listener(position)
        }
    })
}


fun <T> MutableLiveData<T?>.listen(owner: LifecycleOwner, onChanged: (T) -> Unit) {
    observe(owner, Observer {
        if (it == null) return@Observer
        onChanged.invoke(it)
        value = null
    })
}


fun Drawable.changeColor(color: Int) {
    when (this) {
        is ShapeDrawable -> paint.color = color
        is GradientDrawable -> setColor(color)
        is ColorDrawable -> setColor(color)
    }
}