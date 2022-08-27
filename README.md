# Space Invaders

An implementation of Space Invaders using JavaFX library.

You can use the following command to open the jar file.

<pre>java --module-path ${PATH_JAVAFX_SDK}/lib --add-modules javafx.controls,javafx.fxml,javafx.swing -jar ${PATH_JAR_FILE}</pre>

## Java and JavaFX version

Java &nbsp; &nbsp; &nbsp; **11.0.6**

JavaFX &nbsp; **11.0.2**

## How to play

[<kbd>&#8592;</kbd>, <kbd>&#8594;</kbd>] &nbsp; &nbsp; Move to left and right

[<kbd>Space</kbd>] &nbsp; Fire

## Stages

### Select Profile
In this stage, you can create new profiles or delete older ones, also the time players have spent on the game and their highest score is shown.

<img src="GameplayPictures/SelectProfile.png">

### Choose difficulty
In this stage, you can choose difficulty 😀. The health of enemies and also their shoot rate change depending on the difficulty you select. You get more points from killing enemies on harder difficulties which can help you get a higher score. 

<img src="GameplayPictures/ChooseDifficulty.png">

### Loading Screen
Show "loading" for 3 seconds 😀.

<img src="GameplayPictures/LoadingScreen.png">

### Gameplay
A player starts with 3 hearts and must kill all invaders to win. If the player loses all of their hearts or the invaders reach to player's spaceship, the player will lose.

Multiple obstacles block your bullets, but they will break if they got shot 5 times.

A UFO that is faster than the invaders, and flies above them gives a huge amount of points if got defeated.

On the right side of the screen, you can see the number of remaining hearts, the time you spend, and your current score.

<img src="GameplayPictures/GamePlay.png">

### Win or lose
Show you won or lost for 3 seconds. 😇

<img src="GameplayPictures/WinOrLose.png">