package com.marsofandrew.test;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OneTriangleAWT {

  public static void main( String [] args ) {
    GLProfile glprofile = GLProfile.getDefault();
    GLCapabilities glcapabilities = new GLCapabilities( glprofile );
    final GLCanvas glcanvas = new GLCanvas( glcapabilities );

    glcanvas.addGLEventListener( new GLEventListener() {

      @Override
      public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
        OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
      }

      @Override
      public void init( GLAutoDrawable glautodrawable ) {
      }

      @Override
      public void dispose( GLAutoDrawable glautodrawable ) {
      }

      @Override
      public void display( GLAutoDrawable glautodrawable ) {
        OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight() );
      }
    });


    final Frame frame = new Frame( "One Triangle AWT" );
    frame.add( glcanvas );
    frame.addWindowListener( new WindowAdapter() {
      public void windowClosing( WindowEvent windowevent ) {
        frame.remove( glcanvas );
        frame.dispose();
        System.exit( 0 );
      }
    });

    frame.setSize( 640, 480 );
    frame.setVisible( true );
  }
}