package com.chess.core.pieces;

import com.chess.core.board.Board;
import com.chess.core.game.Side;
import com.chess.core.move.Move;

import java.util.HashSet;

import static com.chess.core.service.Converter.getColumnNumber;
import static com.chess.core.service.Converter.isValidPosition;
import static com.chess.core.move.Move.createMove;

public class Knight extends Piece {
    public Knight(Board board, int piecePosition, Side side) {
        super(board, piecePosition, side);
    }

    private final int[] OFFSETS = {-17, -15, -10, -6, 6, 10, 15, 17};

    @Override
    public void calculateLegalMoves() {
        HashSet<Move> legalMovesCache = new HashSet<>(10);
        for (int offset : OFFSETS) {
            int destination = getPiecePosition() + offset;
            if (isValidPosition(destination) && isValidColumn(getPiecePosition(), destination)) {
                Move move;
                if (getBoard().getTile(destination).isTileOccupied()) {
                    Piece piece = getBoard().getTile(destination).getPiece();
                    if (!piece.getPieceAlliance().equals(this.getPieceAlliance())) {
                        move = createMove(getBoard(), this, destination, piece);

                        getBoard().changeAllianceOnTile(destination, getPieceAlliance());
                        legalMovesCache.add(move);
                    }
                }
                else if (isValidColumn(getPiecePosition(), destination)) {
                    move = createMove(getBoard(), this, destination, null);

                    getBoard().changeAllianceOnTile(destination, getPieceAlliance());
                    legalMovesCache.add(move);
                }
            }
            // Переопределение ссылки
        } this.legalMoves = legalMovesCache;
    }

    // Difference between columns less than 2
    public boolean isValidColumn (final int currentCoordinate, final int candidateDestinationCoordinate) {
        int currentColumn = getColumnNumber(currentCoordinate);
        int candidateDestinationColumn = getColumnNumber(candidateDestinationCoordinate);
        return Math.abs(candidateDestinationColumn - currentColumn) <= 2;
    }
}