import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Random;

public class WhacAMole extends JFrame{

    int boardWidth=600;
    int boardHeight=650;
    int score=0;

    //Top panel properties
    JLabel scoreLabel=new JLabel();
    JPanel scorePanel=new JPanel();

    //Main panel properties
    JPanel boardPanel=new JPanel();

    //Clicking area
    JButton[] board=new JButton[9];

    //Element icons
    ImageIcon plantIcon;
    ImageIcon moleIcon;

    //Current location of image
    JButton currMoleTile;
    JButton currPlantTile;

    //Randomly put icons
    Random random=new Random();

    //set Timer
    Timer setMoleTimer;
    Timer setPlantTimer;


    WhacAMole(){
        setTitle("Mario : Whac A Mole");
        setSize(boardWidth,boardHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Top text panel
        scoreLabel.setFont(new Font("Arial",Font.PLAIN,50));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setText("Score: "+Integer.toString(score));
        scoreLabel.setOpaque(true);

        scorePanel.setLayout(new BorderLayout());
        scorePanel.add(scoreLabel);


        //Action area
        boardPanel.setLayout(new GridLayout(3,3));

        add(scorePanel,BorderLayout.NORTH);
        add(boardPanel);


        //getting images
        Image plantImg=new ImageIcon(getClass().getResource("./img/piranha.png")).getImage();
        plantIcon=new ImageIcon(plantImg.getScaledInstance(150,150,Image.SCALE_SMOOTH));

        Image moleImg=new ImageIcon(getClass().getResource("./img/monty.png")).getImage();
        moleIcon=new ImageIcon(moleImg.getScaledInstance(150,150,Image.SCALE_SMOOTH));



        //setting up board area
        for(int i=0;i<9;i++){
            JButton tile=new JButton();
            board[i]=tile;
            boardPanel.add(tile);
            tile.setFocusable(false);

            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton tile= (JButton) e.getSource();
                    if(tile==currMoleTile){
                        score+=10;
                        scoreLabel.setText("Score: "+Integer.toString(score));
                    }else if(tile==currPlantTile){
                        scoreLabel.setText("Game Over : "+Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for(int i=0;i<9;i++){
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
        }

        setMoleTimer=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currMoleTile!=null){
                    currMoleTile.setIcon(null);
                    currMoleTile=null;
                }

                //randomly select another tile
                int num=random.nextInt(9);
                JButton tile=board[num];

                //If tile is occupied by plant , skip the tile for this turn
                if(currPlantTile==tile) return;

                //set tile to mole
                currMoleTile=tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currPlantTile!=null){
                    currPlantTile.setIcon(null);
                    currPlantTile=null;
                }

                //randomly select another tile
                int num= random.nextInt(9);//generate a random number 0-8
                JButton tile=board[num];

                //If tile is occupied by mole, skip the tile for this turn
                if(currMoleTile==tile) return;

                //set tile to mole
                currPlantTile=tile;
                currPlantTile.setIcon(plantIcon);

            }
        });

        setMoleTimer.start();
        setPlantTimer.start();
        setVisible(true);
    }




}
