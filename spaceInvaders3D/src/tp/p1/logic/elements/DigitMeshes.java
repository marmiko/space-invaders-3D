package tp.p1.logic.elements;

import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;

public enum DigitMeshes { ZERO(0, "zero.obj"),
                                ONE(1, "one.obj"),
                                TWO(2, "two.obj"),
                                THREE(3, "three.obj"),
                                FOUR(4, "four.obj"),
                                FIVE(5, "five.obj"),
                                SIX(6, "six.obj"),
                                SEVEN(7, "seven.obj"),
                                EIGHT(8, "eight.obj"),
                                NINE(9, "nine.obj");

    private final static String ObjectPath = "/tp/p1/graphic_interface_3D/resources/models/";
    private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/DIGITS_TEXTURE.png";

    private final int digit;
    private Mesh mesh;

    DigitMeshes(int digit, String ObjectFileName){
        this.digit = digit;

        try{
            this.mesh = OBJLoader.loadMesh(ObjectPath + ObjectFileName);
            this.mesh.setTexture(new Texture(TextureSource));
        } catch (Exception ignore){}
    }

    public static Mesh getMesh(int digit) {
        for(DigitMeshes digitMesh : DigitMeshes.values()) {
            if ( digitMesh.digit == digit ) {
                final Mesh mesh = digitMesh.mesh;
                return mesh;
            }
        }
        return null;
    }

}
