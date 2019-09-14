package com.marsofandrew.helpers;

import com.jogamp.opengl.GL2;

@FunctionalInterface
public interface OGLAction {
  void doAction(GL2 gl2);
}
