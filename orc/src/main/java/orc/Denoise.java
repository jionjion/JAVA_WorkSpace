package orc;

import java.awt.Color;
import java.awt.image.BufferedImage;

/** 降噪 */
public class Denoise {

	/**
	 * 降噪，以1个像素点为单位（实际使用中可以循环降噪，或者把单位可以扩大为多个像素点）
	 * @param image
	 * @return
	 */
	public static BufferedImage denoise(BufferedImage image){
		int w = image.getWidth();  
	    int h = image.getHeight();
	    int white = new Color(255, 255, 255).getRGB();  
 
	    if(isWhite(image.getRGB(1, 0)) && isWhite(image.getRGB(0, 1)) && isWhite(image.getRGB(1, 1))){
	    	image.setRGB(0,0,white);
        }
	    if(isWhite(image.getRGB(w-2, 0)) && isWhite(image.getRGB(w-1, 1)) && isWhite(image.getRGB(w-2, 1))){
	    	image.setRGB(w-1,0,white);
        }
	    if(isWhite(image.getRGB(0, h-2)) && isWhite(image.getRGB(1, h-1)) && isWhite(image.getRGB(1, h-2))){
	    	image.setRGB(0,h-1,white);
        }
	    if(isWhite(image.getRGB(w-2, h-1)) && isWhite(image.getRGB(w-1, h-2)) && isWhite(image.getRGB(w-2, h-2))){
	    	image.setRGB(w-1,h-1,white);
        }
	    
	    for(int x = 1; x < w-1; x++){
	    	int y = 0;
	    	if(isBlack(image.getRGB(x, y))){
            	int size = 0;
                if(isWhite(image.getRGB(x-1, y))){
                	size++;
                }
                if(isWhite(image.getRGB(x+1, y))){
                	size++;
                }
                if(isWhite(image.getRGB(x, y+1))){
                	size++;
                }
                if(isWhite(image.getRGB(x-1, y+1))){
                	size++;
                }
                if(isWhite(image.getRGB(x+1, y+1))){
                	size++;
                } 
                if(size>=5){
                	image.setRGB(x,y,white);                     
                }
            }
	    }
	    for(int x = 1; x < w-1; x++){
	    	int y = h-1;
	    	if(isBlack(image.getRGB(x, y))){
            	int size = 0;
                if(isWhite(image.getRGB(x-1, y))){
                	size++;
                }
                if(isWhite(image.getRGB(x+1, y))){
                	size++;
                }
                if(isWhite(image.getRGB(x, y-1))){
                	size++;
                }
                if(isWhite(image.getRGB(x+1, y-1))){
                	size++;
                }
                if(isWhite(image.getRGB(x-1, y-1))){
                	size++;
                }
                if(size>=5){
                	image.setRGB(x,y,white);                     
                }
            }
	    }
	    
	    for(int y = 1; y < h-1; y++){
	    	int x = 0;
	    	if(isBlack(image.getRGB(x, y))){
            	int size = 0;
                if(isWhite(image.getRGB(x+1, y))){
                	size++;
                }
                if(isWhite(image.getRGB(x, y+1))){
                	size++;
                }
                if(isWhite(image.getRGB(x, y-1))){
                	size++;
                }
                if(isWhite(image.getRGB(x+1, y-1))){
                	size++;
                }
                if(isWhite(image.getRGB(x+1, y+1))){
                	size++;
                } 
                if(size>=5){
                	image.setRGB(x,y,white);                     
                }
            }
	    }
	    
	    for(int y = 1; y < h-1; y++){
	    	int x = w - 1;
	    	if(isBlack(image.getRGB(x, y))){
            	int size = 0;
            	if(isWhite(image.getRGB(x-1, y))){
                	size++;
                }
                if(isWhite(image.getRGB(x, y+1))){
                	size++;
                }
                if(isWhite(image.getRGB(x, y-1))){
                	size++;
                }
                //斜上下为空时，去掉此点
                if(isWhite(image.getRGB(x-1, y+1))){
                	size++;
                }
                if(isWhite(image.getRGB(x-1, y-1))){
                	size++;
                }
                if(size>=5){
                	image.setRGB(x,y,white);                     
                }
            }
	    }
	    
		//降噪，以1个像素点为单位
    	for(int y = 1; y < h-1; y++){
            for(int x = 1; x < w-1; x++){                   
                if(isBlack(image.getRGB(x, y))){
                	int size = 0;
                    //上下左右均为空时，去掉此点
                    if(isWhite(image.getRGB(x-1, y))){
                    	size++;
                    }
                    if(isWhite(image.getRGB(x+1, y))){
                    	size++;
                    }
                    //上下均为空时，去掉此点
                    if(isWhite(image.getRGB(x, y+1))){
                    	size++;
                    }
                    if(isWhite(image.getRGB(x, y-1))){
                    	size++;
                    }
                    //斜上下为空时，去掉此点
                    if(isWhite(image.getRGB(x-1, y+1))){
                    	size++;
                    }
                    if(isWhite(image.getRGB(x+1, y-1))){
                    	size++;
                    }
                    if(isWhite(image.getRGB(x+1, y+1))){
                    	size++;
                    } 
                    if(isWhite(image.getRGB(x-1, y-1))){
                    	size++;
                    }
                    if(size>=8){
                    	image.setRGB(x,y,white);                     
                    }
                }
            }
        }
	    
	    return image;
	}
	
	 public static boolean isBlack(int colorInt)  
     {  
         Color color = new Color(colorInt);  
         if (color.getRed() + color.getGreen() + color.getBlue() <= 300)  
         {  
             return true;  
         }  
         return false;  
     }  
 
     public static boolean isWhite(int colorInt)  
     {  
         Color color = new Color(colorInt);  
         if (color.getRed() + color.getGreen() + color.getBlue() > 300)  
         {  
             return true;  
         }  
         return false;  
     }  
     
     public static int isBlack(int colorInt, int whiteThreshold) {
 		final Color color = new Color(colorInt);
 		if (color.getRed() + color.getGreen() + color.getBlue() <= whiteThreshold) {
 			return 1;
 		}
 		return 0;
 	}
}
