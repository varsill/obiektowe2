import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ImageConverter {
    private BufferedImage bufferedImage;
    private final Dimension imageDimension;
    private  Dimension outputDimension;
    private Dimension singleTileDimension;
    private Color[][] arrayOfPixels;
    private BufferedImage imageToConvert;
    private GREYSCALE_TYPE greyscaleType = GREYSCALE_TYPE.LUMINANCE;
    public ImageConverter(String pathToImageInputFile) {
        try {
            bufferedImage = ImageIO.read(new File(pathToImageInputFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.imageDimension=new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
        this.arrayOfPixels=new Color[this.imageDimension.width][this.imageDimension.height];
        this.singleTileDimension=new Dimension(1,1);
        this.outputDimension = new Dimension(imageDimension.width, imageDimension.height);
        imageToConvert=bufferedImage;
        fillArrayOfPixels();

    }

    public void setOutputDimension(int width, int height) throws Exception
    {
        this.outputDimension=new Dimension(width, height);
        int tileWidth= (int)Math.floor(1.0*imageDimension.width/outputDimension.width);
        int tileHeight= (int)Math.floor(1.0*imageDimension.height/outputDimension.height);
        this.singleTileDimension=new Dimension(tileWidth, tileHeight);
        arrayOfPixels=new Color[width][height];
        this.rescaleImage();
        this.fillArrayOfPixels();
    }

    public void rescale(double rescallingFactor) throws Exception
    {

        setOutputDimension((int)Math.floor(rescallingFactor*this.imageDimension.width), (int)Math.floor(rescallingFactor*this.imageDimension.height));
    }

    public void setGreyscaleType(GREYSCALE_TYPE type)
    {
        this.greyscaleType = type;
    }


    public String getAsciiArtString() throws Exception
    {
        return convert();
    }




    public void saveAsciiArtToString(String pathToOutputFile) throws Exception
    {
        String result = convert();
        try (PrintWriter printWriter = new PrintWriter(pathToOutputFile)) {
            printWriter.println(result);
        }
    }
    private String convert() throws Exception
    {
        StringBuilder builder = new StringBuilder();
        int[][] greyscale = GreyscaleConverter.convert(arrayOfPixels, greyscaleType);


        for(int j=0; j<outputDimension.height; j++)
        {
            for(int i=0; i<outputDimension.width; i++)
            {
                builder.append(getAscii(greyscale[i][j]));
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    private void fillArrayOfPixels()
    {
        for(int i=0; i<outputDimension.width; i++)
        {
            for(int j=0; j<outputDimension.height; j++)
            {
                arrayOfPixels[i][j]=(new Color(imageToConvert.getRGB(i, j)));
            }
        }
    }


    private void rescaleImage()
    {
        this.imageToConvert = new BufferedImage(outputDimension.width, outputDimension.height, BufferedImage.TYPE_INT_ARGB);
        final AffineTransform at = AffineTransform.getScaleInstance(1.0*outputDimension.width/imageDimension.width, 1.0*outputDimension.height/imageDimension.height);
        final AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        imageToConvert = ato.filter(bufferedImage, imageToConvert);
    }
    private String getAscii(int greyscale)
    {

        if(greyscale<16)return "#";
        if(greyscale<32)return "$";
        if(greyscale<48)return "&";
        if(greyscale<64)return "@";
        if(greyscale<80) return "a";
        if(greyscale<96)return "w";
        if(greyscale<112)return "o";
        if(greyscale<128)return "u";
        if(greyscale<144)return "x";
        if(greyscale<160)return "!";
        if(greyscale<176)return "\\";
        if(greyscale<192)return "(";
        if(greyscale<208) return "*";
        if(greyscale<224)return "^";
        if(greyscale<240)return "\'";
        else return " ";


    }








}
