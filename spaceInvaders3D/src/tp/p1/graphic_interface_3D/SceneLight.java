package tp.p1.graphic_interface_3D;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.lights.PointLight;
import tp.p1.graphic_interface_3D.lights.SpotLight;

public class SceneLight {

    public final static int NUM_POINT_LIGHTS = 4;

    private SpotLight spotLight;
    private PointLight[] pointLights;
   private int numPointLights=0;
   private float ambientStrength;

    public SceneLight(){
        pointLights = new PointLight[NUM_POINT_LIGHTS];
    }

    public PointLight[] getPointLights() {
        return pointLights;
    }

    public void setAmbientStrength(float str){
        this.ambientStrength = str;
    }

    public float getAmbientStrength(){
        return ambientStrength;
    }
    public void addPointLight(PointLight pointLight){
        pointLights[numPointLights] = pointLight;
        ++numPointLights;
    }

    public void addSpotLight(SpotLight spotLight){
        this.spotLight = spotLight;
    }

    public void updateSpotLightPos(Vector3f newPosition){
        this.spotLight.setPosition(newPosition);
    }

    public SpotLight getSpotLight() {
        return spotLight;
    }

    public void changePointLightCol(int index, Vector3f color){
        pointLights[index].setColor(color);
    }

    public void changePointLightStr(int index, float strength) {pointLights[index].setStrength(strength);}

    public void disableSpotLight(){
        this.spotLight.disable();
    }

    public void setSpotLightStrength(float strength){
        this.spotLight.setStrength(strength);
    }

}