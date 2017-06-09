package com.gmail.shatskiy.leha.start;

import com.gmail.shatskiy.leha.controller.ClientController;

public class Main {

	public static void main(String[] args) {
		ClientController client;

		client = new ClientController("127.0.0.1", 1245);
		client.startClient();
		
	}
}
