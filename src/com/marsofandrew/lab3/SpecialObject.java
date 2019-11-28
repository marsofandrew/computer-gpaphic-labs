package com.marsofandrew.lab3;

import com.jogamp.opengl.GL2;
import com.marsofandrew.helpers.OGLDrawable;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;

import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sqrt;

public class SpecialObject implements OGLDrawable {

  private final double radius;
  private final List<List<DoubleBuffer>> initialPoints;
  private final List<List<Pair<Double, Double>>> statementRadiuses;
  private final int fillType;
  private int statement;

  public SpecialObject(double radius, int statement) {
    this(radius, statement, GL_FILL);
  }

  public SpecialObject(double radius, int statement, int fillType) {
    this.radius = radius;
    this.initialPoints = countInitialPoints(this.radius);
    this.statementRadiuses = setStatementRadiuses(radius);
    this.fillType = fillType;
    this.statement = statement;
  }


  @Override
  public void draw(GL2 gl2) {
    int verticalSize;
    BezierSurface surface = null;
    if (statement < 5) {
      verticalSize = 2;
      surface = new BezierSurface(
          countPoints(initialPoints.get(statement), verticalSize),
          initialPoints.get(statement).size(),
          verticalSize, 40, fillType);
    } else if (statement >= 5 && statement < 5 + statementRadiuses.size()) {
      List<Pair<Double, Double>> radiuses = statementRadiuses.get(statement - 5);
      verticalSize = radiuses.size();
      surface = new BezierSurface(modifyOctagon(radiuses), 9, verticalSize, 40, fillType);
    } else {
      throw new IndexOutOfBoundsException("index out of range");
    }
    surface.draw(gl2);
  }

  private static List<List<DoubleBuffer>> countInitialPoints(double radius) {
    double baseConstant = sqrt(2) / 2;
    double octagonSide = radius / sqrt((1 + sqrt(2)) / sqrt(2));
    return Arrays.asList(
        Arrays.asList(
            DoubleBuffer.wrap(new double[]{-4 * octagonSide, 0, radius}),
            DoubleBuffer.wrap(new double[]{0, 0, radius}),
            DoubleBuffer.wrap(new double[]{4 * octagonSide, 0, radius})
        ),
        Arrays.asList(
            DoubleBuffer.wrap(new double[]{-baseConstant * radius * 4, 0, (1 - 4 * (1 - baseConstant)) * radius}),
            DoubleBuffer.wrap(new double[]{-baseConstant * radius, 0, baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{0, 0, radius}),
            DoubleBuffer.wrap(new double[]{baseConstant * radius, 0, baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{baseConstant * radius * 4, 0, (1 - 4 * (1 - baseConstant)) * radius})
        ),
        Arrays.asList(
            DoubleBuffer.wrap(new double[]{-radius * (baseConstant + 3 * (1 - baseConstant)), 0, -2 * baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{-radius, 0, 0.0}),
            DoubleBuffer.wrap(new double[]{-baseConstant * radius, 0, baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{0, 0, radius}),
            DoubleBuffer.wrap(new double[]{baseConstant * radius, 0, baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{radius, 0, 0.0}),
            DoubleBuffer.wrap(new double[]{radius * (baseConstant + 3 * (1 - baseConstant)), 0, -2 * baseConstant * radius})
        ),
        Arrays.asList(
            DoubleBuffer.wrap(new double[]{(1 - 2 * baseConstant) * radius, 0, -2 * baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{-baseConstant * radius, 0, -baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{-radius, 0, 0.0}),
            DoubleBuffer.wrap(new double[]{-baseConstant * radius, 0, baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{0, 0, radius}),
            DoubleBuffer.wrap(new double[]{baseConstant * radius, 0, baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{radius, 0, 0.0}),
            DoubleBuffer.wrap(new double[]{baseConstant * radius, 0, -baseConstant * radius}),
            DoubleBuffer.wrap(new double[]{(1 - 2 * (1 - baseConstant)) * radius, 0, -2 * baseConstant * radius})
        ),
        countOctagonPoints(radius, 0)
    );
  }

  private static List<DoubleBuffer> countOctagonPoints(double radius, double y) {
    double baseConstant = sqrt(2) / 2;
    return Arrays.asList(
        DoubleBuffer.wrap(new double[]{0, y, -radius}),
        DoubleBuffer.wrap(new double[]{-baseConstant * radius, y, -baseConstant * radius}),
        DoubleBuffer.wrap(new double[]{-radius, y, 0.0}),
        DoubleBuffer.wrap(new double[]{-baseConstant * radius, y, baseConstant * radius}),
        DoubleBuffer.wrap(new double[]{0, y, radius}),
        DoubleBuffer.wrap(new double[]{baseConstant * radius, y, baseConstant * radius}),
        DoubleBuffer.wrap(new double[]{radius, y, 0.0}),
        DoubleBuffer.wrap(new double[]{baseConstant * radius, y, -baseConstant * radius}),
        DoubleBuffer.wrap(new double[]{0, y, -radius})
    );
  }

  /**
   * Cre
   *
   * @param radiuses
   * @return
   */
  private static List<DoubleBuffer> modifyOctagon(List<Pair<Double, Double>> radiuses) {
    List<DoubleBuffer> list = new ArrayList<>();

    for (Pair<Double, Double> pair : radiuses) {
      List<DoubleBuffer> points = countOctagonPoints(pair.getValue(), pair.getKey());
      list.addAll(points);
    }
    return list;
  }

  private static List<DoubleBuffer> countPoints(List<DoubleBuffer> initpPoints, int v) {
    List<DoubleBuffer> list = new ArrayList<>();
    for (int j = 0; j < v; j++) {
      for (int i = 0; i < initpPoints.size(); i++) {
        DoubleBuffer point = initpPoints.get(i);
        list.add(DoubleBuffer.wrap(new double[]{point.get(0), point.get(1) + ((double) 1) / (v - 1) * j, point.get(2)}));
      }
    }
    return list;
  }

  private static List<List<Pair<Double, Double>>> setStatementRadiuses(double baseRadius) {
    final double r = 0.3;
    ArrayList<List<Pair<Double, Double>>> list = new ArrayList<>();

    for (int diff : Arrays.asList(90, 60, 45, 30, 15, 10, 5, 3)) {
      ArrayList<Pair<Double, Double>> localList = new ArrayList<>();
      for (int i = 0; i <= 360; i += diff) {
        double radians = Math.toRadians(i);
        localList.add(new Pair(0.5 + Math.sin(radians) / 2, baseRadius + r * (1 - Math.cos(radians))));
      }
      //System.out.println(localList);
      list.add(localList);
    }
    return list;

  }

}
