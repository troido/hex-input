package com.troido.ui.keyboard

interface KeyboardListener {
    fun onValueTyped(value : Char)
    fun onRemoveKeyDown()
    fun onRemoveKeyUp()
    fun onRemoveKeyLongPress()
}