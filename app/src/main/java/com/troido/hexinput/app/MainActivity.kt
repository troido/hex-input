package com.troido.hexinput.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.troido.hexinput.formatter.HexFormatters
import com.troido.hexinput.formatter.PlainValuesHexFormatter
import com.troido.hexinput.setContentViewWithHexKeyboardAutoAdded
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithHexKeyboardAutoAdded(R.layout.activity_main,true)

        simple_hex_input.setFormatter(PlainValuesHexFormatter())

        hex_formatter.adapter = ArrayAdapter<HexFormatters.FormatterType>(
            this,
            android.R.layout.simple_spinner_item,
            HexFormatters.FormatterType.values()
        )
        hex_formatter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                demo_hex_input.setFormatter(PlainValuesHexFormatter())
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedFormatterType = HexFormatters.FormatterType.values()[position]
                val formatter = HexFormatters.getFormatter(selectedFormatterType)
                demo_hex_input.setFormatter(formatter)
            }
        }

        apply_limit.setOnClickListener {
            val limit = hex_values_limit.text.toString()
            val limitInt = limit.toIntOrNull() ?: Int.MAX_VALUE
            applyLimit(limitInt)

            applied_limit_value.text = if(limit.isEmpty()) {
                getString(R.string.no_value)
            } else {
                limit
            }
        }
    }

    private fun applyLimit(limit : Int) {
        // TODO
    }
}