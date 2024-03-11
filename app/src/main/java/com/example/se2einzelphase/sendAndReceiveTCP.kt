package com.example.se2einzelphase.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

suspend fun sendAndReceiveTCP(data: String, serverAddress: String = "se2-submission.aau.at", port: Int = 20080): String = withContext(Dispatchers.IO) {
    var response = ""
    try {
        Socket(serverAddress, port).use { socket ->
            DataOutputStream(socket.getOutputStream()).use { dos ->
                DataInputStream(socket.getInputStream()).use { dis ->
                    dos.writeUTF(data)
                    dos.flush()

                    response = dis.readUTF()
                }
            }
        }
    } catch (e: Exception) {
        response = "Error: ${e.message}"
    }
    return@withContext response
}
