package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class Knight extends Piece{
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
	
	/**
	 * Constructor for the Knight Piece
	 * @param piecePosition    where the Knight is on the board
	 * @param pieceAlliance    the color of the Knight
	 */
	Knight(final int piecePosition, final Alliance pieceAlliance) {
		
		super(piecePosition, pieceAlliance);
	}
	
	/**
	 * Function that returns the possible legal moves for the Knight Piece
	 * @param board    the current board state
	 * @return    a List of the legal moves that can be taken
	 */
	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		int candidateDestinationCoordinate;
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
			
			candidateDestinationCoordinate = this.piecePosition + currentCandidate;
			
			if(true /* isValidTileCoordinate*/) {
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				
				if(candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new Move());
				}
				else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					
					if(this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new Move());
					}
				}
			}
		}
		
		return Collections.unmodifiableList(legalMoves);
	}

}
