package uet.oop.bomberman.entities.tile;

import developGame.Audios;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
        // âm thanh khi qua cửa.
        Audios soundPassgame = new Audios("passgame.wav", false); 
        protected Board _board;
    
	public Portal(int x, int y, Board _board, Sprite sprite) {
		super(x, y, sprite);
                this._board = _board; 
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào
                // đã sửa. 
                if(e instanceof  Bomber) {
			
			if(_board.detectNoEnemies() == false)
				return false;
			soundPassgame.playAgain();
			//if(e.getXTile() == getX() && e.getYTile() == getY()) {
				//if(_board.detectNoEnemies())
					_board.nextLevel();
			//}
			
			return true;
		}
		
		return false;
	}

}
