import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.setSoTimeout(3000);
        socket.connect(new InetSocketAddress(2000));
        System.out.println("已发起连接，并进入后续流程");
        System.out.println("客户端信息" + "ip" + socket.getLocalAddress() + "port" + socket.getLocalPort());
        System.out.println("服务端端信息"+ "ip" + socket.getInetAddress() + "port" + socket.getPort() );

        try {
            todo(socket);
        }catch(Exception e){}
        System.out.println("异常关闭");
        socket.close();
        System.out.println("客户端已退出");
    }



    private static void todo(Socket client) throws IOException{
        //构建键盘输入流
        InputStream inputStream = System.in;
        BufferedReader input = new BufferedReader(
                new InputStreamReader(inputStream));
        //得倒socket输出流，并转换为打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);
        //得倒socket输入流并转换为BufferedReader
        BufferedReader socketBufferedReader = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
        boolean flag = true;
        do{
            //键盘读取一行
            String str = input.readLine();
            //发送到服务器
            socketPrintStream.println(str);
            //服务器读一行
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)){
                flag = false;
            }else {
                System.out.println(echo);
            }
        }while(flag);
        socketPrintStream.close();
        socketBufferedReader.close();
    }



}
