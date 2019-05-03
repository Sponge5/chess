package client.GUI;

public enum MinDims{
    BUTTON (40, 40),
    SCENE (400, 400);

    public int width, height;

    MinDims(int w, int h) {
        this.width = w;
        this.height = h;
    }
}
