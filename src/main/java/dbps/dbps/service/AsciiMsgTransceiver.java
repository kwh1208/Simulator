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

/**
 * ASCII 메시지 송수신을 담당하는 서비스 클래스
 */
public class AsciiMsgTransceiver extends AbstractSingleton<AsciiMsgTransceiver> {
    // 싱글톤 인스턴스
    private static volatile AsciiMsgTransceiver instance;
    
    // 서비스 의존성
    private SerialPortManager serialPortManager;
    private LogService logService;
    private UDPManager udpManager;
    private TCPManager tcpManager;
    private ServerTCPManager serverTCPManager;
    private UnderTheLineLeftService underTheLineLeftService;
    private SizeOfDisplayBoardService sizeOfDisplayBoardService;
    private FirmwareService firmwareService;
    private BoardSettingService boardSettingService;
    private BTService btService;
    private ResourceBundle bundle;

    /**
     * 생성자
     */
    private AsciiMsgTransceiver() {
        // 빈 생성자
    }
    
    /**
     * 싱글톤 인스턴스를 반환하는 메서드 (Double-checked locking 구현)
     * @return 싱글톤 인스턴스
     */
    public static AsciiMsgTransceiver getInstance() {
        if (instance == null) {
            synchronized (AsciiMsgTransceiver.class) {
                if (instance == null) {
                    instance = new AsciiMsgTransceiver();
                    instance.initialize();
                }
            }
        }
        return instance;
    }
    
    /**
     * 인스턴스 초기화 - 필요한 서비스들을 초기화
     */
    private void initialize() {
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
        bundle = ResourceManager.getInstance().getBundle();
    }
    
    /**
     * AbstractSingleton에서 요구하는 createInstance 메서드 구현
     */
    @Override
    protected AsciiMsgTransceiver createInstance() {
        return instance;
    }

    /**
     * UTF-8 또는 UTF-16이 아닌 일반 메시지 전송
     * 
     * @param msg 전송할 메시지
     * @param utf8 UTF-8 인코딩 사용 여부
     * @param progressIndicator 진행 상태 표시기
     * @return 비동기 결과를 담은 CompletableFuture
     */
    public CompletableFuture<String> sendMessages(String msg, boolean utf8, ProgressIndicator progressIndicator) {
        CompletableFuture<String> resultFuture = new CompletableFuture<>();
        
        showProgressIndicator(progressIndicator);
        
        Task<String> sendTask = createSendTask(msg, utf8, null);
        processSendTask(sendTask, resultFuture, progressIndicator, msg);
        
        return resultFuture;
    }

    /**
     * UTF-8 또는 UTF-16 인코딩을 지정하여 메시지 전송
     * 
     * @param msg 전송할 메시지
     * @param utf8 UTF-8 인코딩 사용 여부
     * @param utf16 UTF-16 인코딩 사용 여부
     * @param progressIndicator 진행 상태 표시기
     * @return 비동기 결과를 담은 CompletableFuture
     */
    public CompletableFuture<String> sendMessages(String msg, boolean utf8, boolean utf16, ProgressIndicator progressIndicator) {
        CompletableFuture<String> resultFuture = new CompletableFuture<>();
        
        showProgressIndicator(progressIndicator);
        
        Task<String> sendTask = createSendTask(msg, utf8, utf16);
        processSendTask(sendTask, resultFuture, progressIndicator, msg);
        
        return resultFuture;
    }

    /**
     * 연결 유형에 따른 메시지 전송 태스크 생성
     * 
     * @param msg 전송할 메시지
     * @param utf8 UTF-8 인코딩 사용 여부
     * @param utf16 UTF-16 인코딩 사용 여부 (null이면 무시)
     * @return 전송 작업 Task
     */
    private Task<String> createSendTask(String msg, boolean utf8, Boolean utf16) {
        try {
            if (utf16 == null) {
                return switch (CONNECT_TYPE) {
                    case "serial", "bluetooth", "rs485" -> serialPortManager.sendMsgAndGetMsg(msg, utf8);
                    case "UDP" -> udpManager.sendASCMsg(msg, utf8);
                    case "clientTCP" -> tcpManager.sendASCMsg(msg, utf8);
                    case "serverTCP" -> serverTCPManager.sendASCMsg(msg, utf8);
                    default -> {
                        String errorMsg = "지원하지 않는 연결 유형: " + CONNECT_TYPE;
                        logService.errorLog(errorMsg);
                        throw new IllegalStateException(errorMsg);
                    }
                };
            } else {
                return switch (CONNECT_TYPE) {
                    case "serial", "bluetooth", "rs485" -> serialPortManager.sendMsgAndGetMsg(msg, utf8, utf16);
                    case "UDP" -> udpManager.sendASCMsg(msg, utf8, utf16);
                    case "clientTCP" -> tcpManager.sendASCMsg(msg, utf8, utf16);
                    case "serverTCP" -> serverTCPManager.sendASCMsg(msg, utf8, utf16);
                    default -> {
                        String errorMsg = "지원하지 않는 연결 유형: " + CONNECT_TYPE;
                        logService.errorLog(errorMsg);
                        throw new IllegalStateException(errorMsg);
                    }
                };
            }
        } catch (Exception e) {
            logService.errorLog("태스크 생성 중 오류: " + e.getMessage());
            return null;
        }
    }

    /**
     * 진행 상태 표시기 표시
     * 
     * @param progressIndicator 진행 상태 표시기
     */
    private void showProgressIndicator(ProgressIndicator progressIndicator) {
        if (progressIndicator != null) {
            Platform.runLater(() -> progressIndicator.setVisible(true));
        }
    }

    /**
     * 전송 작업 처리 및 결과 반환
     * 
     * @param sendTask 전송 작업 Task
     * @param resultFuture 결과를 담을 CompletableFuture
     * @param progressIndicator 진행 상태 표시기
     * @param origMsg 원본 메시지
     */
    private void processSendTask(Task<String> sendTask, CompletableFuture<String> resultFuture, 
                               ProgressIndicator progressIndicator, String origMsg) {
        if (sendTask == null) {
            resultFuture.completeExceptionally(new IllegalStateException("태스크가 생성되지 않았습니다."));
            hideProgressIndicator(progressIndicator);
            return;
        }

        // 성공 시 콜백
        sendTask.setOnSucceeded(event -> {
            try {
                String receivedMsg = sendTask.getValue();
                processReceivedMessage(receivedMsg, origMsg);
                resultFuture.complete(receivedMsg);
            } catch (Exception e) {
                logService.errorLog("메시지 수신 처리 중 오류 발생: " + e.getMessage());
                resultFuture.completeExceptionally(e);
            } finally {
                hideProgressIndicator(progressIndicator);
            }
        });

        // 실패 시 콜백
        sendTask.setOnFailed(event -> {
            Throwable exception = sendTask.getException();
            logService.errorLog("메시지 전송 실패: " + exception.getMessage());
            resultFuture.completeExceptionally(exception);
            hideProgressIndicator(progressIndicator);
        });

        // 태스크 실행
        try {
            new Thread(sendTask).start();
        } catch (Exception e) {
            logService.errorLog("태스크 실행 실패: " + e.getMessage());
            resultFuture.completeExceptionally(e);
            hideProgressIndicator(progressIndicator);
        }
    }

    /**
     * 진행 상태 표시기 숨기기
     * 
     * @param progressIndicator 진행 상태 표시기
     */
    private void hideProgressIndicator(ProgressIndicator progressIndicator) {
        if (progressIndicator != null) {
            Platform.runLater(() -> progressIndicator.setVisible(false));
        }
    }

    /**
     * 받은 메시지 처리
     * 
     * @param receivedMsg 받은 메시지
     * @param sentMsg 보낸 메시지
     */
    private void processReceivedMessage(String receivedMsg, String sentMsg) {
        // 메시지가 같으면 처리 건너뜀
        if (receivedMsg.equals(sentMsg)) {
            return;
        }
        
        // 메시지 형식 확인 (5번째 문자 확인)
        if (receivedMsg.length() > 5 && (receivedMsg.charAt(4) == '0' || receivedMsg.charAt(4) == '1')) {
            if (receivedMsg.charAt(5) == '0') {
                // 정상 처리
                return;
            }
            if (receivedMsg.charAt(5) == 'F') {
                // 오류 발생
                String errorMsg = bundle.getString("errorOccurred");
                logService.warningLog(errorMsg);
                logService.warningLog(bundle.getString("receivedMsg") + receivedMsg);
                return;
            }
        }
        
        // 특정 명령 코드 처리
        processSpecificCommandCode(sentMsg, receivedMsg);
    }

    /**
     * 특정 명령 코드 처리
     * 
     * @param sentMsg 보낸 메시지
     * @param receiveMsg 받은 메시지
     */
    private void processSpecificCommandCode(String sentMsg, String receiveMsg) {
        // BT DIBD 관련 처리
        if (receiveMsg.contains("BT DIBD")) {
            handleBTDIBDMessage(receiveMsg);
            return;
        }
        
        // 블루투스 확인 메시지
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
            logService.updateInfoLog(bundle.getString("defaultSettingSuccess"));
            return;
        }
        if (cmd.equals("81")) {
            logService.updateInfoLog(bundle.getString("firmwareInfoReadSuccess"));
            firmwareService.setFirmware(receiveMsg.substring(6));
            return;
        }
        if (cmd.equals("96")) {
            if (receiveMsg.equals("![0096F!]")){
                logService.warningLog(bundle.getString("fontNameReadFail"));
                return;
            }
            logService.updateInfoLog(bundle.getString("fontNameReadSuccess"));
            return;
        }
        if (cmd.equals("95")) {
            logService.updateInfoLog(bundle.getString("fontNameSettingSuccess"));
            return;
        }
        if (cmd.equals("40")) {
            int sendRow = Integer.parseInt(sentMsg.substring(6, 8));
            int sendColumn = Integer.parseInt(sentMsg.substring(8, 10));
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

    /**
     * BT DIBD 메시지 처리
     * 
     * @param receivedMsg 받은 메시지
     */
    private void handleBTDIBDMessage(String receivedMsg) {
        Platform.runLater(() -> {
            try {
                TextField bleId = btService.getBle_id();
                TextField blePassword = btService.getBle_password();
                
                if (bleId == null || blePassword == null) {
                    logService.warningLog("BT 필드가 초기화되지 않았습니다");
                    return;
                }
                
                String[] split = receivedMsg.split("\n");
                if (split.length >= 4) {
                    bleId.setText(split[2]);
                    blePassword.setText(split[3].replaceAll("!]", ""));
                }
            } catch (Exception e) {
                logService.errorLog("BT DIBD 메시지 처리 중 오류 발생: " + e.getMessage());
            }
        });
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