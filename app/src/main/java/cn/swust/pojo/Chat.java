package cn.swust.pojo;

public class Chat {
    private int avator;
    private String name;
    private String content;
    private String time;

    public Chat(int avator, String name, String content, String time) {
        this.avator = avator;
        this.name = name;
        this.content = content;
        this.time = time;
    }

    public int getAvator() {
        return avator;
    }

    public void setAvator(int avator) {
        this.avator = avator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
