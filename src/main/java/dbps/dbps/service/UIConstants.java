package dbps.dbps.service;

/**
 * UI 관련 상수들을 정의하는 클래스
 * 매직 넘버를 제거하고 의미 있는 상수로 대체하기 위한 용도
 */
public final class UIConstants {
    // 생성자를 private으로 선언하여 인스턴스화 방지
    private UIConstants() {
        throw new AssertionError("상수 클래스는 인스턴스화할 수 없습니다");
    }

    /* ASCiiMsgController에서 사용하는 UI 관련 상수 */
    // 텍스트 필드 위치 및 크기 상수
    public static final int TEXT_FIELD_MARGIN_LEFT = 14;
    public static final int TEXT_FIELD_MARGIN_TOP = 41;
    public static final int TEXT_FIELD_VERTICAL_SPACING = 40;
    public static final int TEXT_FIELD_WIDTH = 339;
    public static final int TEXT_FIELD_HEIGHT = 28;

    // 버튼 위치 및 크기 상수
    public static final int BUTTON_MARGIN_LEFT = 367;
    public static final int BUTTON_WIDTH = 59;
    public static final int BUTTON_HEIGHT = 28;

    // 메시지 관련 상수
    public static final int MAX_MESSAGES = 10;
} 