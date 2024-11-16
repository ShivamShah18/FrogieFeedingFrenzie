
import java.io.File;

/**
 * An instantiable class maintains data about a Frog in the Froggie Feeding Frenzie game. They can
 * be drawn to the screen, dragged around by the mouse, and attack Bugs with its Tongue.
 */
public class Frog extends GameActor implements Moveable {

  // Fields
  private int health;
  private static final String IMG_PATH = "images" + File.separator + "frog.png";
  private boolean isDragging;
  private float oldMouseX;
  private float oldMouseY;
  private Tongue tongue;

  /**
   * Constructor for a new Frog object using the provided parameters. The Frog is NOT dragging by
   * default.
   *
   * @param x      the x-coordinate for the center of the Frog and starting point of the tongue
   * @param y      the y-coordinate for the center of the Frog and starting point of the tongue
   * @param health the initial health of this Frog
   * @throws IllegalArgumentException if health is less than 1
   */
  public Frog(float x, float y, int health) {
    super(x, y, IMG_PATH);
    if (health < 1) {
      throw new IllegalStateException("Health cannot be less than 1");
    }
    this.health = health;
    this.tongue = new Tongue(x, y);



  }

  /**
   * Draws the image of the Frog to the screen. If the Frog's tongue is active: (1) draw the tongue
   * and (2) extend the tongue by moving its x-coordinate to the Frog's x-coordinate and up 2
   * pixels.
   */
  public void draw() {
    if (this.tongue.isActive()) {
      this.tongue.draw();
      this.tongue.extend(this.getX(), -2);
    }

    super.draw();
  }

  /**
   * Moves the Frog by dragging it by the mouse, if it should be dragging. The starting point of the
   * tongue and the Hitbox need to move along with the Frog. If the Frog's tongue is NOT active,
   * move the tongue's endpoint along with the Frog as well. The Frog only moves if it should move.
   */
  public void move() {

    oldMouseX = processing.mouseX;
    oldMouseY = processing.mouseY;

    if (this.isDragging) {


      setX(processing.mouseX);
      setY(processing.mouseY);
      tongue.updateStartPoint(getX(), getY());
      tongue.getHitbox().setPosition(getX(), getY());
      this.moveHitbox(this.getX(), this.getY());
    }
    if (!tongue.isActive()) {
      tongue.extend(getX(), 0);
    }


  }

  /**
   * Determines whether or not this Frog has run into a Bug.
   *
   * @param b the Bug to check if it collides with the Frog
   * @return true if the Bug's Hitbox and Frog's Hitbox overlap, false otherwise
   */
  public boolean isHitBy(Bug b) {
    return this.getHitbox().doesCollide(b.getHitbox());
  }

  /**
   * Gets the Hitbox for this Frog's tongue.
   *
   * @return this Frog's tongue's hitbox
   * @throws IllegalStateException if the tongue is currently inactive
   */
  public Hitbox getTongueHitbox() {
    if (tongue.isActive() == false) {
      throw new IllegalStateException("Tongue is inactive");
    } else {
      return tongue.getHitbox();
    }
  }

  /**
   * Decreases the health of this Frog by 1.
   */
  public void loseHealth() {
    if (health > 0) {
      this.health--;
    }
  }

  /**
   * Gets the current health of the Frog.
   *
   * @return the current health of this Frog
   */
  public int getHealth() {
    return health;
  }

  /**
   * Reports if the Frog needs to move on the screen.
   *
   * @return true if the Frog is being dragged, false otherwise
   */
  public boolean shouldMove() {
    if (this.isDragging == true) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Determines if this frog is dead.
   *
   * @return true if this Frog's health is 0 or lower, false otherwise
   */
  public boolean isDead() {
    return this.health <= 0;
  }

  /**
   * Changes this Frog so it is now being dragged. This method should only be called externally when
   * the mouse is over this frog and has been clicked.
   */
  public void mousePressed() {
    if (this.isMouseOver() && processing.mousePressed) {
      this.isDragging = true;

    }
  }

  /**
   * Changes this Frog so it is no longer being dragged. This method should only be called
   * externally when the mouse has been released.
   */
  public void mouseReleased() {
    if (!processing.mousePressed) {
      this.isDragging = false;
    }

  }

  /**
   * Determines if the mouse is over the Frog's image.
   *
   * @return true if the mouse is inside the Frog's bounding box of the image, false otherwise
   */
  public boolean isMouseOver() {
    float mouseX = processing.mouseX;
    float mouseY = processing.mouseY;

    float frogCenterX = getX(); // Get the center X-coordinate of the frog
    float frogCenterY = getY(); // Get the center Y-coordinate of the frog
    float frogHalfWidth = this.image.width / 2; // Half of the frog's width
    float frogHalfHeight = this.image.height / 2; // Half of the frog's height

    return (mouseX >= frogCenterX - frogHalfWidth && mouseX <= frogCenterX + frogHalfWidth
        && mouseY >= frogCenterY - frogHalfHeight && mouseY <= frogCenterY + frogHalfHeight);
  }

  /**
   * Starts an attack by resetting the tongue to its default state and activating the tongue.
   */
  public void startAttack() {
    this.tongue.activate();
  }

  /**
   * Stops the attack by deactivating the tongue.
   */
  public void stopAttack() {
    this.tongue.deactivate();
    this.tongue.reset();
  }

  /**
   * Reports if this Frog's tongue has hit the top of the screen.
   *
   * @return true if the tongue has hit the top of the screen, false otherwise
   */
  public boolean tongueHitBoundary() {
    return this.tongue.hitScreenBoundary();
  }
}
