package com.troido.hexinput.ui.keyboard

interface KeyboardListener {
    fun onValueTyped(value : Char)
    fun onRemoveKeyDown()
    fun onRemoveKeyUp()
    fun onRemoveKeyLongPress()
}