import java.awt.geom.AffineTransform;

public class Main {

    static public void main(String[] args)
    {
        try
        {
        String inputImagePath= "image.png";
        String outputPath= "output.txt";
        ImageConverter imageConverter=new ImageConverter(inputImagePath);
        imageConverter.rescale(0.5);
        imageConverter.setGreyscaleType(GREYSCALE_TYPE.DESATURATED);
        imageConverter.saveAsciiArtToString(outputPath);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
