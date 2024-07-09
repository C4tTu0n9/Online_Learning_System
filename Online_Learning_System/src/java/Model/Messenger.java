/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Messenger {
    private int messenger_id;
    private int sender_id;
    private int receiver_id;
    private String message_text;
    private Timestamp message_time;
    private String fullname;
    private String avt;
    public Messenger(int sender_id1, int receiver_id1, String message_text1, Timestamp timestamp) {
    }

    public Messenger(int messenger_id, int sender_id, int receiver_id, String message_text, Timestamp message_time, String avt) {
        this.messenger_id = messenger_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.message_text = message_text;
        this.message_time = message_time;
        this.avt = avt;
    }

    public Messenger(int sender_id, int receiver_id, String message_text) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.message_text = message_text;
    }

    public Messenger(int sender_id, int receiver_id, String fullname, String avt) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.fullname = fullname;
        this.avt = avt;
    }
    
    


    public int getMessenger_id() {
        return messenger_id;
    }

    public void setMessenger_id(int messenger_id) {
        this.messenger_id = messenger_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public Timestamp getMessage_time() {
        return message_time;
    }

    public void setMessage_time(Timestamp message_time) {
        this.message_time = message_time;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    @Override
    public String toString() {
        return "Messenger{" + "receiver_id=" + receiver_id + ", fullname=" + fullname + ", avt=" + avt + '}';
    }

    

   

   

    

   

    

    
    
    
    
}
