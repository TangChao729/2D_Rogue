```markdown
# Rogue Expanded: 2D Terminal-Based Game

Welcome to Rogue Expanded, an advanced version of a basic 2D game created as a project for the COMP90041 assignment. This is a terminal-based Java game where players can engage in interactive gameplay, battling monsters and exploring a digital world.

## Description

Rogue Expanded is an Object-Oriented Program (OOP) written in Java. It creates a virtual 2D game where the player can interact with various entities like monsters, navigate maps, and more. The game is composed of several Java classes that include:

- **Player**: This class handles the details of the player, such as their health, damage capacity, level, etc.
- **Monster**: This class manages the details of the monster the player has to battle, including health, damage capacity, etc.
- **GameEngine**: This is the main class that drives the game. It includes the game loop, manages user inputs, and initiates other classes like Player, Monster, SaveLoad, etc.
- **SaveLoad**: This class handles the save and load functionalities, making sure players can resume their game from where they left off.
- **World**: Represents the world within the game.
- **Map**: Defines the game map the player navigates through.

## Requirements

To install and run this project, you will need a Java Development Kit (JDK) installed on your machine.

## Installation and Running the Project

1. Clone this repository on your local machine.
2. Navigate to the directory of the project.
3. Compile the Java files using the command: `javac *.java`.
4. Run the game using the command: `java GameEngine`.

## Usage

To play the game, you need to interact through the terminal.

For instance, here's a sample piece of the code that runs the game:

```java
GameEngine gameEngine = new GameEngine();  // Creates an instance of the game engine.
gameEngine.runGameLoop();  // Runs the main game loop.
```

During the game, you will be prompted with options on what you'd like to do. You can create a player, create a monster, start the game, save the game, load a game, or exit the game. If you need any help, just type 'help' in the command prompt. 

```bash
help
player
monster
start
save
load
exit
```

Each command will guide you through the next steps.

Thank you for your interest in Rogue Expanded, and enjoy the game!
```
I hope this README file gives an accurate and concise overview of your project! Feel free to modify it according to your needs.
