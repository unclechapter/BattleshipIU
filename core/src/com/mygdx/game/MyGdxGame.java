package com.mygdx.game;

import AppController.AppController;
import Ship_types.Board;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Board board;
	AppController appController;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("Board.jpg");
		board = new Board(img, img, img, img);
		appController = new AppController();
	}

	private void update() {
		appController.update();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		update();
		batch.begin();
		board.draw(false, batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
