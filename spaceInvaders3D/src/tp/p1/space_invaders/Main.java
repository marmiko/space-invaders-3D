package tp.p1.space_invaders;

import java.util.Random;

import tp.p1.controller.Controller;
import tp.p1.logic.Game;

public class Main {
	
	
	public static void main(String[] args) {
		Random rand = new Random();
		long seed;

		////

		if(args.length == 1){
			try {
				seed = Long.parseLong(args[0]);
				rand.setSeed(seed);
			} catch(RuntimeException e) {
				System.err.format("Usage: Main <EASY|HARD|INSANE> [seed]: the seed must be a number %n");
			}
			try{
				new Controller(new Game(rand)).run();
			} catch (Exception e){
				System.err.println("Could not initialize game correctly:");
				System.err.println(e.getMessage());
			}
		}
		else{
			try{
				new Controller(new Game(rand)).run();
			} catch (Exception e){
				System.err.println("Could not initialize game correctly:");
				System.err.println(e.getMessage());
			}
		}
	}

}

