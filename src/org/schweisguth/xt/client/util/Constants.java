package org.schweisguth.xt.client.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import org.schweisguth.xt.client.prefs.Preferences;
import org.schweisguth.xt.common.domain.Board;

public class Constants {
    // Colors
    public static final Color BOARD_BACKGROUND_COLOR = new Color(192, 192, 160);
    public static final Color TILE_BACKGROUND_COLOR = new Color(255, 235, 150);
    public static final Color TILE_BORDER_COLOR = new Color(150, 120, 0);

    // Fonts and sizes
    public static final Font TILE_LETTER_FONT =
        new Font("SansSerif", Font.PLAIN, 20);
    public static final int TILE_HEIGHT =
        (int) (1.05 * new JPanel().getFontMetrics(TILE_LETTER_FONT).getHeight());
    public static final Font TILE_VALUE_FONT =
        new Font("SansSerif", Font.PLAIN, 9);
    private static final int MODIFIER_FONT_SIZE =
        Preferences.userNodeForPackage(Constants.class).
            getInt("MODIFIER_FONT_SIZE", 6);
    public static final Font MODIFIER_FONT =
        new Font("SansSerif", Font.BOLD, MODIFIER_FONT_SIZE);
    public static final int BOARD_SIZE = TILE_HEIGHT * Board.getSize();
    public static final Font GAME_RULES_FONT = new Font("Serif", Font.BOLD, 12);
    public static final Font LETTER_DISTRIBUTION_VIEW_FONT =
        new Font("SansSerif", Font.BOLD, 16);

    private Constants() {
    }

}
