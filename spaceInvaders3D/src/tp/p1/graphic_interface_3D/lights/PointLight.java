package tp.p1.graphic_interface_3D.lights;

import org.joml.Vector3f;

public class PointLight {

    private float strength;
    private Vector3f color;
    protected Vector3f position;

    public PointLight(Vector3f color, Vector3f pos, float strength){
        this.color=color;
        this.position=pos;
        this.strength=strength;
    }


    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength){
        this.strength = strength;
    }

    public void setColor(Vector3f color){
        this.color=color;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getPosition() {
        return position;
    }
}
