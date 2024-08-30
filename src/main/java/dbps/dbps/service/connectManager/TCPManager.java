package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

import static dbps.dbps.Constants.CONNECT_START;
import static dbps.dbps.Constants.hexStringToByteArray;

public class TCPManager {

    @Getter
    private String IP;
    @Getter
    private int PORT;

    Socket socket = null;

    private static TCPManager tcpManager = null;
    private static LogService logService;

    private TCPManager() {
        logService = LogService.getLogService();
    }

    public static TCPManager getManager() {
        if (tcpManager == null) {
            tcpManager = new TCPManager();
        }
        return tcpManager;
    }

    public String sendASCMsg(String msg) {
        if (socket == null) {
            logService.warningLog("TCP 소켓이 열려있지 않습니다.");
            connect(IP, PORT);
        }

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream output = socket.getOutputStream();

            output.write(msg.getBytes(Charset.forName("EUC-KR")));
            output.flush();

            return input.readLine();
        } catch (IOException e){
            logService.errorLog(msg+"전송에 실패했습니다.");
        }

        return "에러코드";
    }

    public String sendHEXMsg(String msg){
        if (socket == null) {
            logService.warningLog("TCP 소켓이 열려있지 않습니다.");
            connect(IP, PORT);
        }

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream output = socket.getOutputStream();

            output.write(hexStringToByteArray(msg));
            output.flush();

            return input.readLine();
        } catch (IOException e){
            logService.errorLog(msg+"전송에 실패했습니다.");
        }

        return "에러코드";
    }


    //접속하기
    public void connect(String IP, int PORT){
        logService.updateInfoLog("TCP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);
        this.IP = IP;
        this.PORT = PORT;

        try {
            socket = new Socket(IP, PORT);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream output = socket.getOutputStream();

            output.write(CONNECT_START);
            output.flush();

            String response = input.readLine();

            if (response.equals("연결성공코드")){
                logService.updateInfoLog("TCP 서버에 연결되었습니다. IP : " + IP + ", PORT: " + PORT);
            } else {
                logService.errorLog("TCP 서버 연결에 실패했습니다. IP: " + IP + ", PORT: " + PORT);
            }


        }catch (IOException e){
            logService.errorLog("UDP 서버 연결에 실패했습니다. IP: " + IP + ", PORT: " + PORT);
        }

    }


    //접속끊기
    public void disconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        socket = null;
        logService.updateInfoLog("TCP 서버 연결이 종료되었습니다. IP: " + IP + ", PORT: " + PORT);
    }
}
