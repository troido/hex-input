package com.troido.hexinput.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aconno.hexinputlib.setContentViewWithHexKeyboardAutoAdded

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithHexKeyboardAutoAdded(R.layout.activity_main,true)
    }
}