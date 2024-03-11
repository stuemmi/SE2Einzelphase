package com.example.se2einzelphase.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.net.Socket


suspend fun sendAndReceiveTCP(data: String): String = withContext(Dispatchers.IO) {
    var message = ""

    try {
        val socket = Socket("se2-submission.aau.at", 20080)
        val output = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))

        output.println(data)

        message = input.readLine()

        socket.close()

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return@withContext message
}
