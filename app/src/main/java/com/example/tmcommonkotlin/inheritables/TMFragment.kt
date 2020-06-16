package com.example.grocerygo.activities_and_frags.Inheritables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class TMFragment(val layout:Int? =null): Fragment() {

    protected val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = if (layout!=null) {
            inflater.inflate(layout, container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}