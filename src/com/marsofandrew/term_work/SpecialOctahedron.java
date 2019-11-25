package com.marsofandrew.term_work;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.marsofandrew.helpers.Axis;
import com.marsofandrew.helpers.OGLDrawable;
import com.marsofandrew.helpers.Shape;
import com.marsofandrew.helpers.Shapes;

import java.io.File;
import java.io.IOException;

public class SpecialOctahedron implements OGLDrawable {
  private static final GLUT glut = new GLUT();
  private static final GLU glu = new GLU();
  private final double size = Math.sqrt(2);
  private static final double K = Math.sqrt(2) / 2;
  Shape shape = new Shape(gl2 -> glut.glutSolidOctahedron()).rotate(45, Axis.Y);

  @Override
  public void draw(GL2 gl2) {
    Texture texture = null;
    try {
      texture = TextureIO.newTexture(new File("res/text.jpg"), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Shapes.addTexture(gl2,texture , shape);
  }

  public SpecialOctahedron changeState(int state) {
    if (state > 10) {
      state = 3 + (state - 3) % 8;
    }
    switch (state) {
      case 0:
        shape = new Shape(gl2 -> glut.glutSolidOctahedron()).rotate(45, Axis.Y);
        break;

      case 1: {
        double degree = Math.toDegrees(Math.PI / 2 - Math.toRadians(75));
        shape.translate(K * Math.sin(Math.toRadians(degree)) * size * K, (Math.cos(Math.toRadians(degree)) - 1) * K * size,
            K * Math.sin(Math.toRadians(degree)) * size * K)
            .addAction(gl2 -> gl2.glRotated(degree, 1, 0, -1));
      }
      break;
      case 2: {
        double degree = Math.toDegrees(Math.PI / 2 - Math.toRadians(65));
        shape.translate(K * Math.sin(Math.toRadians(degree)) * size * K, (Math.cos(Math.toRadians(degree)) - 1) * K * size,
            K * Math.sin(Math.toRadians(degree)) * size * K)
            .addAction(gl2 -> gl2.glRotated(degree, 1, 0, -1));
      }
      break;

      case 3: {
        double degree = Math.toDegrees(Math.PI / 2 - Math.asin(1 / Math.sqrt(3)));
        shape.translate(K * Math.sin(Math.toRadians(degree)) * size * K, (Math.cos(Math.toRadians(degree)) - 1) * K * size,
            K * Math.sin(Math.toRadians(degree)) * size * K)
            .addAction(gl2 -> gl2.glRotated(degree, 1, 0, -1));
      }
      break;
      case 4: {
        shape.translate(0, (Math.cos(Math.toRadians(45)) - 1) * K * size, K * size * Math.sin(Math.toRadians(45)))
            .rotate(45, Axis.X);
      }
      break;
      case 5: {
        double degree = Math.toDegrees(Math.PI / 2 - Math.asin(1 / Math.sqrt(3)));
        shape.translate(-K * Math.sin(Math.toRadians(degree)) * size * K, (Math.cos(Math.toRadians(degree)) - 1) * K * size,
            K * Math.sin(Math.toRadians(degree)) * size * K)
            .addAction(gl2 -> gl2.glRotated(degree, 1, 0, 1));
      }
      break;
      case 6: {
        double degree = -45;
        shape.translate(K * size * Math.sin(Math.toRadians(degree)), (Math.cos(Math.toRadians(degree)) - 1) * K * size, 0)
            .rotate(45, Axis.Z);
      }
      break;
      case 7: {
        double degree = Math.toDegrees(Math.PI / 2 - Math.asin(1 / Math.sqrt(3)));
        shape.translate(-K * Math.sin(Math.toRadians(degree)) * size * K, (Math.cos(Math.toRadians(degree)) - 1) * K * size,
            -K * Math.sin(Math.toRadians(degree)) * size * K)
            .addAction(gl2 -> gl2.glRotated(degree, -1, 0, 1));
      }
      break;
      case 8: {
        double degree = 45;
        shape.translate(0, (Math.cos(Math.toRadians(degree)) - 1) * K * size, -K * size * Math.sin(Math.toRadians(degree)))
            .rotate(45, Axis.X);
      }
      break;
      case 9: {
        double degree = Math.toDegrees(Math.PI / 2 - Math.asin(1 / Math.sqrt(3)));
        shape.translate(K * Math.sin(Math.toRadians(degree)) * size * K, (Math.cos(Math.toRadians(degree)) - 1) * K * size,
            -K * Math.sin(Math.toRadians(degree)) * size * K)
            .addAction(gl2 -> gl2.glRotated(degree, -1, 0, -1));
      }
      break;
      case 10: {
        double degree = 45;
        shape.translate(K * size * Math.sin(Math.toRadians(degree)), (Math.cos(Math.toRadians(degree)) - 1) * K * size, 0)
            .rotate(45, Axis.Z);
      }
      break;
    }

    return this;
  }
}
