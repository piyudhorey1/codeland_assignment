package com.example.codeland

import android.app.Application
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import androidx.core.content.res.ResourcesCompat
import com.example.codeland.utils.PrefHelper

class CodelandApplication: Application() {

    init {
        instance = this
    }
    companion object {
        private var instance: Application? = null
        lateinit var prefHelper: PrefHelper


        var mLoadingAnimDrawable: AnimationDrawable? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

    }

    override fun onCreate() {
        super.onCreate()

        prefHelper = PrefHelper(this)

        mLoadingAnimDrawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.app_loader_animation_list, theme
        ) as AnimationDrawable?

    }

}