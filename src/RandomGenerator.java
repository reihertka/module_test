import java.util.Random;

public class RandomGenerator {

    // Генерує випадковий розмір матриці (більший за 1000)
    static int generateRandomSize() {
        Random random = new Random();
        return random.nextInt(1) + 1000; // Генеруємо випадкове число в діапазоні від 1001 до 10000
//        return random.nextInt(1) + 10; // Генеруємо випадкове число в діапазоні від 1001 до 10000
    }

    // Генерує випадкову матрицю з вказаними розмірами
    static int[][] generateRandomMatrix(int rows, int cols) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(); // Генеруємо випадкове ціле число
//                matrix[i][j] = random.nextInt(1) + 10; // Генеруємо випадкове ціле число
            }
        }

        return matrix;
    }
}
