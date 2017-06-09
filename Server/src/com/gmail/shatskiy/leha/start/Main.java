package com.gmail.shatskiy.leha.start;

import java.io.IOException;

import com.gmail.shatskiy.leha.controller.ServerController;




public class Main {

	public static void main(String[] args) {
		ServerController server;

			server = new ServerController(1245);
			server.startServer();

	}
}
