package GUI;

import GameElements.Board;
import GameElements.PlayerType;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(int size, PlayerType type1, PlayerType type2){
        this.add(new JBoard(size, type1, type2));
        this.setTitle("Impasse");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    public GameFrame(Board board, String title){
        this.add(new JBoard(board));
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
