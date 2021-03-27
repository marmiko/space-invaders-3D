package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class Shockwave extends Weapon {

    private final static int Power = 1;
    private final static int Shield = 0;
    private static Position InitialPos = new Position(100,100);

    private static final float Z_value = 10.0f;

    public final static String MainMessFire = "Cannot use shockwave: ";

    public Shockwave(Game game) {
        super(game, InitialPos, Shield, Power);
    }

    public boolean performAttack(GameElement other) {
        return other.isAlive() && other.receiveShockWaveAttack(power);
    }

    @Override
    public Mesh getMesh(){
        return null;
    }

    @Override
    public Vector3f getPosition3D(){
        return new Vector3f(0.0f, 0.0f, Z_value);
    }

    @Override
    public void move() {
    }

    @Override
    public void computerAction() {
    }

    @Override
    public void onDelete() {
    }

    @Override
    public String toString() {
        return null;
    }

}
