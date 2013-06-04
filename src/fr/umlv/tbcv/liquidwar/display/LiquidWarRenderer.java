package fr.umlv.tbcv.liquidwar.display;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;
import fr.umlv.tbcv.liquidwar.logic.Player;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class LiquidWarRenderer implements GLSurfaceView.Renderer {
	
	LiquidWorld gameWorld ;

    private static final String TAG = "LiquidWarRenderer";
    private ArmiesGL armies ;
    private PlayerGL players ;
    
//    private Triangle mTriangle;

    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    private final float[] mOrthoMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    // Declare as volatile because we are updating it from another thread
    public volatile float mAngle;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
//        float aspectRatio = (float) LiquidWorld.getGamewidth() / (float) LiquidWorld.getGameheight() ;
        
        
        Matrix.setIdentityM(mProjMatrix, 0) ;
        Matrix.orthoM(mOrthoMatrix, 0, (float)-(LiquidWorld.getGamewidth()), (float)(LiquidWorld.getGamewidth()), (float)-(LiquidWorld.getGameheight()), (float)(LiquidWorld.getGameheight()), -5f, 5f) ;
//        Matrix.orthoM(mOrthoMatrix, 0, -aspectRatio, aspectRatio, -1, 1, -1, 1 ) ;
//        Matrix.orthoM(mOrthoMatrix, 0, 0f, (float)(LiquidWorld.getGamewidth()), (float)(LiquidWorld.getGameheight()),0f,  -40f, 40f) ;
//        Matrix.setIdentityM(mOrthoMatrix, 0);
        
        // Initialize game logic
        gameWorld = new LiquidWorld(2) ; //TODO Find a way to ask input for number of players (currently 2)
        // Initialize objects to be drawn
        armies = new ArmiesGL( gameWorld.getArmies() ) ;
        players = new PlayerGL( gameWorld.getPlayers(), gameWorld.getNbPlayers() ) ;
//        mTriangle = new Triangle() ;

    }

    @Override
    public void onDrawFrame(GL10 unused) {

    	// Do logic calculation at each frame
    	gameWorld.turn() ;
    	
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
        
//        Matrix.multiplyMM(mMVPMatrix, 0, mOrthoMatrix, 0, mMVPMatrix, 0) ;

//        mTriangle.draw(mMVPMatrix) ;

        // Draw soldiers
        armies.draw(mMVPMatrix);

        //Draw players
        players.draw(mMVPMatrix);
        
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

//        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjMatrix, 0, 1, -1, -1, 1, 3, 7);

    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * LiquidWarRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}





class Triangle {

    private final String vertexShaderCode =
        // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +

        "attribute vec4 vPosition;" +
        "void main() {" +
        // the matrix must be included as a modifier of gl_Position
        "  gl_Position = vPosition * uMVPMatrix;" +
        "  gl_PointSize = 8.0 ; " +
        "}";

    private final String fragmentShaderCode =
        "precision mediump float;" +
        "uniform vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
        "}";

    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static float triangleCoords[] = { // in counterclockwise order:
         0f,  0f, 0f, 0f, 0f , 0f   // bottom right
    };
    
    static int intCoords[] = { // in counterclockwise order:
        45,  0,    // top
       0, 0,  // bottom left
        22, 80    // bottom right
   };
    
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public Triangle() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        
        

        // prepare shaders and OpenGL program
        int vertexShader = LiquidWarRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                                                   vertexShaderCode);
        int fragmentShader = LiquidWarRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                                                     fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables

    }

    public void draw(float[] mvpMatrix) {
    	
    	for (int i = 0 ; i < intCoords.length ; i++ ) {
        	triangleCoords[i] = (float) (intCoords[i]*2f / LiquidWorld.gameWidth ) -1f ;
        	i++ ;
        	triangleCoords[i] = (float) (intCoords[i]*2f / LiquidWorld.gameHeight ) -1f ;
        	
//        	Log.e("triangleERROR", " computX = " + triangleCoords[i-1] + " | computY = " + triangleCoords[i] ) ;
        }
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
        
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);

        // Triangle get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//        LiquidWarRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
//        LiquidWarRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
