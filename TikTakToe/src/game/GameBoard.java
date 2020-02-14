package game;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3;
    static int cellSize = 150;
    public static char[][] gameField;
    private GameButton[] gameButtons;
    static char nullSymbol = '\u0000';

    private Game game;

    public GameBoard(Game currentGame) {
        this.game = currentGame;
        initField();
    }
    private void  initField(){
        setBounds(cellSize*dimension, cellSize*dimension, 400,300);
        setTitle("Крестики-Нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");


        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             emptyField();
            }
        });
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize*dimension,150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension,dimension));
        gameFieldPanel.setSize(cellSize*dimension,cellSize*dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension*dimension];

        for (int i=0; i<(dimension*dimension); i++) {
         GameButton fieldButton = new GameButton(i, this);
         gameFieldPanel.add(fieldButton);
         gameButtons[i] = fieldButton;
        }
        getContentPane().add(controlPanel,BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel,BorderLayout.CENTER);
        setVisible(true);

    }
    void  emptyField(){
        for (int i=0; i<(dimension*dimension); i++) {
            gameButtons[i].setText("");
            int x = i/GameBoard.dimension;
            int y = i%GameBoard.dimension;

            gameField[x][y]=nullSymbol;
        }
    }
 Game getGame(){
        return game;
 }

    boolean isTurnable(int x, int y){
      boolean result=false;
       if (gameField[y][x]== nullSymbol) result = true;
        return result;
         }

    void updateGameField(int x, int y) {
        gameField[y][x]= game.getCurrentPlayers().getPlayerSign();
    }

    boolean checkWin(){
        boolean result=false;
        char playerSymbol = getGame().getCurrentPlayers().getPlayerSign();
       /* if (checkWinDiagonals(playerSymbol)||checkWinLines(playerSymbol))*/
        if (checkWinLines(playerSymbol))
        {
            result = true;
        }
        return result;
            }

    boolean isFull() {
        boolean result=true;
         for (int i=0; i<dimension; i++) {
             for (int j=0; j<dimension; j++)
             {
                if (gameField[i][j]==nullSymbol) result=false;
             }
         }
        return result;
    }
    /*********Изменил проверку на выигрыш, закомментил что было раньше****************/
    /*****кроме того, здесь сразу проверяем и выигрыш по диагоналям*****/
    /**здесь же накапливаем данные о состоянии игрового поля
    для логики компьютера в матрицу state_gameField**/
    private boolean checkWinLines(char playerSymbol) {
     boolean /*cols, rows,*/ result;
     int cols, rows, d1, d2, i;
      i=d1=d2=0;             // в этих переменных накапливаем кол-во символов по диагонали
     result = false;
     for (int col=0; col<dimension; col++) {
         /*cols=true;
         rows=true;*/

         cols=rows=0;/**  количествo символов в строках и столбцах*/
         for (int row=0; row<dimension; row++) {
             /*cols&=(gameField[col][row]==playerSymbol);
             rows&=(gameField[row][col]==playerSymbol);*/
         /** здесь происходит подсчет количества символов в строках и столбцах*/
         if (gameField[col][row]==playerSymbol) {
             cols=cols+1;
             }
         if (gameField[row][col]==playerSymbol) {
                 rows=rows+1;
             }
         /**здесь происходит подсчет количества символов в диагоналях главной и вспомогательной*/
         if ((col==row)&&(gameField[row][col]==playerSymbol))   {
                 d1=d1+1;
             }
        if ((dimension - 1 - row==col)&&(gameField[row][col]==playerSymbol))   {
                 d2=d2+1;
             }
         }
        if ((cols==dimension)||(rows==dimension)||(d1==dimension)||(d2==dimension)){
             result=true;
             break;
             }
        if (playerSymbol=='X')  i=0;
         else i=1;

            GameActionListener.state_gameField[col][i]=cols; //количество определенных символов в столбцах
            GameActionListener.state_gameField[col+dimension][i]=rows; //количество определенных символов в строках

        /*if (result) {
             break;
         }*/
     }
        GameActionListener.state_gameField[GameActionListener.size-2][i]=d1; //количество определенных символов в диагоналях
        GameActionListener.state_gameField[GameActionListener.size-1][i]=d2;
     return result;
    }
    /****************Здесь сделал сам подсчет победы происходит иначе нежели по строкам
     * или столбцам, но всю проверку перенес в один метод checkWinLines
     * так как все равно там пробегаем всю матрицу**/
    /*private boolean checkWinDiagonals(char playerSymbol) {
       boolean result;
       result=false;
       /*********проверяем выигрыш по главной и побочной диагонали************/
       /*for (int i=0; i<dimension; i++) {
           if (gameField[i][i]==playerSymbol) {
               result=true;
           }
           else {
               result = false;
               break;
           }
       }
        if (result==false) { // попадаем сюда только если на главной диагонали нет выигрыша
            for (int i = 0; i < dimension; i++) {
                if (gameField[dimension - 1 - i][i] == playerSymbol) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;

        }*/
    /************************************************************************/
    public GameButton getButton(int buttonIndex) {
        return gameButtons[buttonIndex];
    }
}
