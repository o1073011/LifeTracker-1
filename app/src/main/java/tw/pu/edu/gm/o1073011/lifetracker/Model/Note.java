package tw.pu.edu.gm.o1073011.lifetracker.Model;

public class Note {
    public String title;
    public String content;



    public String date;

    public Note(){}
    public Note(String title, String content, String date){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
