package tp.p1.graphic_interface_3D;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class OBJLoader {

    public static Mesh loadMesh(String fileName) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(OBJLoader.class.getResource(fileName).toURI()));
        
        List<Vector4f> vertices = new ArrayList<>();
        List<Vector2f> textureCoords = new ArrayList<>();
        List<Vector4f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    // Object's vertices' vectors are read from file

                    Vector4f vertex = new Vector4f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]),
                            1.0f);
                    vertices.add(vertex);
                    break;
                case "vt":
                    // object's texture coordinates' vectors are read from file
                    Vector2f textureCoord = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));
                    textureCoords.add(textureCoord);
                    break;
                case "vn":
                    // object's normal vectors are read from file
                    Vector4f normal = new Vector4f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]),
                           0.0f);
                    normals.add(normal);
                    break;
                case "f":
                    // object's faces' vectors are read from file
                    // Object's Face - connects certain vertex vector with texture coordinates vector and normal vector
                    // it's written in form of vertices, which are presented like: "[vertex index]/[texture coords index]/[normal vector index]"
                    // indexes start from 1 and order is as the order of vectors definitions
                    // one face consists of 3 verices in this implementation

                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                    break;
                default:
                    // Ignore other lines
                    break;
            }
        }

        return reorderLists(vertices, textureCoords, normals, faces);
    }

    // method which creates arrays of vetices coordinates, texture coordinates and normal vectors
    private static Mesh reorderLists(List<Vector4f> vertCoordsList, List<Vector2f> textureCoordList,
            List<Vector4f> normList, List<Face> facesList) {

        List<Integer> indices = new ArrayList<>();
        // transfer vertices coordinates to the array
        float[] vertCoordArr = new float[vertCoordsList.size() * 4];
        int i = 0;
        for (Vector4f pos : vertCoordsList) {
            vertCoordArr[i * 4] = pos.x;
            vertCoordArr[i * 4 + 1] = pos.y;
            vertCoordArr[i * 4 + 2] = pos.z;
            vertCoordArr[i * 4 + 3] = pos.w;
            i++;
        }
        float[] textureCoordArr = new float[vertCoordsList.size() * 2];
        float[] normArr = new float[vertCoordsList.size() * 4];

        for (Face face : facesList) {
            // get each saved face
            IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
            // for each face process every vertex
            for (IdxGroup indValue : faceVertexIndices) {
                processFaceVertex(indValue, textureCoordList, normList,
                        indices, textureCoordArr, normArr);
            }
        }
        int[] indicesArr;
        indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();

        // return new Mesh created with
        return new Mesh(vertCoordArr, textureCoordArr, normArr, indicesArr);
    }

    private static void processFaceVertex(IdxGroup indices, List<Vector2f> textureCoordList,
            List<Vector4f> normList, List<Integer> indicesList,
            float[] texCoordArr, float[] normArr) {

        // get index of current vertex and add it to the list of indices
        int posIndex = indices.idxPos;
        indicesList.add(posIndex);

        // if face contains texture information, then put texture coordinates of an index got from Face to the texture coordinates array
        // on the position of the current vertex index (mul;tiplied by 2 cause there are 2 texture coordinates X and Y, so length of the array is twice bigger)
        if (indices.idxtextureCoord >= 0) {
            Vector2f textureCoord = textureCoordList.get(indices.idxtextureCoord);
            texCoordArr[posIndex * 2] = textureCoord.x;
            texCoordArr[posIndex * 2 + 1] = 1 - textureCoord.y;
        }

        // if face contains normal vector information, then do analogical thing as for texture coords, but now there are 3 coordinates of the normal vector,
        // so the sze triples
        if (indices.idxVecNormal >= 0) {
            Vector4f vecNorm = normList.get(indices.idxVecNormal);
            normArr[posIndex * 4] = vecNorm.x;
            normArr[posIndex * 4 + 1] = vecNorm.y;
            normArr[posIndex * 4 + 2] = vecNorm.z;
            normArr[posIndex * 4 + 3] = vecNorm.w;
        }
    }


    // Face stores informationa about Face collected from .obj file
    protected static class Face {

        /**
         * List of idxGroup groups for a face triangle (3 vertices per face).
         */
        private IdxGroup[] idxGroups = new IdxGroup[3];

        public Face(String v1, String v2, String v3) {
            idxGroups = new IdxGroup[3];
            // Parse the lines
            idxGroups[0] = parseLine(v1);
            idxGroups[1] = parseLine(v2);
            idxGroups[2] = parseLine(v3);
        }

        private IdxGroup parseLine(String line) {
            IdxGroup idxGroup = new IdxGroup();

            String[] lineTokens = line.split("/");
            int length = lineTokens.length;
            idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
            if (length > 1) {
                // It can be empty if the obj does not define texture coords
                String textureCoord = lineTokens[1];
                idxGroup.idxtextureCoord = textureCoord.length() > 0 ? Integer.parseInt(textureCoord) - 1 : IdxGroup.NO_VALUE;
                if (length > 2) {
                    idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
                }
            }

            return idxGroup;
        }

        public IdxGroup[] getFaceVertexIndices() {
            return idxGroups;
        }
    }


    // IdxGroup is the object which stores information about one face's vertex
    protected static class IdxGroup {

        public static final int NO_VALUE = -1;

        public int idxPos;

        public int idxtextureCoord;

        public int idxVecNormal;

        public IdxGroup() {
            idxPos = NO_VALUE;
            idxtextureCoord = NO_VALUE;
            idxVecNormal = NO_VALUE;
        }
    }
}
