package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.data.remote.dto.CurrentsDto
import ru.magomedcoder.askue.data.remote.dto.DeviceDto
import ru.magomedcoder.askue.data.remote.dto.PlaceAddressDto
import ru.magomedcoder.askue.domain.model.DeviceStatus

data class DeviceStatusResponse(
    val currents: CurrentsDto,
    val device: DeviceDto,
    val place_address: PlaceAddressDto
) {

    fun toDeviceStatusDomain(): DeviceStatus {
        return DeviceStatus(device = device.device, status = currents.State)
    }

}