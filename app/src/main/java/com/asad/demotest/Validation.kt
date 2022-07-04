package com.asad.demotest

import androidx.compose.runtime.MutableState


//  perform validations for input in mean/median modes
//  does not allow minus sign at the right of values only allows
//  to add at the left of value
//  restrict adding operators while calculating mean/median

fun getValidation(firstValue: MutableState<String>, categories: ArrayList<String>, item: Int): Boolean {
    return !"*+/".contains(categories[item]) && !(categories[item] == "-" && firstValue.value.endsWith("-"))
            && !(categories[item] == "-" && !firstValue.value.endsWith(","))
}