package tp.p1.controller;

import tp.p1.graphic_interface_3D.Timer;
import tp.p1.graphic_interface_3D.Window;
import tp.p1.logic.Game;

public class Controller {

		private Game game;

//		graphical variables
		private Window window;
		private final Timer timer;

		private static final int InputHandlePeriod=6;

		private int handleInput=0;

		private static final String TITLE="SPACE INVADERS 3D";

		public static final int TARGET_UPS = 30;
		public static final int TARGET_FPS = 75;
//	//////
		
		public Controller(Game game) {
			this.game = game;
			window = new Window(TITLE, Game.DIM_Y*12+200, Game.DIM_X *12);
			timer = new Timer();
		}

		private void init() throws Exception {
			window.init();
			timer.init();
			this.game.initGame(window);
		}


		public void run() throws Exception {
			this.init();
			this.gameLoop();
		}

	private void gameLoop() {
		float elapsedTime;
		float updatesCounter = 0.0f;
		float interval = 1.0f / TARGET_UPS;

		while (!this.game.isExited() && !this.window.windowShouldClose()) {
			elapsedTime = this.timer.getElapsedTime();

			updatesCounter += elapsedTime;


			if(this.game.isMenuState()) {
				if(this.handleInput==0){
					input();
					this.handleInput=InputHandlePeriod;
				}
				this.handleInput--;
			}
			else input();


			while (updatesCounter >= interval) {
				if (!this.game.isPaused()) update();
				updatesCounter -= interval;
			}

			render();

			sync();
		}
		if(this.game.isExited()){
			this.game.cleanUp();
		}
	}

	private void render(){
			game.render(window);
			window.update();
	}

	private void sync() {
		float loopSlot = 1f / TARGET_FPS;
		double endTime = timer.getLastLoopTime() + loopSlot;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ignore) {}
		}
	}

	private void update(){
		game.update();
	}

	private void input(){
		    game.input(window);
    }
}

	