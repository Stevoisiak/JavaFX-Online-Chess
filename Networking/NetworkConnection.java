package Networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

// Based on "JavaFX Software: Chat (Server-Client)" by Almas Baimagambetov
// https://www.youtube.com/watch?v=VVUuo9VO2II
public abstract class NetworkConnection
{
    private ConnectionThread connThread = new ConnectionThread();

    // Called when recieving an object
    private Consumer<Serializable> onRecieveCallback;

    public NetworkConnection(Consumer<Serializable> onRecieveCallback) {
        this.onRecieveCallback = onRecieveCallback;

        // Allow main program to exit even if connection thread is still running
        connThread.setDaemon(true);
    }

    // Initialize connection
    public void startConnection() throws Exception {
        connThread.start();
    }

    // Send data
    public void send(Serializable data) throws Exception {
        connThread.out.writeObject(data);
    }

    // Close connection
    public void closeConnection() throws Exception {
        connThread.socket.close();
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    // Thread is created on startConnection()
    private class ConnectionThread extends Thread {
        private Socket socket;
        private ObjectOutputStream out;
        @Override
        public void run() {
            try (
                // if server, create new ServerSocket server;
                // if client, ServerSocket server = null;
                ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                // If server, wait for incoming connections
                // If client, make socket for outgoing data
                Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                // create output stream
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                // create input stream
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ) {
                onRecieveCallback.accept("Connection Established");
                this.socket = socket;
                this.out = out;

                // Disable message buffering
                socket.setTcpNoDelay(true);

                while (true) {
                    Serializable data = (Serializable) in.readObject();
                    onRecieveCallback.accept(data);
                }
            }
            catch (Exception e) {
                onRecieveCallback.accept("Connection closed");
            }
        }
    }
}
