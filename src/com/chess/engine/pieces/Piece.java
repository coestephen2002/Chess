package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {
	
	protected final PieceType pieceType;
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	private final int cachedHashCode;
	
	/**
	 * Constructor for Piece
	 * @param piecePosition    where the Piece is on the board
	 * @param pieceAlliance    the color of the Piece
	 * @param pieceType        the PieceType of the Piece
	 */
	Piece(final int piecePosition, final Alliance pieceAlliance, final PieceType pieceType) {
		
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.pieceType = pieceType;
		//TODO more work here
		this.isFirstMove = false;
		this.cachedHashCode = computeHashCode();
	}
	
	@Override
	public boolean equals(final Object other) {
		
		if(this == other) {
			return true;
		}
		if(!(other instanceof Piece)) {
			return false;
		}
		
		final Piece otherPiece = (Piece) other;
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType()
				&& pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
		
	}
	
	private int computeHashCode() {
		
		int result = pieceType.hashCode();
		result = 31 * result + pieceAlliance.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;
	}
	
	@Override 
	public int hashCode() {
		return this.cachedHashCode;
	}
	
	/**
	 * A function that returns the tile coordinate of the tile that this piece is on
	 * @return    An integer representing the piece position
	 */
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	/**
	 * A function that returns the Alliance that the piece belongs to
	 * @return    The Alliance of the Piece
	 */
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}
	
	/**
	 * A function that returns the PieceType that this piece is
	 * @return    The PieceType of the Piece
	 */
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	
	public abstract List<Move> calculateLegalMoves(final Board board);
	
	public abstract Piece movePiece(Move move);
	
	
	public enum PieceType {
		
		PAWN("P") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}
			
			@Override
			public boolean isRook() {
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}
			
			@Override
			public boolean isRook() {
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}
			
			@Override
			public boolean isRook() {
				return true;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}
			
			@Override
			public boolean isRook() {
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}
			
			@Override
			public boolean isRook() {
				return false;
			}
		};
		
		
		private String pieceName;
		
		PieceType(String pieceName) {
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName;
		}
		
		public abstract boolean isKing();
		public abstract boolean isRook();
	}
}