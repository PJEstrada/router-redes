/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

/**
 *
 * @author Al
 */
public class Message {
    String from;
    String to;
    String message;

    public Message(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }
    
    public Message()
    {
        this.from = "";
        this.to = "";
        this.message = "";
    }
    
    public String messageToFormat()
    {
        String mensaje = "";
        mensaje+="From: "+from+"\n";
        mensaje+="To: "+to+"\n";
        mensaje+="Msg: "+message+"\n";
        mensaje+="EOF";
        return mensaje;
    }
    
    
    public void formatToMessage(String m)
    {
        String toPart = m.substring(0, m.indexOf("To:"));
        String fromPart =m.substring(m.indexOf("To:"),m.indexOf("Msg:"));
        String msgPart = m.substring(m.indexOf("Msg:"),m.indexOf("EOF"));
        this.to = toPart;
        this.from = fromPart;
        this.message = msgPart;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
