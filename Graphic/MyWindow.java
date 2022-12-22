package Graphic;
import Logic.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {

    Map jpMap;
    Font btnFont = new Font("Times New Roman", Font.PLAIN, 16);


    public MyWindow() {
        setSize(505, 587);
        setResizable(false);
        setLocation(450, 100);
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jpMap = new Map();
        jpMap.startNewGame(3, 3);
        add(jpMap, BorderLayout.CENTER);

        // Основная нижняя панель
        CardLayout cardLayout = new CardLayout();
        JPanel jpBottom = new JPanel(cardLayout);

        jpBottom.setPreferredSize(new Dimension(1, 60));
        add(jpBottom, BorderLayout.SOUTH);


        // Панель с кнопками Старт/Выход
        JPanel jpStartExit = new JPanel(new GridLayout());
        JButton jbStart = new JButton("Начать новую игру");
        jbStart.setFont(btnFont);
        jbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(jpBottom, "jpSelectPlayers");

            }
        });
        jpStartExit.add(jbStart);
        JButton jbExit = new JButton("Выйти из игры");
        jbExit.setFont(btnFont);
        jbExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jpStartExit.add(jbExit);
        jpBottom.add(jpStartExit, "jpStartExit");

        // Создание панели выбора игроков
        JPanel jpSelectPlayers = new JPanel(new GridLayout());
        JButton jbHumanVsHuman = new JButton("Человек vs. Человек");
        jbHumanVsHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpSelectMap");
                jpMap.setHumanVsHuman(true);
            }
        });
        JButton jbHumanVsAI = new JButton("Человек vs. AI");
        jbHumanVsAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpSelectMap");
                jpMap.setHumanVsHuman(false);
            }
        });
        jpSelectPlayers.add(jbHumanVsHuman);
        jpSelectPlayers.add(jbHumanVsAI);
        jpBottom.add(jpSelectPlayers, "jpSelectPlayers");

        // Создание панели выбора поля
        JPanel jpSelectMap = new JPanel(new GridLayout());
        JButton jbSM3x3l3 = new JButton("Поле: 3x3 Серия: 3");
        JButton jbSM5x5l4 = new JButton("Поле: 5x5 Серия: 4");
        JButton jbSM10x10l5 = new JButton("Поле: 10x10 Серия: 5");

        jpSelectMap.add(jbSM3x3l3);
        jbSM3x3l3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpStartExit");
                createMap(3, 3);
            }
        });
        jpSelectMap.add(jbSM5x5l4);
        jbSM5x5l4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpStartExit");
                createMap(5, 4);
            }
        });
        jpSelectMap.add(jbSM10x10l5);
        jbSM10x10l5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpStartExit");
                createMap(10, 5);
            }
        });
        jpBottom.add(jpSelectMap, "jpSelectMap");

        ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpStartExit");

        setVisible(true);
    }

    public void createMap(int size, int dotsToWin) {
        jpMap.startNewGame(size, dotsToWin);
    }

}
