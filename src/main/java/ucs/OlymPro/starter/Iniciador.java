package ucs.OlymPro.starter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ucs.OlymPro.view.MainScreen;

public class Iniciador {
	
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dados");
		MainScreen frame = new MainScreen();
		frame.setVisible(true);
		factory.close();
	}
}