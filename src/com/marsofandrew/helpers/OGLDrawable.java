package com.marsofandrew.helpers;

import com.jogamp.opengl.GL2;

import java.util.List;

@FunctionalInterface
public interface OGLDrawable {
  void draw(GL2 gl2);
}
