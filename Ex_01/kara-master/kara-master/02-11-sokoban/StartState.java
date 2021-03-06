import greenfoot.*;
import java.awt.Color;
import java.awt.Font;

/**
 * The start screen state.
 * 
 * @author Marco Jakob (majakob@gmx.ch)
 * @version 1.3 (2012-07-04)
 */
public class StartState extends ScreenState 
{
    private Button startGameButton;
    private Button highscoreButton;
    private InputLabel passwordInputLabel;
    private Label passwordOkLabel;
    private int passwordLevelNumber = -1;

    public StartState(GameScreen gameScreen)
    {
        super(gameScreen);
    }

    /**
     * Initializes the screen.
     */
    public void initScreen()
    {
        gameScreen.setCurrentLevelNumber(1); // reset level

        gameScreen.setBackground("start_screen.png");

        startGameButton = new Button("Start Game", 130, 30, GameScreen.FONT_M);
        startGameButton.setBorderColor(Color.RED);
        startGameButton.setIcon(GameScreen.ICON_START);
        startGameButton.setBackgroundColor(new Color(255, 205, 205));
        gameScreen.addObject(startGameButton, GameScreen.WIDTH_IN_CELLS / 2 , 10);

        if (MyKara.HIGHSCORE_ENABLED)
        {
            highscoreButton = new Button("Highscore", 130, 30, GameScreen.FONT_M);
            highscoreButton.setBorderColor(Color.RED);
            highscoreButton.setIcon(GameScreen.ICON_HIGHSCORE);
            highscoreButton.setBackgroundColor(new Color(255, 205, 205));
            gameScreen.addObject(highscoreButton, GameScreen.WIDTH_IN_CELLS / 2 , 12);
        }

        Label enterPasswordLabel = new Label("Enter Level Password With Keyboard:", 
                230, 21, GameScreen.FONT_S);
        enterPasswordLabel.setBackgroundColor(Color.BLACK);
        enterPasswordLabel.setTextColor(new Color(255, 205, 205));
        gameScreen.addObject(enterPasswordLabel, GameScreen.WIDTH_IN_CELLS / 2 , 14);

        passwordInputLabel = new InputLabel("", 180, 21, GameScreen.FONT_M);
        passwordInputLabel.setBorderColor(null);
        passwordInputLabel.setBackgroundColor(new Color(255, 205, 205));
        // passwordInputLabel.setMaxLength(10);
        gameScreen.addObject(passwordInputLabel, GameScreen.WIDTH_IN_CELLS / 2 , 15);

        passwordOkLabel = new Label(95, 24, GameScreen.ICON_LOCKED);
        passwordOkLabel.setBackgroundTransparency(0);
        passwordOkLabel.setTextColor(new Color(255, 205, 205));
        passwordOkLabel.setFont(GameScreen.FONT_S);
        passwordOkLabel.setIconVisible(true);
        passwordOkLabel.setAlign(Label.ALIGN_LEFT);
        gameScreen.addObject(passwordOkLabel, GameScreen.WIDTH_IN_CELLS / 2 + 5, 15);
    }

    /**
     * The act method is called by the GameScreen to perform the action in the ScreenState.
     */
    public void act()
    {
        // handle key events
        String key = Greenfoot.getKey();
        if (key != null)
        {
            handleKeyPress(key);
        }       

        // handle mouse events
        if (startGameButton.wasClicked())
        {
            handleStartGame();
        }

        if (highscoreButton != null && highscoreButton.wasClicked())
        {
            // Set the new screen state
            gameScreen.setState(gameScreen.getHighscoreState());
        }
    }

    /**
     * Handles the key press event.
     */
    private void handleKeyPress(String key)
    {
        if (key.equals("enter"))
        {
            handleStartGame();
        }
        else
        {
            passwordInputLabel.handleKeyPress(key);
            if (passwordInputLabel.wasTextChanged())
            {
                String password = passwordInputLabel.getText();
                if (password.length() > 0)
                {
                    checkPassword(password);
                }
            }
        }
    }

    /**
     * Tests whether the specified password matches to a level password.
     * If a match is found, the according level number is saved.
     */
    private void checkPassword(String password)
    {
        passwordLevelNumber = -1;
        for (Level l : gameScreen.getAllLevels())
        {
            if (l.getLevelPassword().equals(password))
            {
                passwordOkLabel.setIcon(GameScreen.ICON_OK);
                passwordOkLabel.setText("Level " + l.getLevelNumber());
                passwordLevelNumber = l.getLevelNumber();
                break;
            }
        }

        if (passwordLevelNumber == -1)
        {
            passwordOkLabel.setIcon(GameScreen.ICON_LOCKED);
            passwordOkLabel.setText("");
        }
    }

    /**
     * Handles the start game event.
     */
    private void handleStartGame()
    {
        if (passwordLevelNumber != -1)
        {
            gameScreen.setCurrentLevelNumber(passwordLevelNumber);
            passwordLevelNumber = -1; // reset
        }

        // Set the new screen state
        if (gameScreen.isHighscoreAvailable() && gameScreen.canSetPlayerName())
        {
            gameScreen.setState(gameScreen.getEnterNameState());
        }
        else
        {
            gameScreen.setState(gameScreen.getLevelSplashState());
        }
    }
}
