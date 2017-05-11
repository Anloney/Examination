package anloneytest.com.test.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import anloneytest.com.test.R;
import anloneytest.com.test.bean.SubjectBean;


/**
 * Created by Anloney on 2016/4/14.
 * 通用的操作
 */
public class UtilOperation {

    private static List<Activity> activityList; // 存储进入的类

    public interface OnAnalysisBeanListener {
        void onSuccessAnalys();
    }

    public interface OnAnalySuccFailListener {
        void onSuccessAnalys();

        void onFailAnalys();
    }

    /**
     * 跳转到新的activity 没有参数
     */
    public static void toNewActivity(Context mContext, Class destActivity) {
        Intent intent = new Intent(mContext, destActivity);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到新的activity 没有参数
     */
    public static double numberMutilply(double num1, double num2) {
        BigDecimal a = new BigDecimal(num1);
        BigDecimal b = new BigDecimal(num2);
        return a.multiply(b).doubleValue();
    }

    /**
     * 跳转到新的activity 没有参数
     */
    public static void toNewActivityWithSerialBeanForResult(Context mContext, Class destActivity, String extraKey, Object extraValue) {
        Intent intent = new Intent(mContext, destActivity);
        intent.putExtra(extraKey, (Serializable) extraValue);
        ((Activity) mContext).startActivityForResult(intent, StatusCode.COMMON_REQUEST_CODE);
    }

    /**
     * 跳转到新的activity 并且带有一个Sring类型的参数
     */
    public static void toNewActivityWithStringExtra(Context mContext, Class destActivity, String extraKey, String extraValue) {
        Intent intent = new Intent(mContext, destActivity);
        intent.putExtra(extraKey, extraValue);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到新的activity 并且带有两个String类型的参数
     */
    public static void toNewActivityWithTwoStringExtra(Context mContext, Class destActivity, String extraKey1, String extraValue1, String extraKey2, String extraValue2) {
        Intent intent = new Intent(mContext, destActivity);
        intent.putExtra(extraKey1, extraValue1);
        intent.putExtra(extraKey2, extraValue2);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到新的activity 并且带有一个Sring类型的参数
     */
    public static void toNewActivityWithStringExtraForResult(Context mContext, Class destActivity, String extraKey, String extraValue) {
        Intent intent = new Intent(mContext, destActivity);
        intent.putExtra(extraKey, extraValue);
        ((Activity) mContext).startActivityForResult(intent, StatusCode.COMMON_REQUEST_CODE);
    }

    //添加activity
    public static void addActivity(Activity activity) {
        if (CommonUtil.isListEmpty(activityList)) {
            activityList = new LinkedList<Activity>();
        }
        activityList.add(activity);
    }

    /*根据资源名称获取图片*/
    public static int getImageResourceId(String name) {
        R.drawable drawables = new R.drawable();
        //默认的id
        int resId = 0;
        try {
            //根据字符串字段名，取字段//根据资源的ID的变量名获得Field的对象,使用反射机制来实现的
            Field field = R.drawable.class.getField("test" + name);
            //取值
            resId = (Integer) field.get(drawables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;
    }

    /**
     * 读取文本数据
     *
     * @param context  程序上下文
     * @param fileName 文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public static String readAssets(Context context, String fileName) {
        InputStream is = null;
        String content = null;
        try {
            is = context.getAssets().open(fileName);
            if (is != null) {

                byte[] buffer = new byte[1024];
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int readLength = is.read(buffer);
                    if (readLength == -1) break;
                    arrayOutputStream.write(buffer, 0, readLength);
                }
                is.close();
                arrayOutputStream.close();
                content = new String(arrayOutputStream.toByteArray());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            content = null;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 读取考试试题
     *
     * @param context  程序上下文
     * @param fileName 文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public static ArrayList<SubjectBean> readAssetsText(Context context, String fileName) {
        InputStream is = null;
        String content = null;
        String encoding = "UTF-8";
//        File file = new File("F:\\test.txt");
        try {
            is = context.getAssets().open(fileName);
            if (is != null) {
                InputStreamReader read = new InputStreamReader(is, encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                ArrayList<SubjectBean> mList = new ArrayList();
                SubjectBean q = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                    if (lineTxt.startsWith("(A)")) {
                        q.setOptionA(lineTxt.substring(3));
                    } else if (lineTxt.startsWith("(B)")) {
                        q.setOptionB(lineTxt.substring(3));
                    } else if (lineTxt.startsWith("(C)")) {
                        q.setOptionC(lineTxt.substring(3));
                    } else if (lineTxt.startsWith("(D)")) {
                        q.setOptionD(lineTxt.substring(3));
                        mList.add(q);
                    } else if (lineTxt.startsWith("[pic]")) {
                        q.setPic(lineTxt.substring(5));
                    } else {
                        if (lineTxt.contains(".")) {
                            q = new SubjectBean();
                            int startPosi = lineTxt.indexOf(".");
                            q.setQuestion(lineTxt.substring(startPosi + 1));
                        }
                    }
                }
                read.close();
                return mList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return null;

//        try {
//            String encoding = "GBK";
//            File file = new File("F:\\test.txt");
////            if (file.isFile() && file.exists()) { //判断文件是否存在
//            InputStreamReader read = new InputStreamReader(
//                    new FileInputStream(file), encoding);//考虑到编码格式
//            BufferedReader bufferedReader = new BufferedReader(read);
//            String lineTxt = null;
//
////                String code = "LK0001";
//            SubjectBean q = new SubjectBean();
//            q.setAnswer("A");
//            ArrayList<SubjectBean> mList = new ArrayList();
//            while ((lineTxt = bufferedReader.readLine()) != null) {
//                System.out.println(lineTxt);
//
//                if (lineTxt.startsWith("(A)")) {
//                    q.setOptionA(lineTxt.substring(3));
//                } else if (lineTxt.startsWith("(B)")) {
//                    q.setOptionB(lineTxt.substring(3));
//                } else if (lineTxt.startsWith("(C)")) {
//                    q.setOptionC(lineTxt.substring(3));
//                } else if (lineTxt.startsWith("(D)")) {
//                    q.setOptionD(lineTxt.substring(3));
//                } else {
//                    if (lineTxt.contains(".")) {
//                        int startPosi = lineTxt.indexOf(".");
//                        q.setQuestion(lineTxt.substring(startPosi + 1));
//                    }
////                        q.setQuestion(lineTxt.substring(3));
//                }
////                    if (lineTxt.startsWith("[Q]")) {
////                        q.setQuestion(lineTxt.substring(3));
////                    }
////                    if (lineTxt.startsWith("[I]")) {
////                        if (!lineTxt.substring(3).equals(code)) {
////                            q.setCode(code);
////                            code = lineTxt.substring(3);
////                            //questionsDAO.save(q);
////                        }
////                    }
//                mList.add(q);
//            }
//            read.close();
//            String result = JsonUtil.getStringFromList(mList);
//            return result;
////            } else {
////                System.out.println("找不到指定的文件");
////                return "";
////            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//            return "";
//        }
    }

    //退出所有的activity
    public static void exitApplication() {
        // 判断activityList是否为空
        if (!CommonUtil.isListEmpty(activityList)) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
    }

    /**
     * 获得软件的versionCode
     *
     * @return
     */
    public static int getVersionCode(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (Exception e) {
        }
        return 0;
    }


    // 如果文件上级目录不存在 创建文件的上级目录
    public static void createFileDirectory(File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }


    private static int getIdentify(Context context) {
        return context.getResources().getIdentifier("test", "drawable",
                context.getPackageName());
    }

    /**
     * 获取设备宽度和高度
     **/
    public static int[] getDeviceWH(Context paramContext) {
        int[] arrayOfInt = new int[2];
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        int j = localDisplayMetrics.heightPixels;
        arrayOfInt[0] = i;
        arrayOfInt[1] = j;
        return arrayOfInt;
    }

    /**
     * 获得软件的版本号
     *
     * @return
     */
    public static String getVersionName(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
        return "";
    }

    //把date转换为string
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    //把字符串转为日期
    public static Date converToDate(String strDate) {
        DateFormat df;
        if (strDate.length() > 8)
            df = new SimpleDateFormat("yyyy-MM-dd");
        else {
            df = new SimpleDateFormat("yyyyMMdd");
        }
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*比较两个日期差值
    * 如果大于7秒 则认为超过了时间 必须强制交卷*/
    public static boolean compareIsOverTime(String lastTime, int second) {
        try {
            Date lastTimeDate = new Date(Long.parseLong(lastTime));
            Date nowTime = new Date(System.currentTimeMillis());
            long diff = nowTime.getTime() - lastTimeDate.getTime();//这样得到的差值是微秒级别
            System.out.println("时间差是" + diff / 1000);
            if (diff / 1000 < second) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    //在一定范围内生成不重复的随机数
    //在testRandom2中生成的随机数可能会重复.
    //在此处避免该问题
    public static ArrayList<Integer> getRandom(int maxRandom, int size) {
        HashSet<Integer> integerHashSet = new HashSet<>();
        ArrayList<Integer> randomList = new ArrayList<>();
        Random random = new Random();
        //从0--maxRandom数据中选出size个随机数
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(maxRandom);
            System.out.println("生成的randomInt=" + randomInt);
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                System.out.println("添加进HashSet的randomInt=" + randomInt);
            } else {
                //因为如果有重复的会被去掉 则不够约定的数量 所以要i减一以保证得到固定的数量
                i--;
                System.out.println("该数字已经被添加,不能重复添加");
            }
        }
        Iterator it = integerHashSet.iterator();
        while (it.hasNext()) {
            int randomInt = (Integer) it.next();
            randomList.add(randomInt);
            System.out.println("B类的随机题目是" + randomInt + "===========");
        }
        System.out.println("/////以上为testRandom3()的测试///////");
        return randomList;
    }

    //在一定范围内生成不重复的随机数
    //在testRandom2中生成的随机数可能会重复.
    //在此处避免该问题   从minRandom到maxRandom范围内的随机数
    public static ArrayList<Integer> getRangeRandom(int minRandom, int maxRandom, int size) {
        HashSet<Integer> integerHashSet = new HashSet<>();
        ArrayList<Integer> randomList = new ArrayList<>();
        Random random = new Random();
        //从0--maxRandom数据中选出size个随机数
        for (int i = 0; i < size; i++) {
            //获取在minRan到maxRandom范围内size个随机数，random.nextInt(i)是获取0~~(i-1)范围内的数，不包括i,因为
            //list的索引正好是从0开始 所以可以直接写
            int randomInt = random.nextInt(maxRandom - minRandom) + minRandom;
            System.out.println("生成的randomInt=" + randomInt);
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                System.out.println("添加进HashSet的randomInt=" + randomInt);
            } else {
                //因为如果有重复的会被去掉 则不够约定的数量 所以要i减一以保证得到固定的数量
                i--;
                System.out.println("该数字已经被添加,不能重复添加");
            }
        }
        Iterator it = integerHashSet.iterator();
        while (it.hasNext()) {
            int randomInt = (Integer) it.next();
            randomList.add(randomInt);
            System.out.println("B类的随机题目是" + randomInt + "===========");
        }
        System.out.println("/////以上为testRandom3()的测试///////");
        return randomList;
    }
}
