package com.chess.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;


public abstract class Tile {
	
	protected final int tileCoordinate;
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	
	/**
	 * Function that creates all the Tiles on a board
	 * @return    A HashMap that contains all the created Tiles that we will use for the board
	 */
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		
		final HashMap<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for(int i = 0; i < 64; i++) {
			
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
		return Collections.unmodifiableMap(emptyTileMap);
	}
	
	/**
	 * Function that creates a new OccupiedTile when a Piece needs to be put on a tile. If Piece is null, then we return one of the cached EmptyTiles
	 * @param tileCoordinate
	 * @param piece
	 * @return    The new OccupiedTile or previously cached EmptyTile
	 */
	private static Tile createTile(final int tileCoordinate, final Piece piece) {
		
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	}
	
	/** 
	 * Constructor for a Tile
	 * @param tileCoordinate    the location of the tile
	 */
	Tile (int tileCoordinate) {
		
		this.tileCoordinate = tileCoordinate;
	}
	
	/**
	 * A function that determines if there is a Piece on the tile
	 * @return    a boolean indicating whether the tile is occupied
	 */
	public abstract boolean isTileOccupied();
	
	/**
	 * A function that returns the Piece that is on this tile
	 * @return    the Piece that is occupying this tile
	 */
	public abstract Piece getPiece();
	
	
	public static final class EmptyTile extends Tile {
		
		EmptyTile(final int coordinate) {
			super(coordinate);
		}
		
		/**
		 * A function that determines if there is a Piece on the tile
		 * @return    a boolean indicating whether the tile is occupied
		 */
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		/**
		 * A function that returns the Piece that is on this tile
		 * @return    the Piece that is occupying this tile
		 */
		@Override
		public Piece getPiece() {
			
			return null;
		}

	}
	
	public static final class OccupiedTile extends Tile {
		
		private final Piece pieceOnTile;
		
		/**
		 * Constructor for OccupiedTile
		 * @param coordinate    location of the tile
		 * @param pieceOnTile    the Piece that is occupying this tile  
		 */
		OccupiedTile(final int coordinate, final Piece pieceOnTile) {
			super(coordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		/**
		 * A function that determines if there is a Piece on the tile
		 * @return    a boolean indicating whether the tile is occupied
		 */
		@Override
		public boolean isTileOccupied() {
			
			return true;
		}
		
		/**
		 * A function that returns the Piece that is on this tile
		 * @return    the Piece that is occupying this tile
		 */
		@Override
		public Piece getPiece() {
			
			return this.pieceOnTile;
		}

	}
	
	
}
