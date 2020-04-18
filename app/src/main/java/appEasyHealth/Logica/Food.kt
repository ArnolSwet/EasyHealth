package com.example.appEasyHealth

import android.media.Image

class Food constructor(private var name: String, private var calories: Float, private var image: Image, private var tipus: String) {

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return this.name
    }

    fun setCalories(calories: Float) {
        this.calories = calories
    }

    fun getCalories(): Float {
        return this.calories
    }

    fun setImage(image: Image) {
        this.image = image
    }

    fun getImage(): Image {
        return this.image
    }

    fun setTipus(tipus: String) {
        this.tipus = tipus
    }

    fun getTipus(): String {
        return this.tipus
    }

}