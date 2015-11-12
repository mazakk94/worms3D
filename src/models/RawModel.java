/*
    LESSON 2 - VAO & VBO
    taki obiekt bedzie reprezentował dane modelu 3d 
*/

package models;

public class RawModel { 
    
    private int vaoID;
    private int vertexCount;
    
    public RawModel(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
    
    
}
