package audioDescription;


import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class DescriptionReader implements Reader {
    private Voice voice;
    private Thread thread;

    public DescriptionReader(){
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager vm  = VoiceManager.getInstance();
        voice = vm.getVoice("kevin16");
        voice.allocate();


    }

    public void read(String text){
        if (thread != null && thread.isAlive()){
            thread.interrupt();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    voice.speak(text);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}