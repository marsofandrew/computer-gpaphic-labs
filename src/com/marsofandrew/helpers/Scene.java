package com.marsofandrew.helpers;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

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
  private final GLCanvas glcanvas;
  private final List<List<? extends OGLDrawable>> scenes;
  private int sceneIndex = 0;
  private OGLAction beforeDisplay;
  private OGLAction afterDisplay;

  public Scene() {
    scenes = new ArrayList<>();
    GLProfile glprofile = GLProfile.getDefault();
    GLCapabilities glcapabilities = new GLCapabilities(glprofile);
    glcanvas = new GLCanvas(glcapabilities);
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
        gl.glLoadIdentity();
        if (beforeDisplay != null) {
          beforeDisplay.doAction(gl);
        }

        if (sceneIndex < scenes.size()) {
          gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
          Shapes.draw(gl, scenes.get(sceneIndex));
        }

        if (afterDisplay != null) {
          afterDisplay.doAction(gl);
        }
        gl.glFlush();
      }

      @Override
      public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int w, int h) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      }
    });

    return glcanvas;
  }

  /**
   * Setter for the perspectiveTransformation.
   *
   * @param beforeDisplay The perspectiveTransformation.
   * @return This, so the API can be used fluently.
   */
  public Scene setBeforeDisplay(OGLAction beforeDisplay) {
    this.beforeDisplay = beforeDisplay;
    return this;
  }

  /**
   * Getter for the perspectiveTransformation.
   *
   * @return The perspectiveTransformation.
   */
  public OGLAction getBeforeDisplay() {
    return beforeDisplay;
  }

  /**
   * Setter for the afterDisplay.
   *
   * @param afterDisplay The afterDisplay.
   * @return This, so the API can be used fluently.
   */
  public Scene setAfterDisplay(OGLAction afterDisplay) {
    this.afterDisplay = afterDisplay;
    return this;
  }

  /**
   * Getter for the afterDisplay.
   *
   * @return The afterDisplay.
   */
  public OGLAction getAfterDisplay() {
    return afterDisplay;
  }
}
