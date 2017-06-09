package com.gmail.shatskiy.leha.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gmail.shatskiy.leha.service.DravingBlock;

public class ServerController {

	private int port;
	private ServerSocket server;

	private static long clientCounter = 1;
	private Map<Long, ClientRequestHendler> clients;

	private DravingBlock dravingBlock;
	private volatile static long flagLastMove;

	public ServerController(int port) {
		this.port = port;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.clients = new ConcurrentHashMap<>();
	}

	public void startServer() {
		Socket socket = null;
		try {
			while (true) {
				System.out.println("[Сервер]Сервер ожидает подключения клиента...");

				socket = server.accept();

				createHandler(socket);

				System.out.println("[Сервер]Поток обработки запроса клиента запущен...");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createHandler(Socket socket) {
		ClientRequestHendler clientHendler = new ClientRequestHendler(socket, clientCounter, this);
		new Thread(clientHendler).start();

		clients.put(clientCounter, clientHendler);
		clientCounter++;
		System.out.println(clients.size());
	}

	public void deliteHandler(Long hendlerKey) {
		clients.remove(hendlerKey);
		System.out.println(clients.size());
	}

	public void writeAll(String mesage) {
		for (Map.Entry entry : clients.entrySet()) {

			long key = (long) entry.getKey();
			clients.get(key).writeHendler(mesage);
		}
	}

	public synchronized static long getFlagLastMove() {
		return flagLastMove;
	}

	public synchronized static void setFlagLastMove(long flagLastMove) {
		ServerController.flagLastMove = flagLastMove;
	}
}
