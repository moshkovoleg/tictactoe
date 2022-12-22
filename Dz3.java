//import com.sun.glass.ui.Size;

import java.util.Random;
import java.util.Scanner;

public class Dz3 {

    private static final int SIZE = 3; // заменить на 5 для Задания №3
    private static final int LENGTH = 3; // заменить на 4 для Задания №3
    private static char[][] map = new char[SIZE][SIZE]; // игровое поле
    private static Scanner sc = new Scanner(System.in); // Scanner для чтения консоли
    private static Random rand = new Random(); // генератор случайных чисел

    public static void initMap() { // инициализируем массив map(игровое поле)
        for (int i = 0; i < SIZE; i++) { // двойным циклом проходимся по всему массиву
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = '*'; // и заполняем каждую ячейку * '\u2B1C' и '\u2B1B'
            }
        }
    }

    public static void printMap() { // выводим игровое поле в консоль
        //       System.out.println("0 1 2 3"); // первая строка с координатами
        for (int k = 0; k < SIZE + 1; k++) {
            System.out.print(k + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) { // начинаем печатать поле
            System.out.print((i + 1) + " "); // ставим номер строки 1-3
            for (int j = 0; j < SIZE; j++) { // начинаем печатать строку
                System.out.print(map[i][j] + " "); // посимвольно печатаем содержимое каждой ячейки поля
            }
            System.out.println(); // после распечатки строки, делаем пеFревод каретки
        }
        System.out.println(); // делаем дополнительный перевод строки
    }

    public static void main(String[] args) {
        initMap(); // инициализируем поле
        printMap(); // печатаем в консоль

        while (true) { // запускаем игровой цикл
            humanTurn(); // ход человека
            printMap(); // печать поля
            if (isCheckWin('X')) { // проверка победы человека
                System.out.println("Победил игрок");
                break;
            }
            if (isMapFull()) break; // если поле заполнилось, завершаем игру
            aiTurn(); // ход компьютера
            printMap(); // печать поля
            if (isCheckWin('O')) { // проверка победы компьютера
                System.out.println("Победил компьютер");
                break;
            }
            if (isMapFull()) break;
        }
        System.out.println("Game Over");
    }


    public static boolean isCheckWin(char c) { // Проверяем победу
        // Задание №2
        // Ищем заполненные столбцы
        int x, y, count;
        for (x = 0; x < SIZE; x++) {
            count = 0;
            for (y = 0; y < SIZE; y++) {
                if (map[y][x] == c) count++;
            }
            if (count == LENGTH) return true;
        }
//        if (map[0][0] == c && map[0][1] == c && map[0][2] == c) return true;
//        if (map[1][0] == c && map[1][1] == c && map[1][2] == c) return true;
//        if (map[2][0] == c && map[2][1] == c && map[2][2] == c) return true;
        // Ищем заполненные строки
        for (y = 0; y < SIZE; y++) {
            count = 0;
            for (x = 0; x < SIZE; x++) {
                if (map[y][x] == c) count++;
            }
            if (count == LENGTH) return true;
        }
//        if (map[0][0] == c && map[1][0] == c && map[2][0] == c) return true;
//        if (map[0][1] == c && map[1][1] == c && map[2][1] == c) return true;
//        if (map[0][2] == c && map[1][2] == c && map[2][2] == c) return true;
        // Ищем заполненные диагональные линии
        for (x = 0, count = 0; x < SIZE; x++) {
            y = x;
            if (map[y][x] == c) count++;
            if (count == LENGTH) return true;
        }
        for (x = 0, count = 0; x < SIZE; x++) {
            y = SIZE - 1 - x;
            if (map[y][x] == c) count++;
            if (count == LENGTH) return true;
        }
//        for (x = 1, count = 0; x <= SIZE; x++) {
//            for (y = 1; y <= SIZE; y++) {
//                if (map[y - 1][x - 1] == c && (x + y) % 2 == 0) count += (x + y);
//            }
//            if (count == SIZE * 4 && map[SIZE / 2][SIZE / 2] == c) return true;
//        }
//        if (map[0][0] == c && map[1][1] == c && map[2][2] == c) return true;
//        if (map[2][0] == c && map[1][1] == c && map[0][2] == c) return true;
        return false; // если ни одной линии не нашли, значит игрок еще не победил
    }

    private static void humanTurn() { // ход человека
        int x, y;
        do {
            System.out.println("Введите координаты в формате Х и У");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;


        } while (!isCellEmpty(x, y)); // пользователь вводит координаты до тех пор, пока не укажет на свободную ячейку
        map[y][x] = 'X'; // после чего ставим туда Х
    }

    private static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == '*') return false; // если на поле нашли хотя бы одну *, значит поле еще не заполнилось
            }
        }
        return true;
    }

    private static void aiTurn() {
        // Задание №4
        int x, y, count;
        System.out.println("Ход компьютера");
        // Проверка предвыигрышных ситуаций (2 крестика подряд)
        for (x = 0; x < SIZE; x++) {
            count = 0;
            for (y = 0; y < SIZE; y++) {
                if (map[y][x] == 'X') count++; // считаем количество крестиков в столбце
                else if (map[y][x] == 'O') count--; // уменьшаем значимость столбца если в ней есть нолик
            }
            if (count == LENGTH - 1) { //в столбце предвыигрышная комбинация
                do { // ищем оставшуюся свободную клетку
                    y = rand.nextInt(SIZE);// x не изменяется (столбец тот же в котором считали крестики)
                }
                while (!isCellEmpty(x, y)); // ставим нолик в свободную клетку в столбце
                map[y][x] = 'O';
                return;
            }
        }
        for (y = 0; y < SIZE; y++) {
            count = 0;
            for (x = 0; x < SIZE; x++) {
                if (map[y][x] == 'X') count++; // считаем количество крестиков в строке
                else if (map[y][x] == 'O') count--; // уменьшаем значимость строки если в ней есть нолик
            }
            if (count == LENGTH - 1) { //в строке предвыигрышная комбинация
                do { // ищем оставшуюся свободную клетку
                    x = rand.nextInt(SIZE); // y не изменяется (строка та же в которой считали крестики)
                }
                while (!isCellEmpty(x, y)); // ставим нолик в свободную клетку в строке
                map[y][x] = 'O';
                return;
            }
        }
        for (x = 0, count = 0; x < SIZE; x++) {
            y = x;
            if (map[y][x] == 'X') count++; // считаем количество крестиков в главной диагонали
            else if (map[y][x] == 'O') count--; // уменьшаем значимость главной диагонали если в ней есть нолик
            if (count == LENGTH - 1) { //в диагонали предвыигрышная комбинация
                do { // ищем оставшуюся свободную клетку
                    x = rand.nextInt(SIZE);// x задает и номер строки и номер столбца
                    y = x;
                }
                while (!isCellEmpty(x, y)); // проверяем свободна ли клетка на главной диагонали
                map[y][x] = 'O';
                return;
            }
        }
        for (x = 0, count = 0; x < SIZE; x++) {
            y = SIZE - 1 - x;
            if (map[y][x] == 'X') count++; // считаем количество крестиков в побочной диагонали
            else if (map[y][x] == 'O') count--; // уменьшаем значимость побочной диагонали если в ней есть нолик
            if (count == LENGTH - 1) { //в диагонали предвыигрышная комбинация
                do { // ищем оставшуюся свободную клетку
                    x = rand.nextInt(SIZE);
                    y = SIZE - 1 - x;
                }
                while (!isCellEmpty(x, y)); // проверяем свободна ли клетка на побочной диагонали
                map[y][x] = 'O';
                return;
            }
        }
        //
        if (map[SIZE / 2][SIZE / 2] == '*') map[SIZE / 2][SIZE / 2] = 'O'; // если центр не занят - занимаем центр
        else if (map[SIZE / 2][SIZE / 2] == 'O') { // если центр занят нами
            do { // компьютер пытается случайно выбрать незанятое поле для хода
                x = rand.nextInt(SIZE);
                y = rand.nextInt(SIZE);
            }
            while ((x + y) % 2 != 0 && !isCellEmpty(x, y)); // ставим в боковую позицию, что бы игрок не поставил вилку
            map[y][x] = 'O';
            return;
//        } else if (map[SIZE / 2][SIZE / 2] == 'X') {// если центр занят игроком
//            do { // компьютер пытается случайно выбрать незанятое поле для хода
//                x = rand.nextInt(SIZE);
//                y = rand.nextInt(SIZE);
//            } while ((x + y) % 2 == 0 && !isCellEmpty(x, y));// ставим в угловую позицию, что бы игрок не поставил вилку
//            map[y][x] = 'O';
//            return;
        } else {
            do { // компьютер пытается случайно выбрать незанятое поле для хода
                x = rand.nextInt(SIZE);
                y = rand.nextInt(SIZE);
            }
            while (!isCellEmpty(x, y));// для игры 3х3 можно добавить проверку ((x + y) % 2 != 0 && !isCellEmpty(x, y))
            map[y][x] = 'O'; // как только ячейка найдена, ставим туда О
        }

    }
//x 0 1 2

    //    y
//    0
//    1
//    2

    public static boolean isCellEmpty(int x, int y) { // проверка является ли ячейка свободной
        if (x < 0 || x > SIZE - 1 || y < 0 || y > SIZE - 1)
            return false; // если указаны неверные координаты, считаем что ячейка условно занята
        if (map[y][x] != '*') return false; // если в ячейке не *, значит занята
        return true; // ячейка свободна
    }


}
