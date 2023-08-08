package mqtt.packets.mqttv4

import mqtt.packets.MQTTControlPacketType
import mqtt.packets.MQTTDeserializer
import mqtt.packets.mqtt.MQTTDisconnect
import socket.streams.ByteArrayOutputStream

public class MQTT4Disconnect : MQTTDisconnect(), MQTT4Packet {

    override fun toByteArray(): UByteArray {
        return ByteArrayOutputStream().wrapWithFixedHeader(MQTTControlPacketType.DISCONNECT, 0)
    }

    public companion object : MQTTDeserializer {

        override fun fromByteArray(flags: Int, data: UByteArray): MQTT4Disconnect {
            checkFlags(flags)
            return MQTT4Disconnect()
        }
    }
}