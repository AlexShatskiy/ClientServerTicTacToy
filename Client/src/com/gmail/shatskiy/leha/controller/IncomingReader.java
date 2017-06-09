package com.gmail.shatskiy.leha.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketException;

public class IncomingReader implements Runnable {

	private DataInputStream dataIn;
	private boolean isWork = true;

	public IncomingReader(DataInputStream dataIn) {
		this.dataIn = dataIn;
	}

	@Override
	public void run() {
		try {

			String response = null;

			while (isWork) {

				response = dataIn.readUTF();

				System.out.println("[клиент]->[сервер]" + "\n" + response);
			}
			System.out.println("Thread finished");
		} catch (SocketException e) {
			System.out.println("Socket closed");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				dataIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopReader(){
		isWork = false;
	}
}
