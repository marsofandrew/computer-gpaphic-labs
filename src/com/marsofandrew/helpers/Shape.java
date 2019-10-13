package com.marsofandrew.helpers;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class Shape implements OGLDrawable {

  private final List<OGLAction> actions;
  private final List<OGLAction> afterActions;
  private OGLDrawable drawable;

  public Shape(OGLDrawable drawable) {
    actions = new ArrayList<>();
    afterActions = new ArrayList<>();
    this.drawable = drawable;
  }

  @Override
  public void draw(GL2 gl2) {
    for (OGLAction action : actions) {
      action.doAction(gl2);
    }
    drawable.draw(gl2);
    for (OGLAction action : afterActions) {
      action.doAction(gl2);
    }
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

  public Shape setColorRGB(double red, double green, double blue) {
    actions.add(gl -> gl.glColor3d(red, green, blue));
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

  public Shape setColorARGB(double alpha, double red, double green, double blue) {
    actions.add(gl2 -> gl2.glColor4d(red, green, blue, alpha));
    return this;
  }

  public Shape addAction(OGLAction action) {
    actions.add(action);
    return this;
  }

  public Shape addAfterAction(OGLAction action) {
    afterActions.add(action);
    return this;
  }

  public Shape addTexture(String path) {
    actions.add(gl2 -> {
      Texture texture = null;
      try {
        texture = TextureIO.newTexture(new File(path), false);
      } catch (IOException e) {
        e.printStackTrace();
      }
      TextureCoords texcoords = texture.getImageTexCoords();
      texture.enable(gl2);
      texture.bind(gl2);
      gl2.glTexCoord2f(texcoords.left(), texcoords.top());
      gl2.glTexCoord2f(texcoords.right(), texcoords.top());
      gl2.glTexCoord2f(texcoords.right(), texcoords.bottom());
      texture.disable(gl2);

    });
    return this;
  }

  public Shape rotateAroundAxis(Axis axis, double angle, double currentX, double currentY, double currentZ) {
    double radians = toRadians(angle);
    double newX = currentX;
    double newY = currentY;
    double newZ = currentZ;

    switch (axis) {
      case X:
        newY = currentY * cos(radians) + currentZ * sin(radians);
        newZ = -currentY * sin(radians) + currentZ * cos(radians);
        break;
      case Y:
        newX = currentX * cos(radians) + currentZ * sin(radians);
        newZ = -currentX * sin(radians) + currentZ * cos(radians);
        break;
      case Z:
        newX = currentX * cos(radians) - currentY * sin(radians);
        newY = -currentX * sin(radians) + currentY * cos(radians);
        break;
    }

    return translate(-currentX, -currentY, -currentZ)
        .rotate(angle, axis)
        .translate(newX, newY, newZ);
  }
}
