public class Dimension extends Object {
    public final int height;
    public final int width;

    public Dimension(int width, int height)
    {
        this.width=width;
        this.height=height;
    }
    @Override
    public String toString()
    {
        return String.valueOf(this.width)+","+String.valueOf(this.height);
    }

}
