package com.marsofandrew.test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jogamp.opengl.*;
import javax.swing.JFrame;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.marsofandrew.helpers.Helpers;

/**
 * This program demonstrates a single modeling transformation, glScalef() and a
 * single viewing transformation, gluLookAt(). A wireframe cube is rendered.
 *
 * @author Kiet Le (Java port) Ported to JOGL 2.x by Claudio Eduardo Goes
 */
public class Cube //
    implements GLEventListener, KeyListener {
  private GLU glu;
  private GLUT glut;


  public static void main(String[] args) {

    Cube demo = new Cube();
    Frame frame  =new Frame("cube");
    GLProfile glprofile = GLProfile.getDefault();
    GLCapabilities glcapabilities = new GLCapabilities(glprofile);
    final GLCanvas glcanvas = new GLCanvas(glcapabilities);
    glcanvas.addGLEventListener(demo);
    Helpers.showFrame(frame, glcanvas, new Dimension(640, 480));
  }

  public void init(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();
    glu = new GLU();
    glut = new GLUT();
    //
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glShadeModel(GL2.GL_FLAT);
  }

  /*
   * Clear the screen. Set the current color to white. Draw the wire frame
   * cube.
   */
  public void display(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();
    //
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    gl.glColor3f(1.0f, 1.0f, 1.0f);
    gl.glLoadIdentity(); /* clear the matrix */
    /* viewing transformation */
    glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
    gl.glScalef(1.0f, 2.0f, 1.0f); /* modeling transformation */
    glut.glutWireCube(1.0f);
    gl.glFlush();

  }

  /*
   * Called when the window is first opened and whenever the window is
   * reconfigured (moved or resized).
   */
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    GL2 gl = drawable.getGL().getGL2();
    //
    gl.glMatrixMode(GL2.GL_PROJECTION); /* prepare for and then */
    gl.glLoadIdentity(); /* define the projection */
    gl.glFrustum(-1.0, 1.0, -1.0, 1.0, 1.5, 20.0); /* transformation */
    gl.glMatrixMode(GL2.GL_MODELVIEW); /* back to modelview matrix */
    gl.glViewport(0, 0, w, h); /* define the viewport */
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
      boolean deviceChanged) {
  }

  public void keyTyped(KeyEvent arg0) {
  }

  public void keyPressed(KeyEvent key) {
    switch (key.getKeyCode()) {
      case KeyEvent.VK_ESCAPE:
        System.exit(0);
      default:
        break;
    }
  }

  public void keyReleased(KeyEvent arg0) {
  }

  public void dispose(GLAutoDrawable arg0) {

  }

}
