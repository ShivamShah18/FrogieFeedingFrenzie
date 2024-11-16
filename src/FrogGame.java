
import java.io.File;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.Random;

public class FrogGame extends PApplet {
  private ArrayList<GameActor> gameActors; // array list of the gameActors in the game
  private int score; // the player's current score
  private PImage backgroundImg; // the image to use for the background
  private boolean isGameOver; // keeps track if the game is over, is true if the game is over
  private Random randGen; // random number generator
  private static final int BUG_COUNT = 5; // how many bugs should be on the screen at all times

  public static void main(String[] args) {
    PApplet.main("FrogGame");
  }

  /**
   * Sets the size of the game window to 800x600 pixels. This method overrides the settings() method
   * in the PApplet class.
   */
  @Override
  public void settings() {
    // TODO #1 call PApplet's size() method giving it 800 as the width and 600 as the height
    super.size(800, 600);
  }

  /**
   * Sets up the initial state of the game, including initializing game elements, loading images,
   * and configuring the game window.
   */
  @Override
  public void setup() {
    // TODO #2 add PApplet method calls from write-up
    this.getSurface().setTitle("Froggie Feeding Frenzie"); // set title of window
    this.imageMode(PApplet.CENTER); // images when drawn will use x,y as their center
    this.rectMode(PApplet.CENTER); // rectangles when drawn will use x,y as their center
    this.focused = true; // window is "active" upon start up
    this.textAlign(PApplet.CENTER); // text written to screen will have center alignment
    this.textSize(30); // text is 30 pt
    // TODO #3 initialize randGen
    randGen = new Random();
    // TODO #4 load in and save the backgroundImg
    backgroundImg = loadImage("images" + File.separator + "background.jpg");

    // TODO #5 initialize gameActors to an empty ArrayList
    gameActors = new ArrayList<GameActor>();
    // TODO #7 set the processing variable for all classes where necessary (update this as needed)
    Hitbox.setProcessing(this);
    GameActor.setProcessing(this);
    Tongue.setProcessing(this);



    // TODO #16 call initGame()

    initGame();

  }

  /**
   * Draws the game elements on the screen, including background, game actors, and relevant text.
   * This method is continuously called to update the game display.
   */
  @Override
  public void draw() {
    // TODO #6 call PApplet's image() method to draw the backgroundImg at the center of the screen
    image(backgroundImg, width / 2, height / 2);


    // TODO #8 draw every GameActor in the ArrayList to the screen

    for (GameActor ga : gameActors) {
      ga.draw();
      if (ga instanceof Moveable) {
        Moveable moveable = (Moveable) ga;
        if (moveable.shouldMove()) {
          moveable.move();
        }
      }
    }
    runGameLogicChecks();


    if (isGameOver) {
      textAlign(CENTER, CENTER);
      textSize(60);
      fill(255);
      text("GAME OVER", width / 2, height / 2);

    } else {
      fill(255);
      for (GameActor actor : gameActors) {
        if (actor instanceof Frog) {
          Frog frog = (Frog) actor;
          text("Health: " + frog.getHealth(), 80, 40);
          break;
        }
      }
      text("Score: " + score, 240, 40);

    }
  }



  // TODO #9 update the code you wrote for TODO #8 to have all Movable GameActors move



  // TODO #19 run all the game logic checks

  // TODO #14 print "Health: " + frog's health at (80,40) and "Score: " + score at (240,40)
  // to the screen
  // (note in the code logic this step to be performed takes place AFTER TODO #19)



  // TODO #20 update the code you wrote above to do the following:
  // (1) if the game is over, do NONE of the above steps. Instead print "GAME OVER" to
  // the center of the screen.
  // (2) otherwise do the above steps

  /**
   * Generates a new bug of random type and adds it to the list of GameActors. The bug's type,
   * position, and characteristics are determined randomly.
   */
  private void addNewBug() {
    // TODO #10 implement this method, see below for more details.
    // This creates a bug of a random type and adds it to the list of GameActors.
    // (1) generate a random number in the range [0,4)
    int bugType = randGen.nextInt(4);

    // (2) generate a random x value in the range [0, windowWidth) for the bug
    float x = (float) randGen.nextFloat(800); // Step (2): Generate a random x value

    // (3) generate a random y value in the range [0, windowHeight - 150) for the bug

    float y = (float) randGen.nextFloat(0, 450);

    // (4) depending on the value generated in step (1)
    // create the following bug and add it to the arraylist
    // 0 -> a new regular Bug at (x,y) that is worth 25 points
    if (bugType == 0) {
      // Create a regular Bug at (x, y) that is worth 25 points
      gameActors.add(new Bug(x, y, 25));
    }

    // 1 -> a new BouncingBug at (x,y) that has a dx of 2 and a dy of 5
    else if (bugType == 1) {
      // Create a BouncingBug at (x, y) that has a dx of 2 and a dy of 5
      gameActors.add(new BouncingBug(x, y, 2, 5));
    }
    // 2 -> a new CirclingBug at (x,y) with a radius of 25 and a random set of RGB values [0,256)
    else if (bugType == 2) {
      // Create a CirclingBug at (x, y) with a radius of 25 and random RGB values [0, 256)
      int r = randGen.nextInt(256);
      int g = randGen.nextInt(256);
      int b = randGen.nextInt(256);
      gameActors.add(new CirclingBug(x, y, 25, new int[] {r, g, b}));
    }
    // 3 -> a new StrongBug at (x,y) with an initial health of 3
    else if (bugType == 3) {
      // Create a StrongBug at (x, y) with an initial health of 3
      gameActors.add(new StrongBug(x, y, 3));
    }
  }

  /**
   * Handles the mousePressed event, checks if the mouse is over the frog, and calls the frog's
   * mousePressed method if so.
   */
  @Override
  public void mousePressed() {
    // TODO #11 if mouse is over the Frog call its mousePressed method
    for (GameActor ga : gameActors) {
      if (ga instanceof Frog && ((Frog) ga).isMouseOver()) {
        ((Frog) ga).mousePressed();
        break; // No need to continue iterating if we found the frog
      }
    }

  }

  /**
   * Handles the mouseReleased event and calls the frog's mouseReleased method.
   */
  @Override
  public void mouseReleased() {
    // TODO #12 call the Frog's mouseReleased method
    for (GameActor ga : gameActors) {
      if (ga instanceof Frog) {
        ((Frog) ga).mouseReleased();
        break;
      }
    }

  }

  /**
   * Handles key presses, allowing the frog to start attacking if the spacebar is pressed.
   * Additionally, resets the game to its initial state if the 'r' key is pressed.
   */

  @Override
  public void keyPressed() {

    // TODO #13 if the key is a space, have the frog starts attacking

    for (GameActor ga : gameActors) {
      if (ga instanceof Frog) {
        Frog frog = (Frog) ga;
        if (key == ' ') {
          frog.startAttack();
        }
        // TODO #17 if the key is a lowercase 'r', reset the game to its initial state
        else if (key == 'r') {
          initGame();
        }
      }
    }

  }

  /**
   * Initializes the game state to its starting conditions, including setting the score to 0,
   * clearing the list of game actors, creating a new frog, and adding bugs to the game.
   */
  public void initGame() {
    // TODO #15 implement this method, see below for more details. This methods sets the game to
    // its initial state before playing.
    // (1) set the score to 0
    score = 0;
    // (2) make the game NOT over
    isGameOver = false;
    // (3) clear out the arraylist
    gameActors.clear();
    // (4) create and add Frog with 100 health to the list. Its x value should be half the
    // width of the screen. Its y value should be the height of the screen minus 100
    Frog frog = new Frog(width / 2, height - 100, 100);
    gameActors.add(frog);
    // (5) add new bugs (of random varieties) to the list UP TO the BUG_COUNT
    for (int i = 0; i < BUG_COUNT; i++) {
      addNewBug();
    }
  }

  /**
   * Runs various game logic checks, including collision detection between bugs and the frog,
   * updates to bug movement, and checks for game over conditions.
   */
  private void runGameLogicChecks() {


    Frog frog = null; // Initialize frog variable
    for (GameActor actor : gameActors) {
      if (actor instanceof Frog) {
        frog = (Frog) actor; // Assign the Frog object
        break;
      }
    }
    if (frog != null && frog.tongueHitBoundary()) {
      frog.stopAttack();
    }

    for (int i = gameActors.size() - 1; i >= 0; i--) {
      GameActor actor = gameActors.get(i);
      if (actor instanceof Bug) {
        Bug bug = (Bug) actor;

        if (frog != null && bug.isEatenBy(frog)) {
          if (bug instanceof StrongBug) {
            StrongBug strongBug = (StrongBug) bug;
            strongBug.loseHealth();
            frog.stopAttack();
            if (strongBug.isDead()) {
              gameActors.remove(i);
              score += strongBug.getPoints();
              addNewBug();
            }
          } else {
            gameActors.remove(i);
            score += bug.getPoints();
            addNewBug();
            frog.stopAttack();
          }
        }
      }

    }
    for (GameActor actor : gameActors) {
      if (actor instanceof Bug) {
        Bug bug = (Bug) actor;

        if (frog != null && frog.isHitBy(bug)) {
          frog.loseHealth();

          if (frog.isDead()) {
            isGameOver = true;
          }
        }
      }
    }
  }

}


