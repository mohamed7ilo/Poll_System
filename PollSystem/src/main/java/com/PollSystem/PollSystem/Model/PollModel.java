package com.PollSystem.PollSystem.Model;

public class PollModel {
    private long id;

    private String title;

    private AnswerModel[] answers = new AnswerModel[4];
    public static long idGenerator;

    public PollModel(PollToSend pollModel) {
    }

    public PollModel() {
        answers = new AnswerModel[4];
        for (int i = 0; i < answers.length; i++) answers[i] = new AnswerModel();
    }

    public PollModel(String title2, String[] answers2) {
        id = idGenerator++;
        title = title2;
        answers = new AnswerModel[answers2.length];
        for (int i = 0; i < answers2.length; i++) {
            answers[i] = new AnswerModel(answers2[i]);
        }
    }

    public int getNumberOfVotes() {
        int sum = 0;
        for (AnswerModel answer : answers)
            sum += answer.getVoters().size();
        return sum;
    }

    public AnswerModel[] getAnswers() {
        return answers;
    }

    public void removeUserAnswers(long userId) {
        for (AnswerModel answerModel : answers) {
            answerModel.removeUser(userId);
        }
    }

    public long getId() {
        return id;
    }

    public void setProps(String title2, String[] answers2) {
        title = title2 == null ? title : title2;
        if (answers2 != null && answers2.length == answers.length)
            for (int i = 0; i < answers.length; i++)
                answers[i].setTxt(answers2[i]);
    }

    public void vote(long userId, int answerIndex) {
        if (answerIndex > answers.length || answerIndex < 1)
            throw new IllegalArgumentException("Invalid answer index");
        for (AnswerModel answerModel : answers)
            if (answerModel.isVoted(userId))
                throw new IllegalStateException("User already voted");
        answers[answerIndex].vote(userId);
    }

    public String getTitle() {
        return title;
    }

    public String[] getAnswersAsTxts() {
        String[] answersTxts = new String[answers.length];
        for(int i = 0 ; i<answers.length;i++) answersTxts[i] = answers[i].getTxt();
        return answersTxts;
    }

    public void setTitle(String string) {
        title =string;
    }

    public void setId(long long1) {
        id = long1;
    }


    public String getFirstAnswer() {
       return answers[0].getTxt();
    }

    public String getSecondAnswer() {
        return answers[1].getTxt();
    }

    public String getThirdAnswer() {
        return answers[2].getTxt();
    }

    public String getFourthAnswer() {
        return answers[3].getTxt();
    }

    public void setAnswers(AnswerModel[] answers2) {
       this.answers = answers2;
    }

    public int AnswerSize() {
       return answers.length;
    }
}
