

/**
 * A subclass of Bug that is Movable and moves in a circle. When drawn to the screen they also have
 * a tint applied to the image
 */
public class CirclingBug extends Bug implements Moveable {

  // Fields
  private float[] circleCenter;
  private static final int POINTS = 200;
  private double radius;
  private double ticks;
  private int[] tintColor;

  /**
   * Creates a new CirclingBug object using the provided parameters. By default, the number of ticks
   * for a new CirclingBug should be 0.0. The x,y-coordinates for the initial position of the Bug
   * must be calculated by using the equations given in the write-up for ticks = 0.0.
   *
   * @param circleX   the x-coordinate for the center of the circle path
   * @param circleY   the y-coordinate for the center of the circle path
   * @param radius    the radius of this CirclingBug's circle path
   * @param tintColor an array containing the Red, Green, and Blue values for the tint color You can
   *                  assume that this array is ALWAYS length 3.
   */
  public CirclingBug(float circleX, float circleY, double radius, int[] tintColor) {
    super(circleX, circleY, POINTS);
    this.circleCenter = new float[] {circleX, circleY};
    this.radius = radius;
    this.tintColor = tintColor;
  }

  /**
   * Draws the image to the screen, tinting it with the tintColor before drawing it. After the image
   * is drawn to the screen, the tinting effect will need to be undone by tinting it again with
   * white (255, 255, 255).
   */
  public void draw() {
    if (processing != null && image != null) {
      processing.tint(tintColor[0], tintColor[1], tintColor[2]);
      super.draw();
      processing.tint(255, 255, 255);
    }
  }

  /**
   * Moves this CirclingBug to the next position on its circular path. The Hitbox should move with
   * the CirclingBug. The bug only changes its xy-coordinates if it should move. Ticks will advance
   * by 0.05 whenever this method is called.
   */
  public void move() {
    if (shouldMove()) {
      ticks += 0.05; // Advance ticks by 0.05

      // Calculate new x and y coordinates using parameterized equations
      float newX = (float) (radius * Math.cos(ticks) + circleCenter[0]);
      float newY = (float) (radius * Math.sin(ticks) + circleCenter[1]);

      this.setX(newX);
      this.setY(newY);

      this.moveHitbox(newX, newY);
    }
  }

  /**
   * Reports if the CirclingBug needs to move.
   *
   * @return true, this bug ALWAYS moves
   */
  public boolean shouldMove() {
    return true;
  }
}
