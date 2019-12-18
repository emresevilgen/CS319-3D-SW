package audioDescription;

import uiComponents.SceneHandler;

public class AudioDescriptionHandler {
    private static AudioDescriptionHandler audioDescriptionHandler = new AudioDescriptionHandler();

    private Reader reader;

    private AudioDescriptionHandler(){
        reader = new DescriptionReader();
    }

    //Get the only object available
    public static AudioDescriptionHandler getInstance(){
        if (audioDescriptionHandler == null)
            audioDescriptionHandler = new AudioDescriptionHandler();
        return audioDescriptionHandler;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

}
