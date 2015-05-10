import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by Иван on 10.05.2015.
 *
 */
public class Client {
    private static String fileName = "test.txt";

    public static void main(String[] args) throws UnknownHostException, SocketException {

        sendFileName(fileName);
        saveFile();

    }
    public static void saveFile() {
        File file = new File("nFile.txt");
        System.out.println("Прием данных…");
        try { // прием файла
            acceptFile(file, 8033, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendFileName(String file) {
        byte[] data = file.getBytes();
        try {
            DatagramSocket s = new DatagramSocket();
            InetAddress addr = InetAddress.getLocalHost();
            DatagramPacket pack = new DatagramPacket(data, data.length, addr, 8033);
            s.send(pack);
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void acceptFile(File file, int port, int pacSize) throws IOException {
        DatagramSocket s = new DatagramSocket(8033);
        InetAddress addr = InetAddress.getLocalHost();
        byte data[] = new byte[pacSize];
        DatagramPacket pac = new DatagramPacket(data, data.length,addr,8033);
        FileOutputStream os = new FileOutputStream(file);
        try {
/* установка времени ожидания: если в течение 10 секунд
не принято ни одного пакета, прием данных заканчивается*/
            s.setSoTimeout(60000);
            while (true) {
                s.receive(pac);
                os.write(data);
                os.flush();
                break;
            }
            System.out.println("Файл принят");
        } catch (SocketTimeoutException e) {
// если время ожидания вышло
            os.close();
            System.out.println("Истекло время ожидания, прием данных закончен");
        }
        s.close();
    }
}
