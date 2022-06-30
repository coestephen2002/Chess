package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {
	
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	
	/**
	 * Constructor for Piece
	 * @param piecePosition    where the Piece is on the board
	 * @param pieceAlliance    the color of the Piece
	 */
	Piece(final int piecePosition, final Alliance pieceAlliance) {
		
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
	}
	
	/**
	 * A function that returns the Alliance that the piece belongs to
	 * @return    The Alliance of the Piece
	 */
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}
	
	
	public abstract List<Move> calculateLegalMoves(final Board board);
}