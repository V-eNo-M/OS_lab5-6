import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Иван on 10.05.2015.
 *
 */
public class ReservServer {
    private static String filename = "";
    public static void main(String[] args) {
        filename = messageFileName();
        retFile(filename);
    }
    public static String messageFileName(){
        try {
            DatagramSocket s = new DatagramSocket(8034);
            InetAddress addr = InetAddress.getLocalHost();
            byte[] data = new byte[100];
            System.out.println("Ожидаю имя файла");
            while (true){
                DatagramPacket pack = new DatagramPacket(data,data.length,addr, 8034);
                s.receive(pack);
                System.out.println(new String(pack.getData()));
                filename = new String(pack.getData());
                System.out.println("Имя файла получено");
                s.close();
                break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String r = filename.replaceAll("\u0000","");
        if(!filename.equals(""))
            return r;
        else return "test.txt";
    }
    public static void retFile(String filename) {
        try {
            DatagramSocket s = new DatagramSocket();
            InetAddress addr = InetAddress.getLocalHost();
            byte[] data = new byte[1000];
            FileInputStream fr = new FileInputStream(new File(filename));
            DatagramPacket pac;
            while (fr.read(data) != -1) {
                //создание пакета данных
                pac = new DatagramPacket(data, data.length, addr, 8033);
                s.send(pac);//отправление пакета
                System.out.println("Файл отправлен");
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){e.printStackTrace();}
    }
}
