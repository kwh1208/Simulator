package dbps.dbps.service;

import dbps.dbps.service.connectManager.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import static dbps.dbps.Constants.CONNECT_TYPE;

public class AsciiMsgTransceiver {
    private static AsciiMsgTransceiver instance = null;

    private final SerialPortManager serialPortManager;
    private final LogService logService;
    private final UDPManager udpManager;
    private final TCPManager tcpManager;
    private final MQTTManager mqttManager;
    private final ServerTCPManager serverTCPManager;
    private final UnderTheLineLeftService underTheLineLeftService;
    private final SizeOfDisplayBoardService sizeOfDisplayBoardService;
    private final FirmwareService firmwareService;
    private final BoardSettingService boardSettingService;
    private final ASCiiDefaultSettingService asciiDefaultSettingService;


    private AsciiMsgTransceiver() {
        serialPortManager = SerialPortManager.getManager();
        logService = LogService.getLogService();
        udpManager = UDPManager.getUDPManager();
        tcpManager = TCPManager.getManager();
        mqttManager = MQTTManager.getInstance();
        serverTCPManager = ServerTCPManager.getInstance();
        underTheLineLeftService = UnderTheLineLeftService.getInstance();
        sizeOfDisplayBoardService = SizeOfDisplayBoardService.getInstance();
        firmwareService = FirmwareService.getFirmwareService();
        boardSettingService = BoardSettingService.getInstance();
        asciiDefaultSettingService = ASCiiDefaultSettingService.getInstance();
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
//            case "mqtt" -> mqttManager.sendASCMsg(msg);
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

            new Thread(sendTask).start(); // 비동기로 실행
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
                logService.warningLog("오류가 발생했습니다.");
                logService.warningLog("받은 메세지 : " + receiveMsg);
                return;
            }

        }
        chkSpecificCmdCode(msg, receiveMsg);
    }

    private void chkSpecificCmdCode(String msg, String receiveMsg) {
        String cmd = receiveMsg.substring(4, 6);
        char status = receiveMsg.charAt(6);
        if (cmd.equals("31")) {
            //시간 바꿈.
            String time = receiveMsg.substring(6, 19);
            String[] koreanWeekdays = {"일", "월", "화", "수", "목", "금", "토"};

            // 요일 숫자를 한글로 변환 (숫자가 한 자릿수라고 가정)
            char weekdayChar = time.charAt(6); // 요일 숫자는 문자열의 7번째 위치
            int weekdayIndex = Character.getNumericValue(weekdayChar); // 숫자 '0'~'6'을 인덱스로 변환
            String weekdayKorean = koreanWeekdays[weekdayIndex]; // 한글 요일로 변환

            // 숫자 요일을 한글 요일로 대체하여 새로운 시간 문자열 생성
            String formattedTime = time.substring(0, 6) + weekdayKorean + time.substring(7);

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyMMddEHHmmss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yy년 MM월 dd일 E요일 HH시 mm분 ss초");

            try {
                // 문자열을 Date로 파싱
                Date date = inputFormat.parse(formattedTime);

                // 원하는 출력 형식으로 변환
                String FormatedTime = outputFormat.format(date);

                logService.updateInfoLog("컨트롤러 시간은 " + FormatedTime + "입니다.");

            } catch (Exception e) {

            }

            SimpleDateFormat finalOutputFormat = new SimpleDateFormat("yy-MM-dd (E) HH:mm:ss");
            try {
                Date date = inputFormat.parse(formattedTime);
                time = finalOutputFormat.format(date);
                underTheLineLeftService.setTime(time);
            } catch (Exception e) {

            }

            return;
        }
        if (cmd.equals("B3")) {
            boardSettingService.setUI(receiveMsg.substring(7,21));
            return;
        }
        if (cmd.equals("B2")) {
            return;
        }
        if (cmd.equals("33")) {
            asciiDefaultSettingService.setProperties(receiveMsg);
            return;
        }
        if (cmd.equals("81")) {
            firmwareService.setFirmware(receiveMsg.substring(6));
            logService.updateInfoLog("펌웨어 정보 읽기에 성공했습니다.");
            return;
        }
        if (cmd.equals("96")) {
            logService.updateInfoLog("폰트 이름 설정에 성공했습니다.");
            return;
        }
        if (cmd.equals("95")) {
            logService.updateInfoLog("폰트 이름 설정에 성공했습니다.");
            return;
        }
        if (cmd.equals("40")) {
            int row = 0;
            int column = 0;

            try {
                row = Integer.parseInt(receiveMsg.substring(6, 8));
                column = Integer.parseInt(receiveMsg.substring(8, 10));
            } catch (NumberFormatException e) {
                row = Integer.parseInt(msg.substring(6, 8));
                column = Integer.parseInt(msg.substring(8, 10));
            }

            finally {
                sizeOfDisplayBoardService.setDisplaySize(row, column);
if (row != Integer.parseInt(msg.substring(6, 8)) || column != Integer.parseInt(msg.substring(8, 10))) {
                    logService.warningLog("화면 크기 설정에 실패했습니다.");
                    logService.warningLog(row + "단, " + column + "열까지만 가능합니다.");
                }
            }
        }

        if (status == '0') { // 정상 처리
            if (cmd.equals("83")) { // 맥주소 읽기
                logService.updateInfoLog("맥주소 : " + receiveMsg.substring(8, 20));
                logService.updateInfoLog("받은 메세지 : " + receiveMsg);
            }
            if (cmd.equals("20")) {
                logService.updateInfoLog("배경이미지 표출에 성공했습니다.");
            }
            if (cmd.equals("21")) {
                logService.updateInfoLog("화면 끄기/켜기에 성공했습니다.");
            }
            if (cmd.equals("22")) {
                logService.updateInfoLog("외부 신호 출력에 성공했습니다.");
            }
            if (cmd.equals("30")) {
                logService.updateInfoLog("시간 동기화에 성공했습니다.");
            }
            if (cmd.equals("41")) {
                logService.updateInfoLog("컨트롤러 리셋에 성공했습니다.");
            }
            if (cmd.equals("42")) {
                logService.updateInfoLog("공장초기화에 성공했습니다.");
            }
            if (cmd.equals("50")) {
                logService.updateInfoLog("밝기 조절에 성공했습니다.");
            }
            if (cmd.equals("52")) {
                logService.updateInfoLog("받은 메세지 : " + receiveMsg);
            }
            if (cmd.equals("54")) {
                logService.updateInfoLog("표출 속도 변경에 성공했습니다.");
            }
            if (cmd.equals("56")) {
                logService.updateInfoLog("폰트 두께 설정 선택에 성공했습니다.");
            }
            if (cmd.equals("60")) {
                logService.updateInfoLog("페이지 메세지 개수 설정에 성공했습니다.");
            }
            if (cmd.equals("61")) {
                logService.updateInfoLog("페이지 메세지 삭제에 성공했습니다.");
            }
            if (cmd.equals("62")) {
                logService.updateInfoLog("실시간 문구 설정에 성공했습니다.");
            }
            if (cmd.equals("70")) {
                logService.updateInfoLog("화면 단색 채우기에 성공했습니다.");
            }
            if (cmd.equals("32")) {
                logService.updateInfoLog("default 설정에 성공했습니다.");
            }
            if (cmd.equals("82")) {
                logService.updateInfoLog("맥주소 설정에 성공했습니다.");
            }
            if (cmd.equals("85")) {
                logService.updateInfoLog("하트비트 세팅에 성공했습니다.");
            }
            if (cmd.equals("B4")) {
                logService.updateInfoLog("잔상지연 설정에 성공했습니다.");
            }


        } else if (status == 'F') { // 오류 발생
            errorLog(cmd, receiveMsg);
        } else {
            logService.warningLog("알 수 없는 상태 코드입니다. 전송 패킷을 확인해주세요.");
            logService.warningLog("받은 메세지 : " + receiveMsg);
        }

    }

    private void errorLog(String command, String msg) {
        String errorMsg = switch (command) {
            case "20" -> "배경이미지 표출에 실패했습니다.";
            case "21" -> "화면 끄기/켜기에 실패했습니다.";
            case "22" -> "외부 신호 출력에 실패했습니다.";
            case "30" -> "시간 동기화에 실패했습니다.";
            case "31" -> "컨트롤러 시간 읽기에 실패했습니다.";
            case "41" -> "컨트롤러 리셋에 실패했습니다.";
            case "42" -> "공장초기화에 실패했습니다.";
            case "50" -> "밝기 조절에 실패했습니다.";
            case "54" -> "표출 속도 변경에 실패했습니다.";
            case "56" -> "배경이미지 표출 목록 선택에 실패했습니다.";
            case "60" -> "페이지 메세지 개수 설정에 실패했습니다.";
            case "61" -> "페이지 메세지 삭제에 실패했습니다.";
            case "62" -> "실시간 문구 설정에 실패했습니다.";
            case "70" -> "화면 단색 채우기에 실패했습니다.";
            case "81" -> "펌웨어 정보 읽기에 실패했습니다.";
            case "82" -> "맥주소 설정에 실패했습니다.";
            case "85" -> "하트비트 세팅에 실패했습니다.";
            default -> "알 수 없는 명령어입니다. 전송 패킷을 확인해주세요.";
        };
        logService.warningLog(errorMsg);
        logService.warningLog("받은 메세지 : " + msg);
    }
}
