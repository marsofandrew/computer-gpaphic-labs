package com.marsofandrew.lab2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.marsofandrew.helpers.Axis;
import com.marsofandrew.helpers.Helpers;
import com.marsofandrew.helpers.Scene;
import com.marsofandrew.helpers.Shape;

import java.awt.*;
import java.nio.FloatBuffer;

import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL2.GL_BLEND;
import static com.jogamp.opengl.GL2.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL2.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL2.GL_SRC_ALPHA;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT_AND_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;

public class Lab {
  private static final GLUT glut = new GLUT();
  private static final GLU glu = new GLU();

  public static void main(String[] args) {

    Scene scene = new Scene();

    scene.setBeforeDisplay(gl2 -> {
      //gl2.glViewport(0, 0, 800, 600);
      gl2.glMatrixMode(GL2.GL_PROJECTION);
      glu.gluPerspective(60, 1, 1, 300);
      gl2.glMatrixMode(GL2.GL_MATRIX_MODE);
      glu.gluLookAt(-2, 1, 2, 0, 0, 0, 0, 1, 0);
      gl2.glEnable(GL_BLEND);
      gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
      gl2.glEnable(GL_COLOR_MATERIAL);
      gl2.glEnable(GL2.GL_DEPTH_TEST);
      gl2.glEnable(GL_LIGHTING);
      gl2.glEnable(GL_LIGHT0);
      gl2.glLightfv(GL_LIGHT0, GL_POSITION, FloatBuffer.wrap(new float[]{-10, 2, 5}));
    });

    scene.addFrame(
        new Shape(gl2 -> glut.glutSolidTeapot(0.3))
            .setColorRGB(1, 0, 1)
            .translate(0.3, 0, -1)
            .addAction(gl2 -> gl2.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 128)),
        new Shape(gl2 -> glut.glutSolidCube(0.5f))
            .setColorARGB(0.5, 1, 0, 0)
            .translate(-0.65, 0, -0.5),
        new Shape(gl2 -> glut.glutSolidSphere(0.25, 50, 50))
            .setColorRGB(0, 1, 1)
            .translate(0.05, 0, 0.2),
        new Shape(gl2 -> glut.glutSolidCone(0.25, 1, 50, 50))
            .setColorARGB(0.25, 0.5, 1, 0)
            .rotate(-90, Axis.X)
            .translate(1, 0, 0)
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT_AND_BACK, GL_SHININESS, FloatBuffer.wrap(new float[]{0, 0, 0})))

    );

    Helpers.showFrame(scene.getFrame("Lab 2"), scene.getScene(), new Dimension(800, 600));
  }
}
