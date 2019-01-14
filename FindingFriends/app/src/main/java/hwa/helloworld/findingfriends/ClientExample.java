package hwa.helloworld.findingfriends;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by student on 2019-01-14.
 */

public class ClientExample {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket();
            System.out.println("[연결 요청]");
            socket.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("[연결 성공]");

            // 데이터 보내고 받기
            byte[] bytes = null;
            String msg = null;

            OutputStream os = socket.getOutputStream();
            msg = "Hello Server";
            bytes = msg.getBytes("UTF-8");
            os.write(bytes);
            os.flush();
            System.out.println("[데이터 보내기 성공]");

            InputStream is = socket.getInputStream();
            bytes = new byte[100];
            int readByteCount = is.read(bytes);
            msg = new String(bytes, 0, readByteCount, "UTF-8");
            System.out.println("[데이터 받기 성공]: " + msg);

            os.close();
            is.close();

        } catch (Exception e) {

        }

        if(!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e1) {

            }
        }
    }
}
