package ru.magomedcoder.askue.data.remote

import ru.magomedcoder.askue.data.remote.dto.ElectronicCounterDto
import ru.magomedcoder.askue.domain.model.ElectronicCounter

fun ElectronicCounterDto.toElectronicCounterDomain(): ElectronicCounter {
    return ElectronicCounter(devEui = dev_eui, SerN = SerN)
}