package com.marsofandrew.helpers;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class Helpers {

  public static void showFrame(Frame frame, Component component, Dimension frameSize) {
    frame.add(component);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowevent) {
        frame.remove(component);
        frame.dispose();
        System.exit(0);
      }
    });

    frame.setSize(frameSize);
    frame.setVisible(true);
  }

  public static void showAnimatesFrame(Scene scene, String title, Dimension frameSize, int fps) {
    GLCanvas canvas = scene.getScene();
    Frame frame = scene.getFrame(title);
    final FPSAnimator animator = new FPSAnimator(canvas, fps);
    frame.add(canvas);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowevent) {
        frame.remove(canvas);
        frame.dispose();
        animator.stop();
        System.exit(0);
      }
    });

    frame.setSize(frameSize);
    frame.setVisible(true);
    scene.enableAnimation();
    animator.start();
  }
}
