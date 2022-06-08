<div id="top" align="center">
<!-- <img src="screenshots/Banner_For_Readme.png" alt="Banner"> -->
</div>

<!-- PROJECT LOGO 
<br />
<div align="center">
  <a href="https://github.com/unclechapter/BattleshipIU">
    <img src="res/images/ttsalpha4.0@0.5x.png" alt="Logo" width="200" height="200">
  </a>

  <h3 align="center">DSA Battleship Project</h3>
  <h4 align="center">Minh Phan and Co</h4>
</div> -->

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#introduction">Introduction</a>
      <ul>
        <li><a href="#team-members">Team Members</a></li>
	<li><a href="#installation">Installation</a></li>      
      </ul>
    </li>
    <li><a href="#technologies">Technologies</a></li>
    <li><a href="#How to Play">How to play</a></li>
    <li><a href="#features">Features</a></li>
    
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## Introduction

<div align="center">
<img src="screenshots/Intro.gif" alt="">
</div>

<div style="text-align:justify">
Battleship is a classic strategy 2-players game. This game is the product of the final project in Data Structure and Algorithm course. Our project attempts to apply our understandings of object-oriented programming, data structure and algorithms to build this game with variations of new features and a BotAI to play with the player.  
</div>

### Team Members

|         Name          |     ID      |                        Github account                        |
| :-------------------: | :---------: |  :---------------------------------------------------------: |
| Hang Huynh Cong Thuan | ITITIU20021 |        [Torches0307]https://github.com/Torches0307           |
| Phan Ngoc Dong Minh   | ITITIU20252 |             [pndminh](https://github.com/pndminh)            |
| Huynh Tan Thien       | ITITIU20093 |              [thien20](https://github.com/thien20)           |
| Tran Bac Chuong       | ITITIU20043 |        [unclechapter](https://github.com/unclechapter)       |

### Installation

1. Open the terminal on your IDE
2. Clone the repo
   ```sh
   git clone https://github.com/unclechapter/BattleshipIU
   ```
3. Check the file status
   ```sh
   git status
   ```
4. Change branch
   ```js
   git checkout 'branch_name'
   ```

<br />

## Technologies


- Language: [JAVA](https://www.java.com/en/)
- Framework: [IntelliJ](https://www.jetbrains.com/idea/), [Visual Studio Code](https://code.visualstudio.com)
- Library: [LibGDX](https://libgdx.com/)


### How to play:question:
<div style="text-align:justify">

- Each will place their ships of various sizes on a 12x12 board. The player each takes turns to "shoot" the ing, the player will know opposing player's ship by guessing their opponent's ships position. After each guess, the game will let the player know if their guess is a "hit", a "miss". A ship is "sunk" when the player have shot all of the positions of the ship. The player wins when they have sunk all of their opponent's ships.

<br />
	
## Features
<div style="text-align:justify">
Our game implements 3 new features including:
  - Shield: player is able to put a shield over a grid to prevent it being shot. The shield is broken after it is shot.
  - Sonar: The player is able to place a 3x3 sonar on the map. The sonar will tell the player of there is a ship in the area that it was placed
  - Bomb: the bomb allows the player to fire 4 consecutive shots either vertically or horizontally to their opponent's board.
<div>

<br />