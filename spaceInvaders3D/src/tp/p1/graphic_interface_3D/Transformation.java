package tp.p1.graphic_interface_3D;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import tp.p1.logic.elements.GameElement;

public class Transformation {

    private final Matrix4f projectionMatrix;

    private final Matrix4f modelViewMatrix;
    
    private final Matrix4f viewMatrix;


    public Transformation() {
        projectionMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        return projectionMatrix.setPerspective(fov, width / height, zNear, zFar);
    }

    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();
        
        viewMatrix.identity();
        // rotation of the camera
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1.0f, 0.0f, 0.0f))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0.0f, 1.0f, 0.0f))
                .rotate((float)Math.toRadians(rotation.z), new Vector3f(0.0f,0.0f, 1.0f));
        // translation of the camera position
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }

    public Matrix4f getModelMatrix(GameElement gameElement) {
        Vector3f rotation = gameElement.getRotation3D();
        if(gameElement.simpleScale()) modelViewMatrix.identity().translate(gameElement.getPosition3D()).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameElement.getScale());
        else modelViewMatrix.identity().translate(gameElement.getPosition3D()).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameElement.getComplexScale().x, gameElement.getComplexScale().y, gameElement.getComplexScale().z);

        return modelViewMatrix;
    }

}