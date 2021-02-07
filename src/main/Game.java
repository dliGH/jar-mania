package main;

import java.awt.*;
import javax.swing.*;

public class Game {

	//main method where client runs
	public static void main(String[] args) {
		
		//creating frame and just showing it
		JFrame window = new JFrame("Legend of Zelda: Jar Mania");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
	
}
