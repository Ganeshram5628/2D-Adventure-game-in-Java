package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font Arial_40, Arial_80B;
    BufferedImage keyImage;
    BufferedImage heart_full, heart_half, heart_blank;
    public Boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public Boolean gameFinished = false;
    public int commandNum = 0;
    
    
    public double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    
    public UI(GamePanel gp){
        this.gp = gp;
        Arial_40 = new Font("Arial", Font.PLAIN,40);
        Arial_80B = new Font("Arial", Font.BOLD,80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
        //Create HUD
        SuperObject heart = new OBJ_Heart(gp);
        heart.image = heart_full;
        heart.image2 = heart_half;
        heart.image3 = heart_blank;
    }
    
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    
    public void draw(Graphics2D g2){
        this.g2 = g2;
        if(gameFinished == true){
            g2.setFont(Arial_40);
            g2.setColor(Color.white);
            
            String text;
            int textLength;
            int x;
            int y;
            
            text = "You Found The Treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 -  textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);
            
            text = "Your Play time is :"+dFormat.format(playTime);
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 -  textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*4);
            g2.drawString(text, x, y);
            
            g2.setFont(Arial_80B);
            g2.setColor(Color.yellow);
            
            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 -  textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);
        }
        else{
            g2.setFont(Arial_40);
            g2.setColor(Color.white);
            //PlayState
            if(gp.gameState == gp.playState){
                g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2,gp.tileSize,gp.tileSize,null);
                g2.drawString("x "+gp.player.hasKey,74,65);

                //PLAYTIME
                playTime += (double)1/60;
                g2.drawString("Time:"+dFormat.format(playTime),gp.tileSize*11,65);

                if(messageOn == true){
                    g2.setFont(g2.getFont().deriveFont(30F));
                    g2.drawString(message,gp.tileSize/2,gp.tileSize*5);
                    messageCounter++;

                    if(messageCounter > 120){
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
                //Draw Life
                
            }
            //PauseState
            if(gp.gameState == gp.pauseState){
                drawPauseScreen();
            }
            if(gp.gameState == gp.titleState){
                drawTitleScreen();
            }
        }
    }
    
    
    public void drawTitleScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "2D Java Game";
        
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        //Draw Player
        x = gp.screenWidth/2 - gp.tileSize;
        y += gp.tileSize*2;  
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
        
        text = "START";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }
        
        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    
    public void drawPauseScreen(){
        String text = "PAUSED";
        int x,y;
        x = getXforCenteredText(text);
        y = gp.screenHeight/2;
        
        g2.drawString(text,x,y);
    }
    
    public int getXforCenteredText(String text){
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 -  textLength/2;
        return x;
    }
}
