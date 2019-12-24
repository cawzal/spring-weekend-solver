import java.awt.*;
import java.awt.event.InputEvent;

class Mouse {

    final private static int[][] currentStateCoordinates = new int[][]{
            {147, 95}, {201, 95}, {255, 95},
            {120, 142}, {174, 142}, {228, 142}, {282, 142},
            {93, 189}, {147, 189}, {201, 189}, {255, 189}, {309, 189},
            {120, 236}, {174, 236}, {228, 236}, {282, 236},
            {147, 283}, {201, 283}, {255, 283}
    };

   static void perform(String[] actions, int offsetX, int offsetY) throws Exception {
        final Robot robot = new Robot();
        for (String action : actions) {
            final String button = action.substring(0, 1);
            final byte index = Byte.valueOf(action.substring(1));

            final int[] coordinate = currentStateCoordinates[index];
            robot.mouseMove(coordinate[0] + offsetX, coordinate[1] + offsetY);
            if (button.equals(("R"))) {
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            } else {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
        }
    }
}
