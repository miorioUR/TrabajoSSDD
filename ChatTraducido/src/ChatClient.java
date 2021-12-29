import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class ChatClient {
	public static void main(String[] args) {
		try {
			CountDownLatch cdl = new CountDownLatch(1);
			
			Socket s = new Socket("localhost",51515);
			
			HiloUsuarioEscribir hue = new HiloUsuarioEscribir(s, cdl);
			hue.start();
			
			cdl.await();
			s.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
