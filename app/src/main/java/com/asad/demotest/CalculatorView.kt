package com.asad.demotest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

enum class ACTION {
    ADD, SUB, MUL, DIV, MEAN, EQUAL
}
//Calculator screen view composable
@Composable
fun CalculatorView(
) {
    val categories = arrayListOf<String>(
        "7", "8", "9", "/", "4", "5",
        "6", "-", "1", "2", "3", "+", "0", ".", "C", "*"
    )
    val isMean : MutableState<Boolean> = remember { mutableStateOf(false) }
    val isMedian : MutableState<Boolean> = remember { mutableStateOf(false) }
    val firstOperator: MutableState<String> = remember { mutableStateOf("") }
    val secondOperator: MutableState<String> = remember { mutableStateOf("") }
    val totalResult: MutableState<String> = remember { mutableStateOf("") }
    val firstValue: MutableState<String> = remember { mutableStateOf("") }
    val secondValue: MutableState<String> = remember { mutableStateOf("") }
    val meanTitle: MutableState<String> = remember { mutableStateOf("Mean") }
    val medianTitle: MutableState<String> = remember { mutableStateOf("Median") }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            //Header text
            Text(
                text = getHeading(isMean,isMedian),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp)
            )

            //input values and operators views
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (isMean.value || isMedian.value)
                        "(${firstValue.value})"
                    else
                        firstValue.value,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp)
                )
                Text(
                    text = firstOperator.value,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp)
                )
                Text(
                    text = (secondValue.value),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp)
                )
            }

            //total value calculated view
            Text(
                text = "total : ${totalResult.value.toString()}",
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp)
            )

            //Constraint layout with numbers and operators inside it
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (medianBtn, equalBtn, meanBtn) = createRefs()
                val (ceBtn, gridView) = createRefs()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.constrainAs(gridView) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ) {
                    items(categories.size) { item ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            TextButton(
                                modifier = Modifier
                                    .padding(1.dp)
                                    .fillMaxWidth()
                                    .height(90.dp)
                                    .background(
                                        MaterialTheme.colors.primary,
                                        RoundedCornerShape(7.dp)
                                    ),
                                onClick = {
                                    buttonClicked(
                                        firstValue,
                                        secondValue,
                                        firstOperator,
                                        secondOperator,
                                        totalResult,
                                        categories,
                                        item,
                                        isMean,
                                        isMedian,
                                        meanTitle,
                                        medianTitle
                                    )
                                },
                                // Uses ButtonDefaults.ContentPadding by default
                                contentPadding = PaddingValues(
                                    start = 20.dp,
                                    top = 12.dp,
                                    end = 20.dp,
                                    bottom = 12.dp
                                ),
                            ) {
                                Text(
                                    categories[item],
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }

                    }
                }

                //Mean Button allows to add values to  calculate median
                TextButton(
                    modifier = Modifier
                        .padding(1.dp)
                        .height(90.dp)
                        .constrainAs(meanBtn) {
                            top.linkTo(gridView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(medianBtn.start)
                            width = Dimension.fillToConstraints
                        }
                        .background(
                            MaterialTheme.colors.primary, RoundedCornerShape(7.dp)
                        ),
                    onClick = {
                        if (!isMean.value) {
                            firstOperator.value = ""
                            secondValue.value = ""
                            isMean.value = true
                            meanTitle.value = ","
                            firstValue.value = ""
                            isMedian.value = false
                            medianTitle.value = "Median"
                        }else{
                            addValues(
                                firstValue
                            )
                        }
                    },
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                ) {
                    Text(
                        meanTitle.value,
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                //Median Button allows to add values to  calculate mean
                TextButton(
                    modifier = Modifier
                        .padding(1.dp)
                        .height(90.dp)
                        .constrainAs(medianBtn) {
                            top.linkTo(gridView.bottom)
                            start.linkTo(meanBtn.end)
                            end.linkTo(equalBtn.start)
                            width = Dimension.fillToConstraints
                        }
                        .background(
                            MaterialTheme.colors.primary, RoundedCornerShape(7.dp)
                        ),
                    onClick = {
                        if (!isMedian.value) {
                            firstOperator.value = ""
                            secondValue.value = ""
                            isMedian.value = true
                            medianTitle.value = ","
                            firstValue.value = ""
                            isMean.value = false
                            meanTitle.value = "Mean"
                            firstValue.value = ""
                        }else{
                            addValues(
                                firstValue
                            )
                        }
                    },
                    // Uses ButtonDefaults.ContentPadding by default
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                ) {
                    Text(
                        medianTitle.value,
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                //Equals Button
                TextButton(
                    modifier = Modifier
                        .padding(1.dp)
                        .height(90.dp)
                        .constrainAs(equalBtn) {
                            top.linkTo(gridView.bottom)
                            start.linkTo(medianBtn.end)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .background(
                            MaterialTheme.colors.primary, RoundedCornerShape(7.dp)
                        ),
                    onClick = {
                        if (!isMean.value && !isMedian.value) {
                            performOperator(
                                firstValue,
                                secondValue,
                                firstOperator,
                                secondOperator,
                                totalResult,
                                "="
                            )
                        }else{
                            performMeanOrMedian(firstValue,isMean,isMedian,totalResult)
                        }
                    },
                    // Uses ButtonDefaults.ContentPadding by default
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                ) {
                    Text(
                        "=",
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}


//executed when button from lazyverticalgird is pressed
fun buttonClicked(
    firstValue: MutableState<String>,
    secondValue: MutableState<String>,
    firstOperator: MutableState<String>,
    secondOperator: MutableState<String>,
    totalResult: MutableState<String>,
    categories: ArrayList<String>,
    item: Int,
    isMean: MutableState<Boolean>,
    isMedian: MutableState<Boolean>,
    meanTitle: MutableState<String>,
    medianTitle: MutableState<String>
) {
    if ((!isMean.value && !isMedian.value) || categories[item] == "C") {
        when (categories[item]) {
            in "/-*+"  -> {
                performOperator(
                    firstValue,
                    secondValue,
                    firstOperator,
                    secondOperator,
                    totalResult,
                    categories[item]
                )
            }
            "C" -> {
                firstValue.value = ""
                secondValue.value = ""
                secondOperator.value = ""
                firstOperator.value = ""
                totalResult.value = ""
                isMedian.value = false
                medianTitle.value = "Median"
                isMean.value = false
                meanTitle.value = "Median"
            }
            else -> {
                setValue(firstValue, secondValue, categories[item],
                    firstOperator, secondOperator,isMean,isMedian)
            }
        }
    }else if(isMean.value){
        if (getValidation(firstValue,categories,item)) {
            setValue(firstValue, secondValue, categories[item],
                firstOperator, secondOperator,isMean,isMedian)
        }
    }else if(isMedian.value){
        if (getValidation(firstValue,categories,item)) {
            setValue(firstValue, secondValue, categories[item],
                firstOperator, secondOperator,isMean,isMedian)
        }
    }
}

//populates value selected from calculator which is to be processed
fun setValue(
    firstValue: MutableState<String>,
    secondValue: MutableState<String>,
    s: String,
    firstOperator: MutableState<String>,
    secondOperator: MutableState<String>,
    isMean: MutableState<Boolean>,
    isMedian: MutableState<Boolean>
) {
    if (!isMean.value && !isMedian.value) {
        if (firstOperator.value.isEmpty()) {
            firstValue.value += s
        } else {
            secondValue.value += s
        }
    }else {
        firstValue.value += s
    }
}

//Choose which arithmetic function to perform based on entered operator
fun performOperator(
    firstValue: MutableState<String>,
    secondValue: MutableState<String>,
    firstOperator: MutableState<String>,
    secondOperator: MutableState<String>,
    totalResult: MutableState<String>,
    operator: String
) {
    if (operator != "=") {
        if (firstOperator.value.isEmpty()) {
            firstOperator.value = operator
            if (firstValue.value.isEmpty()) {
                firstValue.value = "0"
            }
        } else {
            if (secondValue.value.isEmpty()) {
                firstOperator.value = operator
            } else {
                secondOperator.value = operator
                performAction(
                    firstValue,
                    secondValue,
                    firstOperator,
                    secondOperator,
                    totalResult,
                    operator
                )
            }
        }
    } else if (firstValue.value.isNotEmpty() && secondValue.value.isNotEmpty()) {
        performAction(
            firstValue,
            secondValue,
            firstOperator,
            secondOperator,
            totalResult,
            operator
        )
    } else if (firstValue.value.isNotEmpty()) {
        totalResult.value = firstValue.value
        firstValue.value = ""
    }
}

//Performs basic arithmetic functions Add/Subtract/Multiply/Divide
fun performAction(
    firstValue: MutableState<String>,
    secondValue: MutableState<String>,
    firstOperator: MutableState<String>,
    secondOperator: MutableState<String>,
    totalResult: MutableState<String>,
    operator: String
) {
    var first = firstValue.value.toFloat()
    var second = secondValue.value.toFloat()
    when (firstOperator.value) {
        "+" -> {
            totalResult.value = (first + second).toString()
        }
        "-" -> {
            totalResult.value = (first - second).toString()
        }
        "/" -> {
            totalResult.value = if (second==0f) {
                "0"
            }else{
                (first / second).toString()
            }
        }
        "*" -> {
            totalResult.value = (first * second).toString()
        }
    }
    firstValue.value = totalResult.value.toString()
    secondValue.value = ""
    firstOperator.value = secondOperator.value
    secondOperator.value = ""
}
