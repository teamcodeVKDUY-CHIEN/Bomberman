package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

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
                ArrayList<String> s = new ArrayList<>();
                FileReader fr = null;
                try {
                  
                    fr = new FileReader("res\\levels\\Level" + level + ".txt");
                    BufferedReader br = new BufferedReader(fr);
                    String str = br.readLine();  
                    int i = 0; 
                    while (!str.equals("")) {
                        s.add(str);
                        str = br.readLine(); 
                    }
                    
                } catch (FileNotFoundException ex) {
                } catch (IOException ex) {
                }
                
                String[] ar = s.get(0).trim().split(" ");
                _level = Integer.parseInt(ar[0]);
                _height = Integer.parseInt(ar[1]);
                _width = Integer.parseInt(ar[2]);
                _map = new char[_height][_width];
                for (int i = 0; i < _height; i++) {
                    for (int j = 0; j < _width; j++) {
                        _map[i][j] = s.get(i + 1).charAt(j);
                    }
                }
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall

                for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
//				int pos = x + y * _width;
//				Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
//				_board.addEntity(pos, new Grass(x, y, sprite));
                    
                            	int pos = x + y * getWidth();
                                char c = _map[y][x]; 
                                switch(c) { // TODO: minimize this method
                                    case '#': 
                                            _board.addEntity(pos, new Wall(x, y, Sprite.wall));  
                                            break;
                                    case 'b': 
                                            LayeredEntity layer = new LayeredEntity(x, y, 
                                                            new Grass(x ,y, Sprite.grass), 
                                                            new Brick(x ,y, Sprite.brick));

                                            if(_board.isItemUsed(x, y, _level) == false) {
                                                    layer.addBeforeTop(new BombItem(x, y, _level, Sprite.powerup_bombs));
                                            }

                                            _board.addEntity(pos, layer);
                                            break;
                                    case 's':
                                            layer = new LayeredEntity(x, y, 
                                                            new Grass(x ,y, Sprite.grass), 
                                                            new Brick(x ,y, Sprite.brick));

                                            if(_board.isItemUsed(x, y, _level) == false) {
                                                    layer.addBeforeTop(new SpeedItem(x, y, _level, Sprite.powerup_speed));
                                            }

                                            _board.addEntity(pos, layer);
                                            break;
                                    case 'f': 
                                            layer = new LayeredEntity(x, y, 
                                                            new Grass(x ,y, Sprite.grass), 
                                                            new Brick(x ,y, Sprite.brick));

                                            if(_board.isItemUsed(x, y, _level) == false) {
                                                    layer.addBeforeTop(new FlameItem(x, y, _level, Sprite.powerup_flames));
                                            }

                                            _board.addEntity(pos, layer);
                                            break;
                                    case '*': 
                                            _board.addEntity(pos, new LayeredEntity(x, y, 
                                                            new Grass(x ,y, Sprite.grass), 
                                                            new Brick(x ,y, Sprite.brick)) );
                                            break;
                                    case 'x': 
                                            _board.addEntity(pos, new LayeredEntity(x, y, 
                                                            new Grass(x ,y, Sprite.grass), 
                                                            new Portal(x ,y, _board, Sprite.portal), 
                                                            new Brick(x ,y, Sprite.brick)) );
                                            
                                            break;
                                    case ' ': 
                                            _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                                            break;
                                    case 'p': 
                                            _board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
                                            Screen.setOffset(0, 0);

                                            _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                                            break;
                                    //Enemies
                                    case '1':
                                            _board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                                            _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                                            break;
                                    case '2':
                                            _board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                                            _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                                            break;
                                    default: 
                                            _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                                            break;
                                }
			}
		}
	}

}
