package com.powaclient;

/**
 * Global theme: a soft, modern palette — dark slate with periwinkle and blush accents.
 * Change the values here to re-skin the whole client.
 */
public final class Theme
{
    private Theme() {}

    // Accents (delicate, low saturation)
    public static final int ACCENT         = 0xFF9FB0E0; // soft periwinkle
    public static final int ACCENT_BRIGHT  = 0xFFC3CDEF; // lighter periwinkle
    public static final int ACCENT2        = 0xFFD9A7B0; // soft blush
    public static final int ACCENT_DARK    = 0xFF454B61; // muted, for "off" states

    // Surfaces (dark slate, never pure black)
    public static final int PANEL      = 0xE6232634;
    public static final int ROW        = 0xC82A2E3D;
    public static final int ROW_HOVER  = 0xE6363B50;
    public static final int BORDER     = 0xFF3C4258;
    public static final int BG_SOLID   = 0xFF1B1E29;

    // Text
    public static final int TEXT           = 0xFFE5E7F0;
    public static final int TEXT_DISABLED  = 0xFF9095A8;

    // Category colours (soft pastel)
    public static final int CAT_HUD     = 0xFF9FB0E0; // periwinkle
    public static final int CAT_PLAYER  = 0xFFD9A7B0; // blush
    public static final int CAT_RENDER  = 0xFFB8D8B0; // sage green
    public static final int CAT_MISC    = 0xFFE0CBA0; // sand
}
