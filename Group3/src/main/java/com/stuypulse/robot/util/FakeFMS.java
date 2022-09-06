package com.stuypulse.robot.util;

import java.util.Random;

/** This class is used to act as a fake FMS, returning
 *  a random color for each switch/scale segment.
 */
public final class FakeFMS {

    public enum Color {
        RED,
        BLUE
    }
    
    public static Color getRandomColor() {
        return Color.values()[new Random().nextInt(Color.values().length)];
    }

    public static Color[] getColors(){
        Color RedSwitchTop = getRandomColor();
        Color RedSwitchBottom = getRandomColor();
        Color ScaleTop = getRandomColor();
        Color ScaleBottom = getRandomColor();
        Color BlueSwitchTop = getRandomColor();
        Color BlueSwitchBottom = getRandomColor();

        if (RedSwitchTop == RedSwitchBottom) {
            if (RedSwitchBottom == Color.RED) {
                RedSwitchBottom = Color.BLUE;
            } else {
                RedSwitchBottom = Color.RED;
            }
            
        }

        if (ScaleTop == ScaleBottom) {
            if (ScaleBottom == Color.RED) {
                ScaleBottom = Color.BLUE;
            } else {
                ScaleBottom = Color.RED;
            }
        }

        if (BlueSwitchTop == BlueSwitchBottom) {
            if (BlueSwitchBottom == Color.RED) {
                BlueSwitchBottom = Color.BLUE;
            } else {
                BlueSwitchBottom = Color.RED;
            }
        }

        Color[] colors = {
            RedSwitchTop, 
            RedSwitchBottom, 
            ScaleTop, 
            ScaleBottom, 
            BlueSwitchTop, 
            BlueSwitchBottom
        };

        return colors;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            for (Color color:getColors()) {
                System.out.println(color);
            }
            System.out.println("-----");
        }
    }
}
