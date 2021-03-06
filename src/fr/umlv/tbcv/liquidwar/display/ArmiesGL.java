/*
 Copyright (C) 2013 Thomas Bardoux, Christophe Venevongsos

 This file is part of Liquid Wars Android

 Liquid Wars Android is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Liquid Wars Android is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

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

		    private FloatBuffer vertexBuffer;
		    private final int mProgram;
		    private int mPositionHandle;
		    private int mColorHandle;
		    private int mMVPMatrixHandle;

		    // number of coordinates per vertex in this array
		    static final int COORDS_PER_VERTEX = 2;
		    
		    private int vertexCount ;
		    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

		    // Set color with red, green, blue and alpha (opacity) values

		    public ArmiesGL(Armies a) {
		    	
		    	armies = a ;

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

            for(int j = 0 ; j < armies.getArmiesNumber() ; j++ ) {
                float[] pointsCoords = new float[armies.getFightersNumber(j) * 2];
                vertexCount = armies.getFightersNumber(j) * 2 / COORDS_PER_VERTEX;

                // initialize vertex byte buffer for shape coordinates
                ByteBuffer bb = ByteBuffer.allocateDirect(
                        // (number of coordinate values * 4 bytes per float)
                        armies.getFightersNumber(j) *2 * 4);
                // use the device hardware's native byte order
                bb.order(ByteOrder.nativeOrder());

                // create a floating point buffer from the ByteBuffer
                vertexBuffer = bb.asFloatBuffer();

                int[] fightersIntPosition = armies.getFightersPosition(j) ;

                    for (int i = 0 ; i < armies.getFightersNumber(j) *2 ; i++ ) {
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
                    GLES20.glUniform4fv(mColorHandle, 1, Colors.getTeamColor(j), 0); // Every team has a different color

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

}
