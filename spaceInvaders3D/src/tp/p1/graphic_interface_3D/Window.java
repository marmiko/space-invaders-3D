package tp.p1.graphic_interface_3D;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2d;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final String title;

    private int width;

    private int height;

    private long windowHandle;

    private boolean resized;

    private double[] xCoursorPos = {0};
    private double[] yCoursorPos = {0};
    public static final float MouseHoverRadius = 10.0f;

    // colors

    private static final Vector4f MenuClearColor = new Vector4f(0.049f, 0.028f, 0.135f, 1.0f);
    private static final Vector4f GameClearColor = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    // ////

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resized = false;
    }

    public void init() {
        // Error callback is set to System default error output channel
        GLFWErrorCallback.createPrint(System.err).set();

        // initialization of the GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        // Creation of the window
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup of the resize callback
        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });


        // get the primary monitor video parameters
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // set window in the center of the screen
        glfwSetWindowPos(
                windowHandle,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // setting this window as a current OpenGL context
        glfwMakeContextCurrent(windowHandle);

        glfwSwapInterval(1);

        // making window visible
        glfwShowWindow(windowHandle);

        GL.createCapabilities(); // for LWJGL to recognise OpenGL context and initialize itself using it

        // setting the clear color
        glClearColor(MenuClearColor.x, MenuClearColor.y, MenuClearColor.z, MenuClearColor.w);
        glEnable(GL_DEPTH_TEST);

        // enabling transparency handling
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    // colors

    public void menuClearColor(){
        glClearColor(MenuClearColor.x, MenuClearColor.y, MenuClearColor.z, MenuClearColor.w);
    }

    public void gameClearColor(){
        glClearColor(GameClearColor.x, GameClearColor.y, GameClearColor.z, GameClearColor.w);
    }

    public Vector2d getMousePos(){
        glfwGetCursorPos(this.windowHandle, this.xCoursorPos, this.yCoursorPos);
        return new Vector2d(this.xCoursorPos[0], this.yCoursorPos[0]);
    }

    // //////

    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

}
