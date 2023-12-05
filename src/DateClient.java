import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DateClient {
    public static void main(String[] args) throws Exception {
        var socket = new Socket("localhost", 59090);

        //об'єкти для спілкування  з сервером
        ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

        // Генеруємо випадкові розміри для матриць (N, M, L > 1000)
        int N = RandomGenerator.generateRandomSize();
        int M = RandomGenerator.generateRandomSize();
        int L = RandomGenerator.generateRandomSize();

        // Створюємо та заповнюємо першу матрицю NxM
        int[][] matrix1 = RandomGenerator.generateRandomMatrix(N, M);

        // Створюємо та заповнюємо другу матрицю MxL
        int[][] matrix2 = RandomGenerator.generateRandomMatrix(M, L);

        // Виводимо згенеровані матриці
        System.out.println("Перша матриця NxM:");
        PrintMatrix.printMatrix(matrix1);

        System.out.println("\nДруга матриця MxL:");
        PrintMatrix.printMatrix(matrix2);

        // відправляємо масив 1 на сервер
        outToServer.writeObject(matrix1);
        // відправляємо масив 2 на сервер
        outToServer.writeObject(matrix2);

        // отримуємо результат від сервер відповідно до нашого запиту
        int[][] serverResult = (int[][]) inFromServer.readObject();
        System.out.println("\nResult matrix from server");
        PrintMatrix.printMatrix(serverResult);

        System.out.println();

        // закриваємо потоки
        outToServer.close();
        inFromServer.close();
        socket.close();

        System.out.println("Process completed");

        // для того щоб подивитися результат
        Thread.sleep(3);
    }
}
