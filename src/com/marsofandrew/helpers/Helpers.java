package com.marsofandrew.helpers;

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
}
