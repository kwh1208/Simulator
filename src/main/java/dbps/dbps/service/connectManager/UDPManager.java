package dbps.dbps.service.connectManager;

import dbps.dbps.service.DabitNetService;
import dbps.dbps.service.LogService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static dbps.dbps.Constants.*;

public class UDPManager {
    //IP, PORT에 정보 넣는 시점은 버튼 누를때 controller에서 파라미터로 넣을 예정
    @Getter
    @Setter
    private String IP;

    @Getter
    @Setter
    private int PORT;


    DatagramSocket socket = null;

    private static UDPManager udpManager = null;
    private static LogService logService;
    private static DabitNetService dabitNetService;


    private UDPManager() {
        logService = LogService.getLogService();
        dabitNetService = DabitNetService.getInstance();
        setIP(UDP_IP);
        setPORT(UDP_PORT);
    }

    public static UDPManager getUDPManager() {
        if (udpManager == null) {
            udpManager = new UDPManager();
        }
        return udpManager;
    }

    public Task<String> sendASCMsg(String msg, boolean utf8){
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                if (socket == null||socket.isClosed()) {
                    connect(IP, PORT);
                }
                DatagramPacket receivePacket;
                try{
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    byte[] sendByte = msg.getBytes(Charset.forName("MS949"));

                    if (utf8) sendByte = msg.getBytes(StandardCharsets.UTF_8);
                    else if (ascUTF16) sendByte = msg.getBytes(StandardCharsets.UTF_16BE);
                    DatagramPacket sendPacket = new DatagramPacket(sendByte, sendByte.length, serverAddr, PORT);
                    logService.updateInfoLog("전송 메세지 :"+msg);
                    socket.send(sendPacket);
                    int totalBytesRead = 0;
                    byte[] receiveBuffer = new byte[1024];
                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    while (true) {
                        try {
                            // 패킷 수신
                            socket.receive(receivePacket);
                            int bytesRead = receivePacket.getLength(); // 수신된 바이트 수
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;

                                // 데이터 처리 로직
                                if (dataReceivedIsComplete(receiveBuffer, totalBytesRead)) {
                                    break; // 수신 완료 조건 만족 시 루프 종료
                                }
                            }
                        } catch (java.net.SocketTimeoutException e) {
                            logService.errorLog("데이터 수신에 실패했습니다. 연결상태를 확인해주세요.");
                            throw e;
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }

                    String result = new String(receiveBuffer, 0, totalBytesRead);
                    if (result.contains("RX") && result.contains("![") && result.contains("!]")) {
                        int indexTX = result.indexOf("TX");
                        result = result.substring(indexTX);
                        result = result.substring(result.indexOf("!["), result.indexOf("!]")+2);
                    }
                    logService.updateInfoLog("받은 메세지 :"+result);
                    return result;
                }catch (IOException e){
                    //에러 처리
                    logService.errorLog(msg+"전송에 실패했습니다.");
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg){
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket == null||socket.isClosed()) {
                    connect(IP, PORT);
                }
                DatagramPacket receivePacket;
                try {
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    logService.updateInfoLog("전송 메세지 :"+bytesToHex(msg, msg.length));
                    DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, serverAddr, PORT);
                    socket.send(sendPacket);

                    byte[] receiveBuffer = new byte[1024];
                    int totalBytesRead = 0;
                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    while (true) {
                        try {
                            socket.receive(receivePacket);
                            int bytesRead = receivePacket.getLength();
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;
                                // 데이터 처리 로직
                                if (dataReceivedIsCompleteHex(receiveBuffer, totalBytesRead)) {
                                    break; // 수신 완료 조건 만족 시 루프 종료
                                }
                            }
                        } catch (SocketTimeoutException e) {
                            logService.errorLog("데이터 수신에 실패했습니다. 연결상태를 확인해주세요.");
                            throw new RuntimeException();
                        }
                    }
                    String result = bytesToHex(receivePacket.getData(), receivePacket.getLength());
                    if (result.contains("52 58 28")) {
                        result = result.substring(result.indexOf("10 02"));
                    }
                    logService.updateInfoLog("받은 메세지 :"+result);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    public String sendMsgAndGetMsgByteNoLog(byte[] msg) throws IOException {
        if (socket == null||socket.isClosed()) {
            connectNoLog(IP, PORT);
        }
        DatagramPacket receivePacket;
        try {
            InetAddress serverAddr = InetAddress.getByName(IP);
            DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, serverAddr, PORT);
            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];
            int totalBytesRead = 0;
            receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            while (true) {
                try {
                    socket.receive(receivePacket);
                    int bytesRead = receivePacket.getLength();
                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;
                        // 데이터 처리 로직
                        if (dataReceivedIsCompleteHex(receiveBuffer, totalBytesRead)) {
                            break; // 수신 완료 조건 만족 시 루프 종료
                        }
                    }
                } catch (SocketTimeoutException e) {
                    logService.errorLog("데이터 수신에 실패했습니다. 연결상태를 확인해주세요.");
                    throw new RuntimeException();
                }
            }
            String result = bytesToHex(receivePacket.getData(), receivePacket.getLength());
            if (result.contains("52 58 28")) {
                result = result.substring(result.indexOf("10 02"));
            }
            return result;
        } catch (IOException e) {
            throw e;
        } finally {
            disconnectNoLog();
        }
    }

    List<DatagramSocket> socketList = new ArrayList<>();

    boolean WifiOnly;
    boolean etherNetOnly;

    public Task<String> send300MsgAndGetMsgByte(byte[] msg) {
        return new Task<>() {
            @Override
            protected String call() throws IOException {
                if (socketList == null || socketList.isEmpty()) {
                    throw new IOException("연결된 소켓이 없습니다.");
                }

                List<String> receivedMessages = new ArrayList<>();
                InetAddress serverAddr = InetAddress.getByName(IP);


                try {
                    for (DatagramSocket socket : socketList) {
                        if (socket == null || socket.isClosed()) {
                            continue;
                        }

                        InetAddress localAddr = socket.getLocalAddress();
                        NetworkInterface netInterface = NetworkInterface.getByInetAddress(localAddr);
                        String interfaceName = (netInterface != null) ? netInterface.getDisplayName().toLowerCase() : "unknown";

                        boolean isWifi = interfaceName.contains("wi-fi") || interfaceName.contains("wlan");
                        boolean isEthernet = (interfaceName.contains("ethernet") || interfaceName.contains("eth") || interfaceName.contains("usb") || interfaceName.contains("thunderbolt"))
                                && !interfaceName.contains("vmware")
                                && !interfaceName.contains("virtualbox")
                                && !interfaceName.contains("hyper-v");

                        if (isWifi) {
                            if (!WifiOnly) {
                                continue;
                            }
                            socket.send(new DatagramPacket(msg, msg.length, serverAddr, 5107));
                            socket.send(new DatagramPacket(msg, msg.length, serverAddr, 5108));
                        } else if (isEthernet) {
                            if (!etherNetOnly) {
                                continue;
                            }
                            socket.send(new DatagramPacket(msg, msg.length, serverAddr, 5108));
                        }


                        byte[] receiveBuffer = new byte[1024];
                        while (true) {
                            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                            try {
                                socket.receive(receivePacket);
                                int bytesRead = receivePacket.getLength();
                                if (bytesRead > 0) {
                                    String message = new String(receivePacket.getData(), 0, bytesRead);
                                    Platform.runLater(() -> dabitNetService.updateUI(message));
                                    receivedMessages.add(message);
                                }
                            } catch (SocketTimeoutException e) {
                                break;
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return String.join("", receivedMessages);
            }
        };
    }



    public void  connect300All(){
        connect300Wifi(5107);
        connect300Ethernet(5108);

        WifiOnly = true;
        etherNetOnly = true;
    }

    public void connect300Wifi(int port){
        this.IP = "255.255.255.255";
        this.PORT = port;
        try {
            String wifiIP = getLocalWiFiIP();
            WifiOnly = true;
            etherNetOnly = false;

            boolean alreadyExists = false;
            for (DatagramSocket socket : socketList) {
                if (socket.getLocalAddress().getHostAddress().equals(wifiIP)) {
                    alreadyExists = true;
                    break;
                }
            }
            if (alreadyExists) {
                return;
            }
            DatagramSocket tmpSocket = new DatagramSocket(new InetSocketAddress(wifiIP, 5109));
            tmpSocket.setBroadcast(true);
            tmpSocket.setSoTimeout(RESPONSE_LATENCY*1000);

            socketList.add(tmpSocket);
        } catch (SocketException e) {
            e.printStackTrace();
            logService.errorLog("IP: " + IP + ", PORT: " + PORT+"열기에 실패했습니다.");
        }
    }

    private String getLocalWiFiIP() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                // Wi-Fi 인터페이스인지 확인 (이름이 보통 "wlan" 또는 "wi-fi" 포함)
                if (networkInterface.isLoopback() || !networkInterface.isUp() || networkInterface.getDisplayName().toLowerCase().contains("virtual")) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        return addr.getHostAddress(); // Wi-Fi IP 주소 반환
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1"; // 실패 시 기본값
    }

    public void connect300Ethernet(int port) {
        this.IP = "255.255.255.255";
        this.PORT = port;

        try {
            List<String> ethernetIPs = getLocalEthernetIPs();

            WifiOnly = false;
            etherNetOnly = true;

            // ✅ 기존 소켓의 IP 주소를 Set으로 변환하여 빠른 중복 검사 (O(1))
            Set<String> existingIPs = new HashSet<>();
            for (DatagramSocket socket : socketList) {
                existingIPs.add(socket.getLocalAddress().getHostAddress());
            }

            for (String ethernetIP : ethernetIPs) {
                if (existingIPs.contains(ethernetIP)) {
                    continue; // ✅ 이미 존재하는 IP라면 건너뜀 (O(1) 연산)
                }

                DatagramSocket tmpSocket = new DatagramSocket(new InetSocketAddress(ethernetIP, 5109));
                tmpSocket.setBroadcast(true);
                tmpSocket.setSoTimeout(RESPONSE_LATENCY * 1000);
                socketList.add(tmpSocket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logService.errorLog("IP: " + IP + ", PORT: " + PORT + " 열기에 실패했습니다.");
        }
    }


    private List<String> getLocalEthernetIPs() {
        List<String> ipList = new ArrayList<>();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                // Wi-Fi가 아닌, 이더넷 인터페이스만 찾기
                if (networkInterface.isLoopback() || !networkInterface.isUp() || networkInterface.getDisplayName().toLowerCase().contains("wi-fi")) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        ipList.add(addr.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return ipList;
    }

    //접속하기
    public void connect(String IP, int PORT){
        logService.updateInfoLog("UDP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);
        this.IP = IP;
        this.PORT = PORT;
        try {
            if (socket == null) {
                socket = new DatagramSocket(5109);
            }
            socket.setBroadcast(true);
            socket.setSoTimeout(RESPONSE_LATENCY*1000);
        } catch (SocketException e) {
            e.printStackTrace();
            logService.errorLog("IP: " + IP + ", PORT: " + PORT+"열기에 실패했습니다.");
        }
    }

    public void connectNoLog(String IP, int PORT){
        this.IP = IP;
        this.PORT = PORT;
        try {
            if (socket == null) {
                socket = new DatagramSocket();
            }
            socket.setBroadcast(true);
            socket.setSoTimeout(RESPONSE_LATENCY*1000);
        } catch (SocketException e) {
            logService.errorLog("IP: " + IP + ", PORT: " + PORT+"열기에 실패했습니다.");
        }
    }

    //접속끊기
    public void disconnect() {
        if (socket == null && socketList.isEmpty()){
            return;
        }

        if (!socketList.isEmpty()){
            for (DatagramSocket datagramSocket : socketList) {
                System.out.println(datagramSocket.getInetAddress());
                datagramSocket.disconnect();
                datagramSocket.close();
            }
        }
        socketList.clear();

        if (socket != null){
            socket.disconnect();
            socket.close();
            socket = null;
        }
        logService.updateInfoLog("UDP 서버를 종료합니다. IP: " + IP + ", PORT: " + PORT);
    }

    public void disconnectNoLog() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                if (socket == null && socketList.isEmpty()) {
                    return null;
                }

                if (!socketList.isEmpty()) {
                    for (DatagramSocket datagramSocket : socketList) {
                        datagramSocket.disconnect();
                        datagramSocket.close();
                    }
                    socketList.clear();
                }

                if (socket != null) {
                    socket.disconnect();
                    socket.close();
                    socket = null;
                }

                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true); // UI 종료 시 자동 종료되도록 설정
        thread.start();
    }
}
