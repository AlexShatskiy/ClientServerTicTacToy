package com.gmail.shatskiy.leha.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientController {

	private final String STOP_COMMAND = "STOP";
	private final byte READER_KEY = 1;

	private String netAddress;
	private int port;

	private Socket socket;
	private Map<Byte, IncomingReader> reader;

	public ClientController(String netAddress, int port) {
		try {
			InetAddress inetAddress = InetAddress.getByName(netAddress);

			this.socket = new Socket(inetAddress, port);

			this.reader = new ConcurrentHashMap<>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startClient() {

		try (DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
				DataInputStream dataIn = new DataInputStream(socket.getInputStream())) {

			createIncomingReader(dataIn);

			String request = null;

			while (!STOP_COMMAND.equals(request)) {
				request = readRequest();

				dataOut.writeUTF(request);
				dataOut.flush();
			}
			reader.get(READER_KEY).stopReader();
			
			deliteIncomingReader();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readRequest() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		return reader.readLine();
	}

	private void createIncomingReader(DataInputStream dataIn) {
		IncomingReader incomingReader = new IncomingReader(dataIn);
		new Thread(incomingReader).start();

		reader.put(READER_KEY, incomingReader);
	}

	public void deliteIncomingReader() {
		reader.remove(READER_KEY);
	}
}
