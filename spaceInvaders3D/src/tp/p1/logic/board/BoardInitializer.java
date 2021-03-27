package tp.p1.logic.board;

import tp.p1.logic.Game;
import tp.p1.logic.Level;
import tp.p1.logic.elements.*;
import tp.p1.util.Position;

public class BoardInitializer {
	
	private Board board;
	private Game game;
	private Level level;
	
	public Board initialize(Game game, Level level) {
		this.level = level;
		this.game = game;
		
		board = new Board(Game.DIM_X, Game.DIM_Y);
		try {
			//
			initializePointsDigits();
			initializeBaseBlocks();
			initalizeLifeHearts();
			initializeShockWaveIcon();
			//
			initializeUfo();
			initializeCarrierShips();
			initializeDestroyers();
		} catch (Exception e) {
			System.err.println("Initialization failed:");
			System.err.println(e.getMessage());
			game.exit();
		}
		
		return board;
	}

	public Board initializeMainMenu(Game game){
		this.game = game;

		this.board = new Board(MainMenuElement.MaxSelectableElements+2, 1);

		try{
			initializeNonSelectableMainMenu();
			initializeSelectableMainMenu();
		} catch (Exception e){
			System.err.println("Main menu initialization failed:");
			System.err.println(e.getMessage());
			game.exit();
		}
		return board;
	}

	private void initializeNonSelectableMainMenu() throws Exception {
		initializeTitleElementMainMenu();
		initializeLevelSelectionInfoElementMainMenu();
	}

	private void initializeTitleElementMainMenu() throws Exception {
		board.add(new MenuElement(game, new Position(MenuElement.UP, Game.DIM_Y/2), -2, "titleElement.png", 1.5f));
	}

	private void initializeLevelSelectionInfoElementMainMenu() throws Exception {
		board.add(new MenuElement(game, new Position((Game.DIM_X/2)+MenuElement.UP, Game.DIM_Y/2), -1, "levelSelectionInfo.png", 0.7f));
	}

	private void initializeSelectableMainMenu() throws Exception {
		for(int i = MainMenuElement.MaxSelectableElements-1; i>=0; i--){
			int menuID = MainMenuElement.MaxSelectableElements-i-1;
			board.add(new MainMenuElement(game, new Position((Game.DIM_X)+MenuElement.UP, (MainMenuElement.MaxSelectableElements-i)*Game.DIM_Y/(MainMenuElement.MaxSelectableElements+1)-10*Integer.compare(i, MainMenuElement.MaxSelectableElements/2)), menuID));
		}
	}

	public Board initializeEndMenu(Game game){
		this.game = game;

		this.board = new Board(EndMenuElement.MaxSelectableElements+1, 1);

		try{
			initializeNonSelectableEndMenu();
			initializeSelectableEndMenu();
		} catch (Exception e){
			System.err.println("End menu initialization failed:");
			System.err.println(e.getMessage());
			game.exit();
		}
		return board;
	}

	private void initializeNonSelectableEndMenu() throws Exception {
		board.add(new GameResultInfoElement(game, new Position(3*MenuElement.UP/4, Game.DIM_Y / 2)));
	}

	private void initializeSelectableEndMenu() throws Exception {
		for(int i = EndMenuElement.MaxSelectableElements-1; i>=0; i--){
			int menuID = EndMenuElement.MaxSelectableElements-i-1;
			board.add(new EndMenuElement(game, new Position((Game.DIM_X)+2*MenuElement.UP, (EndMenuElement.MaxSelectableElements-i)*Game.DIM_Y/(EndMenuElement.MaxSelectableElements+1)-10*Integer.compare(i, EndMenuElement.MaxSelectableElements/2)), menuID));
		}
	}
	
	private void initializeCarrierShips() throws Exception {
		int n = level.getNumCarrierShips();
		int i;
		int inRow = level.getNumCarriersPerRowe();
		Position pos = level.getCarrierShipsInitPosition();
		for(i=0;i<n;i++) {
				board.add(new CarrierShip(game, pos));
				if((i+1) % inRow == 0) pos = new Position(pos.getX()+1*Game.MULT_DIM, level.getCarrierShipsInitPosition().getY());
				else pos = new Position(pos.getX(), pos.getY()+1*Game.MULT_DIM);
			}
	}
	
	private void initializeDestroyers() throws Exception {
		int n = level.getNumDestroyers();
		int i;
		int inRow = level.getNumDestroyersPerRow();
	
		Position pos = level.getDestroyersInitPosition();
	
		for(i=0;i<n;i++) {
			board.add(new Destroyer(game, pos));
			if((i+1) % inRow == 0) pos = new Position(pos.getX()+1*Game.MULT_DIM, level.getDestroyersInitPosition().getY());
			else pos = new Position(pos.getX(), pos.getY()+1*Game.MULT_DIM);
		}
		
	}
	
	private void initializeUfo() throws Exception {
		Ufo ufo = new Ufo(game);
		board.add(ufo);
	}

	private void initalizeLifeHearts() throws Exception{
		for(int i=0; i<UCMShip.Shield; i++){
			board.add(new LifeHeart(game, i));
		}
	}

	private void initializePointsDigits() throws Exception {
		for(int i=0; i<PointDigit.COUNT; i++){
			board.add(new PointDigit(game, i));
		}
	}

	private void initializeShockWaveIcon() throws Exception {
		board.add(new ShockWaveIcon(game));
	}

	private void initializeBaseBlocks() throws Exception {
		for(int i=Game.DIM_Y+Game.MULT_DIM/2; i>=BaseBlock.FirstBlockPos.getY(); i-=Game.MULT_DIM/2){
			board.add(new BaseBlock(game,
					new Position(BaseBlock.FirstBlockPos.getX(), i)));
		}
		board.add(new BaseBlock(game,
				new Position(BaseBlock.FirstBlockPos.getX()-Game.MULT_DIM/2, BaseBlock.FirstBlockPos.getY())));
		board.add(new BaseBlock(game,
				new Position(BaseBlock.FirstBlockPos.getX()-Game.MULT_DIM/2, Game.DIM_Y+2*Game.MULT_DIM/4)));
	}


}
