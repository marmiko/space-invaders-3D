package tp.p1.logic.interfaces;

import tp.p1.logic.elements.GameElement;

public interface IAttack {
default boolean canBeRemoved(){return true;}

default boolean performAttack(GameElement other) {return false;};

default boolean receiveMissileAttack(int damage) {return false;}; 
default boolean receiveBombAttack(int damage) {return false;}; 
default boolean receiveShockWaveAttack(int damage) {return false;};
}

