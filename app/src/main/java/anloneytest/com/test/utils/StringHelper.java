package anloneytest.com.test.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Selection;
import android.text.Spannable;
import android.util.Base64;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 功能说明:字符串处理类
 * 日期:	2014年12月18日
 * 开发者:
 * </pre>
 */


public class StringHelper {
    public static double toDouble(String arg, double def) {
        if (isEmpty(arg)) {
            return def;
        }
        try {
            return Double.valueOf(arg);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static float toFloat(String arg, float def) {
        if (isEmpty(arg)) {
            return def;
        }
        try {
            return Float.valueOf(arg);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static int toInteger(String arg, int def) {
        if (isEmpty(arg)) {
            return def;
        }
        int result = 0;
        int max = arg.length();
        char ch = arg.charAt(0);
        int i = (ch == '-' || ch == '+') ? 1 : 0;
        boolean negative = (i == 0 || ch == '+') ? false : true;
        for (; ; ) {
            if (i == max)
                break;
            int value = analyze(arg.charAt(i++));
            int temp = result;
            temp *= 10;
            if (temp > result || value == -1) {
                return def;
            }
            result = temp - value;
        }
        if (negative) {
            return result;
        } else {
            return result == Integer.MIN_VALUE ? def : -result;
        }
    }

    public static long toLong(String arg, long def) {
        if (isEmpty(arg)) {
            return def;
        }
        long result = 0;

        int max = arg.length();
        char ch = arg.charAt(0);
        int i = (ch == '-' || ch == '+') ? 1 : 0;
        boolean negative = (i == 0 || ch == '+') ? false : true;

        for (; ; ) {
            if (i == max)
                break;
            int value = analyze(arg.charAt(i++));
            long temp = result;
            temp *= 10;
            if (temp > result || value == -1) {
                return def;
            }
            result = temp - value;
        }

        if (negative) {
            return result;
        } else {
            return result == Long.MIN_VALUE ? def : -result;
        }
    }

    private static int analyze(char ch) {
        if (isDigit(ch))
            return ch - '0';
        return -1;
    }

    /**
     * 是否是数字
     *
     * @param ch
     * @return
     */
    private static boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

    /**
     * 判断是否是手机号
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(16[^4,\\D])|(17[^4,\\D])|(18[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否是正确的IP地址
     */
    public static boolean isIpAddress(String ipAddress) {
        /**
         * 判断IP格式和范围
         */
        if (isEmpty(ipAddress)) {
            return false;
        } else {
            String rexp = "^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$";
            Pattern p = Pattern
                    .compile(rexp);
            Matcher m = p.matcher(ipAddress);
            return m.matches();
        }
    }

    /**
     * 正则表达式 密码验证 此方法是测试 是否匹配 匹配ture 不匹配false
     */
    public static boolean isPwdFormat(String s) {
        Pattern patten = Pattern.compile("[0-9a-zA-z^$.*+ -?=!]{6,12}?");
        Matcher matcher = patten.matcher(s);
        return matcher.matches();
    }

    /**
     * 判断是否是手机号 只判断首位是否为1
     */
    public static boolean isMobileNumber(String mobiles) {
        String firstNum = mobiles.substring(0, 1);
        if ("1".equals(firstNum) && mobiles.length() == 11) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是身份证
     */
    public static boolean isIDCard(String idCard) {
        //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9])|(\\d{17}[0-9xX])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(idCard);
        //判断用户输入是否为身份证号
        return idNumMatcher.matches();
    }

    // 用于判断是否是网址的参数
    public static final String GOOD_IRI_CHAR = "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
    public static final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL = "(?:"
            + "(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
            + "|(?:biz|b[abdefghijmnorstvwyz])"
            + "|(?:cat|com|coop|c[acdfghiklmnoruvxyz])"
            + "|d[ejkmoz]"
            + "|(?:edu|e[cegrstu])"
            + "|f[ijkmor]"
            + "|(?:gov|g[abdefghilmnpqrstuwy])"
            + "|h[kmnrtu]"
            + "|(?:info|int|i[delmnoqrst])"
            + "|(?:jobs|j[emop])"
            + "|k[eghimnprwyz]"
            + "|l[abcikrstuvy]"
            + "|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])"
            + "|(?:name|net|n[acefgilopruz])"
            + "|(?:org|om)"
            + "|(?:pro|p[aefghklmnrstwy])"
            + "|qa"
            + "|r[eosuw]"
            + "|s[abcdeghijklmnortuvyz]"
            + "|(?:tel|travel|t[cdfghjklmnoprtvwz])"
            + "|u[agksyz]"
            + "|v[aceginu]"
            + "|w[fs]"
            + "|(?:\u03b4\u03bf\u03ba\u03b9\u03bc\u03ae|\u0438\u0441\u043f\u044b\u0442\u0430\u043d\u0438\u0435|\u0440\u0444|\u0441\u0440\u0431|\u05d8\u05e2\u05e1\u05d8|\u0622\u0632\u0645\u0627\u06cc\u0634\u06cc|\u0625\u062e\u062a\u0628\u0627\u0631|\u0627\u0644\u0627\u0631\u062f\u0646|\u0627\u0644\u062c\u0632\u0627\u0626\u0631|\u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629|\u0627\u0644\u0645\u063a\u0631\u0628|\u0627\u0645\u0627\u0631\u0627\u062a|\u0628\u06be\u0627\u0631\u062a|\u062a\u0648\u0646\u0633|\u0633\u0648\u0631\u064a\u0629|\u0641\u0644\u0633\u0637\u064a\u0646|\u0642\u0637\u0631|\u0645\u0635\u0631|\u092a\u0930\u0940\u0915\u094d\u0937\u093e|\u092d\u093e\u0930\u0924|\u09ad\u09be\u09b0\u09a4|\u0a2d\u0a3e\u0a30\u0a24|\u0aad\u0abe\u0ab0\u0aa4|\u0b87\u0ba8\u0bcd\u0ba4\u0bbf\u0baf\u0bbe|\u0b87\u0bb2\u0b99\u0bcd\u0b95\u0bc8|\u0b9a\u0bbf\u0b99\u0bcd\u0b95\u0baa\u0bcd\u0baa\u0bc2\u0bb0\u0bcd|\u0baa\u0bb0\u0bbf\u0b9f\u0bcd\u0b9a\u0bc8|\u0c2d\u0c3e\u0c30\u0c24\u0c4d|\u0dbd\u0d82\u0d9a\u0dcf|\u0e44\u0e17\u0e22|\u30c6\u30b9\u30c8|\u4e2d\u56fd|\u4e2d\u570b|\u53f0\u6e7e|\u53f0\u7063|\u65b0\u52a0\u5761|\u6d4b\u8bd5|\u6e2c\u8a66|\u9999\u6e2f|\ud14c\uc2a4\ud2b8|\ud55c\uad6d|xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-3e0b707e|xn\\-\\-45brj9c|xn\\-\\-80akhbyknj4f|xn\\-\\-90a3ac|xn\\-\\-9t4b11yi5a|xn\\-\\-clchc0ea0b2g2a9gcd|xn\\-\\-deba0ad|xn\\-\\-fiqs8s|xn\\-\\-fiqz9s|xn\\-\\-fpcrj9c3d|xn\\-\\-fzc2c9e2c|xn\\-\\-g6w251d|xn\\-\\-gecrj9c|xn\\-\\-h2brj9c|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-j6w193g|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-kprw13d|xn\\-\\-kpry57d|xn\\-\\-lgbbat1ad8j|xn\\-\\-mgbaam7a8h|xn\\-\\-mgbayh7gpa|xn\\-\\-mgbbh1a71e|xn\\-\\-mgbc0a9azcg|xn\\-\\-mgberp4a5d4ar|xn\\-\\-o3cw4h|xn\\-\\-ogbpf8fl|xn\\-\\-p1ai|xn\\-\\-pgbs0dh|xn\\-\\-s9brj9c|xn\\-\\-wgbh1c|xn\\-\\-wgbl6a|xn\\-\\-xkc2al3hye2a|xn\\-\\-xkc2dl3a5ee0h|xn\\-\\-yfro4i67o|xn\\-\\-ygbi2ammx|xn\\-\\-zckzah|xxx)"
            + "|y[et]" + "|z[amw]))";

    /**
     * 判断是否是网址格式 正则表达
     */
    public static boolean isWEBURL(String webSite) {
        Pattern pattern = Pattern
                .compile("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
                        + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
                        + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
                        + "((?:(?:["
                        + GOOD_IRI_CHAR
                        + "]["
                        + GOOD_IRI_CHAR
                        + "\\-]{0,64}\\.)+" // named host
                        + TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL
                        + "|(?:(?:25[0-5]|2[0-4]" // or ip address
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]"
                        + "|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9])))"
                        + "(?:\\:\\d{1,5})?)" // plus option port number
                        + "(\\/(?:(?:["
                        + GOOD_IRI_CHAR
                        + "\\;\\/\\?\\:\\@\\&\\=\\#\\~" // plus option query
                        // params
                        + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
                        + "(?:\\b|$)"); // and finally, a word boundary or end
        // of
        // input. This is to stop foo.sure from
        // matching as foo.su

        Matcher m = pattern.matcher(webSite);
        return m.matches();
    }

    /**
     * 判断是否是邮箱地址格式 正则表达
     */
    public static boolean isEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    /**
     * 判断srcVersion是否大于destVersion
     *
     * @param srcVersion
     * @param destVersion
     * @return if srcVersion > destVersion return true, else return false
     */
    public static boolean compareVersion(String srcVersion, String destVersion) {
        if (isEmpty(srcVersion))
            return false;
        if (isEmpty(destVersion))
            return true;
        if (srcVersion.equals(destVersion)) {
            return false;
        }
        SimpleStringSplitter srcSplitter = new SimpleStringSplitter(srcVersion,
                '.');
        SimpleStringSplitter destSplitter = new SimpleStringSplitter(
                destVersion, '.');
        while (srcSplitter.hasNext() || destSplitter.hasNext()) {
            String subSrc = srcSplitter.hasNext() ? srcSplitter.next() : "0";
            String subDest = destSplitter.hasNext() ? destSplitter.next() : "0";
            int compare = compareString(subSrc, subDest);
            if (compare == 0)
                continue;
            if (compare > 0)
                return true;
            if (compare < 0)
                return false;
        }
        return false;
    }

    /**
     * 同等级版本的比较
     *
     * @param src
     * @param dest
     * @return
     */
    private static int compareString(String src, String dest) {
        src = handle(src);
        dest = handle(dest);
        if (src.length() < dest.length())
            return -1;
        if (src.length() > dest.length())
            return 1;
        return src.compareTo(dest);
    }

    /**
     * 消去前面的0
     *
     * @param result
     * @return
     */
    private static String handle(String result) {
        int i = -1;
        int size = result.length() - 1;
        while (i < size && result.charAt(++i) == '0')
            ;
        return result.substring(i);
    }

    /**
     * 判断str是否为null 或isEmpty
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0 || str
                .equalsIgnoreCase("null"));
        // return str == null || str.trim().length() == 0;
    }

    /**
     * 判断list是否为null 或isEmpty
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List mList) {
        return (mList == null || mList.size() == 0);
        // return str == null || str.trim().length() == 0;
    }

    /**
     * 字节转换B、K、M、G单位 三位小数点
     *
     * @param bytes
     * @return
     */
    public final static String toUnitSize(long bytes) {
        if (bytes <= 0) {
            return "0B";
        }
        double result = bytes;
        char unit;
        if (bytes >> 10 == 0) {
            unit = 'B';
        } else if (bytes >> 20 == 0) {
            result /= (1 << 10);
            unit = 'K';
        } else if (bytes >> 30 == 0) {
            result /= (1 << 20);
            unit = 'M';
        } else {
            result /= (1 << 30);
            unit = 'G';
        }
        return DecimalFormat.getInstance().format(result) + unit;
    }

    static final class SimpleStringSplitter {
        private final char mDelimiter;
        private int mPosition;
        private final int mLength;
        private final char[] values;

        public SimpleStringSplitter(String string, char delimiter) {
            mDelimiter = delimiter;
            mLength = string.length();
            values = string.toCharArray();
        }

        public boolean hasNext() {
            return mPosition < mLength;
        }

        public String next() {
            do {
                int end = mPosition - 1;
                while (mLength > ++end && mDelimiter != values[end])
                    ;// Find next delimiter
                String nextString = String.valueOf(values, mPosition, end
                        - mPosition);
                mPosition = end + 1; // Skip the delimiter.
                if (nextString.length() != 0) {
                    return nextString;
                }
            } while (mPosition < mLength);
            return "";
        }
    }

    /**
     * 将时间格式的字符串转换成无“-”和“：”的格式的字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String setTimeValue(TextView editText) {
        String hour = editText.getText().toString();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date timeTemp = null;
        try {
            timeTemp = df.parse(hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        hour = formatter.format(timeTemp);
        return hour;
    }

    /**
     * 对url进行编码
     */
    public static String encodeURL(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 将服务器返回的图片的string类型转化为bitmap类型
    public static Bitmap stringToBitmap(String imgString) {
        Bitmap bitmap = null;
        try {
            // 解析从服务器获取的图片（字符串格式）转化为bitmap格式并显示
            byte[] bs = Base64.decode(imgString, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
        } catch (Exception e) {
        }
        return bitmap;
    }

    // 将EditText的光标定位到字符的最后面
    public static void setEditTextCursorLocation(EditText editText) {
        CharSequence text = editText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * 去掉字符串中的标点符号
     */
    public static String formatSignal(String s) {
        String str = s
                .replaceAll(
                        "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]",
                        "");
        return str;
    }

    /**
     * 得到一个数的小数点后两位
     */
    public static String formatMoney(String money) {
        if (!isEmpty(money)) {
            DecimalFormat myformat = new DecimalFormat();
            myformat.applyPattern("0.00");
            String ret = myformat.format(new Double(money));
            return ret;
        }
        return money;
    }
}