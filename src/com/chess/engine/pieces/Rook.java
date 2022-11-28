package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;

public class Rook extends Piece {
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8 };

	public Rook(int piecePosition, Alliance pieceAlliance) {
		
		super(piecePosition, pieceAlliance, PieceType.ROOK);
	}

	/**
	 * Function that returns the possible legal moves for the Rook Piece
	 * @param board    the current board state
	 * @return    a List of the legal moves that can be taken
	 */
	@Override
	public List<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves =  new ArrayList<>();
		
		for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			
			int candidateDestinationCoordinate = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
						isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}
				
				candidateDestinationCoordinate += candidateCoordinateOffset;
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
					
					if(!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
					else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						
						if(this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.ROOK.toString();
	}
	
	@Override
	public Rook movePiece(Move move) {
		return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}
	
	/**
	 * Helper function that tells us if a current move from the first column is legal
	 * @param currentPosition    current position of the Rook
	 * @param candidateOffset    the offset from the Rook to the target tile
	 * @return    a boolean that is true if moving our Rook by the given offset is a LEGAL move
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}
	
	/**
	 * Helper function that tells us if a current move from the eighth column is legal
	 * @param currentPosition    current position of the Rook
	 * @param candidateOffset    the offset from the Rook to the target tile
	 * @return    a boolean that is true if moving our Rook by the given offset is a LEGAL move
	 */
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
	}

	
}
