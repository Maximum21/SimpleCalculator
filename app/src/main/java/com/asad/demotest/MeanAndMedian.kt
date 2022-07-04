package com.asad.demotest

import androidx.compose.runtime.MutableState

//Inserts comma separated values for mean/median input and check for comma input validation
fun addValues(firstValue: MutableState<String>) {
    if ( !firstValue.value.endsWith(",")) {
        firstValue.value = "${firstValue.value},"
    }
}

//returns header for mean/median/normal modes
fun getHeading(mean: MutableState<Boolean>, median: MutableState<Boolean>): String {
    return if(mean.value){
        "Enter values to calculate mean separating different values using ,"
    }else if(median.value){
        "Enter values to calculate median separating different values using ,"
    }else{
        "Calcualte"
    }
}


//performs mean/median calculations and updates values in total field
fun performMeanOrMedian(
    firstValue: MutableState<String>,
    mean: MutableState<Boolean>,
    median: MutableState<Boolean>,
    totalResult: MutableState<String>
) {
    val temp = firstValue.value.split(",")
    if(mean.value){
        var result = 0f
        var count = 0
        for(item in temp){
            if(item.isNotEmpty()){
                result += item.toFloat()
                count++
            }
        }
        totalResult.value = (result/count).toString()
    }else if(median.value){
        val numberArray = arrayListOf<Float>()
        for(item in temp){
            if (item.isNotEmpty()) {
                numberArray.add(item.toFloat())
            }
        }
        numberArray.sort()
        val median = if (numberArray.size%2==1)  {
            (numberArray[numberArray.size/2]).toString()
        }else{
            ("${(numberArray[numberArray.size/2] + numberArray[(numberArray.size/2)-1])/2}")
        }
        totalResult.value = median
    }
}