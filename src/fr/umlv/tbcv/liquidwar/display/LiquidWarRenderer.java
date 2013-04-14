package fr.umlv.tbcv.liquidwar.display;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class LiquidWarRenderer implements GLSurfaceView.Renderer {
	
	LiquidWorld gameWorld ;

    private static final String TAG = "LiquidWarRenderer";
    private ArmiesGL armies ;

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
        gameWorld = new LiquidWorld() ;
     		
        // Initialize objects to be drawn
        armies = new ArmiesGL( gameWorld.getArmies() ) ;
        
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
        
        Matrix.multiplyMM(mMVPMatrix, 0, mOrthoMatrix, 0, mMVPMatrix, 0) ;
        

     // Draw soldiers
        armies.draw(mMVPMatrix); 
        
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
