package com.marsofandrew.lab3;

import com.jogamp.opengl.GL2;
import com.marsofandrew.helpers.OGLDrawable;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jogamp.opengl.GL2GL3.GL_LINE;

public class SpecialObject implements OGLDrawable {

  private static List<List<DoubleBuffer>> initialPoints = Arrays.asList(
      Arrays.asList(
          DoubleBuffer.wrap(new double[]{0.5, 0, 0}),
          DoubleBuffer.wrap(new double[]{0.354, 0, -0.354}),
          DoubleBuffer.wrap(new double[]{0.0, 0, -0.5}),
          DoubleBuffer.wrap(new double[]{-0.354, 0, -0.354}),
          DoubleBuffer.wrap(new double[]{-0.5, 0, 0.0}),
          DoubleBuffer.wrap(new double[]{-0.354, 0, 0.354}),
          DoubleBuffer.wrap(new double[]{0.0, 0, 0.5}),
          DoubleBuffer.wrap(new double[]{0.354, 0, 0.354}),
          DoubleBuffer.wrap(new double[]{0.5, 0, 0.0})
      )
  );
  private int state = 0;

  @Override
  public void draw(GL2 gl2) {
    BezierSurface surface = new BezierSurface(countPoints(initialPoints.get(state), 4), 9, 4, 40, GL_LINE);
    surface.draw(gl2);
  }

  private List<DoubleBuffer> countPoints(List<DoubleBuffer> initpPoints, int v) {
    List<DoubleBuffer> list = new ArrayList<>();
    for (int j = 0; j < v; j++) {
      for (int i = 0; i < initpPoints.size(); i++) {
        DoubleBuffer point = initpPoints.get(i);
        list.add(DoubleBuffer.wrap(new double[]{point.get(0), point.get(1) + ((double) 1) / (v - 1) * j, point.get(2)}));
      }
    }
    return list;
  }
}
