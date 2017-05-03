package com.aman.snippingtool;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

class AlsXYMouseLabelComponent extends JComponent
{
  public int x;
  public int y;
  
  public AlsXYMouseLabelComponent() {
    this.setBackground(Color.blue);
  }
  // use the xy coordinates to update the mouse cursor text/label
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    String s = x + ", " + y;
    g.setColor(Color.red);
    g.drawString(s, x, y);
  }
}