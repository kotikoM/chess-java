# Chess for Java

A classic two-player chess game built entirely in Java with a graphical user interface, custom game logic, and a built-in chess clock. Originally developed in the Spring of 2014 by **[ jlundstedt - Penn Programming Course Student, 2014]** as part of a university programming course at the University of Pennsylvania.

---

## Fork & Refactoring Notice
This repository is a **refactored fork** of the original project. All UI enhancements, layout cleanups, and structure polishing were done by **@kotikoM**, with respect for the original author's work.

- Updated visuals and layout
- Improved font, spacing, and design using only **Java Swing** (no external libraries)
- Preserved all original functionality and architecture

### Refactoring Improvements
- Reorganized the project structure by clearly separating concerns into `model`, `view`, and `controller` packages to improve maintainability and scalability.

- Moved core game mechanics into `model.core` and `model.internal` to isolate chess logic from core components.

- Refactored UI components (`board`, `square`, `gamewindow`, and `startmenu`) into distinct `view` and `controller` layers, aligning with the MVC design pattern.

- Improved the logic handling for game-ending scenarios by refining rules around checkmate, stalemate, legal king moves, and in-check states.

- Added unit tests to verify the correctness of piece movement logic, ensuring more robust and predictable behavior across gameplay scenarios.


---

## Technology

- Java 21
- Java Swing for GUI
- Custom drawing and logic for game rules and timers
- JUnit for unit testing 
- Build systems: Maven

---

## Project Structure

```plaintext
chess-java/
├── pom.xml                  # Maven configuration
├── src/
│   ├── main/
│   │   └── java/
│   │   │   └── com/kotikom/chess/
│   │   │        ├── model/         # Game logic and rules
│   │   │        ├── view/          # GUI classes (e.g., StartMenuView)
│   │   │        ├── controller/    # User input & interaction
│   │   │        └── Game.java      # Entry point
│   │   └── resources/
│   │       └── wp.png, ...         # Chess piece i   
│   └── test/
│       └── java/                   # Unit tests (JUnit)
└── README.md
```

## How to Run
1. Clone the repository:
    ```bash
   git clone git@github.com:kotikoM/chess-java.git
    ```
2. Build the project:
    ```bash
   mvn clean install
    ```
3. Run the game
    ```bash
   mvn exec:java
    ```
If maven commands don't work, open project in editor and manually run [Game.java](src%2Fmain%2Fjava%2Fcom%2Fkotikom%2Fchess%2FGame.java).
 
---

## License

This project is licensed under the MIT License. See the LICENSE file for details.
