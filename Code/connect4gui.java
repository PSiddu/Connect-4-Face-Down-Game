/*     Name : Siddu Palaparthi
 Student ID : 723078 
        Year: 2018
 Description: A Basic Connect 4 GUI, except rather than the conventional connect 4, it is a face-down edition,
 meaning that you can place the pieces anywhere in a tic-tac-toe like manner. This offers a plethora
 of new/different strategies. This GUI is complete with a homescreen, instructions, the game (obviously), and 
 sound settings. Enjoy!
 */

//Importing libraries
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//START OF THE CLASS (I'm using J-Frame rather than an Applet!)
public class connect4gui extends JFrame implements ActionListener {
  
  /////
  //Global Variables
  /////
  
  //Sound clip variables
  Clip clip;
  Clip clip2;
  
  //Sound strings
  String click = "click.wav";
  String win = "win.wav";
  String dirt = "dirt_off_your_shoulder.wav";
  String mario2 = "MarioUndergroundTheme.wav";
  String south = "southern_hospitality.wav";
  
  //This card layout is what gives the effect of multiple screens.
  CardLayout cardLayout = new CardLayout ();
  
  //Initialising buttons important to the card layout (to switch between screens)
  JButton button1, button2, button3, button4, button5, button6;
  
  //A J-Button to reset the game board
  JButton reset;
  
  //Settings J-Buttons
  JButton nerd, thinking, matrix, obama, mute, unmute;
  
  //Some J-Labels (the last two are .gif images!!!)
  JLabel title2, title3, title4, turncount, backgroundImage1, background2, background3, background4, sig, bamagif, boringgif;
  
  //Labels for my instructions screen
  JLabel instructions, instructions2, instructions3, instructions4;
  
  //Images/JLabels for settings screen
  JLabel p1, p2, p3, p4, giflab;
  
  //Setting a panel for each card, or screen:
  JPanel card1 = new JPanel();
  JPanel card2 = new JPanel();
  JPanel card3 = new JPanel();
  JPanel card4 = new JPanel();
  
  //an integer that will help in closing game soundtrack
  int ins = 0;
  
  /////
  //FROM HERE THE VARIABLES ARE ALL TO DO WITH THE GAME!
  /////
  
  //THIS IS THE NUMBER OF ROWS
  int rows = 6;
  
  //THIS IS THE NUMBER OF COLUMNS
  int cols = 7;
  
  //the physical creation of the grid spots (J-Buttons)
  JButton btn[] [] = new JButton [rows] [cols];
  int turn = 0;
  
  //numeric values of the grid spots are in a seperate 2D array
  int butval[] [] = {{9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9, 9, 9, 9}}; //Set to nines to make wins easier to check
  
  
  //Creates the big panel that contains all of the cards or screens. (the boss card)
  JPanel cards = new JPanel(new CardLayout());
  
  
  /////  
  //CONSTRUCTOR STARTS HERE:
  /////
  
  public connect4gui() {
    
    playSound("start.wav");
    
    //CREATION OF MY PHYSICAL GAME BOARD: (on a panel so that the entire board can be treated as a single entity to set bounds and screen)
    Panel g = new Panel (new GridLayout (rows, cols));
    for (int x = 0 ; x < rows ; x++)
    {
      int pixeladd1 = x+80;
      for (int y = 0 ; y < cols ; y++)
      {
        int pixeladd2 = y+90;
        btn [x] [y] = new JButton ();
        btn [x] [y].addActionListener (this);
        
        btn [x] [y].setIcon(new ImageIcon("blank.jpg"));
        
        btn [x] [y].setActionCommand ("" + (x * 10 + y));
        //btn [x] [y].setForeground (Color.black);
        btn [x] [y].setBackground (new Color(0,0,255));
        btn [x] [y].setBounds((35 + pixeladd1), (107 + pixeladd2), 10, 10);
        g.add (btn [x] [y]);
        g.setBackground (new Color(0,0,255));
      }
    }
    
    /*Sets the place where the board goes on the screen using null/absolute layout. It goes: x-coordinate, y-coordinate,
     and then dimensions of the object. */
    
    g.setBounds(140, 200, 800, 600);
    
    //adds g to the JFrame
    add (g);
    
    /////
    //CREATING AND INITIALISING COMPONENTS:
    /////
    
    //Layout must be set as null to set bounds manually
    card1.setLayout(null);
    card2.setLayout(null);
    card3.setLayout(null);
    card4.setLayout(null);
    
    
    //Creating and editing buttons
    
    //PLAY BUTTON
    button1 = new JButton();
    button1.setIcon (new ImageIcon("play2.jpg")); 
    button1.setBounds(96, 99, 250, 88);
    //Turns the button invisible and keeps the border visible to show that the button, while keeping the button looking cool
    //button1.setBorderPainted(false);
    button1.setContentAreaFilled(false);
    
    //INSTRUCTIONS BUTTON
    button2 = new JButton("Instructions");
    button2.setIcon (new ImageIcon("instructions2.jpg"));
    //Coordinates and length and width size of your button; feel free to modify!
    button2.setBounds(770, 99, 400, 88);
    button2.setContentAreaFilled(false);
    
    //HIGH-SCORES BUTTON
    button3 = new JButton("options");
    button3.setIcon (new ImageIcon("options.jpg"));
    button3.setBounds(60, 700, 400, 88);
    button3.setContentAreaFilled(false);
    
    //MAIN MENU RETURN BUTTON FOR CARD 2
    button4 = new JButton("Main Menu");
    button4.setFont(new Font("Courier", Font.PLAIN, 36));
    button4.setBounds(100, 55, 450, 88);
    
    //MY GAME RESET BUTTON (CARD 2)
    reset = new JButton ("Reset");
    reset.setFont(new Font("Courier", Font.PLAIN, 36));
    reset.setBounds(570, 55, 450, 88);
    
    //MAIN MENU CARD 3 (INSTRUCTIONS)
    button5 = new JButton("Main Menu");
    button5.setBounds(300, 700, 500, 50);
    button5.setFont(new Font("Courier", Font.PLAIN, 36));
    
    button6 = new JButton("Main Menu");
    button6.setFont(new Font("Courier", Font.PLAIN, 36));
    button6.setBounds(850, 10, 250, 80);
    
    //Creates JLabels
    
    //ENJOY THE GAME FOR GAME PANEL
    title2 = new JLabel("Enjoy the game!");
    title2.setFont(new Font("Courier", Font.BOLD, 48));
    title2.setBounds(300, 0, 500, 50);
    
    //turncount
    turncount = new JLabel("Yellow's Turn");
    turncount.setFont(new Font("Courier", Font.BOLD, 48));
    turncount.setBounds(350, 830, 500, 50);
    
    //BORING GIF
    boringgif = new JLabel ("boring rules");
    boringgif.setIcon (new ImageIcon("boring.gif"));
    boringgif.setBounds(800, 20, 220, 124);
    
    //COOL TRICK: PUTTING HTML TAGS BEFORE JLABEL TEXT WILL ALLOW MULTIPLE LINES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    
    //INSTRUCTIONS J-LABEL
    title3 = new JLabel("Instructions:");
    title3.setFont(new Font("Courier", Font.BOLD, 48));
    title3.setBounds(300, 0, 900, 50);
    
    //WRITING INNSTRUCTIONS
    instructions = new JLabel("<html>Player 1 will be yellow. Player 2 will be red. You each take turns placing your disc on the board by clicking on the blank circle where you wish to place it.</html>");
    instructions2 = new JLabel("<html>The goal is to put four of these pieces on the board in a row, hence the name “Connect-4”. You can make a Connect-4 horizontally, vertically, or even diagonally in any direction. If the board fills up before you make a connect-4, there is a reset button that will allow you to continue the game!</html>");
    instructions3 = new JLabel("<html>The unique part of this game is that it isn’t like traditional connect-4 because the pieces won’t drop down! This will add to the challenge of the game, and open up a whole realm of possiblilites!</html>");
    instructions4 = new JLabel("[POINTS SYSTEM COMING SOON IN 1.01 UPDATE :) ]               ENJOY!");
    
    //Setting the font of the 4 J-Labels above
    instructions.setFont(new Font("Courier", Font.PLAIN, 24));
    instructions2.setFont(new Font("Courier", Font.PLAIN, 24));
    instructions3.setFont(new Font("Courier", Font.PLAIN, 24));
    instructions4.setFont(new Font("Courier", Font.PLAIN, 24));
    
    //Setting bounds for the 4 J-Labels above
    instructions.setBounds(10, 65, 1100, 400);
    instructions2.setBounds(10, 200, 1100, 400);
    instructions3.setBounds(10, 330, 1100, 400);
    instructions4.setBounds(10, 410, 1100, 400);
    
    //J-Label for High-Scores Panel
    title4 = new JLabel("Sound & Home Page GIF Settings!");
    title4.setFont (new Font ("Courier", Font.BOLD, 42));
    title4.setBounds(50, 10, 840, 80);
    
    //setting the background image of the homescreen
    backgroundImage1 = new JLabel ("lmao this doesnt matter");
    backgroundImage1.setIcon (new ImageIcon("homebackground.jpg")); 
    backgroundImage1.setBounds(0, 0, 1200, 900);
    
    //added my signature on the home-page
    sig = new JLabel ("lmao this doesnt matter");
    sig.setIcon (new ImageIcon("sig.jpg"));
    sig.setBounds(770, 700, 400, 88);
    
    //other backgrounds
    background2 = new JLabel ("lmao this doesnt matter");
    background2.setIcon (new ImageIcon("white.jpg"));
    background2.setBounds(0, 0, 1200, 900);
    background3 = new JLabel ("lmao this doesnt matter");
    background3.setIcon (new ImageIcon("pink.jpg"));
    background3.setBounds(0, 0, 1200, 900);
    background4 = new JLabel ("lmao this doesnt matter");
    background4.setIcon (new ImageIcon("orange.jpg"));
    background4.setBounds(0, 0, 1200, 900);
    
    
    //OBAMA GIF
    bamagif = new JLabel ("ya ya ya ya yeet");
    bamagif.setIcon (new ImageIcon("obama.gif"));
    bamagif.setBounds(400, 300, 400, 200);
    
    //Setting buttons:
    
    //to set the home picture to the nerd image
    nerd = new JButton ("Nerd Zone");
    nerd.setFont(new Font ("Courier", Font.PLAIN, 24));
    nerd.setBounds(350, 370, 500, 50);
    p1 = new JLabel ("...");
    p1.setIcon (new ImageIcon("nerd.gif"));
    p1.setBounds(50, 600, 450, 300);
    
    //to set the home picture to the thinking meme
    thinking = new JButton ("Thinking Meme");
    thinking.setFont(new Font ("Courier", Font.PLAIN, 24));
    thinking.setBounds(350, 430, 500, 50);
    p2 = new JLabel ("...");
    p2.setIcon (new ImageIcon("thinking.gif"));
    p2.setBounds(700, 600, 450, 300);
    
    //to set the home picture to the Matrix image
    matrix = new JButton ("The Matrix");
    matrix.setFont(new Font ("Courier", Font.PLAIN, 24));
    matrix.setBounds(350, 500, 500, 50);
    p3 = new JLabel ("...");
    p3.setIcon (new ImageIcon("matrix.gif"));
    p3.setBounds(800, 100, 350, 300);
    
    //to set the home picture to the obama image
    obama = new JButton ("Obama Plays Connect-4");
    obama.setFont(new Font ("Courier", Font.PLAIN, 24));
    obama.setBounds(350, 570, 500, 50);
    p4 = new JLabel ("...");
    p4.setIcon (new ImageIcon("obama.gif"));
    p4.setBounds(25, 100, 450, 300);
    
    //mute button
    mute = new JButton ("Mute Sound FX");
    mute.setFont(new Font ("Courier", Font.PLAIN, 20));
    mute.setBounds(350, 790, 200, 50);
    //unmute button
    unmute = new JButton ("Unmute Sound FX");
    unmute.setFont(new Font ("Courier", Font.PLAIN, 20));
    unmute.setBounds(610, 790, 250, 50);
    
    //A label for options screen
    giflab = new JLabel ("Set Homepage GIF:");
    giflab.setFont(new Font ("Courier", Font.BOLD, 30));
    giflab.setBounds(480, 320, 350, 50);
    
    
    /////
    //ADDING ACTIONLISTENERS AND ACTIONCOMMANDS OF ALL THE BUTTONS
    /////
    
    
    //button 1
    button1.addActionListener(this);
    button1.setActionCommand ("button1");
    
    //button2
    button2.addActionListener(this);
    button2.setActionCommand ("button2");
    
    //button3
    button3.addActionListener(this);
    button3.setActionCommand ("button3");
    
    //All of these listeners and commands are for the buttons that will take the user back to the main menu
    button4.addActionListener(this);
    button4.setActionCommand ("button4");
    
    button5.addActionListener(this);
    button5.setActionCommand ("button4");
    
    button6.addActionListener(this);
    button6.setActionCommand ("button4");
    
    
    //THIS IS FOR MY GAME RESET BUTTON
    reset.addActionListener(this);
    reset.setActionCommand ("reset");
    
    //Homepage picture buttons:
    nerd.addActionListener(this);
    thinking.addActionListener(this);
    matrix.addActionListener(this);
    obama.addActionListener(this);
    
    nerd.setActionCommand ("nerd");
    thinking.setActionCommand ("thinking");
    matrix.setActionCommand ("matrix");
    obama.setActionCommand ("obama");
    
    //DEALING WITH SOUNDS
    mute.addActionListener(this);
    mute.setActionCommand ("mute");
    
    unmute.addActionListener(this);
    unmute.setActionCommand ("unmute");
    
    /////
    //ADDING EVERYTHING TO ITS CORRESPONDING J-PANEL
    /////
    
    //Home Screen
    card1.add(button1);
    card1.add(button2);
    card1.add(button3);
    card1.add(bamagif);
    card1.add(sig);
    card1.add(backgroundImage1);
    
    //Game Screen
    card2.add(title2);
    card2.add(button4);
    card2.add(reset);
    card2.add(turncount);
    card2.add(g);
    card2.add(background2);
    
    //Instructions Screen
    card3.add(title3);
    card3.add(instructions);
    card3.add(instructions2);
    card3.add(instructions3);
    card3.add(instructions4);
    card3.add(boringgif);
    card3.add(button5);
    card3.add(background3);
    
    //Options Screen
    card4.add(title4);
    card4.add(button6);
    card4.add(giflab);
    card4.add(nerd);
    card4.add(thinking);
    card4.add(matrix);
    card4.add(obama);
    card4.add(mute);
    card4.add(unmute);
    card4.add(p1);
    card4.add(p2);
    card4.add(p3);
    card4.add(p4);
    card4.add(background4);
    
    //Adds each of the cards to the big JPanel
    cards.add(card1);
    
    /////
    //setting window attributes
    /////
    
    //Set the size of the JFrame
    setSize(1200, 940);
    
    //Puts title on top of JFrame
    setTitle("Connect 4: Face-Down Edition");
    
    //makes it so that you can't full screen the app. (THIS IS IMPORTANT BECAUSE THE PROGRAM WILL LOOK WEIRD IF IT IS FULL-SCREENED
    setResizable(false);
    
    //Adds each of the cards to the big J-Panel
    cards.add(card1);
    cards.add(card2);
    cards.add(card3);
    
    //Adds the big J-Panel to the J-Frame which contains each card
    getContentPane().add(cards);
    
    //Makes J-Frame visible
    setVisible(true);
  }
  
  /////
  //THE ACTIONPERFORMED PART OF THE PROGRAM WHERE THE BUTTON ACTIONS ARE SET AND THE GAME LOGIC IS IMPLEMENTED
  /////
  
  public void actionPerformed(ActionEvent e) {
    
    //This is applicable to each button
    if (e.getActionCommand ().equals ("button1")) {
      
      //clicking noise
      playSound(click);
      
      //Deletes all cards
      cards.removeAll();
      
      //Essentially refreshes the GUI
      cards.revalidate();
      cards.repaint();
      
      //Goes to the game screen
      cards.add(card2);
      
      //Going to the instructions screen
    } else if (e.getActionCommand ().equals ("button2")) {
      
      //clicking noise
      playSound(click);
      
      //I put a soundtrack of the instructions screen (choice of music was just for you Mr. Couckyt ;) )
      playSoundLoop("super_mario.wav", true);
      ins = 1;
      
      cards.removeAll();
      cards.revalidate();
      cards.repaint();
      cards.add(card3);
      
      //Going to the options screen
    } else if (e.getActionCommand ().equals ("button3")) {
      
      //clicking noise
      playSound(click);
      
      
      cards.removeAll();
      cards.revalidate();
      cards.repaint();
      cards.add(card4);
      
      //Going back to the Home screen
    } else if (e.getActionCommand ().equals ("button4")) {
      
      //clicking noise
      playSound(click);
      
      //closing the soundtrack on the instruction space:
      if (ins == 1)
      playSoundLoop("super_mario.wav", false);
      
      ins = 0;
      
      cards.removeAll();
      cards.revalidate();
      cards.repaint();
      cards.add(card1);
    } 
    //RESETTING MY GAME
    else if (e.getActionCommand ().equals ("reset")) {
      
      //clicking noise
      playSound(click);
      
      for (int x = 0 ; x < rows ; x++)
      {
        for (int y = 0 ; y < cols ; y++)
        {
          btn [x] [y].setIcon(new ImageIcon("blank.jpg"));
          butval[x] [y] = 9;
        }
      }
      turn = 0;
      turncount.setText("Yellow's Turn");
    }
    
    else if (e.getActionCommand ().equals ("nerd")) {
      
      //clicking noise
      playSound(click);
      
      bamagif.setIcon (new ImageIcon("nerd.gif"));
      bamagif.setBounds(375, 200, 450, 300);
    }
    else if (e.getActionCommand ().equals ("thinking")) {
      
      //clicking noise
      playSound(click);
      
      bamagif.setIcon (new ImageIcon("thinking.gif"));
      bamagif.setBounds(400, 300, 400, 200);
    }
    else if (e.getActionCommand ().equals ("matrix")) {
      
      //clicking noise
      playSound(click);
      
      bamagif.setIcon (new ImageIcon("matrix.gif"));
      bamagif.setBounds(400, 300, 400, 200);
    }
    else if (e.getActionCommand ().equals ("obama")) {
      
      //clicking noise
      playSound(click);
      
      bamagif.setIcon (new ImageIcon("obama.gif"));
      bamagif.setBounds(400, 300, 400, 200);
    }
    else if (e.getActionCommand ().equals ("mute")) {
      
      //clicking noise
      playSound(click);
      
      click = "nothing.wav";
      win = "nothing.wav";
    }
    else if (e.getActionCommand ().equals ("unmute")) {
      
      //clicking noise
      playSound(click);
      
      click = "click.wav";
      win = "win.wav";
    }
    
    //THIS PERTAINS TO ADDING PIECES TO MY GAME BOARD
    else {
      int j = Integer.parseInt (e.getActionCommand ());
      int row = j / 10;
      int column = j % 10;
      String letter[] = {"Red", "Yellow"};
      
      // Put the X or O on the button only if blank
      if (butval [row] [column] == 9) {
        turn = (turn + 1) % 2;
        if (turn == 1) {
          btn [row] [column].setIcon(new ImageIcon("yellowpiece.jpg"));
          playSound(click);
          turncount.setText("Red's Turn");
          
        } else {
          btn [row] [column].setIcon(new ImageIcon("redpiece.jpg"));
          playSound(click);
          turncount.setText("Yellow's Turn");
        }
        butval [row] [column] = turn;
      }
    }
    
    /////
    //WIN-CHECK LOGIC
    /////
    
    //Vertical Check
    for (int x = 0; x < 6; x++) {
      for (int i = 0; i < 7 - 4; i++) {
        if (butval[i][x] == butval[i + 1][x] &&
            butval[i + 1][x] == butval[i + 2][x] &&
            butval[i + 2][x] == butval[i + 3][x] &&
            butval[i + 3][x] != 9) {
          
          if (butval[i + 3][x] == 1) {
            playSound(win);
            JOptionPane.showMessageDialog(null, "Yellow wins!", "Connect 4: Notification", JOptionPane.INFORMATION_MESSAGE, (new ImageIcon("//win image here ")));
            
            for (int a = 0 ; a < rows ; a++)
            {
              for (int b = 0 ; b < cols ; b++)
              {
                butval[a] [b] = 9;
                btn [a] [b].setIcon(new ImageIcon("blank.jpg"));
                
              }
            }
            turn = 0;
            turncount.setText("Yellow's Turn");
          } else if (butval[i + 3][x] == 0) {
            playSound(win);
            JOptionPane.showMessageDialog(null, "Red wins!", "Connect 4: Notification", JOptionPane.INFORMATION_MESSAGE, (new ImageIcon("//win image here")));
            
            for (int c = 0 ; c < rows ; c++)
            {
              for (int d = 0 ; d < cols ; d++)
              {
                butval[c] [d] = 9;
                btn [c] [d].setIcon(new ImageIcon("blank.jpg"));
                
              }
            }
            turn = 0;
            turncount.setText("Yellow's Turn");
          }
        }
      }
    }
    
    //Horiziontal Check
    for (int x = 0; x < 6 - 2; x++) {
      for (int i = 0; i < 6; i++) {
        if (butval[i][x] == butval[i][x + 1] &&
            butval[i][x + 1] == butval[i][x + 2] &&
            butval[i][x + 2] == butval[i][x + 3] &&
            butval[i][x + 3] != 9) {
          
          if (butval[i][x + 3] == 1) {
            playSound(win);
            JOptionPane.showMessageDialog(null, "Yellow wins!", "Connect 4: Notification", JOptionPane.INFORMATION_MESSAGE, (new ImageIcon("//win image here ")));
            
            for (int j = 0 ; j < rows ; j++)
            {
              for (int k = 0 ; k < cols ; k++)
              {
                butval[j] [k] = 9;
                btn [j] [k].setIcon(new ImageIcon("blank.jpg"));
                
              }
            }
            turn = 0;
            turncount.setText("Yellow's Turn");
            
          } else if (butval[i][x + 3] == 0) {
            playSound(win);
            JOptionPane.showMessageDialog(null, "Red wins!", "Connect 4: Notification", JOptionPane.INFORMATION_MESSAGE, (new ImageIcon("//win image here")));
            
            for (int l = 0 ; l < rows ; l++)
            {
              for (int m = 0 ; m < cols ; m++)
              {
                butval[l] [m] = 9;
                btn [l] [m].setIcon(new ImageIcon("blank.jpg"));
                
              }
            }
            turn = 0;
            turncount.setText("Yellow's Turn");
          }
        }
      }
    }
    
    //Diagonal from the top left to bottom right
    for (int x = 0; x < 7 - 3; x++) {
      for (int i = 0; i < 6 - 3; i++) {
        if (butval[i][x] == butval[i + 1][x + 1] &&
            butval[i + 1][x + 1] == butval[i + 2][x + 2] &&
            butval[i + 2][x + 2] == butval[i + 3][x + 3] &&
            butval[i + 3][x + 3] != 9) {
          
          if (butval[i][x] == 1) {
            playSound(win);
            JOptionPane.showMessageDialog(null, "Yellow wins!", "Connect 4: Notification", JOptionPane.INFORMATION_MESSAGE, (new ImageIcon("//win image here ")));
            
            for (int n = 0 ; n < rows ; n++)
            {
              for (int o = 0 ; o < cols ; o++)
              {
                butval[n] [o] = 9;
                btn [n] [o].setIcon(new ImageIcon("blank.jpg"));
                turn = 0;
              }
            }
            turn = 0;
            turncount.setText("Yellow's Turn");
            
          } else if (butval[i][x] == 0) {
            playSound(win);
            JOptionPane.showMessageDialog(null, "Red wins!", "Connect 4: Notification", JOptionPane.INFORMATION_MESSAGE, (new ImageIcon("//win image here")));
            
            for (int q = 0 ; q < rows ; q++)
            {
              for (int r = 0 ; r < cols ; r++)
              {
                butval[q] [r] = 9;
                btn [q] [r].setIcon(new ImageIcon("blank.jpg"));
                
              }
            }
            
          }
          turn = 0;
          turncount.setText("Yellow's Turn");
        }
      }
    }
    
    //Diagonal from the top right to the bottom left
    for (int x = 0; x < 4; x++) {
      for (int i = 3; i <6 ; i++) {
        if (butval[i][x] == butval[i - 1][x + 1] &&
            butval[i - 1][x + 1] == butval[i - 2][x + 2] &&
            butval[i - 2][x + 2] == butval[i - 3][x + 3] &&
            butval[i - 3][x + 3] != 9) {
          
          if (butval[i][x] == 1) {
            playSound(win);
            JOptionPane.showMessageDialog(null, "Yellow wins!", "Connect 4: Notification", JOptionPane.INFORMATION_MESSAGE, (new ImageIcon("//win image here ")));
            
            for (int s = 0 ; s < rows ; s++)
            {
              for (int t = 0 ; t < cols ; t++)
              {
                butval[s] [t] = 9;
                btn [s] [t].setIcon(new ImageIcon("blank.jpg"));
                
              }
            }
            turn = 0;
            turncount.setText("Yellow's Turn");
            
          } else if (butval[i][x] == 0) {
            playSound(win);
            JOptionPane.showMessageDialog(null, "Red wins!", "Connect 4: Notification", JOptionPane.INFORMATION_MESSAGE, (new ImageIcon("//win image here")));
            
            for (int u = 0 ; u < rows ; u++)
            {
              for (int v = 0 ; v < cols ; v++)
              {
                butval[u] [v] = 9;
                btn [u] [v].setIcon(new ImageIcon("blank.jpg"));
                
              }
            }
            turn = 0;
            turncount.setText("Yellow's Turn");
          }
        }
      }
    }
  }
  
  /////
  //MAIN METHOD
  /////
  public static void main(String args[]) {
    
    //Constructor is called and run
    new connect4gui(); 
  }
  
  /////
  //THIS METHOD DEALS WITH SOUND, USING A THROW-CATCH STATEMENT. The string that it collects is the file name. (works with .wav files best)
  /////
  public void playSound(String audioFileName) {
    
    //Tries the code, but provides a "catch" to handle any exceptions
    try {
      
      //Opens an audio input stream, which looks in the same folder as the class file for the audio file
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(connect4gui.class.getResource(audioFileName));
      
      //Gets a sound clip resource
      clip = AudioSystem.getClip();
      
      //Open audio clip and loads clip from the audio input stream.
      clip.open(audioIn);
      
      //Starts
      clip.start();
      
      
      //In case of error, a JOptionPane pops up, saying an audio file is not found
    } catch (Exception ex) {
      
      JOptionPane.showMessageDialog(null, "ERROR 404; AUDIOFILE NOT FOUND!", "AUDIO: " + "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
    }
    
  }
  
  //this is another sound method which uses a boolean method to actually start and stop the music.
  public void playSoundLoop(String audioFileName, boolean yahOrNah) {
    
    if (yahOrNah == true) {
      
      //Tries the code, but provides a "catch" to handle any exceptions
      try {
        
        //Opens an audio input stream, which looks in the same folder as the class file for the audio file
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(connect4gui.class.getResource(audioFileName));
        
        //Gets a sound clip resource
        clip2 = AudioSystem.getClip();
        
        //Open audio clip and loads clip from the audio input stream.
        clip2.open(audioIn);
        
        clip2.loop(Clip.LOOP_CONTINUOUSLY);
        
        //Starts
        clip2.start();
        
        
        //In case of error, a JOptionPane pops up, saying an audio file is not found
      } catch (Exception ex) {
        
        JOptionPane.showMessageDialog(null, "ERROR 404; AUDIOFILE NOT FOUND!", "AUDIO: " + "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
      }
      
    } else {
      clip2.stop();
    }
  }
}
