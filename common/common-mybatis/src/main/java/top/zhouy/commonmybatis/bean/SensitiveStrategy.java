package top.zhouy.commonmybatis.bean;

/**
 * @description: 脱敏规则
 * @author: zhouy
 * @create: 2020-12-15 13:23:00
 */
public enum SensitiveStrategy {

    /**
     * 姓名：周某*（后1位遮挡）
     */
    NAME("NAME", "姓名", "(.$)", "*"),

    /**
     * 姓名长度过长：网民1234******（长度超过6后面的遮挡）
     * 如果长度大于6则调用这种加密方式
     */
    NAME_LONG("NAME_LONG", "姓名长度过长", "(.{6}).*", "$1******"),

    /**
     * 手机号：155****9345（遮挡中间4位）
     */
    PHONE("PHONE", "11位手机号", "(\\d{3})\\d{4}(\\d{4})", "$1****$2"),

    /**
     * 微信号：df3g****，1565****（显示前4位）
     */
    WE_CHAT("WE_CHAT", "微信号", "(.{4}).*", "$1******"),

    /**
     * 邮箱：*****@qq.com(@前遮挡)
     */
    EMAIL("EMAIL", "邮箱", "[^@]*(@.*$)", "******$1"),

    /**
     * 银行卡：***************2098(只显示后4位)
     */
    BANKCARD("BANKCARD", "银行卡号", ".*(.{4}$)", "***************$1"),

    /**
     * 身份证号：34250119********0E（显示前8个字后2个字，中间字符隐藏）
     */
    ID_CARD("ID_CARD", "身份证号", "^(.{8})(?:\\d+)(.{2})$", "$1********$2"),

    /**
     * 收货地址：上海市奉贤区青村镇人******（显示前10个字）
     */
    ADDRESS("ADDRESS", "收货地址", "(.{10}).*", "$1******"),
    ;

    String type;
    String describe;
    String regex;
    String replacement;

    SensitiveStrategy(String type, String describe, String regex, String replacement) {
        this.type = type;
        this.describe = describe;
        this.regex = regex;
        this.replacement = replacement;
    }

    public String getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }

    public String getRegex() {
        return regex;
    }

    public String getReplacement() {
        return replacement;
    }

    public static String sensitive(String text, SensitiveStrategy strategy) {
        return text.replaceAll(strategy.regex, strategy.replacement);
    }

    public static void main(String[] args) {
        String idCard = "410322199411186113X";
        String s = idCard.replaceAll("^(.{8})(?:\\d+)(.{2})$", "$1********$2");
        System.out.println("--"+s);
        System.out.println(SensitiveStrategy.sensitive(idCard, SensitiveStrategy.ID_CARD));
    }
}
