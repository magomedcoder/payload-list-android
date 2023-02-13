package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.domain.model.DeviceStatus

data class DeviceMgmtResponse(
    val device: String,
    val result: Boolean
) {

    fun toDeviceStatusDomain(): DeviceStatus {
        return DeviceStatus(device = device, status = result)
    }

}