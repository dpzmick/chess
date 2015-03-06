package com.dpzmick.chess.model;

/**
 * Represents a player object (immutable)
 */
final public class Player {

    private final PlayerColor playerColor;
    private String playerName = null;

    /**
     * Creates a new player
     *
     * @param playerColor the player's direction of movement
     */
    public Player(PlayerColor playerColor) {
        this.playerColor = playerColor;
        this.playerName = null;
    }

    public Player(PlayerColor playerColor, String playerName) {
        this.playerColor = playerColor;
        this.playerName = playerName;
    }

    /**
     * Creates a new player, copying from an old player
     *
     * @param other player to copy
     */
    public Player(Player other) {
        this.playerColor = other.playerColor;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public String getPlayerName() {
        if (playerName == null) {
            return "Player (" + this.getPlayerColor().toString() + ")";
        }
        return playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Player)) return false;

        Player other = (Player) o;
        return other.playerColor == this.playerColor;
    }

    @Override
    public int hashCode() {
        return this.getPlayerColor().ordinal();
    }

    @Override
    public String toString() {
        return this.getPlayerName();
    }

    public enum PlayerColor {
        BLACK, WHITE
    }
}
