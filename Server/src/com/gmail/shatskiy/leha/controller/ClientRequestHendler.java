package com.gmail.shatskiy.leha.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.gmail.shatskiy.leha.service.DravingBlock;

public class ClientRequestHendler implements Runnable {

	private final String STOP_COMMAND = "STOP";
	private final long clientNumber;

	private ServerController myServer;
	private Socket socket;

	private DravingBlock game = DravingBlock.getDravingBlock();

	public ClientRequestHendler(Socket socket, final long clientNumber, ServerController server) {
		this.socket = socket;
		this.clientNumber = clientNumber;
		myServer = server;
	}

	@Override
	public void run() {

		try (DataInputStream dataIn = new DataInputStream(socket.getInputStream())) {

			String clientRequest = null;

			while (!STOP_COMMAND.equals(clientRequest)) {

				writeHendler(game.printDravingBlock());

				clientRequest = dataIn.readUTF();

				if (myServer.getFlagLastMove() != clientNumber) {

					game.makeMove(clientRequest, clientNumber + "");

					myServer.writeAll(game.printDravingBlock());

					if (game.isVinner()) {
						myServer.writeAll("Winner: " + clientNumber);
						game.createGame();
					}

					myServer.setFlagLastMove(clientNumber);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			myServer.deliteHandler(clientNumber);
		}
	}

	public void writeHendler(String responseToClient) {
		try {
			DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
			String clientRequest = null;

			dataOut.writeUTF(responseToClient);

			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
