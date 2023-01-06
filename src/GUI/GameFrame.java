package GUI;

import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(int size){
//        this.add(new JBoard(size));
        this.add(new JBoard(size));
        this.setTitle("Impasse 👾");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public GameFrame(JBoard jb){
        this.add(jb);
        this.setTitle("Impasse 🌈");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
