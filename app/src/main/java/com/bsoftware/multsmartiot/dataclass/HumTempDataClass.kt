package com.bsoftware.multsmartiot.dataclass

data class HumTempDataClass(
    var humidity : Double = 0.00,
    var temperature : Double = 0.00,
    var status : Boolean = false
)
