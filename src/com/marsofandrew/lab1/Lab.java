package com.marsofandrew.lab1;

import com.jogamp.opengl.util.gl2.GLUT;
import com.marsofandrew.helpers.Axis;
import com.marsofandrew.helpers.Helpers;
import com.marsofandrew.helpers.Scene;
import com.marsofandrew.helpers.Shape;

import java.awt.*;

public class Lab {
  private static final GLUT glut = new GLUT();

  public static void main(String[] args) {

    Scene scene = new Scene();

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
            .rotate(30, Axis.X),
        new Shape(gl2 -> glut.glutWireCube(1f))
            .setColor(1, 1, 0)
            .translate(0, 0, -0.5)
    );
    scene.addFrame(
        new Shape(gl2 -> glut.glutWireCone(0.2, 0.8, 50, 50))
            .translate(0.5, 0, 0)
            //.rotate(0, Axis.Z)
            .setColor(1, 1, 1)
    );

    Helpers.showFrame(scene.getFrame("Lab 1"), scene.getScene(), new Dimension(800, 600));
  }
}
