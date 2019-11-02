package com.marsofandrew.helpers;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import java.util.List;

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

  public static void addTexture(GL2 gl2, Texture texture, OGLDrawable drawable){
    texture.enable(gl2);
    texture.bind(gl2);
    drawable.draw(gl2);
    texture.disable(gl2);
    texture.destroy(gl2);
  }

  private Shapes(){
    throw new UnsupportedOperationException();
  }
}
