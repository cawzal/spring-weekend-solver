import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.*;

import static java.util.Map.entry;

class Scraper {

    final private static int[][] board_coordinates = new int[][]{
            {147, 95}, {201, 95}, {255, 95},
            {120, 142}, {174, 142}, {228, 142}, {282, 142},
            {93, 189}, {147, 189}, {201, 189}, {255, 189}, {309, 189},
            {120, 236}, {174, 236}, {228, 236}, {282, 236},
            {147, 283}, {201, 283}, {255, 283}
    };

    final private static int[][] target_coordinates = new int[][]{
            {448, 50}, {475, 50}, {502, 50},
            {435, 74}, {462, 74}, {488, 74}, {515, 74},
            {421, 97}, {448, 97}, {475, 97}, {502, 97}, {529, 97},
            {435, 120}, {462, 120}, {488, 120}, {515, 120},
            {448, 144}, {475, 144}, {502, 144}
    };

    final private static Set<String> boardRGBCodes = Set.of(
            "2216_3384_416",
            "3680_336_3216",
            "3196_848_564",
            "2492_3124_4528",
            "3924_828_192",
            "5692_2528_280",
            "4172_3260_1728",
            "5420_3856_628",
            "2652_2556_1300",
            "4548_2072_320",
            "4136_1736_3116"
    );

    final private static Set<String> targetRGBCodes = Set.of(
            "2704_4276_404",
            "3992_248_3488",
            "2756_580_268",
            "2460_3564_4772",
            "4432_904_276",
            "5484_2944_804",
            "3592_2908_1824",
            "4604_3840_2016",
            "1720_1964_2040",
            "3596_2040_756",
            "4520_1948_3428"
    );

    final private static Map<String, String> targetCodesToBoardCodes = Map.ofEntries(
            entry("2216_3384_416", "2704_4276_404"),    // gecko
            entry("3680_336_3216", "3992_248_3488"),    // purple flower
            entry("3196_848_564", "2756_580_268"),      // lady bug
            entry("2492_3124_4528", "2460_3564_4772"),  // blue flower
            entry("3924_828_192", "4432_904_276"),      // red flower
            entry("5692_2528_280", "5484_2944_804"),    // orange flower
            entry("4172_3260_1728", "3592_2908_1824"),  // dragonfly
            entry("5420_3856_628", "4604_3840_2016"),   // white flower
            entry("2652_2556_1300", "1720_1964_2040"),  // butterfly
            entry("4548_2072_320", "3596_2040_756"),    // wasp
            entry("4136_1736_3116", "4520_1948_3428")   // snail
    );

    static Point findGameClientPosition() throws Exception {
        final int SCREEN_CAPTURE_WIDTH = 1920;
        final int SCREEN_CAPTURE_HEIGHT = 1080;
        final Rectangle rectangle = new Rectangle(0, 0, SCREEN_CAPTURE_WIDTH, SCREEN_CAPTURE_HEIGHT);
        final BufferedImage image = new Robot().createScreenCapture(rectangle);
        final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < pixels.length; i++) {
            final int pixel = pixels[i];
            final int r = (pixel >> 16) & 0xff;
            final int g = (pixel >> 8) & 0xff;
            final int b = pixel & 0xff;
            if (r == 116 && g == 160 && b == 52) {
                final int x = i % SCREEN_CAPTURE_WIDTH;
                final int y = Math.floorDiv(i, SCREEN_CAPTURE_WIDTH);
                return new Point(x, y);
            }
        }
        return null;
    }

    static Puzzle findPuzzle(int offsetX, int offsetY) throws Exception {
        final int GAME_CAPTURE_WIDTH = 628;
        final int GAME_CAPTURE_HEIGHT = 400;
        final Rectangle rectangle = new Rectangle(offsetX, offsetY, GAME_CAPTURE_WIDTH, GAME_CAPTURE_HEIGHT);
        final BufferedImage image = new Robot().createScreenCapture(rectangle);
        final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        final int[] targetValuesRed = new int[19];
        final int[] targetValuesGreen = new int[19];
        final int[] targetValuesBlue = new int[19];
        for (int k = 0; k < 19; k++) {
            final int[] center = target_coordinates[k];
            final int x = center[0];
            final int y = center[1];
            for (int i = y - 2; i <= y + 2; i++) {
                for (int j = x - 2; j <= x + 2; j++) {
                    final int pixel = pixels[i * 628 + j];
                    final int r = (pixel >> 16) & 0xff;
                    final int g = (pixel >> 8) & 0xff;
                    final int b = pixel & 0xff;
                    targetValuesRed[k] += r;
                    targetValuesGreen[k] += g;
                    targetValuesBlue[k] += b;
                }
            }
        }

        final int[] boardValuesRed = new int[19];
        final int[] boardValuesGreen = new int[19];
        final int[] boardValuesBlue = new int[19];
        for (int k = 0; k < 19; k++) {
            final int[] center = board_coordinates[k];
            final int x = center[0];
            final int y = center[1];
            for (int i = y - 2; i <= y + 2; i++) {
                for (int j = x - 2; j <= x + 2; j++) {
                    final int pixel = pixels[i * 628 + j];
                    final int r = (pixel >> 16) & 0xff;
                    final int g = (pixel >> 8) & 0xff;
                    final int b = pixel & 0xff;
                    boardValuesRed[k] += r;
                    boardValuesGreen[k] += g;
                    boardValuesBlue[k] += b;
                }
            }
        }

        final Map<String, Byte> targetCodeToBoardNumber = new HashMap<>();
        byte number = 0;
        final byte[] target = new byte[19];
        for (int i = 0; i < 19; i++) {
            final String targetValue = targetValuesRed[i] + "_" + targetValuesGreen[i] + "_" + targetValuesBlue[i];

            if (!targetRGBCodes.contains(targetValue))
                return null;

            if (!targetCodeToBoardNumber.containsKey(targetValue)) {
                targetCodeToBoardNumber.put(targetValue, number);
                number++;
            }

            target[i] = targetCodeToBoardNumber.get(targetValue);
        }

        final byte[] board = new byte[19];
        for (int i = 0; i < 19; i++) {
            final String boardValue = boardValuesRed[i] + "_" + boardValuesGreen[i] + "_" + boardValuesBlue[i];

            if (!boardRGBCodes.contains(boardValue))
                return null;

            board[i] = targetCodeToBoardNumber.get(targetCodesToBoardCodes.get(boardValue));
        }

        final byte[] copyOfBoard = Arrays.copyOf(board, 19);
        final byte[] copyOfTarget = Arrays.copyOf(target, 19);
        Arrays.sort(copyOfBoard);
        Arrays.sort(copyOfTarget);
        if (!Arrays.equals(copyOfBoard, copyOfTarget))
            return null;

        return new Puzzle(board, target);
    }
}