package com.csse3200.game.services;

import com.csse3200.game.components.gamearea.EngineerCountDisplay;

public class GameEndService {

    private int engineerCount;
    private boolean gameOver = false;
    private static final int STARTING_COUNT = 5;
    private final EngineerCountDisplay display;

    /**
     * Constructor for the Game End Service
     */
    public GameEndService() {
        this.engineerCount = STARTING_COUNT;
        this.display = new EngineerCountDisplay();
    }

    /**
     * Set the engineer limit. During instantiation, limit defaults to 5.
     * @param newLimit as an integer representing the maximum number of engineer deaths
     */
    public void setEngineerCount(int newLimit) {
        if (newLimit > 0 && newLimit < 1000) {
            engineerCount = newLimit;
            display.updateCount();
        }
    }

    /**
     * Returns the number of engineers left
     * @return (int) engineer count
     */

    public int getEngineerCount() {
        return engineerCount;
    }

    /**
     * Updates engineer count and the UI display
     * If engineer count is 0, the game is over.
     */
    public void updateEngineerCount() {
        engineerCount -= 1;
        display.updateCount();

        if (engineerCount == 0) {
            gameOver = true;
        }
    }

    /**
     * Return the warning threshold for the engineer count
     * @return the warning threshold int
     */
    public float getThreshold() {
        return (float)(0.25 * STARTING_COUNT);
    }

    /**
     * Returns the game over state
     * @return (boolean) true if the game is over; false otherwise
     */
    public boolean hasGameEnded() {
        return gameOver;
    }

    /**
     * Returns the Engineer Count UI component
     */
    public EngineerCountDisplay getDisplay() {
        return display;
    }
}
