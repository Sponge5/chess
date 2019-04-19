package client.GUI;

public enum MinDims{
    BUTTON (40, 40),
    SCENE (320, 320);

    public int width, height;

    MinDims(int w, int h) {
        this.width = w;
        this.height = h;
    }
}
