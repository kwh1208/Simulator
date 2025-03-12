package dbps.dbps.service;

import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import static dbps.dbps.Constants.CONNECT_TYPE;

public class AsciiMsgTransceiver {
    private static AsciiMsgTransceiver instance = null;

    private final SerialPortManager serialPortManager;
    private final LogService logService;
    private final UDPManager udpManager;
    private final TCPManager tcpManager;
    private final ServerTCPManager serverTCPManager;
    private final UnderTheLineLeftService underTheLineLeftService;
    private final SizeOfDisplayBoardService sizeOfDisplayBoardService;
    private final FirmwareService firmwareService;
    private final BoardSettingService boardSettingService;
    private final ASCiiDefaultSettingService asciiDefaultSettingService;
    private final BTService btService;
    private final ResourceBundle bundle;


    private AsciiMsgTransceiver() {
        serialPortManager = SerialPortManager.getManager();
        btService = BTService.getInstance();
        logService = LogService.getLogService();
        udpManager = UDPManager.getUDPManager();
        tcpManager = TCPManager.getManager();
        serverTCPManager = ServerTCPManager.getInstance();
        underTheLineLeftService = UnderTheLineLeftService.getInstance();
        sizeOfDisplayBoardService = SizeOfDisplayBoardService.getInstance();
        firmwareService = FirmwareService.getFirmwareService();
        boardSettingService = BoardSettingService.getInstance();
        asciiDefaultSettingService = ASCiiDefaultSettingService.getInstance();
        bundle = ResourceManager.getInstance().getBundle();
    }

    public static AsciiMsgTransceiver getInstance() {
        if (instance == null) {
            instance = new AsciiMsgTransceiver();
        }
        return instance;
    }

    public CompletableFuture<String> sendMessages(String msg, boolean utf8, ProgressIndicator progressIndicator) {
        CompletableFuture<String> resultFuture = new CompletableFuture<>();

        Platform.runLater(() -> {
            if (progressIndicator != null) {
                progressIndicator.setVisible(true);
            }
        });

        Task<String> sendTask = switch (CONNECT_TYPE) {
            case "serial", "bluetooth", "rs485" -> serialPortManager.sendMsgAndGetMsg(msg, utf8);
            case "UDP" -> udpManager.sendASCMsg(msg, utf8);
            case "clientTCP" -> tcpManager.sendASCMsg(msg, utf8);
            case "serverTCP" -> serverTCPManager.sendASCMsg(msg, utf8);
            default -> {
                resultFuture.completeExceptionally(new IllegalStateException("Unexpected value: " + CONNECT_TYPE));
                yield null;
            }
        };

        if (sendTask != null) {
            sendTask.setOnSucceeded(event -> {
                String receivedMsg = sendTask.getValue();
                msgReceive(receivedMsg, msg); // msgReceive를 통해 결과 처리
                resultFuture.complete(receivedMsg);

                Platform.runLater(() -> {
                    if (progressIndicator != null) {
                        progressIndicator.setVisible(false);
                    }
                });
            });

            sendTask.setOnFailed(event -> {
                Throwable exception = sendTask.getException();
                resultFuture.completeExceptionally(exception);
                Platform.runLater(() -> {
                    if (progressIndicator != null) {
                        progressIndicator.setVisible(false);
                    }
                });
            });

            try {
                new Thread(sendTask).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resultFuture.completeExceptionally(new IllegalStateException("Task is null."));
        }

        return resultFuture;
    }


//

    private void msgReceive(String receiveMsg, String msg) {
        //실시간 메세지, 페이지 메세지
        if (receiveMsg.equals(msg)) {
            return;
        }
        if (receiveMsg.charAt(4) == '0' || receiveMsg.charAt(4) == '1') {
            if (receiveMsg.charAt(5) == '0') {//정상 처리
                return;
            }
            if (receiveMsg.charAt(5) == 'F') {//오류 발생
                logService.warningLog("errorOccurred");
                logService.warningLog(bundle.getString("receivedMsg") + receiveMsg);
                return;
            }

        }
        chkSpecificCmdCode(msg, receiveMsg);
    }

    private void chkSpecificCmdCode(String msg, String receiveMsg) {
        if (receiveMsg.contains("BT DIBD")) {
            Platform.runLater(() -> {
                TextField bleId = btService.getBle_id();
                TextField blePassword = btService.getBle_password();
                String[] split = receiveMsg.split("\n");
                bleId.setText(split[2]);
                blePassword.setText(split[3].replaceAll("!]", ""));
            });
            return;
        }
        if (receiveMsg.contains("![DIBD BLE OK!]")) {
            return;
        }

        String cmd = receiveMsg.substring(4, 6);
        char status = receiveMsg.charAt(6);

        if (cmd.equals("31")) {
            String time = receiveMsg.substring(6, 19);

            // 한글과 영어 요일을 다국어 지원하도록 변경
            String[] weekdaysKorean = {"일", "월", "화", "수", "목", "금", "토"};
            String[] weekdaysEnglish = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

            // 현재 설정된 언어 확인
            boolean isKorean = bundle.getLocale().getLanguage().equals("ko");

            // 숫자 요일을 언어별 요일로 변환
            char weekdayChar = time.charAt(6);
            int weekdayIndex = Character.getNumericValue(weekdayChar);
            String weekday = isKorean ? weekdaysKorean[weekdayIndex] : weekdaysEnglish[weekdayIndex];

            // 다국어 형식 문자열 생성
            String formattedTime = String.format(
                    "%s-%s-%s (%s) %s:%s:%s",
                    time.substring(0, 2), time.substring(2, 4), time.substring(4, 6),
                    weekday,
                    time.substring(7, 9), time.substring(9, 11), time.substring(11, 13)
            );

            logService.updateInfoLog(MessageFormat.format(bundle.getString("controllerTimeInfo"), formattedTime));
            underTheLineLeftService.setTime(formattedTime);
            return;
        }

        if (cmd.equals("B3")) {
            boardSettingService.setUI(receiveMsg.substring(7, 21));
            logService.updateInfoLog(bundle.getString("boardSettingSuccess"));
            return;
        }
        if (cmd.equals("B2")) {
            return;
        }
        if (cmd.equals("33")) {
            asciiDefaultSettingService.setProperties(receiveMsg);
            logService.updateInfoLog(bundle.getString("defaultSettingSuccess"));
            return;
        }
        if (cmd.equals("81")) {
            firmwareService.setFirmware(receiveMsg.substring(6));
            logService.updateInfoLog(bundle.getString("firmwareInfoReadSuccess"));
            return;
        }
        if (cmd.equals("96")) {
            if (receiveMsg.equals("![0096F!]")){
                logService.updateInfoLog(bundle.getString("fontNameReadFail"));
            }
            logService.updateInfoLog(bundle.getString("fontNameReadSuccess"));
            return;
        }
        if (cmd.equals("95")) {
            logService.updateInfoLog(bundle.getString("fontNameSettingSuccess"));
            return;
        }
        if (cmd.equals("40")) {
            int sendRow = Integer.parseInt(msg.substring(6, 8));
            int sendColumn = Integer.parseInt(msg.substring(8, 10));
            int receiveRow = Integer.parseInt(receiveMsg.substring(6, 8));
            int receiveColumn = Integer.parseInt(receiveMsg.substring(8, 10));

            if (sendRow == receiveRow && sendColumn == receiveColumn) {
                logService.updateInfoLog(bundle.getString("displaySizeSettingSuccess"));
            } else {
                logService.warningLog(bundle.getString("displaySizeSettingFailed"));
                logService.warningLog(MessageFormat.format(bundle.getString("displaySizeLimit"), receiveRow, receiveColumn));
                sizeOfDisplayBoardService.setDisplaySize(receiveRow, receiveColumn);
            }
        }

        if (status == '0') { // 정상 처리
            if (cmd.equals("20")) {
                logService.updateInfoLog(bundle.getString("backgroundImageDisplaySuccess"));
            }
            if (cmd.equals("21")) {
                logService.updateInfoLog(bundle.getString("screenPowerToggleSuccess"));
            }
            if (cmd.equals("22")) {
                logService.updateInfoLog(bundle.getString("externalSignalOutputSuccess"));
            }
            if (cmd.equals("30")) {
                logService.updateInfoLog(bundle.getString("timeSyncSuccess"));
            }
            if (cmd.equals("41")) {
                logService.updateInfoLog(bundle.getString("controllerResetSuccess"));
            }
            if (cmd.equals("42")) {
                logService.updateInfoLog(bundle.getString("factoryResetSuccess"));
            }
            if (cmd.equals("50")) {
                logService.updateInfoLog(bundle.getString("brightnessControlSuccess"));
            }
            if (cmd.equals("52")) {
                logService.updateInfoLog(bundle.getString("receivedMsg") + receiveMsg);
            }
            if (cmd.equals("54")) {
                logService.updateInfoLog(bundle.getString("displaySpeedChangeSuccess"));
            }
            if (cmd.equals("56")) {
                logService.updateInfoLog(bundle.getString("fontThicknessSettingSuccess"));
            }
            if (cmd.equals("60")) {
                logService.updateInfoLog(bundle.getString("pageMessageCountSettingSuccess"));
            }
            if (cmd.equals("61")) {
                logService.updateInfoLog(bundle.getString("pageMessageDeletionSuccess"));
            }
            if (cmd.equals("62")) {
                logService.updateInfoLog(bundle.getString("sectionEffectSettingSuccess"));
            }
            if (cmd.equals("70")) {
                logService.updateInfoLog(bundle.getString("fillDisplaySuccess"));
            }
            if (cmd.equals("32")) {
                logService.updateInfoLog(bundle.getString("defaultSettingSuccess"));
            }
            if (cmd.equals("82")) {
                logService.updateInfoLog(bundle.getString("macAddressSettingSuccess"));
            }
            if (cmd.equals("85")) {
                logService.updateInfoLog(bundle.getString("heartbeatSettingSuccess"));
            }
            if (cmd.equals("B4")) {
                logService.updateInfoLog(bundle.getString("afterimageDelaySettingSuccess"));
            }


        } else if (status == 'F') { // 오류 발생
            errorLog(cmd, receiveMsg);
        } else {
            logService.warningLog(bundle.getString("unknownStatusCode"));
            logService.warningLog(bundle.getString("receivedMsg") + receiveMsg);
        }

    }

    private void errorLog(String command, String msg) {
        String errorMsg = switch (command) {
            case "20" -> bundle.getString("backgroundImageDisplayFailed");
            case "21" -> bundle.getString("screenPowerToggleFailed");
            case "22" -> bundle.getString("externalSignalOutputFailed");
            case "30" -> bundle.getString("timeSyncFailed");
            case "31" -> bundle.getString("controllerTimeReadFailed");
            case "41" -> bundle.getString("controllerResetFailed");
            case "42" -> bundle.getString("factoryResetFailed");
            case "50" -> bundle.getString("brightnessControlFailed");
            case "54" -> bundle.getString("displaySpeedChangeFailed");
            case "56" -> bundle.getString("backgroundImageListSelectionFailed");
            case "60" -> bundle.getString("pageMessageCountSettingFailed");
            case "61" -> bundle.getString("pageMessageDeletionFailed");
            case "62" -> bundle.getString("sectionEffectSettingFailed");
            case "79" -> bundle.getString("fillDisplayFailed");
            case "81" -> bundle.getString("firmwareInfoReadFailed");
            case "85" -> bundle.getString("heartbeatSettingFailed");
            default -> bundle.getString("unknownStatusCode");
        };
        logService.warningLog(errorMsg);
        logService.warningLog(bundle.getString("receivedMsg") + msg);
    }
}