package tp.p1.graphic_interface_3D.lights;

import org.joml.Vector3f;

import static org.joml.Math.cos;

public class SpotLight extends PointLight{

    Vector3f coneDir;
    float cutOff;

    public SpotLight(Vector3f color, Vector3f pos, float strength, Vector3f coneDir, float phiAngle) {
        super(color, pos, strength);
        this.coneDir= coneDir;
        this.cutOff = (float)cos(phiAngle);
    }

    public Vector3f getConeDir(){
        return coneDir;
    }

    public float getCutOff() {
        return cutOff;
    }

    public void setPosition(Vector3f newPosition){
        this.position = newPosition;
    }

    public void disable(){
        this.setStrength(0.0f);
    }

}
