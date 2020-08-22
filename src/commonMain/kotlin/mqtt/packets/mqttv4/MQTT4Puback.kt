package mqtt.packets.mqttv4

import mqtt.packets.MQTTControlPacketType
import mqtt.packets.MQTTDeserializer
import mqtt.packets.mqtt.MQTTPuback
import socket.streams.ByteArrayInputStream
import socket.streams.ByteArrayOutputStream

class MQTT4Puback(
    packetId: UInt
) : MQTTPuback(packetId) {

    companion object : MQTTDeserializer {

        override fun fromByteArray(flags: Int, data: UByteArray): MQTT4Puback {
            mqtt.packets.mqttv5.MQTT5Puback.checkFlags(flags)
            val inStream = ByteArrayInputStream(data)
            val packetId = inStream.read2BytesInt()
            return MQTT4Puback(packetId)
        }
    }

    override fun toByteArray(): UByteArray {
        val outStream = ByteArrayOutputStream()

        outStream.write2BytesInt(packetId)

        return outStream.wrapWithFixedHeader(MQTTControlPacketType.PUBACK, 0)
    }
}
