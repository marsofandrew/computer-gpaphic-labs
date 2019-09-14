package com.marsofandrew.helpers;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

public class Shape implements OGLDrawable {

  private List<OGLAction> actions;
  private OGLDrawable drawable;

  public Shape(OGLDrawable drawable) {
    actions = new ArrayList<>();
    this.drawable = drawable;
  }

  @Override
  public void draw(GL2 gl2) {
    for (OGLAction action : actions) {
      action.doAction(gl2);
    }
    drawable.draw(gl2);
  }

  public Shape rotate(double angle, Axis axis) {
    actions.add(gl -> {
      switch (axis) {
        case X:
          gl.glRotated(angle, 1, 0, 0);
          break;
        case Y:
          gl.glRotated(angle, 0, 1, 0);
          break;
        case Z:
          gl.glRotated(angle, 0, 0, 1);
          break;
      }
    });
    return this;
  }


  public Shape setColor(float red, float green, float blue) {
    actions.add(gl -> gl.glColor3f(red, green, blue));
    return this;
  }

  public Shape translate(double x, double y, double z) {
    actions.add(gl2 -> gl2.glTranslated(x, y, z));
    return this;
  }

  public Shape scale(double scaleX, double scaleY, double scaleZ) {
    actions.add(gl2 -> gl2.glScaled(scaleX, scaleY, scaleZ));
    return this;
  }

  public Shape addaction(OGLAction action) {
    actions.add(action);
    return this;
  }

}
