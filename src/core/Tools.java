package core;

import java.awt.*;

public class Tools {
    /**
     * Delay function, used for vizualization, puts the current Thread to sleep
     */
    public static void Delay() {
        try {
            Thread.sleep(Application.ms);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a color between c1 and c2 (Lerping)
     * @param dt
     * @param c1
     * @param c2
     * @return
     */
    public static Color GetColorBlend(float dt, Color c1, Color c2) {
        float r = Math.max(0, Math.min(c1.getRed() * (1 - dt) + c2.getRed() * dt, 255));
        float g = Math.max(0, Math.min(c1.getGreen() * (1 - dt) + c2.getGreen() * dt, 255));
        float b = Math.max(0, Math.min(c1.getBlue() * (1 - dt) + c2.getBlue() * dt, 255));

        return new Color(r / 255f, g / 255f, b / 255f);
    }
}
