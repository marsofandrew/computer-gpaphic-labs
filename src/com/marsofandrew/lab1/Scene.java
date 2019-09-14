package com.marsofandrew.lab1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.marsofandrew.helpers.Axis;
import com.marsofandrew.helpers.Helpers;
import com.marsofandrew.helpers.OGLDrawable;
import com.marsofandrew.helpers.Shape;
import com.marsofandrew.helpers.Shapes;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;

public class Scene {
  private static final GLU glu = new GLU();
  private static final GLUT glut = new GLUT();
  private final GLCanvas glcanvas;
  private final List<List<? extends OGLDrawable>> scenes;
  private int sceneIndex = 0;

  public Scene() {
    scenes = new ArrayList<>();
    GLProfile glprofile = GLProfile.getDefault();
    GLCapabilities glcapabilities = new GLCapabilities(glprofile);
    glcanvas = new GLCanvas(glcapabilities);
  }

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

  public Frame getFrame(String name) {
    final Frame frame = new Frame(name);
    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_ESCAPE:
            System.out.println("Program has finished");
            System.exit(0);
            break;
          case KeyEvent.VK_RIGHT:
            sceneIndex = sceneIndex + 1 < scenes.size() ? sceneIndex + 1 : sceneIndex;
            System.out.printf("scene %d is choosed\n", sceneIndex);
            glcanvas.display();
            break;
          case KeyEvent.VK_LEFT:
            sceneIndex = sceneIndex - 1 > 0 ? sceneIndex - 1 : 0;
            System.out.printf("scene %d is choosed\n", sceneIndex);
            glcanvas.display();
            break;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });
    return frame;
  }

  public void addFrame(List<? extends OGLDrawable> list) {
    scenes.add(list);
  }

  public <T extends OGLDrawable> void addFrame(T... list) {
    addFrame(Arrays.asList(list));
  }

  public GLCanvas getScene() {

    glcanvas.addGLEventListener(new GLEventListener() {
      @Override
      public void init(GLAutoDrawable glAutoDrawable) {

      }

      @Override
      public void dispose(GLAutoDrawable glAutoDrawable) {

      }

      @Override
      public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        //gl.glMatrixMode(GL2.GL_MODELVIEW);
        glu.gluLookAt(4, 10, 0, 5, 0, 5, 0, 50, 100);
        if (sceneIndex < scenes.size()) {
          gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
          Shapes.draw(gl, scenes.get(sceneIndex));
        }
      }

      @Override
      public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int w, int h) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      }
    });

    return glcanvas;
  }
}
