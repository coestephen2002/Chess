package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.board.Tile;

public class Knight extends Piece{
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
	
	/**
	 * Constructor for the Knight Piece
	 * @param piecePosition    where the Knight is on the board
	 * @param pieceAlliance    the color of the Knight
	 */
	public Knight(final int piecePosition, final Alliance pieceAlliance) {
		
		super(piecePosition, pieceAlliance, PieceType.KNIGHT, true);
	}
	
	public Knight(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		
		super(piecePosition, pieceAlliance, PieceType.KNIGHT, isFirstMove);
	}
	
	/**
	 * Function that returns the possible legal moves for the Knight Piece
	 * @param board    the current board state
	 * @return    a List of the legal moves that can be taken
	 */
	@Override
	public List<Move> calculateLegalMoves(final Board board) {
		
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
						isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
						isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
						isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
				else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					
					if(this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}
		}
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	
	@Override
	public Knight movePiece(Move move) {
		return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}
	
	/**
	 * Helper function that tells us if a current move from the first column is legal
	 * @param currentPosition    current position of the Knight
	 * @param candidateOffset    the offset from the Knight to the target tile
	 * @return    a boolean that is true if moving our Knight by the given offset is a LEGAL move
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 || 
				candidateOffset == 6 || candidateOffset == 15);
	}
	
	/**
	 * Helper function that tells us if a current move from the second column is legal
	 * @param currentPosition    current position of the Knight
	 * @param candidateOffset    the offset from the Knight to the target tile
	 * @return    a boolean that is true if moving our Knight by the given offset is a LEGAL move
	 */
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
	}
	
	/**
	 * Helper function that tells us if a current move from the seventh column is legal
	 * @param currentPosition    current position of the Knight
	 * @param candidateOffset    the offset from the Knight to the target tile
	 * @return    a boolean that is true if moving our Knight by the given offset is a LEGAL move
	 */
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	
	/**
	 * Helper function that tells us if a current move from the eighth column is legal
	 * @param currentPosition    current position of the Knight
	 * @param candidateOffset    the offset from the Knight to the target tile
	 * @return    a boolean that is true if moving our Knight by the given offset is a LEGAL move
	 */
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
				candidateOffset == 10 || candidateOffset == 17);
	}
	
	

}
