package com.roaa.ahmed_abu_elbukhari.data.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.GridLayoutManager
import com.roaa.ahmed_abu_elbukhari.ui.interfaces.IOrientation

class Orientation constructor(val context: Context) :IOrientation{

    private val orientation by lazy {
        context.resources.configuration.orientation
    }

    override fun getLayoutMangerByOrientation():GridLayoutManager{
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            orientationLandscape()
        }else{
            orientationPortrait()
        }
    }

    override fun orientationLandscape():GridLayoutManager{
        return GridLayoutManager(context,3)
    }


     override fun orientationPortrait():GridLayoutManager{
        return GridLayoutManager(context,2)
    }
}