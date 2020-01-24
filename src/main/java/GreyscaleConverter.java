import java.awt.*;




public class GreyscaleConverter {

    static int[][] convert(Color[][] pixelsArray, GREYSCALE_TYPE type) throws Exception
    {

        int width = pixelsArray.length;
        int height = pixelsArray[0].length;
        for(int i=1; i<width; i++)
        {
            if(pixelsArray[i].length!=height) throw new Exception("Input array is not rectangular!");
        }

        int[][] result = new int[width][height];
        for(int i=0; i<width; i++) {
            for (int j = 0; j < height; j++)
            {
                result[i][j]=getGreyscaleOfPixel(pixelsArray[i][j], type);
            }
        }
        return result;
    }

    static int[][] convert(Color[][] pixelsArray) throws Exception
    {
        return convert(pixelsArray, GREYSCALE_TYPE.DESATURATED);
    }

    static private int getGreyscaleOfPixel(Color pixel, GREYSCALE_TYPE type)
    {
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();
        int result=0;
        switch(type) {
            case AVARAGE: {
                result = (int)Math.floor((red+green+blue)/3);
                break;

            }
            case LUMINANCE: {
                result = (int)Math.floor(red*0.299 + green*0.587 + blue*0.114);
                break;
            }

            case DESATURATED:
            {
                result = (int)Math.floor(( Math.max(Math.max(red, green), blue) + Math.min(Math.min(red, green), blue) ) / 2);
                break;
            }

        }
        return result;

    }

}
