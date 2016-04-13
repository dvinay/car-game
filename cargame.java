/*the car game is having 2 ways to move on to the road ,a player can change the way by presing left or right keyword, if a player hit the other car the game will be over, if the player hits the blue box he/she will earn 50 points.by pressing Escape key the game can be paused,the game will be resumed by again pressing the Escape key*/

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.Random;
/*<applet code=cargame width=300 height=300> </applet>*/

public class cargame extends Applet implements Runnable,KeyListener//,ActionListener
{
Thread t=new Thread(this);
boolean flag=false,brk=false,change=true,pause=false,point=false,msg=true;
Random r;
int y=0,ran=0,car=0,block=10,stage=1;
int score=0;

public void init()
{
r=new Random();
addKeyListener(this);
requestFocus();
//repaint();
}

synchronized public void keyPressed(KeyEvent ke)  //main    key---------------------------------------   key
{
int key=ke.getKeyCode();
if(!flag)
{
if(key==KeyEvent.VK_LEFT)
{
y=0;
if(!brk)
repaint();
}
if(key==KeyEvent.VK_RIGHT)
{
y=50;
if(!brk)
repaint();
}
}
if(key==KeyEvent.VK_ESCAPE)
{
pause=!pause;
if(pause)
showStatus("PAUSED");
else
{
showStatus("STARTED");
notify();
}
}
}
public void keyReleased(KeyEvent ke){}

public void keyTyped(KeyEvent ke){}

public void start()//=================start
{
brk=false;
t.start();
}

public void run()//run     ..........................run
{
repaint();
try{Thread.sleep(1000);}
catch(Exception e){}
msg=false;
while(true)
{
block=10;
while(true)
{
repaint();
block=block+10;
if(block==230)
break;
if(block>=130 && block<=200 && car==y && !point)
break;
if(block>=160 && block<=200 && car==y && point)
break;
for(int i=5;i>=stage;i--)
try{Thread.sleep(10);}//==========================to increase the speed
catch(Exception e){}
if(pause)
lock();
}
if(block>=130 && block<=200 && car==y && !point)//if the car hits another car
break;
if(block>=160 && block<=200 && car==y && point)//if the car hits the blue bonus point box
{
score+=50;
}
ran=r.nextInt();
if(ran%5==0)
point=true;
else if(ran%2==0)
{
point=false;
car=0;
}
else
{
point=false;
car=50;
}
score+=10;
if(brk)
break;
}
if(block>=130 && block<=200 && car==y && !point)
{
flag=true;
int c=6;
while(c!=0)
{
repaint();
try{Thread.sleep(500);}
catch(Exception e){}
c--;
change=!change;
}
}

}//======================end of run

synchronized public void lock()
{
if(pause)
try{wait();}
catch(Exception e){}
}

public void stop()
{
brk=true;
}

public void paint(Graphics g)
{
g.drawString("CAR GAME",55,100);
stage=(score/1000)+1;
g.drawString("Stage:"+stage,150,100);

if(msg)
{
g.drawString("please click",50,190);
g.drawString("inside before",50,200);
g.drawString(" game starts",50,210);
}
g.drawRect(40,130,90,150);
if(!flag)
{
g.setColor(Color.green);
g.fillRect((y+40),240,40,10);
g.fillRect((y+55),230,10,40);
g.fillRect((y+40),270,40,10);

g.setColor(Color.black);
g.drawString("score:"+score,170,210);
g.drawString("press ESC to pause/play",150,160);
g.setColor(Color.blue);
g.drawString("blue box=50 points",150,150);

if(!point)
{
g.setColor(Color.red);
if((70+block)>=130 && (70+block)<=270)
g.fillRect(55+car,70+block,10,10);
if((80+block)>=130 && (80+block)<=270)
g.fillRect(40+car,80+block,40,10);
if((90+block)>=130 && (90+block)<=270)
g.fillRect(55+car,90+block,10,10);
if((100+block)>=130 && (100+block)<=270)
g.fillRect(55+car,100+block,10,10);
if((110+block)>=130 && (110+block)<=270)
g.fillRect(40+car,110+block,40,10);
}

if(point)
{
g.setColor(Color.blue);
if((70+block)>=130 && (70+block)<=270)
g.fillRect(50+car,70+block,20,10);
if((80+block)>=130 && (80+block)<=270)
g.fillRect(50+car,80+block,20,10);
}
}

if(flag)
{
g.setColor(Color.black);
g.drawString("GAME OVER",50,200);
g.setColor(Color.red);
g.drawString("TOTAL SCORE:"+score,170,210);
g.setColor(Color.orange);
if(change)
{
g.fillRect((y+40),230,15,10);
g.fillRect((y+65),230,15,10);
g.fillRect((y+55),240,10,10);
g.fillRect((y+40),250,15,10);
g.fillRect((y+65),250,15,10);
}
else
{
g.fillRect((y+55),230,10,10);
g.fillRect((y+40),240,40,10);
g.fillRect((y+55),250,10,10);
}

}//if

}//paint

}//class