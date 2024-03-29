package com.marsofandrew.lab3;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.marsofandrew.helpers.Axis;
import com.marsofandrew.helpers.Helpers;
import com.marsofandrew.helpers.OGLAction;
import com.marsofandrew.helpers.Scene;
import com.marsofandrew.helpers.Shape;
import com.marsofandrew.term_work.SpecialOctahedron;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.FloatBuffer;

import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_CW;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FRONT;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRUE;
import static com.jogamp.opengl.GL2.GL_AUTO_NORMAL;
import static com.jogamp.opengl.GL2.GL_BLEND;
import static com.jogamp.opengl.GL2.GL_LIGHT_MODEL_COLOR_CONTROL;
import static com.jogamp.opengl.GL2.GL_LIGHT_MODEL_LOCAL_VIEWER;
import static com.jogamp.opengl.GL2.GL_SRC_ALPHA;
import static com.jogamp.opengl.GL2ES1.GL_LIGHT_MODEL_AMBIENT;
import static com.jogamp.opengl.GL2ES1.GL_LIGHT_MODEL_TWO_SIDE;
import static com.jogamp.opengl.GL2GL3.GL_LINE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_NORMALIZE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MATRIX_MODE;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

public class TermWork {
  private static final GLUT glut = new GLUT();
  private static final GLU glu = new GLU();
  private static final float DIFFERENCE = 1;
  private static final int FRAMES = 100;
  private static final int FPS = 1;
  private static FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{5, 6, 10});
  private static FloatBuffer ambient = FloatBuffer.wrap(new float[]{0.1f, 0.1f, 0.1f, 1f});
  private static FloatBuffer lightDiffuse = FloatBuffer.wrap(new float[]{1f, 1f, 1f, 1f});

  public static void main(String[] args) {
    Scene scene = new Scene();
    scene.setInit(gl2 -> {
      gl2.glEnable(GL_BLEND);
      gl2.glEnable(GL_COLOR_MATERIAL);
      gl2.glEnable(GL_DEPTH_TEST);
      gl2.glEnable(GL_LIGHTING);
      gl2.glEnable(GL_LIGHT0);
      gl2.glLightModelf(GL_LIGHT_MODEL_COLOR_CONTROL, GL_TRUE);
      gl2.glEnable(GL_NORMALIZE);
      gl2.glEnable(GL_AUTO_NORMAL);
      gl2.glEnable(GL_TEXTURE_2D);
      gl2.glEnable(GL2.GL_BLEND);
      gl2.glFrontFace(GL_CW);
    });
    scene.setBeforeDisplay(createBeforeAction(lightPosition, lightDiffuse, ambient));

    for (int i = 0; i <= FRAMES; i++) {
      scene.addFrame(new SpecialOctahedron().changeState(i),
      new Shape(gl -> glut.glutSolidCube(3)).translate(0,-2.55,0));
    }

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
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });

    Helpers.showAnimatesFrame(scene, "Term work", new Dimension(800, 800), FPS);
  }

  private static OGLAction createBeforeAction(FloatBuffer lightPosition, FloatBuffer lightDiffuse, FloatBuffer ambient) {
    return gl2 -> {
      gl2.glMatrixMode(GL_PROJECTION);
      glu.gluPerspective(90, 0.75, 1, 300);
      gl2.glMatrixMode(GL_MATRIX_MODE);
      glu.gluLookAt(0, 0, 3, 0, 0, 0, 0, 1, 0);

      gl2.glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse);
      gl2.glLightfv(GL_LIGHT0, GL_POSITION, lightPosition);
      gl2.glLightfv(GL_LIGHT0, GL_AMBIENT, ambient);
      gl2.glLightfv(GL_LIGHT0, GL_SPECULAR, FloatBuffer.wrap(new float[]{1, 1, 1, 0}));
      gl2.glMaterialf(GL_FRONT, GL_SHININESS, 128);
      gl2.glMaterialfv(GL_FRONT, GL_SPECULAR, FloatBuffer.wrap(new float[]{1f, 1f, 1f, 1}));
      gl2.glMaterialfv(GL_FRONT, GL_DIFFUSE, FloatBuffer.wrap(new float[]{0f, 0f, 0f, 0f}));
      gl2.glColor4d(1, 1, 1, 1);
      //gl2.glScaled(0.4, 0.4, 0.4);
    };
  }

}
