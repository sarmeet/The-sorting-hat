package sorting_hat.ui;

import java.awt.event.KeyEvent;
import static sorting_hat.SortingHatConstants.GAME_SCREEN_STATE;
import static sorting_hat.SortingHatConstants.MENU_SCREEN_STATE;
import static sorting_hat.SortingHatConstants.VIEWPORT_INC;
import sorting_hat.TheSortingHat;
import sorting_hat.data.SortTransaction;
import sorting_hat.data.SortingHatDataModel;
import sorting_hat.file.SortingHatFileManager;

/**
 *
 * @author Richard McKenna & _____________________
 */
public class SortingHatEventHandler
{
    // THE SORTING HAT GAME, IT PROVIDES ACCESS TO EVERYTHING
    private SortingHatMiniGame game;

    /**
     * Constructor, it just keeps the game for when the events happen.
     */
    public SortingHatEventHandler(SortingHatMiniGame initGame)
    {
        game = initGame;
    }

    /**
     * Called when the user clicks the close window button.
     */    
    public void respondToExitRequest()
    {
        // IF THE GAME IS STILL GOING ON, END IT AS A LOSS
        if (game.getDataModel().inProgress())
        {
            game.getDataModel().endGameAsLoss();
        }
        // AND CLOSE THE ALL
        System.exit(0);        
    }

    /**
     * Called when the user clicks the New button.
     */
    public void respondToNewGameRequest()
    {
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
        if (game.getDataModel().inProgress())
        {
            game.getDataModel().endGameAsLoss();
        }

        // RESET THE GAME AND ITS DATA
        game.reset();        
    }
    
    /**
     * Called when the user clicks the Back button.
     */
    
    public void respondToBackButtonRequest()
    {
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
        if (game.getDataModel().inProgress())
        {
            game.getDataModel().endGameAsLoss();
        }
        // BACK TO SPLASH SCREEN
        game.switchToSplashScreen();
    }
    

    /**
     * Called when the user clicks a button to select a level.
     */    
    public void respondToSelectLevelRequest(String levelFile)
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (game.isCurrentScreenState(MENU_SCREEN_STATE))
        {
            // GET THE GAME'S DATA MODEL, WHICH IS ALREADY LOCKED FOR US
            SortingHatDataModel data = (SortingHatDataModel)game.getDataModel();
        
            // UPDATE THE DATA
            SortingHatFileManager fileManager = game.getFileManager();
            fileManager.loadLevel(levelFile);
            data.reset(game);

            // GO TO THE GAME
            game.switchToGameScreen();
        }        
    }

    /**
     * Called when the user clicks the Stats button.
     */
    public void respondToDisplayStatsRequest()
    {
        // DISPLAY THE STATS
        game.displayStats();        
    }

    /**
     * Called when the user presses a key on the keyboard.
     */    
    public void respondToKeyPress(int keyCode)
    {
        SortingHatDataModel data = (SortingHatDataModel)game.getDataModel();

        // CHEAT BY ONE MOVE. NOTE THAT IF WE HOLD THE C
        // KEY DOWN IT WILL CONTINUALLY CHEAT
        if (keyCode == KeyEvent.VK_C)
        {            
            // ONLY DO THIS IF THE GAME IS NO OVER
            if (data.inProgress())
            {
                // FIND A MOVE IF THERE IS ONE
                SortTransaction move = data.getNextSwapTransaction();
                if (move != null)
                {
                    data.swapTiles(move.getFromIndex(), move.getToIndex());
                    game.getAudio().play(TheSortingHat.SortingHatPropertyType.AUDIO_CUE_CHEAT.toString(), false);
                }
            }
        }
        else{
            if ( keyCode == KeyEvent.VK_U){
             if (data.inProgress())
                 respondToUndoButtonRequest();
            }
        }
    }

    void respondToUndoButtonRequest() {
                SortingHatDataModel data = (SortingHatDataModel)game.getDataModel();

     if (data.inProgress())
            {
                // FIND A MOVE IF THERE IS ONE
                SortTransaction move = data.getPreviousSwapTransaction();
                if (move != null)
                {
                    data.swapTiles(move.getFromIndex(), move.getToIndex());
                    int tc = data.getTransactionCounter();
                    data.setTransactionCounter(tc-1);
                    game.getAudio().play(TheSortingHat.SortingHatPropertyType.AUDIO_CUE_CHEAT.toString(), false);
                }
            }
       
    }
}