

import java.io.File;

/**
 * The `Bug` class represents a type of `GameActor` that can be eaten by a `Frog`.
 * It has an associated point value when eaten.
 */
public class Bug extends GameActor {

    // Fields
    private static final String IMG_PATH = "images" + File.separator + "bug.png";
    private int points;

    /**
     * Constructs a `Bug` object with the specified position and point value.
     *
     * @param x      The x-coordinate for the center of the Bug.
     * @param y      The y-coordinate for the center of the Bug.
     * @param points The point value associated with the Bug when eaten.
     */
    public Bug(float x, float y, int points) {
        super(x, y, IMG_PATH);
        this.points = points;
        System.out.println(image.width);

    }

    /**
     * Gets the point value associated with the Bug.
     *
     * @return The point value of the Bug.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Determines whether this Bug has been eaten by a Frog.
     *  
     * @param f The Frog attempting to eat the Bug.
     * @return true if the Bug's Hitbox collides with the Frog's Hitbox, false otherwise.
     */
    public boolean isEatenBy(Frog f) {
      try {
        return this.getHitbox().doesCollide(f.getTongueHitbox()); 

    }
      catch (IllegalStateException e) {
        return false;
      }
}
}
