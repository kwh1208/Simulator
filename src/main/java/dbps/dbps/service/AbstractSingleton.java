package dbps.dbps.service;

/**
 * 스레드 안전한 싱글톤 패턴을 제공하는 추상 클래스
 * 이 클래스를 상속받는 각 하위 클래스는 자체적으로 instance 필드를 관리해야 합니다.
 * @param <T> 싱글톤 인스턴스 타입
 */
public abstract class AbstractSingleton<T> {
    
    /**
     * 실제 인스턴스를 생성하는 메서드 (하위 클래스에서 구현)
     * @return 생성된 인스턴스
     */
    protected abstract T createInstance();
    
    /**
     * 생성자는 protected로 선언하여 외부에서 직접 인스턴스화 방지
     */
    protected AbstractSingleton() {
        // 기본 생성자
    }
} 