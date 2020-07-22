package com.example.tmcommonkotlin

import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.databinding.BindingAdapter
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import java.lang.Math.round

object RxDatabinding {
    @BindingAdapter("rxClick")
    @JvmStatic
    fun rxClick(view: View, subject: Subject<Unit>) {
        view.setOnClickListener { view1: View? ->
            subject.onNext(Unit)
        }
    }

    @BindingAdapter("rxLongClick")
    @JvmStatic
    fun rxLongClick(
        view: View,
        subject: Subject<Unit>
    ) {
        view.setOnLongClickListener { view1: View? ->
            subject.onNext(Unit)
            true
        }
    }

    @BindingAdapter("rxTouch")
    @JvmStatic
    fun rxTouch(
        view: View,
        subject: Subject<MotionEvent?>
    ) {
        view.setOnTouchListener { view1: View?, motionEvent: MotionEvent? ->
            subject.onNext(motionEvent!!)
            true
        }
    }

    @BindingAdapter("rxText")
    @JvmStatic
    fun rxText(
        editText: EditText,
        subject: BehaviorSubject<String?>
    ) {
        // Initial value
        editText.setText(subject.value)

        // Text changes
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                subject.onNext(editable.toString())
            }
        })
    }

    @BindingAdapter("rxChecked")
    @JvmStatic
    fun rxChecked(
        compoundButton: CompoundButton,
        subject: BehaviorSubject<Boolean?>
    ) {

        // Initial value
        compoundButton.isChecked = subject.value!!

        // Switch checked changes
        compoundButton.setOnCheckedChangeListener { compoundButton1: CompoundButton?, checked: Boolean ->
            subject.onNext(
                checked
            )
        }
    }

    @BindingAdapter("rxItem")
    @JvmStatic
    fun rxItem(
        radioGroup: RadioGroup,
        subject: BehaviorSubject<String>
    ) {

        // Initial value
        for (i in 0 until radioGroup.childCount) {
            val button = radioGroup.getChildAt(i) as RadioButton
            if (button.text.toString() == subject.value) {
                button.isChecked = true
            }
        }

        // RadioGroup checked changes
        radioGroup.setOnCheckedChangeListener { radioGroup1: RadioGroup, checkedId: Int ->
            val checkedButton = radioGroup1.findViewById<RadioButton>(checkedId)
            val text = checkedButton.text.toString()
            subject.onNext(text)
        }
    }

    @BindingAdapter("rxProgress", "rxMin", "rxMax", requireAll = false)
    @JvmStatic
    fun rxProgress(
        seekBar: SeekBar,
        subject: BehaviorSubject<Int>,
        min: Int,
        max: Int
    ) {
        val step = max - min
        rxProgressWithStep(seekBar, subject, min, max, step)
    }

    @BindingAdapter("rxProgress", "rxMin", "rxMax", "rxStep", requireAll = false)
    @JvmStatic
    fun rxProgressWithStep(
        seekBar: SeekBar, subject: BehaviorSubject<Int>,
        min: Int, max: Int, step: Int
    ) {
        val calcProgress = { value: Float, inMin:Float, inMax:Float, outMin:Float, outMax:Float ->
            round(outMin + (outMax - outMin) * ((value - inMin) / (inMax - inMin)))
        }

        // Limit ranges
        if (subject.value!! < min) {
            subject.onNext(min)
        }
        if (subject.value!! > max) {
            subject.onNext(max)
        }
        val mappedProgress = calcProgress(subject.value.toFloat(), min.toFloat(), max.toFloat(), 0.0F, step.toFloat())

        // SeekBar progress changes
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    val unmappedProgress =
                        calcProgress(progress.toFloat(), 0.0F, step.toFloat(), min.toFloat(), max.toFloat())
                    subject.onNext(unmappedProgress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Initial value
        seekBar.max = step
        seekBar.progress = mappedProgress
    }
}