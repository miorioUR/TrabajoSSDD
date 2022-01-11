import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class AtenderPeticion implements Runnable {

	List<Socket> users;
	Socket s;

	public AtenderPeticion(Socket s, List<Socket> u) {
		this.users = u;
		this.s = s;
		u.add(s);
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.s.getInputStream(), "UTF-8"));
			String mensaje = br.readLine();

			while (!mensaje.equalsIgnoreCase("/disconnect")) {
				for (int i = 0; i < users.size(); i++) {
					if(!users.get(i).equals(s)) {
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(users.get(i).getOutputStream(), "UTF-8"));
						bw.write(mensaje + "\r\n");
						bw.flush();
					}
				}
				mensaje = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}