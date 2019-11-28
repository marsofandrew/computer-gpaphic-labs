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
  private OGLAction init;
  private OGLAction afterDisplay;
  private KeyListener keyListener;
  private boolean isAnimated;

  public Scene() {
    scenes = new ArrayList<>();
    GLProfile glprofile = GLProfile.getDefault();
    GLCapabilities glcapabilities = new GLCapabilities(glprofile);
    glcanvas = new GLCanvas(glcapabilities);
    this.keyListener = new BaseKeyListener();
    this.isAnimated = false;
  }

  /**
   * Getter for the sceneIndex.
   *
   * @return The sceneIndex.
   */
  public int getSceneIndex() {
    return sceneIndex;
  }

  /**
   * Setter for the sceneIndex.
   *
   * @param sceneIndex The sceneIndex.
   * @return This, so the API can be used fluently.
   */
  public Scene setSceneIndex(int sceneIndex) {
    this.sceneIndex = sceneIndex;
    return this;
  }

  public Frame getFrame(String name) {
    final Frame frame = new Frame(name);
    frame.addKeyListener(keyListener);
    return frame;
  }

  public Scene addFrame(List<? extends OGLDrawable> list) {
    scenes.add(list);
    return this;
  }

  /**
   * Setter for the init.
   *
   * @param init The init.
   * @return This, so the API can be used fluently.
   */
  public Scene setInit(OGLAction init) {
    this.init = init;
    return this;
  }

  /**
   * Getter for the init.
   *
   * @return The init.
   */
  public OGLAction getInit() {
    return init;
  }

  public <T extends OGLDrawable> Scene addFrame(T... list) {
    return addFrame(Arrays.asList(list));
  }

  public GLCanvas getScene() {

    glcanvas.addGLEventListener(new GLEventListener() {
      @Override
      public void init(GLAutoDrawable glAutoDrawable) {
        init.doAction(glAutoDrawable.getGL().getGL2());
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
        if (isAnimated) {
          nextFrame(Direction.NEXT);
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

  /**
   * Getter for the keyListener.
   *
   * @return The keyListener.
   */
  public KeyListener getKeyListener() {
    return keyListener;
  }

  /**
   * Setter for the keyListener.
   *
   * @param keyListener The keyListener.
   * @return This, so the API can be used fluently.
   */
  public Scene setKeyListener(KeyListener keyListener) {
    this.keyListener = keyListener;
    return this;
  }

  public void nextFrame(Direction direction) {
    int shift = direction.getStep();
    sceneIndex += shift;
    if (sceneIndex >= scenes.size()) {
      sceneIndex -= 1;
    } else if (sceneIndex < 0) {
      sceneIndex = 0;
    }
  }

  public void autoChangeFrame(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ESCAPE:
        System.out.println("Program has finished");
        System.exit(0);
        break;
      case KeyEvent.VK_RIGHT:
        nextFrame(Direction.NEXT);
        System.out.printf("scene %d is choosed\n", sceneIndex);
        glcanvas.display();
        break;
      case KeyEvent.VK_LEFT:
        nextFrame(Direction.PREV);
        System.out.printf("scene %d is choosed\n", sceneIndex);
        glcanvas.display();
        break;
    }
  }

  public void enableAnimation() {
    isAnimated = true;
  }

  public void disableAnimation() {
    isAnimated = false;
  }

  class BaseKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
      autoChangeFrame(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
  }

  enum Direction {
    PREV(-1),
    NEXT(1);
    private int step;

    Direction(int step) {
      this.step = step;
    }

    /**
     * Getter for the step.
     *
     * @return The step.
     */
    int getStep() {
      return step;
    }
  }
}
