package tp.p1.logic.interfaces;

import tp.p1.logic.Game;

public interface IExecuteRandomActions {
static boolean canGenerateUfo(Game game){ return game.getRandom().nextInt(100) < 100 * game.getLevel().getUfo();}
static boolean canGenerateBomb(Game game){ return game.getRandom().nextInt(100) < 100 * game.getLevel().getFirepower();}
}
