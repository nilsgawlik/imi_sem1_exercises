import greenfoot.*;
import java.awt.Color;
import java.awt.Font;

/**
 * The start screen state.
 * 
 * @author Marco Jakob (majakob@gmx.ch)
 * @version 1.3 (2012-07-04)
 */
public class LevelSplashState extends ScreenState 
{
    private AnimatedLabel levelAnimLabel;
    private AnimatedLabel levelPasswordAnimLabel;
    private Button startLevelButton;
    private Button backToMenuButton;

    private boolean listenToKeyEvents = false;

    public LevelSplashState(GameScreen gameScreen)
    {
        super(gameScreen);
    }

    /**
     * Initializes the screen.
     */
    public void initScreen()
    {
        GreenfootImage img = new GreenfootImage(GameScreen.WIDTH_IN_CELLS, GameScreen.HEIGHT_IN_CELLS);
        img.setColor(Color.BLACK);
        img.fill();
        gameScreen.setBackground(img);

        levelAnimLabel = new AnimatedLabel("Level " + gameScreen.getCurrentLevelNumber(), 
            350, 50, GameScreen.FONT_XXL, AnimatedLabel.LEFT_TO_RIGHT);
        levelAnimLabel.setTextColor(new Color(255, 205, 205));
        levelAnimLabel.setBackgroundColor(Color.BLACK);
        levelAnimLabel.setStopInMiddle(true);
        levelAnimLabel.setInitialSpeed(21.3f);
        gameScreen.addObject(levelAnimLabel, GameScreen.WIDTH_IN_CELLS / 2, 6);

        levelPasswordAnimLabel = new AnimatedLabel("Password: " + gameScreen.getCurrentLevel().getLevelPassword(), 
            350, 25, GameScreen.FONT_L,  AnimatedLabel.RIGHT_TO_LEFT);
        levelPasswordAnimLabel.setTextColor(new Color(255, 205, 205));
        levelPasswordAnimLabel.setBackgroundColor(Color.BLACK);
        levelPasswordAnimLabel.setStopInMiddle(true);
        levelPasswordAnimLabel.setInitialSpeed(21.3f);
        gameScreen.addObject(levelPasswordAnimLabel, GameScreen.WIDTH_IN_CELLS / 2, 9);

        startLevelButton = new Button("Start Level", 130, 30, GameScreen.FONT_M);
        startLevelButton.setBorderColor(Color.RED);
        startLevelButton.setIcon(GameScreen.ICON_START);
        startLevelButton.setBackgroundColor(new Color(255, 205, 205));  

        backToMenuButton = new Button("Main Menu", 130, 30, GameScreen.FONT_M);
        backToMenuButton.setBorderColor(Color.RED);
        backToMenuButton.setIcon(GameScreen.ICON_HOME);
        backToMenuButton.setBackgroundColor(new Color(255, 205, 205)); 
    }

    /**
     * The act method is called by the GameScreen to perform the action in the ScreenState.
     */
    public void act()
    {
        if (levelAnimLabel.wasStopped())
        {
            // Fly in animation ended --> add the buttons
            gameScreen.addObject(startLevelButton, GameScreen.WIDTH_IN_CELLS / 2, 12);
            gameScreen.addObject(backToMenuButton, GameScreen.WIDTH_IN_CELLS / 2, 14);
            listenToKeyEvents = true;
        }
        else if (levelAnimLabel.isDone())
        {
            // Fly out animation ended --> go to game state
            gameScreen.setState(gameScreen.getGameState());
        }

        if (listenToKeyEvents)
        {
            String key = Greenfoot.getKey();
            if (key != null && key.equals("enter"))
            {
                // enter clicked
                handleStartLevel();
            }     
        }

        if (startLevelButton.wasClicked())
        {
            handleStartLevel();
        } 
        else if (backToMenuButton.wasClicked())
        {
            gameScreen.removeObject(startLevelButton);
            gameScreen.removeObject(backToMenuButton);
            levelAnimLabel.continueAnimation();
            levelPasswordAnimLabel.continueAnimation();
            // go to start state
            gameScreen.setState(gameScreen.getStartState());
        }
    }

    private void handleStartLevel()
    {
        listenToKeyEvents = false;
        gameScreen.removeObject(startLevelButton);
        gameScreen.removeObject(backToMenuButton);
        levelAnimLabel.continueAnimation();
        levelPasswordAnimLabel.continueAnimation();
    }
}
