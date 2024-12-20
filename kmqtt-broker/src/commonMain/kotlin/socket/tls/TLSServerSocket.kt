package socket.tls

import mqtt.broker.Broker
import socket.ServerSocket
import socket.ServerSocketLoop
import socket.SocketState

internal expect class TLSServerSocket(
    broker: Broker,
    selectCallback: (attachment: Any?, state: SocketState) -> Boolean
) : ServerSocket
