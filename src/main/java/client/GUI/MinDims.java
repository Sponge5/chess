package client.GUI;

public enum MinDims{
    BUTTON (80, 80),
    SCENE (800, 800);

    public int width, height;

    MinDims(int w, int h) {
        this.width = w;
        this.height = h;
    }
}
