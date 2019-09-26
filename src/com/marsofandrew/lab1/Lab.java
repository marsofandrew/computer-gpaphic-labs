package com.marsofandrew.lab1;

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
      gl2.glMatrixMode(GL2.GL_PROJECTION);
      glu.gluPerspective(10, 1, 1, 300);
      gl2.glMatrixMode(GL2.GL_MATRIX_MODE);
      glu.gluLookAt(10, 10, 0, 0, 0, 0, 1, 0, 0);
    });

    scene.addFrame(
        new Shape(gl2 -> glut.glutWireTeapot(0.25))
            .translate(0, -0.3, 0)
            .setColor(1, 1, 1),
        new Shape(gl2 -> glut.glutWireCube(1f))
            .setColor(1, 1, 0)
            .translate(0, 0, -0.5)
    );
    scene.addFrame(
        new Shape(gl2 -> glut.glutWireTeapot(0.25))
            .translate(0, -0.2, 0)
            .setColor(1, 1, 1)
            .rotateAroundAxis(Axis.X, 30, 0, -0.2, 0),
        new Shape(gl2 -> glut.glutWireCube(1f))
            .setColor(1, 1, 0)
            .translate(0, 0, -0.5)
    );
    scene.addFrame(
        new Shape(gl2 -> glut.glutWireCone(0.2, 0.6, 50, 50))
            .translate(0, -0.6, 0)
            .rotate(-90, Axis.X)
            .setColor(0.5f, 1, 1),
        new Shape(gl2 -> glut.glutWireSphere(0.25, 50, 50))
            .setColor(1, 0, 0)
    );

    scene.addFrame(
        new Shape(gl2 -> glut.glutWireCone(0.2, 0.6, 50, 50))
            .translate(0, -0.6, 0)
            .translate(0, -0.4, 0)
            .rotate(-90, Axis.X)
            .setColor(0.5f, 1, 1),
        new Shape(gl2 -> glut.glutWireSphere(0.25, 50, 50))
            .setColor(1, 0, 0)
            .scale(1.5, 1.5, 1.5)
    );

    scene.addFrame(
        new Shape(gl2 -> glut.glutWireTetrahedron())
            .setColor(1, 0, 1)
            .scale(0.5, 0.5, 0.5)
    );
    Helpers.showFrame(scene.getFrame("Lab 1"), scene.getScene(), new Dimension(800, 600));
  }
}
