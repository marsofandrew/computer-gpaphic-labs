package com.marsofandrew.lab3;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.marsofandrew.helpers.OGLDrawable;

import java.nio.DoubleBuffer;
import java.util.List;

import static com.jogamp.opengl.GL2.GL_MAP2_VERTEX_3;
import static com.jogamp.opengl.GL2GL3.GL_FILL;

public class BezierSurface implements OGLDrawable {
  private static final GLUT glut = new GLUT();
  private static final GLU glu = new GLU();
  private final List<DoubleBuffer> points;
  private final int u; //x
  private final int v; //y
  private final boolean shouldDrawBasePoints;
  private final int drawType;
  private final int gridSize;

  public BezierSurface(List<DoubleBuffer> points, int u, int v, int gridSize) {
    this(points, u, v, gridSize, GL_FILL);
  }

  public BezierSurface(List<DoubleBuffer> points, int u, int v, int gridSize, int drawType) {
    this(points, u, v, gridSize, drawType, false);
  }

  public BezierSurface(List<DoubleBuffer> points, int u, int v, int gridSize, int drawType, boolean shouldDrawBasePoints) {
    this.points = points;
    this.u = u;
    this.v = v;
    this.drawType = drawType;
    this.gridSize = gridSize;
    this.shouldDrawBasePoints = shouldDrawBasePoints;
  }

  @Override
  public void draw(GL2 gl2) {
    gl2.glEnable(GL_MAP2_VERTEX_3);
    if (shouldDrawBasePoints) {
      drawControls(gl2);
    }

    gl2.glMap2d(GL_MAP2_VERTEX_3, 0.0f, 1.0f, 3, u, 0.0f, 1.0f, 3 * u, v, joinBuffer(points).array(), 0);
    gl2.glMapGrid2d(gridSize, 0.0f, 1.0f, gridSize, 0.0f, 1.0f);
    gl2.glEvalMesh2(drawType, 0, gridSize, 0, gridSize);

  }

  private void drawControls(GL2 gl2) {
    gl2.glPointSize(5);
    gl2.glBegin(GL.GL_POINTS);

    for (int i = 0; i < u; i++) {
      for (int j = 0; j < v; j++) {
        gl2.glVertex3dv(points.get(j * v + i));
      }
    }

    for (int j = 0; j < u; j++) {
      for (int i = 0; i < v; i++) {
        gl2.glVertex3dv(points.get(j * v + i));
      }
    }

    gl2.glEnd();
  }

  private static DoubleBuffer joinBuffer(List<DoubleBuffer> list) {
    if (list.size() < 1) {
      return DoubleBuffer.wrap(new double[0]);
    }
    DoubleBuffer retBuffer = DoubleBuffer.allocate(list.get(0).capacity() * list.size());
    for (DoubleBuffer buffer : list) {
      for (int i = 0; i < buffer.capacity(); i++) {
        retBuffer.put(buffer.get(i));
      }
    }
    return retBuffer;
  }
}