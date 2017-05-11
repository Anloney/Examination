package anloneytest.com.test.bean;

import java.io.Serializable;

/**
 * Created by Anloney on 2016/8/2.
 * 考试试题的bean
 */
public class SubjectBean implements Serializable {

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    //    "optionD":"对调频信号进行解调的过程称为鉴频","optionC":"判断信号频率是否超过允许的频率范围的过程称为鉴频",
//            "optionB":"判断信号频率是否发生了不应有的偏离或者漂移过程称为鉴频","signUpId":1,
//            "optionA":"对调幅信号进行解调的过程称为鉴频","answer":"D","examId":49,
//            "question":"什么叫做“鉴频”？"}
    public String pic;//试题图片 如果不是空 说明有图片
    public String question;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String selectedOption;//选择的答案

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String answer;

    public int examId;

    public int getTestNo() {
        return testNo;
    }

    public void setTestNo(int testNo) {
        this.testNo = testNo;
    }

    public int testNo;//题号

}
