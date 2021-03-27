package tp.p1.graphic_interface_3D;

import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.*;
import tp.p1.logic.elements.GameElement;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final Transformation transformation;

    private ShaderProgram sceneShaderProgram;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init() throws Exception {
        setupSceneShader();
    }

    private void setupSceneShader() throws Exception {
        // Create shader
        sceneShaderProgram = new ShaderProgram();
        sceneShaderProgram.createVertexShader(Utils.loadResource("/tp/p1/graphic_interface_3D/resources/shaders/v_sh.glsl"));
        sceneShaderProgram.createFragmentShader(Utils.loadResource("/tp/p1/graphic_interface_3D/resources/shaders/f_sh.glsl"));
        sceneShaderProgram.link();

        // creation of uniforms for Projection, Model and View matrices
        sceneShaderProgram.createUniform("P");
        sceneShaderProgram.createUniform("M");
        sceneShaderProgram.createUniform("V");
        // creation of uniforms for textures
        sceneShaderProgram.createUniform("textureMap0");
        sceneShaderProgram.createUniform("textureMap1");



        // creation of lights' uniforms
        sceneShaderProgram.createPointLightsUniform("pointLights", SceneLight.NUM_POINT_LIGHTS);
        sceneShaderProgram.createUniform("ambientStrength");
        sceneShaderProgram.createSpotLightsUniform("spotLight");
        sceneShaderProgram.createUniform("ufoState");

        sceneShaderProgram.createUniform("hasSpecularMap");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Camera camera, GameElement[] gameElements,
                       SceneLight sceneLight) {

        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        renderScene(window, camera, gameElements, sceneLight);
    }

    public void renderScene(Window window, Camera camera, GameElement[] gameElements,
                            SceneLight sceneLight) {

        sceneShaderProgram.bind();

        // calculate projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        sceneShaderProgram.setUniform("P", projectionMatrix);
        // calculate view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        sceneShaderProgram.setUniform("V", viewMatrix);

        renderLights(sceneLight);


        // rendering each game element
        for (GameElement gameElement : gameElements) {
            Mesh mesh = gameElement.getMesh();
            if( mesh!=null ){
                // calculate model matrix for this element
                Matrix4f modelMatrix = transformation.getModelMatrix(gameElement);
                sceneShaderProgram.setUniform("M", modelMatrix);
                sceneShaderProgram.setUniform("textureMap0", 0);
                sceneShaderProgram.setUniform("textureMap1", 1);
                // render the mesh for this game element
                mesh.render(this);
            }
        }
        sceneShaderProgram.unbind();
    }

    private void renderLights(SceneLight sceneLight) {
        sceneShaderProgram.setUniform("ambientStrength", sceneLight.getAmbientStrength());
        sceneShaderProgram.setUniform("pointLights", sceneLight.getPointLights());

        if(sceneLight.getSpotLight()!=null) {
            sceneShaderProgram.setUniform("ufoState", 1);
            sceneShaderProgram.setUniform("spotLight", sceneLight.getSpotLight());
        }
      else  sceneShaderProgram.setUniform("ufoState", 0);
    }


    public void setShaderUniform(String name, int value){
        sceneShaderProgram.setUniform(name, value);
    }

    public void cleanUp() {
        if (sceneShaderProgram != null) {
            sceneShaderProgram.cleanup();
        }
    }
}
