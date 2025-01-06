package manager;

public class IllegalName extends RuntimeException {
    // 默认构造方法
    public IllegalName() {
        super("非法的名字输入");
    }

    // 带自定义错误信息的构造方法
    public IllegalName(String message) {
        super(message);
    }
}
