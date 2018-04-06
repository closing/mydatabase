package chr06;

import java.awt.*;
import java.awt.image.*;

public class GrayscaleImageFilter extends RGBImageFilter {

  public GrayscaleImageFilter() {
    canFilterIndexColorModel = true;
  }

  // Convert color pixels to grayscale
  // The algorithm matches the NTSC specification
  public int filterRGB(int x, int y, int pixel) {

    // Get the average RGB intensity
    int red = (pixel & 0x00ff0000) >> 16;
    int green = (pixel & 0x0000ff00) >> 8;
    int blue = pixel & 0x000000ff;

    int luma = (int) (0.299 * red + 0.587 * green + 0.114 * blue);

    // Return the luma value as the value for each RGB component
    // Note: Alpha (transparency) is always set to max (not transparent)
    return (0xff << 24) | (luma << 16) | (luma << 8) | luma;
  }
}
