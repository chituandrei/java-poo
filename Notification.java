public class Notification {
    String notification ;
    String situation;
    Notification(String notification, String situation){
        this.notification = notification ;
        this.situation = situation;
    }

    @Override
    public java.lang.String toString() {
        return "Notificare pentru abonamentul dumneavoastra la catalog este pentru: " + notification + "\n" + "Studentul se afla in situatie de : " + situation;
    }
}
