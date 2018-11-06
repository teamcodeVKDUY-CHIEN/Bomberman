package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import java.io.*;
import java.nio.charset.CodingErrorAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
            try {
                File file = new File("Level1.txt");
                //D:/Hoc_Tap/Lap_Trinh/JAVA/bomberman-starter/res/levels/
                FileReader reader = new FileReader(file);
                BufferedReader in = new BufferedReader(reader);
                String s;
                String[] arr;
                s = in.readLine();
                arr =s.split(" ");
                
                super._level=Integer.parseInt(arr[0]);
                super._height=Integer.parseInt(arr[1]);
                super._width=Integer.parseInt(arr[2]);
                
                _map = new char[super._height][];
                int j=0;
                while((s=in.readLine())!=null && s.length()>1)
                {
                    _map[j] =new char[s.length()];
                    for(int i=0;i<s.length();i++)
                    {
                        FileLevelLoader._map[j][i] = s.charAt(i);
                    }
                    j++;
                }
                
            } catch (Exception ex) {
                Logger.getLogger(FileLevelLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall
                
                for(int i=0;i<super._height;i++)
                {
                    for(int j=0;j<super._width;j++)
                    {
                       if(_map[i][j]=='#'||_map[i][j]=='*'||_map[i][j]=='x' || _map[i][j]==' ')
                       {
                            Sprite sprite = null;
                            if (_map[i][j] == '#')
                            {
                                sprite = Sprite.wall;
                                _board.addEntity(j+i*_width, new Wall(j, i, sprite));
                            }
                            else if (_map[i][j] == '*')
                            {
                                sprite = Sprite.brick;
                                _board.addEntity(j+i*_width, new LayeredEntity(j, i, 
						new Grass(j ,i, Sprite.grass), 
						new Brick(j ,i, Sprite.brick)) );
                            }
                            else if (_map[i][j] == 'x')
                            {
                                sprite = Sprite.portal;
                                _board.addEntity(j+i*_width, new LayeredEntity(j, i, 
						new Grass(j ,i, Sprite.grass), 
						new Portal(j ,i, Sprite.brick)) );
                            }
                            else if(_map[i][j]==' ')
                            {
                                sprite = Sprite.grass;
                                _board.addEntity(j+i*_width, new Grass(j, i, sprite));
                            }
                        }
                        
                        else if(_map[i][j]=='p'||_map[i][j]=='1'||_map[i][j]=='2')
                        {
                            if(_map[i][j]=='p')
                            {
                                _board.addCharacter(new Bomber(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i) + Game.TILES_SIZE, _board));
                                Screen.setOffset(0, 0);
                            }
                            else if(_map[i][j]=='1')
                            {
                                _board.addCharacter( new Balloon(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i) + Game.TILES_SIZE, _board));
                            }
                            else
                            {
                                _board.addCharacter( new Oneal(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i) + Game.TILES_SIZE, _board));
                            }
                            _board.addEntity(j+i*_width, new Grass(j, i, Sprite.grass));
                        }
                        else if(_map[i][j]=='f' || _map[i][j]=='b'||_map[i][j]=='s')
                        {
                            if(_map[i][j]=='s')
                            {
                                _board.addEntity(j+i*_width,
                                        new LayeredEntity(j, i,
                                                new Grass(j, i, Sprite.grass),
                                                new SpeedItem(j, i, Sprite.powerup_speed),
                                                new Brick(j, i, Sprite.brick)
                                        )
                                );
                            }
                            else if(_map[i][j]=='b')
                            {
                                _board.addEntity(j+i*_width,
                                        new LayeredEntity(j, i,
                                                new Grass(j, i, Sprite.grass),
                                                new BombItem(j, i, Sprite.powerup_bombs),
                                                new Brick(j, i, Sprite.brick)
                                        )
                                );
                            }
                            else
                            {
                                _board.addEntity(j+i*_width,
                                        new LayeredEntity(j, i,
                                                new Grass(j, i, Sprite.grass),
                                                new FlameItem(j, i, Sprite.powerup_flames),
                                                new Brick(j, i, Sprite.brick)
                                        )
                                );
                            }
                        }
                    }
                }
//		for (int x = 0; x < 18; x++) {
//			for (int y = 0; y < 18; y++) {
//				int pos = x + y * _width;
//				Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
//				_board.addEntity(pos, new Grass(x, y, sprite));
//			}
//		}
//
//		// thêm Bomber
//		int xBomber = 1, yBomber = 1;
//		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
//		Screen.setOffset(0, 0);
//		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
//
//		// thêm Enemy
//		int xE = 2, yE = 1;
//		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
//		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
//
//		// thêm Brick
//		int xB = 3, yB = 1;
//		_board.addEntity(xB + yB * _width,
//				new LayeredEntity(xB, yB,
//					new Grass(xB, yB, Sprite.grass),
//					new Brick(xB, yB, Sprite.brick)
//				)
//		);

//		// thêm Item kèm Brick che phủ ở trên
//		int xI = 1, yI = 2;
//		_board.addEntity(xI + yI * _width,
//				new LayeredEntity(xI, yI,
//					new Grass(xI ,yI, Sprite.grass),
//					new SpeedItem(xI, yI, Sprite.powerup_flames),
//					new Brick(xI, yI, Sprite.brick)
//				)
//		);
	}

}
