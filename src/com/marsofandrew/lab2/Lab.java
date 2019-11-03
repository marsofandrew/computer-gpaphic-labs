package com.marsofandrew.lab2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import com.marsofandrew.helpers.Axis;
import com.marsofandrew.helpers.Helpers;
import com.marsofandrew.helpers.OGLAction;
import com.marsofandrew.helpers.Scene;
import com.marsofandrew.helpers.Shape;
import com.marsofandrew.helpers.Shapes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicReference;

import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FRONT;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TRUE;
import static com.jogamp.opengl.GL2.GL_BLEND;
import static com.jogamp.opengl.GL2.GL_SRC_ALPHA;
import static com.jogamp.opengl.GL2ES1.GL_LIGHT_MODEL_TWO_SIDE;
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

public class Lab {
  private static final GLUT glut = new GLUT();
  private static final GLU glu = new GLU();
  private static FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{-10, 3, 2});
  private static FloatBuffer ambient = FloatBuffer.wrap(new float[]{0, 0, 0, 1f});
  private static FloatBuffer lightDiffuse = FloatBuffer.wrap(new float[]{1f, 1f, 1f, 1f});
  private static final float DIFFERENCE = 1f;
  private static final float COLOR_DIFF = 0.05f;

  public static void main(String[] args) {
    Scene scene = new Scene();
    scene.setInit(gl2 -> {
      gl2.glEnable(GL_BLEND);
      gl2.glEnable(GL_COLOR_MATERIAL);
      gl2.glEnable(GL_DEPTH_TEST);
      gl2.glEnable(GL_LIGHTING);
      gl2.glEnable(GL_LIGHT0);
      gl2.glLightModelf(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);
      gl2.glEnable(GL_NORMALIZE);
      gl2.glBlendFunc(GL_SRC_ALPHA, GL_SRC_ALPHA);
      gl2.glEnable(GL_TEXTURE_2D);
      gl2.glEnable(GL2.GL_BLEND);
      //gl2.glDisable(GL_CULL_FACE);
      gl2.glFrontFace(GL_CCW);
    });
    scene.setBeforeDisplay(createBeforeAction(lightPosition, lightDiffuse, ambient));

    scene.addFrame(
        new Shape(gl2 -> glut.glutSolidTeapot(0.3))
            .setColorRGB(1, 0, 1)
            .translate(0.3, 0, -1),
        new Shape(gl2 -> glut.glutSolidCube(0.5f))
            .setColorARGB(0.5 ,1, 0, 1)
            .translate(-0.65, 0, -0.5),
        new Shape(gl2 -> glut.glutSolidSphere(0.25, 50, 50))
            .setColorRGB(1, 0, 1)
            .translate(0.05, 0, 0.2)
            .addAction(gl2 -> gl2.glMaterialf(GL_FRONT, GL_SHININESS, 128))
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_SPECULAR, FloatBuffer.wrap(new float[]{1f, 1f, 1f, 1})))
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_DIFFUSE, FloatBuffer.wrap(new float[]{0f, 0f, 0f, 0f}))),
        new Shape(gl2 -> glut.glutSolidCone(0.25, 1, 50, 50))
            .setColorARGB(1, 1, 1, 1)
            .rotate(-90, Axis.X)
            .translate(0.6, 0, 0.5)
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_AMBIENT, FloatBuffer.wrap(new float[]{0.2f, 0.2f, 0.2f, 1f})))
            //.addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_SPECULAR, FloatBuffer.wrap(new float[]{0f, 0f, 0f, 0})))
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_DIFFUSE, FloatBuffer.wrap(new float[]{0.3f, 0.4f, 0.3f, 1f})))

    );
    scene.addFrame(
        new Shape(gl2 -> {
          Texture texture = null;
          try {
            texture = TextureIO.newTexture(new File("res/text.jpg"), true);
          } catch (IOException e) {
            e.printStackTrace();
          }
          Shapes.addTexture(gl2, texture, gl->glut.glutSolidTeapot(0.3));
        })
            .setColorRGB(1, 0, 1)
            .translate(0.3, 0, -1)
        ,
        new Shape(gl2 -> glut.glutSolidCube(0.5f))
            .setColorARGB(0.5 ,1, 0, 1)
            .translate(-0.65, 0, -0.5)
        ,
        new Shape(gl2 -> glut.glutSolidSphere(0.25, 50, 50))
            .setColorRGB(1, 0, 1)
            .translate(0.05, 0, 0.2)
            .addAction(gl2 -> gl2.glMaterialf(GL_FRONT, GL_SHININESS, 128))
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_SPECULAR, FloatBuffer.wrap(new float[]{1f, 1f, 1f, 1})))
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_DIFFUSE, FloatBuffer.wrap(new float[]{0f, 0f, 0f, 0f}))),
        new Shape(gl2 -> glut.glutSolidCone(0.25, 1, 50, 50))
            .setColorARGB(1, 1, 1, 1)
            .rotate(-90, Axis.X)
            .translate(0.6, 0, 0.5)
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_AMBIENT, FloatBuffer.wrap(new float[]{0.2f, 0.2f, 0.2f, 1f})))
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_SPECULAR, FloatBuffer.wrap(new float[]{0f, 0f, 0f, 0})))
            .addAction(gl2 -> gl2.glMaterialfv(GL_FRONT, GL_DIFFUSE, FloatBuffer.wrap(new float[]{0.3f, 0.4f, 0.3f, 1f})))

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
          case KeyEvent.VK_I:
            if (ambient.get(3) + COLOR_DIFF <= 1) {
              ambient.put(3, ambient.get(3) + COLOR_DIFF);
              scene.getScene().display();
            }
            break;
          case KeyEvent.VK_O:
            if (ambient.get(3) - COLOR_DIFF >= 0) {
              ambient.put(3, ambient.get(3) - COLOR_DIFF);
              scene.getScene().display();
            }
            break;
          case KeyEvent.VK_0:
            lightDiffuse = FloatBuffer.wrap(new float[]{1, 1, 1, 1});
            scene.setBeforeDisplay(createBeforeAction(lightPosition, lightDiffuse, ambient));
            scene.getScene().display();
            break;
          case KeyEvent.VK_1:
            lightDiffuse = FloatBuffer.wrap(new float[]{0, 1, 1, 1});
            scene.setBeforeDisplay(createBeforeAction(lightPosition, lightDiffuse, ambient));
            scene.getScene().display();
            break;
          case KeyEvent.VK_2:
            lightDiffuse = FloatBuffer.wrap(new float[]{0, 0, 1, 1});
            scene.setBeforeDisplay(createBeforeAction(lightPosition, lightDiffuse, ambient));
            scene.getScene().display();
            break;
          case KeyEvent.VK_3:
            lightDiffuse = FloatBuffer.wrap(new float[]{0, 1, 0, 1});
            scene.setBeforeDisplay(createBeforeAction(lightPosition, lightDiffuse, ambient));
            scene.getScene().display();
            break;
          case KeyEvent.VK_4:
            lightDiffuse = FloatBuffer.wrap(new float[]{1, 1, 0, 1});
            scene.setBeforeDisplay(createBeforeAction(lightPosition, lightDiffuse, ambient));
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

  private static OGLAction createBeforeAction(FloatBuffer lightPosition, FloatBuffer lightDiffuse, FloatBuffer ambient) {
    return gl2 -> {
      gl2.glMatrixMode(GL_PROJECTION);
      glu.gluPerspective(90, 0.75, 1, 300);
      gl2.glMatrixMode(GL_MATRIX_MODE);
      glu.gluLookAt(-1.1, 0.8, 1.2, 0, 0, 0, 0, 1, 0);

      gl2.glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse);
      gl2.glLightfv(GL_LIGHT0, GL_POSITION, lightPosition);
      gl2.glLightfv(GL_LIGHT0, GL_AMBIENT, ambient);
      gl2.glLightfv(GL_LIGHT0, GL_SPECULAR, FloatBuffer.wrap(new float[]{1, 1, 1, 1}));
      //gl2.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, FloatBuffer.wrap(new float[]{0, 1, 1}));
    };
  }

}
