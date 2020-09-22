package com.troido.hexinput.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.aconno.hexinputlib.formatter.HexFormatters
import com.aconno.hexinputlib.setContentViewWithHexKeyboardAutoAdded
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithHexKeyboardAutoAdded(R.layout.activity_main,true)

        hex_formatter.adapter = ArrayAdapter<HexFormatters.FormatterType>(
            this,
            android.R.layout.simple_spinner_item,
            HexFormatters.FormatterType.values()
        )
    }
}