```markdown
# CITS2005 Java Game Project

## 🧩 Project Overview

This project is a Java-based board game developed as part of the CITS2005 course. It supports both two-player matches and playing against an AI. The game includes a modular architecture for game logic, board control, and AI decision-making using heuristic search.

## 📁 Project Structure

```

src/
├── game/
│   ├── Game.java               # Game interface
│   ├── GameImpl.java           # Game implementation
│   ├── Grid.java               # Board interface
│   ├── GridImpl.java           # Board implementation
│   ├── Move.java               # Move interface
│   ├── MoveImpl.java           # Move implementation
│   ├── PieceColour.java        # Enum for piece colors
│   ├── PathFinder.java         # Pathfinding logic
│   └── tests/                  # JUnit test cases
├── ai/
│   ├── AI.java                 # Main AI interface
│   ├── PlayVsAI.java           # Entry point for playing against AI
│   ├── Heuristic.java          # Heuristic evaluation interface
│   ├── Minimax.java            # Minimax algorithm
│   └── MinPiecesHeuristic.java# Sample heuristic implementation

````

## 🎮 Features

- Abstract game board and move implementation
- Two-player gameplay and AI mode
- Modular AI system using heuristic-based decision making
- Pathfinding and game rule enforcement
- Unit testing with JUnit

## 🧠 AI Strategy

The AI uses the **Minimax** algorithm with a customizable **Heuristic**. One implementation, `MinPiecesHeuristic`, favors moves that minimize opponent pieces.

## ✅ Testing

Unit tests are located in `src/game/tests` and include:
- `GameTest.java`
- `GridTest.java`
- `MoveTest.java`

To run the tests, use a Java IDE with JUnit support or execute from CLI:
```bash
javac -cp .:junit-4.13.2.jar src/game/tests/*.java
java -cp .:junit-4.13.2.jar org.junit.runner.JUnitCore game.tests.GameTest
````

## 🛠️ How to Run

Compile the source files:

```bash
javac src/**/*.java
```

Run the game against the AI:

```bash
java src/ai/PlayVsAI
```

## 📌 Requirements

* Java 8+
* JUnit 4 for testing (optional)

## 📄 License

This project is created for academic purposes and does not currently use any open-source license. Please contact the original authors for reuse.


