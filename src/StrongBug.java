
/**
 * A subclass of Bug that is Movable. These bugs only move when they are not at max health. When hit
 * they become smaller and start moving horizontally across the screen.
 */
public class StrongBug extends Bug implements Moveable {

  // Fields
  private int currentHealth;
  private final int MAX_HEALTH;
  private static final int POINTS = 500;

  /**
   * Creates a new StrongBug object at full health using the provided parameters.
   *
   * @param x      the x-coordinate for the center of this StrongBug
   * @param y      the y-coordinate for the center of this StrongBug
   * @param health the max health for this StrongBug
   * @throws IllegalArgumentException if health is below one
   */
  public StrongBug(float x, float y, int health) {
    super(x, y, POINTS);
    if (health < 1) {
      throw new IllegalArgumentException("Argument must be Greater than 1");
    }
    this.MAX_HEALTH = health;
    this.currentHealth = health;
  }

  /**
   * Moves this StrongBug 3 units to the right, wrapping around the screen when the center hits the
   * edge of the window. The Hitbox should move with the StrongBug. The x,y-coordinate of only
   * changes if the StrongBug should move.
   */
  public void move() {
    if (this.shouldMove()) {
      this.setX((this.getX() + 3) % 800); // Wrap around screen width (800)
      this.moveHitbox(this.getX(), this.getY());
    }
  }

  /**
   * Reports if this StrongBug is dead.
   *
   * @return true if its currentHealth is 0 or less, false otherwise
   */
  public boolean isDead() {
    return (this.currentHealth <= 0);
  }

  /**
   * Decreases the health of this StrongBug by 1.
   */
  public void loseHealth() {
    this.currentHealth--;
  }

  /**
   * Reports if the StrongBug needs to move.
   *
   * @return true if the bug is NOT at full health, false otherwise
   */
  public boolean shouldMove() {
    return (this.currentHealth < this.MAX_HEALTH);
  }

  /**
   * Determines whether or not this bug has been eaten by the Frog. If the Bug has been hit by the
   * frog: - decrease the StrongBug's health - resize the image to 75% of its current height and 75%
   * of its current width - change the dimensions of the hitbox to match the new image dimensions.
   * [HINT] PImage resize().
   *
   * @param f the frog that has possibly eaten this bug
   * @return true if this Bug's Hitbox overlaps that Frog's Tongue's Hitbox, false otherwise
   */
  public boolean isEatenBy(Frog f) {
    if (super.isEatenBy(f)) {
      this.loseHealth();

      int w = (int) (image.width * .75);
      int h = (int) (image.height * .75);

      this.image.resize(w, h);
      this.getHitbox().changeDimensions(w, h);

      return true;
    }
    return false;
  }
}
