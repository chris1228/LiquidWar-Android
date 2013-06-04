package fr.umlv.tbcv.liquidwar.display;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;


import fr.umlv.tbcv.liquidwar.logic.Armies;
import fr.umlv.tbcv.liquidwar.logic.SimpleArmies;
import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;

/**
 * Draw soldiers on the screen using OpenGL ES
 */
public class ArmiesGL {
	
	private Armies armies ;
	
	float xFactor = 2f / LiquidWorld.gameWidth ;
	float yFactor = 2f / LiquidWorld.gameHeight ;
	
	 private final String vertexShaderCode =
		        // This matrix member variable provides a hook to manipulate
		        // the coordinates of the objects that use this vertex shader
		        "uniform mat4 uMVPMatrix;" +

		        "attribute vec4 vPosition;" +
		        "void main() {" +
		        // the matrix must be included as a modifier of gl_Position
		        "  gl_Position = vPosition * uMVPMatrix;" +
		        "  gl_PointSize = 4.0 ; " +
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
		    private float[] pointsCoords ;	
		    
		    private final int vertexCount ;
		    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

		    // Set color with red, green, blue and alpha (opacity) values

		    public ArmiesGL(Armies a) {
		    	
		    	pointsCoords = new float[SimpleArmies.fighterNumber * 2 ] ;
		    	
		    	armies = a ;
		    	vertexCount = armies.getFightersNumber() * 2 / COORDS_PER_VERTEX;
		    	
		        // initialize vertex byte buffer for shape coordinates
		        ByteBuffer bb = ByteBuffer.allocateDirect(
		                // (number of coordinate values * 4 bytes per float)
		                armies.getFightersNumber() *2 * 4);
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
		    	
		    int[] fightersIntPosition = armies.getFightersPosition() ;
		    	
		    	for (int i = 0 ; i < armies.getFightersNumber() *2 ; i++ ) {
			        	pointsCoords[i] = (float) (fightersIntPosition[i]* xFactor) - 0.98f ;
			        	i++ ;
			        	pointsCoords[i] = (float) (fightersIntPosition[i]* yFactor) - 0.98f ;
			        }
			        // add the coordinates to the FloatBuffer
			        vertexBuffer.put(pointsCoords);
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

		        // get handle to fragment shader's vColor member
		        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

		        // Set color for drawing the triangle
		        GLES20.glUniform4fv(mColorHandle, 1, Colors.getColor(1), 0);

		        // get handle to shape's transformation matrix
		        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//		        LiquidWarRenderer.checkGlError("glGetUniformLocation");

		        // Apply the projection and view transformation
		        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
//		        LiquidWarRenderer.checkGlError("glUniformMatrix4fv");

		        // Draw the triangle
		        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vertexCount);

		        // Disable vertex array
		        GLES20.glDisableVertexAttribArray(mPositionHandle);
		    }

}
