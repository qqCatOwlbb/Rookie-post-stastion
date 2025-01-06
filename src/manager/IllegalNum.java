package manager;

public class IllegalNum extends RuntimeException {
    // 默认构造方法
    public IllegalNum() {
        super("非法的数字输入");
    }

    // 带自定义错误信息的构造方法
    public IllegalNum(String message) {
        super(message);
    }
}
