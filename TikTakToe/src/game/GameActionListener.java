package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener  {
    public static int size = 2*GameBoard.dimension+2;
    public static int[][] state_gameField = new int[size][2];  // сюда накапливаем данные о количестве
    // символов по строкам, столбцам и диагоналям в 0-строку для человека, в первую строку
    // для компьютера
    /**
 "X"   [сумма в 0 столбце,...,сумма в dimension столбце, сумма в главной диагонали, сумма в вспом. диагонали ]
 "0"   [сумма в 0 столбце,...,сумма в dimension столбце, сумма в главной диагонали, сумма в вспом. диагонали ]

     */

    private int row;
    private int cell;
    private GameButton button;

    public GameActionListener(int row, int cell, GameButton gButton){
        this.row = row;
        this.cell = cell;
        this.button = gButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board=button.getBoard();
        if (board.isTurnable(row,cell)) {
            updateByPlayersData(board);

            if (board.isFull()){
                board.getGame().showMessage("ничья!");
                board.emptyField();
            }
            else {
                updateByAiData(board);
            }
        }
        else {
            board.getGame().showMessage("некорректный ход!");
        }

    }
    private void updateByPlayersData(GameBoard board) {
        board.updateGameField(row,cell);
        button.setText(Character.toString(board.getGame().getCurrentPlayers().getPlayerSign()));

        if (board.checkWin()){
            button.getBoard().getGame().showMessage("Вы выиграли!");
            board.emptyField();
        }
        else {
            board.getGame().passTurn();
        }

    }
    private void updateByAiData(GameBoard board) {
        int x,y;
        int cols, rows, d1, d2, index_cols, index_rows;
        boolean result=false;
        /***********здесь будет логика компьютера**************/
       x=y=0;
        d1=d2=0;
        cols=rows=0;
        index_cols=index_rows=0;
       // ищем максимальный элемент в нашей матрице состояния, он даст где больше
       // всего накопилось определенных символов. При равенстве состояний в символах
       // человека (0 строка) и компьютера (1 строка) последней проверяется 1 строка
       // т.е. у компьютера приоритет ставить свои символы.
        for (int i=0; i<2; i++) {
            for (int j=0; j<GameBoard.dimension; j++) {
             if (cols<=state_gameField[j][i]) {
                 cols = state_gameField[j][i]; // макс по столбцам
                 index_cols=i;
             }

             if (rows<=state_gameField[j+GameBoard.dimension][i]) {
                 rows = state_gameField[j+GameBoard.dimension][i]; // макс по строкам
                 index_rows=i;
             }
            }
            if(d1<=state_gameField[size-2][i]) { // проверяем чего больше на главной диагонали
              d1= state_gameField[size-2][i];
            }
            if(d2<=state_gameField[size-1][i]) {// проверяем чего больше на второстеп. диагонали
                d2= state_gameField[size-1][i];
            }
           }
           // получили 4 параметра - номер столбца, строки, главная или вспом. диагонали
        // и выполняем по мере "важности" начиная с самого макисмального уже из них
        // по ходу проверяем корректность хода

       //главная диагональ
        if ((d1>=d2)&&(d1>=cols)&&(d1>=rows)) {
            for (int i=0; i<GameBoard.dimension; i++) {
                if (GameBoard.gameField[i][i]=='\u0000') {
                    x=i;
                            y=i;
                    result =true;
                    break;
                }
            }

        }
        //вспомогательная диагональ
        if ((d2>=d1)&&(d2>=cols)&&(d2>=rows)&&(result==false)) {
            for (int i=0; i<GameBoard.dimension; i++) {
                if (GameBoard.gameField[GameBoard.dimension-1-i][i]=='\u0000') {
                    y=GameBoard.dimension-i-1;
                    x=i;
                    result =true;
                    break;
                }
            }

        }
        //столбцы
        if ((cols>=d1)&&(cols>=d2)&&(cols>=rows)&&(result==false)) {
            for (int i=0; i<GameBoard.dimension; i++) {
                if (GameBoard.gameField[index_cols][i]=='\u0000') {
                    y=index_cols;
                    x=i;
                    result =true;
                    break;
                }
            }

        }
        //строки
        if ((rows>=d1)&&(rows>=d2)&&(rows>=cols)&&(result==false)) {
            for (int i=0; i<GameBoard.dimension; i++) {
                if (GameBoard.gameField[i][index_rows]=='\u0000') {
                    y=i;
                    x=index_rows;
                    result =true;
                    break;
                }
            }

        }


        /*********в случае если логичный ход не определен***********/

       if (result==false) {
            Random rnd = new Random();


            do {

                x = rnd.nextInt(GameBoard.dimension);
                y = rnd.nextInt(GameBoard.dimension);
            } while (!board.isTurnable(x, y));
        }
        /******************************************************/

        board.updateGameField(x,y);

        int cellIndex = GameBoard.dimension*x+y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayers().getPlayerSign()));

        if (board.checkWin()){
            button.getBoard().getGame().showMessage("Компьютер выиграл!");
            board.emptyField();
        }
        else {
            board.getGame().passTurn();
        }
    }

}
