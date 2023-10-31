package com.quizeu.appitaly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import com.quizeu.appitaly.R

class OptionsAdapter(
    context: Context,
    private val options: List<String>
) : ArrayAdapter<String>(context, R.layout.custom_radio_button, options) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var radioButton = convertView
        if (radioButton == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            radioButton = inflater.inflate(R.layout.custom_radio_button, null)
        }

        val option = options[position]
        val radioButtonView = radioButton!!.findViewById<RadioButton>(R.id.radioButton)
        radioButtonView.text = option
        return radioButton!!
    }
}

