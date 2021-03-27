package tp.p1.graphic_interface_3D;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.MemoryStack;
import tp.p1.exceptions.TextureLoadingException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private final int id;

    private final int specularID;

    private final boolean hasSpecularMap;

    private final int width;

    private final int height;

    public Texture(String fileName) throws TextureLoadingException {
        ByteBuffer buf;
        // Load Texture file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = stbi_load(fileName, w, h, channels, 4);
            if (buf == null) {
                throw new TextureLoadingException("Image file [" + fileName  + "] not loaded: " + stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        this.id = createTexture(buf);

        this.specularID = 0;

        this.hasSpecularMap = false;

        stbi_image_free(buf);
    }

    public Texture(String textureFile, String specularFile) throws TextureLoadingException {
        ByteBuffer buf1;
        ByteBuffer buf2;
        // Load Texture file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf1 = stbi_load(textureFile, w, h, channels, 4);
            if (buf1 == null) {
                throw new TextureLoadingException("Image file [" + textureFile + "] not loaded: " + stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        this.id = createTexture(buf1);

        stbi_image_free(buf1);

        // Load specular file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf2 = stbi_load(specularFile, w, h, channels, 4);
            if (buf2 == null) {
                throw new TextureLoadingException("Image file [" + specularFile + "] not loaded: " + stbi_failure_reason());
            }

        }

        this.specularID = createTexture(buf2);
        this.hasSpecularMap = true;

        stbi_image_free(buf2);
    }

    public Texture(ByteBuffer imageBuffer) throws TextureLoadingException {
        ByteBuffer buf;
        // Load Texture file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = stbi_load_from_memory(imageBuffer, w, h, channels, 4);
            if (buf == null) {
                throw new TextureLoadingException("Image file not loaded: " + stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        this.id = createTexture(buf);

        this.specularID = 0;
        this.hasSpecularMap = false;
        stbi_image_free(buf);
    }

    private int createTexture(ByteBuffer buf) {
        // creation of a new OpenGL texture
        int textureId = glGenTextures();
        // bind of the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        // pass to OpenGL information on how to unpack the RGBA bytes - each component is 1 byte size
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // uploading the texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
            GL_RGBA, GL_UNSIGNED_BYTE, buf);

        return textureId;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }

    public int getSpecularID() { return specularID; }


    public boolean hasSpecularMap() { return hasSpecularMap; }


    public void cleanup() {
        glDeleteTextures(id);
    }
}
