package com.PollSystem.PollSystem.Model;

public class PollToSend {
    public long id;
    public String title;
    public String[] answers;
    
    
    
    
    public PollToSend(PollModel pollModel) {
        id = pollModel.getId();
        title = pollModel.getTitle();
        answers = pollModel.getAnswersAsTxts();  
    }
}
