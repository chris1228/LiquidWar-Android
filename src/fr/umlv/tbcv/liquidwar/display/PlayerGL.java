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
import java.nio.IntBuffer;

import android.opengl.GLES20;
import android.util.Log;

import fr.umlv.tbcv.liquidwar.input.GameInput;
import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;
import fr.umlv.tbcv.liquidwar.logic.Player;

/**
 * Draw players cursor on the screen with OpenGL ES
 */
public class PlayerGL {
	private FloatBuffer vertexBuffer ;
	private int mMVPMatrixHandle;
    private int nbPlayers = 1 ;
    private Player[] players ;
    float xFactor = 2f / LiquidWorld.gameWidth ;
    float yFactor = 2f / LiquidWorld.gameHeight ;
    private float pointCoords[] ;

	// Number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 2 ;

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +

            "attribute vec4 vPosition;" +
            "void main() {" +
            // the matrix must be included as a modifier of gl_Position
            "  gl_Position = vPosition * uMVPMatrix;" +
            "  gl_PointSize = 6.0 ; " +
            "}";

	private final String fragmentShaderCode =
		    "precision mediump float;" +
		    "uniform vec4 vColor;" +
		    "void main() {" +
		    "  gl_FragColor = vColor;" +
		    "}";

	private int mProgram;

	private int mPositionHandle;

	private int mColorHandle;
	
	private int vertexCount ;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
	
	public PlayerGL(Player[] players, int nbPlayers) {
        this.players = players ;
		this.nbPlayers = nbPlayers ;
		pointCoords = new float[2*nbPlayers] ;
        vertexCount = (2*nbPlayers) / COORDS_PER_VERTEX ;
		
		// Allocating number of element in the array * 4 bytes ( Size of a float ) 
		ByteBuffer bb = ByteBuffer.allocateDirect( pointCoords.length * 4 ) ;
		bb.order(ByteOrder.nativeOrder() ) ;
		
		vertexBuffer = bb.asFloatBuffer() ;
		vertexBuffer.put( pointCoords ) ;
		vertexBuffer.position(0) ;
		
		 int vertexShader = LiquidWarRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		 int fragmentShader = LiquidWarRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

		 mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
		 GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
		 GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
		 GLES20.glLinkProgram(mProgram);                  // creates OpenGL ES program executables
		
	}
	
	public void draw(float [] mvpMatrix ) {
		
		// Update the float array with the players position
        int i = 0 ;
        for(Player p : players) {
            pointCoords[i++] = (float)(p.getPosition().getX() * xFactor) - 0.98f ;
            pointCoords[i++] = (float)(p.getPosition().getY() * yFactor) - 0.98f;
        }
		vertexBuffer.put( pointCoords ) ;
		vertexBuffer.position(0) ;
		
//		Log.e("Array", Arrays.toString( armies.getFightersPosition() ) ) ;
		
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
        GLES20.glUniform4fv(mColorHandle, 1, Colors.getColor(0), 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//        LiquidWarRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
//        LiquidWarRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the points
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
	}

	

}
