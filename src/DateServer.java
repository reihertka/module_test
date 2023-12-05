import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DateServer {

    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(59090)) {
            System.out.println("The date server is running...");
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

            while (true) {
                // слухаємо підлючення клієнтів
                var socket = listener.accept();

                // закидуємо задачу у ThreadPool коли з'являється новий клієнт
                executor.submit(() -> {
                    try {
                        runClientProcessing(socket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            }
        }
    }

    // Функція для перемноження двох матриць
    private static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;

        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    private static void runClientProcessing(Socket socket) throws IOException, ClassNotFoundException {
        System.out.println("\n[" + new Date() + "]  New client has been accepted.");

        // об'єкти для спілкування  з клієнтом
        ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());

        // зчитуємо масив 1
        int[][] matrix1 = (int[][]) inFromClient.readObject();

        // виводимо масив 1 на консоль серверу
        System.out.println("matrix1 size from Client::" + matrix1.length + "x" + matrix1[0].length);
        System.out.print("matrix1 from Client:: [ ");
        PrintMatrix.printMatrixStr(matrix1);
        System.out.println("]");

        // зчитуємо масив 2
        int[][] matrix2 = (int[][]) inFromClient.readObject();

        // виводимо масив 2 на консоль серверу
        System.out.println("matrix2 size from Client::" + matrix2.length + "x" + matrix2[0].length);
        System.out.print("matrix2 from Client:: [ ");
        PrintMatrix.printMatrixStr(matrix2);
        System.out.println("]");

        // розраховуємо перемноження матриць
        int[][] resultMatrix = multiplyMatrices(matrix1, matrix2);
        System.out.print("\nresult matrix:: [ ");
        PrintMatrix.printMatrixStr(resultMatrix);
        System.out.println("]");

        // відправляємо результат до клієнта
        outToClient.writeObject(resultMatrix);
    }
}
