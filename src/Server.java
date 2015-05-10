import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
/**
 * Created by Иван on 10.05.2015.
 * UDP Server
 */
public class Server {
    public static void main(String[] args) {
        try {
            byte[] data = new byte[1000];
            DatagramSocket s = new DatagramSocket();
            InetAddress addr = InetAddress.getLocalHost();
/*файл с именем toxic.mp3 должен лежать в корне проекта*/
            FileInputStream fr = new FileInputStream(new File("test.txt"));
            DatagramPacket pac;
            while (fr.read(data) != -1) {
//создание пакета данных
                pac = new DatagramPacket(data, data.length, addr, 8033);
                s.send(pac);//отправление пакета
            }
            fr.close();
            System.out.println("Файл отправлен");
        } catch (UnknownHostException e) {
// неверный адрес получателя
            e.printStackTrace();
        } catch (SocketException e) {
// возникли ошибки при передаче данных
            e.printStackTrace();
        } catch (FileNotFoundException e) {
// не найден отправляемый файл
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}