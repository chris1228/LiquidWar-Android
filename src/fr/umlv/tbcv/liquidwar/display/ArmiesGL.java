package fr.umlv.tbcv.liquidwar.display;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import android.opengl.GLES20;

import fr.umlv.tbcv.liquidwar.logic.Armies;

public class ArmiesGL {
	
	private Armies armies ;
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
		        0,  6,    // top
		       -5, -3,  // bottom left
		        5, -3    // bottom right
		   };
		    
		    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
		    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

		    // Set color with red, green, blue and alpha (opacity) values
		    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

		    public ArmiesGL(Armies a) {
		    	
		    	armies = a ;
		        // initialize vertex byte buffer for shape coordinates
		        ByteBuffer bb = ByteBuffer.allocateDirect(
		                // (number of coordinate values * 4 bytes per float)
		                triangleCoords.length * 4);
		        // use the device hardware's native byte order
		        bb.order(ByteOrder.nativeOrder());

		        // create a floating point buffer from the ByteBuffer
		        vertexBuffer = bb.asFloatBuffer();
		        
		        for (int i = 0 ; i < intCoords.length ; i++ ) {
		        	triangleCoords[i] = (float) intCoords[i] ;
		        }
		        // add the coordinates to the FloatBuffer
		        vertexBuffer.put(triangleCoords);
		        // set the buffer to read the first coordinate
		        vertexBuffer.position(0);

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
		        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

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
