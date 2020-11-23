package com.roaa.ahmed_abu_elbukhari.ui.interfaces

import androidx.recyclerview.widget.GridLayoutManager

interface IOrientation {
    fun getLayoutMangerByOrientation():GridLayoutManager
    fun orientationLandscape():GridLayoutManager
    fun orientationPortrait():GridLayoutManager
}