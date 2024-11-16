
/**
 * The `GameActor` class represents an element in the game with a position, image, and hitbox. It
 * provides methods for manipulating and interacting with these attributes.
 */
public class GameActor {

  // Fields
  private float[] coordinates;
  private Hitbox hitbox;
  protected processing.core.PImage image;
  protected static processing.core.PApplet processing;

  /**
   * Constructs a GameActor with the specified position and loads an image from the given path.
   *
   * @param x       The x-coordinate for the center of the GameActor.
   * @param y       The y-coordinate for the center of the GameActor.
   * @param imgPath The path to the image file used for the GameActor.
   */
  GameActor(float x, float y, String imgPath) {
    if (processing == null) {
      throw new IllegalStateException("Processing Cannot be Null");
    }
    this.coordinates = new float[] {x, y};
    this.image = processing.loadImage(imgPath);

    // Initialize the hitbox with dimensions based on image size and position
    this.hitbox = new Hitbox(this.getX(), this.getY(), this.image.width, this.image.height);
  }

  /**
   * Sets the Processing applet for the GameActor class. This should be called once at the beginning
   * of the program.
   *
   * @param processing The Processing applet.
   */
  public static void setProcessing(processing.core.PApplet processing) {
    GameActor.processing = processing;
  }

  /**
   * Gets the x-coordinate of the GameActor.
   *
   * @return The x-coordinate.
   */
  public float getX() {
    return this.coordinates[0];
  }

  /**
   * Gets the y-coordinate of the GameActor.
   *
   * @return The y-coordinate.
   */
  public float getY() {
    return this.coordinates[1];
  }

  /**
   * Sets the x-coordinate of the GameActor.
   *
   * @param newX The new x-coordinate.
   */
  public void setX(float newX) {
    this.coordinates[0] = newX;
  }

  /**
   * Sets the y-coordinate of the GameActor.
   *
   * @param newY The new y-coordinate.
   */
  public void setY(float newY) {
    this.coordinates[1] = newY;
  }

  /**
   * Gets the hitbox of the GameActor.
   *
   * @return The hitbox.
   */
  public Hitbox getHitbox() {
    return this.hitbox;
  }

  /**
   * Moves the hitbox of the GameActor to the specified position.
   *
   * @param x The new x-coordinate of the hitbox.
   * @param y The new y-coordinate of the hitbox.
   */
  public void moveHitbox(float x, float y) {
    if (hitbox != null) {
      this.hitbox.setPosition(x, y);
    }
  }

  /**
   * Draws the image of the GameActor to the screen at its current position. Additionally, it
   * visualizes the hitbox (optional).
   */
  public void draw() {
    if (processing != null && image != null) {
      processing.image(image, coordinates[0], coordinates[1]);
    }
    // Optional: Visualize the hitbox
    this.hitbox.visualizeHitbox();
  }
}
