package com.marsofandrew.lab2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.marsofandrew.helpers.Axis;
import com.marsofandrew.helpers.Helpers;
import com.marsofandrew.helpers.Scene;
import com.marsofandrew.helpers.Shape;

import java.awt.*;

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
      glu.gluLookAt(-3, 1.5, 4, 0, 0, 0, 0, 1, 0);
    });

    scene.addFrame(
        new Shape(gl2 -> glut.glutSolidTeapot(0.3))
            .setColor(1, 0, 1)
            .translate(0.5, 0, -1),
        new Shape(gl2 -> glut.glutSolidCube(0.5f))
            .setColor(1, 0, 0)
            .translate(-0.65, 0, -0.1),
        new Shape(gl2 -> glut.glutSolidSphere(0.25, 50, 50))
            .setColor(0, 1, 1)
            .translate(0.05, 0, 0.2),
        new Shape(gl2 -> glut.glutSolidCone(0.25, 1, 50, 50))
            .setColor(0, 1, 0)
            .rotate(-90, Axis.X)
        .translate(1, 0, 0)

    );

    Helpers.showFrame(scene.getFrame("Lab 1"), scene.getScene(), new Dimension(800, 600));
  }
}
