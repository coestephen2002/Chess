package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Move.PawnAttackMove;
import com.chess.engine.board.Move.PawnEnPassantAttackMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
import com.chess.engine.pieces.Piece.PieceType;

public class Pawn extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

	public Pawn(final int piecePosition, final Alliance pieceAlliance) {
		
		super(piecePosition, pieceAlliance, PieceType.PAWN, true);
	}
	
	public Pawn(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		
		super(piecePosition, pieceAlliance, PieceType.PAWN, isFirstMove);
	}
	
	/**
	 * Function that returns the possible legal moves for the Pawn Piece
	 * @param board    the current board state
	 * @return    a List of the legal moves that can be taken
	 */	
	@Override
	public List<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			
			final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);
			
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			//Basic move
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				
				if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
					legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
				}
				else {
					legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
				}
			} 
			//Double jump move
			else if(currentCandidateOffset == 16 && this.isFirstMove() && 
					((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.pieceAlliance.isBlack()) || 
					(BoardUtils.SECOND_RANK[this.piecePosition] && this.pieceAlliance.isWhite()))) {
				
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					
					legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
				}
			}
			//Attacking right for white; left for black
			else if(currentCandidateOffset == 7 &&
					!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.pieceAlliance) {
						if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
						}
						else {
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
				else if(board.getEnPassantPawn() != null) {
					if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
						final Piece pieceOnCandidate = board.getEnPassantPawn();
						if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
							legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
			}
			//Attacking left for white; right for black
			else if(currentCandidateOffset == 9 &&
					!((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.pieceAlliance) {
						if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
						}
						else {
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
				else if(board.getEnPassantPawn() != null) {
					if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
						final Piece pieceOnCandidate = board.getEnPassantPawn();
						if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
							legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
			}
		}
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}
	
	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}
	
	public Piece getPromotionPiece() {
		return new Queen(this.piecePosition, this.pieceAlliance, false);
	}

}
