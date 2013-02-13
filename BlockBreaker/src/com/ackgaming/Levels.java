package com.ackgaming;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.ackgaming.entity.Entity;
import com.ackgaming.entity.blocks.*;

public class Levels {
	
	public ArrayList blocks = new ArrayList<Entity>();
	int bx = 0;
    int by = 0;
    int levelNumber = 0;
	//Level each level is 10x10
	//You can add levels and they will be added to the end of the game;
	
	// # = Basic block
	// $ = block Red 2 hits
	// % = block Green 3 hits
    // . = bonus life block
	// \n = new line
	public String level[] = new String[6];
	
	public Levels() {
		
		level[0] = "   ####   \n"
				 + "  ##  ##  \n"
				 + "##      ##\n"
				 + "##      ##\n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n";
		
		level[1] = "   ####   \n"
				 + "###$$$$###\n"
				 + "  ######  \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n";
		
		level[2] = "##########\n"
				 + "#$$$$$$$$#\n"
				 + "##########\n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n";
		
		level[3] = "   $$.$   \n"
				 + "  $$%%$$  \n"
				 + " $$%##%$$ \n"
				 + " $$%%%%$$ \n"
				 + "   $$$$   \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n";
		level[4] = ".   $$    \n"
				 + "   $%%$   \n"
				 + "  $%##%$  \n"
				 + " $$####$$ \n"
				 + "$$$$$$$$$$\n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n";
		
		level[6] = "##      ##\n"
				 + "##      ##\n"
				 + "    %%    \n"
				 + "##      ##\n"
				 + "$$      $$\n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n"
				 + "          \n";
	}
	
	public ArrayList loadLevel(Image bbi, Image sbi,Image tbi, Image bli, int num) {
    	
   	 levelNumber = num;

  		 for (int i = 0; i < level[num].length(); i++) {

  		 	char item = level[num].charAt(i);

  		 	if (item == '\n') {
  		 		by += 50;
  		 		bx = 0;
  		 	} else if (item == '#') {
  		 		blocks.add(new BasicBlock(bbi, bx, by));
  		 		bx += 60;
  		 	} else if (item == ' ') {
  		 		bx += 60;
  		 	} else if (item == '$') {
  		 		blocks.add(new SecondBlock(sbi, bx, by));
  		 		bx += 60;
  		 	} else if (item == '%') {
  		 		blocks.add(new ThirdBlock(tbi, bx, by));
  		 		bx += 60;
  		 	} else if (item == '.') {
  		 		blocks.add(new BonusLifeBlock(bli, bx, by));
  		 		bx += 60;
  		 	} 
  		 }

  		 bx = 0;
  		 by = 0;
  		 return blocks;
   }
}
