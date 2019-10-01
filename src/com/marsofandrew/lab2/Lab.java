package com.marsofandrew.lab2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.marsofandrew.helpers.Axis;
import com.marsofandrew.helpers.Helpers;
import com.marsofandrew.helpers.OGLAction;
import com.marsofandrew.helpers.Scene;
import com.marsofandrew.helpers.Shape;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.FloatBuffer;

import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_FRONT;
import static com.jogamp.opengl.GL2.GL_BLEND;
import static com.jogamp.opengl.GL2.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL2.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL2.GL_SRC_ALPHA;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;

public class Lab {
  private static final GLUT glut = new GLUT();
  private static final GLU glu = new GLU();
  private static final FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{-10, 3, 2});
  private static final float DIFFERENCE = 1f;

  public static void main(String[] args) {

    Scene scene = new Scene();

    scene.setBeforeDisplay(createBeforeAction(lightPosition));

    scene.addFrame(
        new Shape(gl2 -> glut.glutSolidTeapot(0.3))
            .setColorARGB(1, 1, 0, 1)
            .translate(0.3, 0, -1)
            .addAction(gl2 -> gl2.glMaterialf(GL_FRONT, GL_SHININESS, 128)),
        new Shape(gl2 -> glut.glutSolidCube(0.5f))
            .setColorARGB(0.5, 1, 0, 0)
            .translate(-0.65, 0, -0.5),
        new Shape(gl2 -> glut.glutSolidSphere(0.25, 50, 50))
            .setColorRGB(0, 1, 1)
            .translate(0.05, 0, 0.2)
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, FloatBuffer.wrap(new float[]{0.2f, 0.2f, 0.2f, 1}))),
        new Shape(gl2 -> glut.glutSolidCone(0.25, 1, 50, 50))
            .setColorARGB(0.25, 0.5, 1, 0)
            .rotate(-90, Axis.X)
            .translate(1, 0, 0)
    );

    scene.setKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        scene.autoChangeFrame(e);
        switch (e.getKeyCode()) {
          case KeyEvent.VK_W:
            lightPosition.put(1, lightPosition.get(1) + DIFFERENCE);
            scene.getScene().display();
            break;
          case KeyEvent.VK_S:
            lightPosition.put(1, lightPosition.get(1) - DIFFERENCE);
            scene.getScene().display();
            break;
          case KeyEvent.VK_A:
            lightPosition.put(0, lightPosition.get(0) - DIFFERENCE);
            scene.getScene().display();
            break;
          case KeyEvent.VK_D:
            lightPosition.put(0, lightPosition.get(0) + DIFFERENCE);
            scene.getScene().display();
            break;
          case KeyEvent.VK_Z:
            lightPosition.put(2, lightPosition.get(2) + DIFFERENCE);
            scene.getScene().display();
            break;
          case KeyEvent.VK_X:
            lightPosition.put(2, lightPosition.get(2) - DIFFERENCE);
            scene.getScene().display();
            break;
        }
        System.out.printf("Light pos: %f, %f, %f\n", lightPosition.get(0), lightPosition.get(1),
            lightPosition.get(2));
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });


    Helpers.showFrame(scene.getFrame("Lab 2"), scene.getScene(), new Dimension(800, 600));
  }

  private static OGLAction createBeforeAction(FloatBuffer lightPosition) {
    return gl2 -> {
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
      gl2.glLightfv(GL_LIGHT0, GL_POSITION, lightPosition);
      gl2.glEnable(GL_CULL_FACE);
    };
  }

}
