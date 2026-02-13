// KillerClownGame.java

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class KillerClownGame {
    private static Player player;
    private static Clown clown;
    private static ArrayList<Obstacle> obstacles;
    private static int score;
    private static boolean gameRunning;

    public static void main(String[] args) {
        player = new Player();
        clown = new Clown();
        obstacles = new ArrayList<>();
        score = 0;
        gameRunning = true;

        // Add some obstacles
        obstacles.add(new Spike(5));
        obstacles.add(new Pit(10));
        obstacles.add(new MovingPlatform(15));

        gameLoop();
    }

    private static void gameLoop() {
        Scanner scanner = new Scanner(System.in);
        while (gameRunning) {
            System.out.println("Player Position: " + player.getPosition());
            System.out.println("Score: " + score);
            System.out.println("Enter distance to move (1-5): ");
            int moveDistance = scanner.nextInt();
            player.move(moveDistance);

            checkCollision();
            if (clown.isDefeated()) {
                System.out.println("You defeated the clown! Final score: " + score);
                gameRunning = false;
            }
        }
        scanner.close();
    }

    private static void checkCollision() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isColliding(player)) {
                System.out.println("Collided with " + obstacle.getName() + ". Game Over!");
                gameRunning = false;
                return;
            }
        }
        if (clown.getPosition() == player.getPosition()) {
            clown.defeat();
            score += 10;
            System.out.println("You hit the clown!");
        }
    }
}

class Player {
    private int position;

    public Player() {
        this.position = 0;
    }

    public void move(int distance) {
        position += distance;
    }

    public int getPosition() {
        return position;
    }
}

class Clown {
    private int position;
    private boolean defeated;

    public Clown() {
        this.position = 20;
        this.defeated = false;
    }

    public int getPosition() {
        return position;
    }

    public void defeat() {
        defeated = true;
    }

    public boolean isDefeated() {
        return defeated;
    }
}

abstract class Obstacle {
    protected int position;

    public Obstacle(int position) {
        this.position = position;
    }

    public abstract boolean isColliding(Player player);
    public abstract String getName();
}

class Spike extends Obstacle {
    public Spike(int position) {
        super(position);
    }

    public boolean isColliding(Player player) {
        return player.getPosition() == position;
    }

    public String getName() {
        return "Spike";
    }
}

class Pit extends Obstacle {
    public Pit(int position) {
        super(position);
    }

    public boolean isColliding(Player player) {
        return player.getPosition() == position;
    }

    public String getName() {
        return "Pit";
    }
}

class MovingPlatform extends Obstacle {
    public MovingPlatform(int position) {
        super(position);
    }

    public boolean isColliding(Player player) {
        return player.getPosition() == position;
    }

    public String getName() {
        return "Moving Platform";
    }
}