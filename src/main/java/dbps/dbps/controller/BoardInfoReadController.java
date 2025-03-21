package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.BoardInfoReadService;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.*;

public class BoardInfoReadController {
    public TextField brightness;
    public TextField horizontal;
    public TextField vertical;
    public TextField array;
    public TextField firmware;
    public TextField cpu;
    public AnchorPane brAp;
    AsciiMsgTransceiver asciiMsgTransceiver;
    BoardInfoReadService boardInfoReadService;
    MQTTManager mqttManager;
    @FXML
    public void initialize(){
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        boardInfoReadService = BoardInfoReadService.getInstance();
        mqttManager = MQTTManager.getInstance();

        boardInfoReadService.setBrightness(brightness);
        boardInfoReadService.setHorizontal(horizontal);
        boardInfoReadService.setVertical(vertical);
        boardInfoReadService.setArray(array);
        boardInfoReadService.setFirmware(firmware);
        boardInfoReadService.setCpu(cpu);

        brAp.getStylesheets().add(getClass().getResource("/dbps/dbps/css/additionalFunction.css").toExternalForm());
    }


    public void readBrightness() {
        if (ROAD) {
            long msgId = System.currentTimeMillis();

            JSONObject moid = new JSONObject();
            moid.put("2.RTE058.2.2", 1);

            // GET 요청 메시지 구성
            JSONObject getRequest = new JSONObject();
            getRequest.put("MSG_TYPE", "GET");
            getRequest.put("MSG_VER", 20241028);
            getRequest.put("MSG_ID", msgId);
            getRequest.put("MOID", moid);

            Task<String> stringTask = mqttManager.sendRoadMsg(getRequest.toString());

            new Thread(stringTask).start();

            stringTask.setOnSucceeded((event)->{
                try {
                    String result = stringTask.get();
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONObject resultObj = jsonResponse.getJSONObject("RESULT");
                    String brightnessValue = resultObj.get("2.RTE058.2.2").toString();

                    brightness.setText(brightnessValue);

                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

            return;
        }
        String sendMsg;
        if (isRS){
            sendMsg = "!["+convertRS485AddrASCii()+"051!]";
        }
        else {
            sendMsg = "![0051!]";
        }
        asciiMsgTransceiver.sendMessages(sendMsg, false, null);
    }

    public void readDisplaySize() {
        if (ROAD) {
            long msgId = System.currentTimeMillis();
            JSONObject moid = new JSONObject();
            moid.put("2.RTE058.2.3", 1);

            JSONObject getRequest = new JSONObject();
            getRequest.put("MSG_TYPE", "GET");
            getRequest.put("MSG_VER", 20241028);
            getRequest.put("MSG_ID", msgId);
            getRequest.put("MOID", moid);

            Task<String> stringTask = mqttManager.sendRoadMsg(getRequest.toString());

            new Thread(stringTask).start();

            stringTask.setOnSucceeded((event)->{
                try {
                    String result = stringTask.get();
                    JSONObject jsonResponse = new JSONObject(result);

                    // RESULT 객체 추출
                    JSONObject resultObj = jsonResponse.getJSONObject("RESULT");

                    // "2.RTE058.2.5" 배열 추출
                    JSONArray result11 = resultObj.getJSONArray("2.RTE058.2.3");

                    // 배열 내 각 정보 추출: [0] = 펌웨어 버전, [1] = CPU 속도
                    String horizontalValue = result11.getString(0);
                    String verticalValue = result11.getString(1);
                    String arrayValue = result11.getString(2);

                    horizontal.setText(horizontalValue);
                    vertical.setText(verticalValue);
                    if (arrayValue.equals("0")){
                        array.setText("가로형");
                    } else if (arrayValue.equals("1")){
                        array.setText("1줄 세로형");
                    } else if (arrayValue.equals("2")){
                        array.setText("2줄 세로형");
                    } else if (arrayValue.equals("3")){
                        array.setText("가로형 양면");
                    } else if (arrayValue.equals("4")){
                        array.setText("1줄 세로형 양면");
                    } else if (arrayValue.equals("5")){
                        array.setText("2줄 가로형");
                    }

                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

            return;
        }


        String sendMsg;
        if (isRS){
            sendMsg = "!["+convertRS485AddrASCii()+"043!]";
        }
        else {
            sendMsg = "![0043!]";
        }
        asciiMsgTransceiver.sendMessages(sendMsg, false, null);
    }

    public void readCPU() {
        if (ROAD) {
            long msgId = System.currentTimeMillis();
            JSONObject moid = new JSONObject();
            moid.put("2.RTE058.2.5", 1);

            JSONObject getRequest = new JSONObject();
            getRequest.put("MSG_TYPE", "GET");
            getRequest.put("MSG_VER", 20241028);
            getRequest.put("MSG_ID", msgId);
            getRequest.put("MOID", moid);

            Task<String> stringTask = mqttManager.sendRoadMsg(getRequest.toString());

            new Thread(stringTask).start();

            stringTask.setOnSucceeded((event)->{
                try {
                    String result = stringTask.get();
                    JSONObject jsonResponse = new JSONObject(result);

                    // RESULT 객체 추출
                    JSONObject resultObj = jsonResponse.getJSONObject("RESULT");

                    // "2.RTE058.2.5" 배열 추출
                    JSONArray cpuInfoArr = resultObj.getJSONArray("2.RTE058.2.5");

                    // 배열 내 각 정보 추출: [0] = 펌웨어 버전, [1] = CPU 속도
                    String firmwareVersion = cpuInfoArr.getString(0);
                    int cpuSpeed = cpuInfoArr.getInt(1);

                    firmware.setText(firmwareVersion);
                    cpu.setText(cpuSpeed+"");

                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

            return;
        }


        String sendMsg;
        if (isRS){
            sendMsg = "!["+convertRS485AddrASCii()+"097!]";
        }
        else {
            sendMsg = "![0097!]";
        }
        asciiMsgTransceiver.sendMessages(sendMsg, false, null);
    }


    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void readAll(MouseEvent mouseEvent) {
        long msgId = System.currentTimeMillis();
        JSONObject moid = new JSONObject();
        moid.put("2.RTE058.2.1", 1);
        moid.put("2.RTE058.2.2", 1);
        moid.put("2.RTE058.2.3", 1);
        moid.put("2.RTE058.2.4", 1);
        moid.put("2.RTE058.2.5", 1);

        JSONObject getRequest = new JSONObject();
        getRequest.put("MSG_TYPE", "GET");
        getRequest.put("MSG_VER", 20241028);
        getRequest.put("MSG_ID", msgId);
        getRequest.put("MOID", moid);

        Task<String> stringTask = mqttManager.sendRoadMsg(getRequest.toString());

        new Thread(stringTask).start();
        stringTask.setOnSucceeded((event) -> {
            try {
                String result = stringTask.get();
                JSONObject jsonResponse = new JSONObject(result);
                JSONObject resultObj = jsonResponse.getJSONObject("RESULT");

                // 밝기 정보 ("2.RTE058.2.2")
                String brightnessValue = resultObj.get("2.RTE058.2.2").toString();
                brightness.setText(brightnessValue);

                // 디스플레이 구성 정보 ("2.RTE058.2.3")
                JSONArray displayConfig = resultObj.getJSONArray("2.RTE058.2.3");
                int horizontalValue = displayConfig.getInt(0);
                int verticalValue = displayConfig.getInt(1);
                int arrayCode = displayConfig.getInt(2);
                horizontal.setText(String.valueOf(horizontalValue));
                vertical.setText(String.valueOf(verticalValue));
                // 배열 방식 매핑
                if (arrayCode==0) {
                    array.setText("가로형");
                } else if (arrayCode==1) {
                    array.setText("1줄 세로형");
                } else if (arrayCode==2) {
                    array.setText("2줄 세로형");
                } else if (arrayCode==3) {
                    array.setText("가로형 양면");
                } else if (arrayCode==4) {
                    array.setText("1줄 세로형 양면");
                } else if (arrayCode==5) {
                    array.setText("2줄 가로형");
                } else {
                    array.setText(String.valueOf(arrayCode));
                }

                // CPU 정보 ("2.RTE058.2.5")
                JSONArray cpuInfo = resultObj.getJSONArray("2.RTE058.2.5");
                String firmwareVersion = cpuInfo.getString(0);
                int cpuSpeed = cpuInfo.getInt(1);
                firmware.setText(firmwareVersion);
                cpu.setText(String.valueOf(cpuSpeed));

                // (옵션) 전광판 시간("2.RTE058.2.1")이나 폰트 목록("2.RTE058.2.4")은 별도로 활용 가능
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
