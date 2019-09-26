package com.marsofandrew.helpers;

import com.jogamp.opengl.GL2;

import java.util.List;

import static com.jogamp.opengl.GL2.GL_ALL_ATTRIB_BITS;

public final class Shapes {

  public static void draw(GL2 gl2, List<? extends OGLDrawable> displayables) {
    for (OGLDrawable displayable : displayables) {
      draw(gl2, displayable);
    }
  }

  public static void draw(GL2 gl2, OGLDrawable drawable) {
    gl2.glPushMatrix();
    drawable.draw(gl2);
    gl2.glPopMatrix();
  }


  private Shapes(){
    throw new UnsupportedOperationException();
  }
}
