package socket.tls

import close
import mqtt.broker.Broker
import mqtt.broker.ClientConnection
import socket.ServerSocket
import socket.ServerSocketLoop
import socket.tcp.IOException
import socket.tcp.WebSocket

actual class TLSServerSocket actual constructor(
    private val broker: Broker,
    selectCallback: (attachment: Any?, state: ServerSocketLoop.SocketState) -> Boolean
) : ServerSocket(broker, selectCallback) {

    private val tlsServerContext = TLSServerContext(broker)

    override fun accept(socket: Int, type: TCPSocketType) {
        try {
            val engine = TLSEngine(tlsServerContext)
            val sock = TLSSocket(socket, engine, writeRequest, buffer)
            clients[socket] = when (type) {
                TCPSocketType.MQTT -> ClientConnection(sock, broker)
                TCPSocketType.MQTTWS -> ClientConnection(WebSocket(sock), broker)
                TCPSocketType.CLUSTER -> {
                    TODO("Cluster not yet complete in Native")
                    //val clusterConnection = ClusterConnection(sock, broker)
                    //val remoteAddress = (channel.socket().remoteSocketAddress as InetSocketAddress).address.hostAddress
                    //broker.addClusterConnection(remoteAddress, clusterConnection)
                    //clusterConnection
                }
            }
        } catch (e: IOException) {
            close(socket)
        }
    }

    override fun close() {
        super.close()
        tlsServerContext.close()
    }
}
