
import java.util.Random;



/**
 * This class represents a Bouncing Bug, a type of bug that bounces around the screen.
 */
public class BouncingBug extends Bug implements Moveable {

  // Fields
  private int[] speedNums;
  private Random randGen = new Random(); // Random number generator
  private boolean goLeft;
  private boolean goDown;
  private static final int POINTS = 100; // The points earned by capturing this bug

  /**
   * Constructs a new Bouncing Bug with the specified initial position and movement speeds.
   *
   * @param x  The x-coordinate for the center of this BouncingBug.
   * @param y  The y-coordinate for the center of this BouncingBug.
   * @param dx The number of pixels to move horizontally.
   * @param dy The number of pixels to move vertically.
   */
  public BouncingBug(float x, float y, int dx, int dy) {
    super(x, y, POINTS);
    this.speedNums = new int[] {dx, dy};
    this.randGen = new Random();
    this.goLeft = randGen.nextBoolean();
    this.goDown = randGen.nextBoolean();
  }

  /**
   * Moves this BouncingBug horizontally and vertically based on its current direction. The Bug's
   * Hitbox also moves accordingly. Direction may reverse upon hitting screen boundaries.
   */
  public void move() {
    if (shouldMove()) {
      float newX = getX();
      float newY = getY();

      if (goLeft) {
        newX -= speedNums[0];
      } else {
        newX += speedNums[0];
      }

      if (goDown) {
        newY += speedNums[1];
      } else {
        newY -= speedNums[1];
      }

      // Reverse direction when hitting the screen boundaries
      if (newX < 0 || newX > processing.width) {
        goLeft = !goLeft;
      }

      if (newY < 0 || newY > processing.height) {
        goDown = !goDown;
      }

      this.setX(newX);
      this.setY(newY);
      this.getHitbox().setPosition(newX, newY);
    }
  }

  /**
   * Determines if the BouncingBug should move.
   *
   * @return true, indicating that this Bug always moves.
   */
  public boolean shouldMove() {
    return true;
  }
}

