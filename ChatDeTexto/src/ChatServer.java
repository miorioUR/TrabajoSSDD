import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		List<Socket> usuarios = new ArrayList<Socket>();
		try(ServerSocket ss = new ServerSocket(51515)){
			while(true) {
				try{
					Socket s = ss.accept();
					usuarios.add(s);
					System.out.println("Ha entrado un nuevo cliente al servidor");
					AtenderPeticion ap = new AtenderPeticion(s,usuarios);
					pool.execute(ap);
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			pool.shutdown();
		}
	}
}
