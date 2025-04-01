package dbps.dbps.service;

import java.util.ArrayList;
import java.util.List;

/**
 * ASCII 메시지 관리를 담당하는 서비스 클래스
 */
public class ASCiiMsgService extends AbstractSingleton<ASCiiMsgService> {
    // 상수 정의
    private static final int MAX_MESSAGES = 10;
    
    // 싱글톤 인스턴스
    private static volatile ASCiiMsgService instance;
    
    // final 키워드 제거 (필요시 나중에 초기화)
    private ConfigService configService;

    /**
     * 생성자는 private으로 변경하여 싱글톤 패턴 강화
     */
    private ASCiiMsgService() {
        // 빈 생성자
    }
    
    /**
     * 싱글톤 인스턴스를 반환하는 메서드 (Double-checked locking 구현)
     * @return 싱글톤 인스턴스
     */
    public static ASCiiMsgService getInstance() {
        if (instance == null) {
            synchronized (ASCiiMsgService.class) {
                if (instance == null) {
                    instance = new ASCiiMsgService();
                    instance.initialize(); // 초기화 메서드 분리
                }
            }
        }
        return instance;
    }
    
    /**
     * 인스턴스 초기화 - 다른 서비스와의 의존성 설정
     */
    private void initialize() {
        configService = ConfigService.getInstance();
    }

    /**
     * AbstractSingleton에서 요구하는 createInstance 메서드 구현
     */
    @Override
    protected ASCiiMsgService createInstance() {
        return instance; // 이미 생성된 인스턴스 반환
    }

    /**
     * 메시지 목록을 설정 파일에 저장
     * @param msgList 저장할 메시지 목록
     */
    public void saveMessages(List<String> msgList) {
        for (int i = 0; i < msgList.size(); i++) {
            configService.setProperty("ASCMsg" + (i + 1), msgList.get(i));
        }
    }

    /**
     * 설정 파일에서 메시지 목록 로드
     * @return 로드된 메시지 목록
     */
    public List<String> loadMessages() {
        List<String> messages = new ArrayList<>();

        for (int i = 1; i <= MAX_MESSAGES; i++) {
            String value = configService.getProperty("ASCMsg" + i);
            messages.add(value != null ? value : "");
        }

        return messages;
    }
}
