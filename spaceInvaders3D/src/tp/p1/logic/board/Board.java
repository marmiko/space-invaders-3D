package tp.p1.logic.board;

import org.joml.Vector2d;
import org.joml.Vector3f;
import tp.p1.logic.elements.AlienShip;
import tp.p1.logic.elements.GameElement;
import tp.p1.util.Position;

public class Board { 
	private GameElement[] elements; 
	private int currentElements;


public Board (int width, int height) {
	elements = new GameElement[width * height];
	currentElements = 0;
}

public void add (GameElement gameElement) {
	elements[currentElements] = gameElement;
	currentElements += 1;
}

public void update() {

	for(int i = 0; i < currentElements; i++) {
		if(elements[i] != null) {
			elements[i].move();
			checkAttacks(elements[i]);
		}
	}
	AlienShip.setIfMoveDownNextMove();
	removeDead();
}


private void checkAttacks(GameElement element) {
	for(int i=0; i<currentElements; i++) {
		if(elements[i] != null) {
			element.performAttack(elements[i]);
		}
	}
}

public void addSwap(GameElement one, Position pos) {
	int index = getIndex(pos);
	elements[index] = one;
}

public void computerAction() {
	int n = 0;
	for (GameElement el : elements) {
		if (el != null) {
			++n;
			el.computerAction();
		}
		if (n == currentElements) {
			break;
		}
	}
}

public String toString(Position pos) { 
	GameElement obj = getObjectAt(pos);
	return (obj != null) ? obj.toString() : "";
}

public Vector3f getUfo3DPos(){
	int i;
	for(i=0;i<currentElements;i++){
		if(elements[i] != null && elements[i].isUfo()){
			return elements[i].getPosition3D();
		}

	}
	return null;
}

private GameElement getObjectAt (Position pos) 
{
	int i;
	for (i=0;i<currentElements; i++) {
		if (elements[i].getPosition().equals(pos)) {
			return elements[i];
		}
}
	return null;
	
}
	
private int getIndex(Position pos) {
		int i;
		for (i=0;i<currentElements; i++) {
			if(elements[i] != null) {
				if (elements[i].getPosition().equals(pos) && elements[i].canBeRemoved()) {
					return i;
				}
			}
			
	
	}
		return -1;
}

private void squeeze(int ind) {
	int j;
	for(j=ind;j<currentElements;j++) {
		elements[j] = elements[j+1];
	}
	
	elements[currentElements] = null; 
	}


private void removeDead() {
int i;
	for(i=0; i < currentElements; i++) {
		if(elements[i] != null && !elements[i].isAlive()) {
			elements[i].onDelete();
			
			if(!elements[i].isAlive()) {
			elements[i] = null;
			--currentElements;
			squeeze(i);
			i -= 1;
			}
		}
	}
}


public GameElement[] getAllGameElements(){
	int i;
	GameElement[] gameElements = new GameElement[currentElements];
	for(i=currentElements-1; i>=0; i--){
		gameElements[currentElements-i-1] = elements[i];
	}

	return gameElements;
}

public void checkHover(Vector2d mousePos){
	for(int i=0; i<currentElements; i++){
		elements[i].mouseHover(mousePos);
	}
}

}